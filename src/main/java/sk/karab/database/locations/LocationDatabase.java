package sk.karab.database.locations;

import sk.karab.database.Database;
import sk.karab.database.ISQLTask;
import sk.karab.database.SafeSQL;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocationDatabase {


    public static LocationDatabase instance;

    private final String TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS `locations` (
            	`identifier` VARCHAR(32) NOT NULL,
            	`world` VARCHAR(32) NOT NULL,
            	`x` DOUBLE() NOT NULL,
            	`y` DOUBLE() NOT NULL,
            	`z` DOUBLE() NOT NULL,
            	`pitch` FLOAT() NOT NULL,
            	`yaw` FLOAT() NOT NULL,
            	PRIMARY KEY (`identifier`)
            );
            """;
    private final String ADD_LOC_QUERY = """
            INSERT INTO `locations` VALUES (
            "%identifier",
            "%world",
            %x,
            %y,
            %z,
            %pitch,
            %yaw);
            """;
    private final String CHECK_LOCATION_EXISTENCE_QUERY = """
            SELECT COUNT(*) AS amount FROM `locations` WHERE `identifier` = %identifier;
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

            Statement statement = Database.getConn().createStatement();
            statement.executeUpdate(ADD_LOC_QUERY
                    .replace("%identifier", pinataLocation.identifier())
                    .replace("%world", pinataLocation.location().getWorld().getName())
                    .replace("%x", pinataLocation.location().getX() + "")
                    .replace("%y", pinataLocation.location().getY() + "")
                    .replace("%z", pinataLocation.location().getZ() + "")
                    .replace("%pitch", pinataLocation.location().getPitch() + "")
                    .replace("%yaw", pinataLocation.location().getYaw() + ""));
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


    public boolean locationExists(String identifier) {
        return instance._locationExists(identifier);
    }


}
