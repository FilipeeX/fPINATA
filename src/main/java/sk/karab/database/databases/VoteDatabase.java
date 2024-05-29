package sk.karab.database.databases;

import sk.karab.database.Database;
import sk.karab.database.ISQLTask;
import sk.karab.database.SafeSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteDatabase {


    public static VoteDatabase instance;


    private final String TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS `votes` (
            	`amount` INT NOT NULL
            );
            """;
    private final String ENTRY_QUERY = "INSERT INTO `votes` VALUES (0);";
    private final String ENTRY_EXISTS_QUERY = "SELECT COUNT(*) FROM `votes`;";
    private final String GET_VOTES_QUERY = "SELECT * FROM `votes`;";
    private final String SET_VOTES_QUERY = "UPDATE `votes` SET `amount` = ?;";


    public VoteDatabase() {

        if (instance != null) return;
        instance = this;

        createTable();
    }


    private void createTable() {

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            statement.executeUpdate(TABLE_QUERY);
            statement.close();

        };
        SafeSQL.run(task);

        if (!entryExists())
            createEntry();
    }


    private boolean entryExists() {
        AtomicBoolean result = new AtomicBoolean(false);

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            ResultSet results = statement.executeQuery(ENTRY_EXISTS_QUERY);

            int recordCount = results.getInt(1);
            result.set(recordCount > 0);

            statement.close();
            results.close();
        };

        SafeSQL.run(task);
        return result.get();
    }


    private void createEntry() {

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            statement.executeUpdate(ENTRY_QUERY);
            statement.close();

        };

        SafeSQL.run(task);
    }


    private int _getVotes() {
        AtomicInteger result = new AtomicInteger();

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            ResultSet results = statement.executeQuery(GET_VOTES_QUERY);

            result.set(results.getInt(1));

            statement.close();
            results.close();
        };

        SafeSQL.run(task);
        return result.get();
    }

    public static int getVotes() {
        return instance._getVotes();
    }


    private boolean _setVotes(int newAmount) {

        ISQLTask task = () -> {

            PreparedStatement statement = Database.getConn().prepareStatement(SET_VOTES_QUERY);
            statement.setInt(1, newAmount);

            statement.executeUpdate();
            statement.close();
        };

        return SafeSQL.run(task);
    }

    public static boolean setVotes(int newAmount) {
        return instance._setVotes(newAmount);
    }


    private boolean _addVotes(int amount) {

        int currentAmount = _getVotes();
        return _setVotes(currentAmount + amount);
    }

    public static boolean addVotes(int amount) {
        return instance._addVotes(amount);
    }


    private boolean _removeVotes(int amount) {

        int currentAmount = _getVotes();
        int newAmount = currentAmount - amount;

        if (newAmount < 0) return false;

        return _setVotes(currentAmount - amount);
    }

    public static boolean removeVotes(int amount) {
        return instance._removeVotes(amount);
    }

    private boolean _resetVotes() {
        return _setVotes(0);
    }

    public static boolean resetVotes() {
        return instance._resetVotes();
    }


}
