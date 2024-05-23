package sk.karab.database.locations;

import sk.karab.database.Database;
import sk.karab.database.ISQLTask;
import sk.karab.database.SafeSQL;
import sk.karab.util.debug.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocationDatabase {


    public static LocationDatabase instance;

    private final String TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS `locations` (
                `identifier` VARCHAR(32) NOT NULL,
                `world` VARCHAR(32) NOT NULL,
                `x` DOUBLE NOT NULL,
                `y` DOUBLE NOT NULL,
                `z` DOUBLE NOT NULL,
                `pitch` FLOAT NOT NULL,
                `yaw` FLOAT NOT NULL,
                PRIMARY KEY (`identifier`)
              );
            """;
    private final String ADD_LOC_QUERY = """
            INSERT INTO `locations` VALUES (
            ?,
            ?,
            ?,
            ?,
            ?,
            ?,
            ?);
            """;
    private final String CHECK_LOCATION_EXISTENCE_QUERY = """
            SELECT COUNT(*) AS amount FROM `locations` WHERE `identifier` = "%identifier";
            """;


    public LocationDatabase() {

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

    }


    private boolean _addLocation(PinataLocation pinataLocation) {

        assert pinataLocation.location().getWorld() != null;
        if (_locationExists(pinataLocation.identifier())) return false;

        ISQLTask task = () -> {

            PreparedStatement statement = Database.getConn().prepareStatement(ADD_LOC_QUERY);

            statement.setString(1, pinataLocation.identifier());
            statement.setString(2, pinataLocation.location().getWorld().getName());
            statement.setDouble(3, pinataLocation.location().getX());
            statement.setDouble(4, pinataLocation.location().getY());
            statement.setDouble(5, pinataLocation.location().getZ());
            statement.setFloat(6, pinataLocation.location().getPitch());
            statement.setFloat(7, pinataLocation.location().getYaw());

            statement.executeUpdate();
            statement.close();

        };

        return SafeSQL.run(task);
    }

    public static boolean addLocation(PinataLocation pinataLocation) {
        return instance._addLocation(pinataLocation);
    }


    private boolean _locationExists(String identifier) {
        AtomicBoolean result = new AtomicBoolean(false);

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            ResultSet results = statement.executeQuery(CHECK_LOCATION_EXISTENCE_QUERY
                    .replace("%identifier", identifier));

            int recordAmount = results.getInt("amount");
            result.set(recordAmount != 0);

            statement.close();
            results.close();
        };
        SafeSQL.run(task);

        return result.get();
    }


    public static boolean locationExists(String identifier) {
        return instance._locationExists(identifier);
    }


}
