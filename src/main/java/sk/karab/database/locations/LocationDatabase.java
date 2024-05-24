package sk.karab.database.locations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import sk.karab.database.Database;
import sk.karab.database.ISQLTask;
import sk.karab.database.SafeSQL;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
    private final String ADD_LOC_QUERY = "INSERT INTO `locations` VALUES (?,?,?,?,?,?,?);";
    private final String CHECK_LOC_EXISTENCE_QUERY = "SELECT COUNT(*) AS amount FROM `locations` WHERE `identifier` = ?;";
    private final String REMOVE_LOC_QUERY = "DELETE FROM `locations` WHERE `identifier` = ?;";
    private final String GET_LOC_QUERY = "SELECT 1 FROM `locations` WHERE `identifier` = ?;";
    private final String GET_LOCS_QUERY = "SELECT * FROM `locations`;";


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
        Location location = pinataLocation.location();

        if (_locationExists(pinataLocation.identifier())) return false;


        ISQLTask task = () -> {

            PreparedStatement statement = Database.getConn().prepareStatement(ADD_LOC_QUERY);

            statement.setString(1, pinataLocation.identifier());
            statement.setString(2, location.getWorld().getName());
            statement.setDouble(3, location.getX());
            statement.setDouble(4, location.getY());
            statement.setDouble(5, location.getZ());
            statement.setFloat(6, location.getPitch());
            statement.setFloat(7, location.getYaw());

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

            PreparedStatement statement = Database.getConn().prepareStatement(CHECK_LOC_EXISTENCE_QUERY);
            statement.setString(1, identifier);
            ResultSet results = statement.executeQuery();

            int recordAmount = results.getInt("amount");
            result.set(recordAmount != 0);

            statement.close();
            results.close();
        };
        if (!SafeSQL.run(task)) return true;

        return result.get();
    }

    public static boolean locationExists(String identifier) {
        return instance._locationExists(identifier);
    }


    private boolean _removeLocation(String identifier) {

        if (!_locationExists(identifier)) return false;

        ISQLTask task = () -> {

            PreparedStatement statement = Database.getConn().prepareStatement(REMOVE_LOC_QUERY);
            statement.setString(1, identifier);

            statement.executeUpdate();
            statement.close();
        };

        return SafeSQL.run(task);
    }

    public static boolean removeLocation(String identifier) {
        return instance._removeLocation(identifier);
    }


    @Nullable
    private PinataLocation _getLocation(String identifier) {

        if (!_locationExists(identifier)) return null;

        AtomicLocation location = new AtomicLocation(null);
        ISQLTask task = () -> {

            PreparedStatement statement = Database.getConn().prepareStatement(GET_LOC_QUERY);
            statement.setString(1, identifier);

            ResultSet results = statement.executeQuery();
            results.next();

            World world = Bukkit.getWorld(results.getString("world"));
            double x = results.getDouble("x");
            double y = results.getDouble("y");
            double z = results.getDouble("z");
            float pitch = results.getFloat("pitch");
            float yaw = results.getFloat("yaw");

            location.set(new Location(world, x, y, z, yaw, pitch));

            statement.close();
            results.close();
        };
        if (!SafeSQL.run(task)) return null;

        return new PinataLocation(identifier, location.get());
    }

    public static PinataLocation getLocation(String identifier) {
        return instance._getLocation(identifier);
    }


    private ArrayList<PinataLocation> _getLocations() {
        ArrayList<PinataLocation> locations = new ArrayList<>();

        ISQLTask task = () -> {

            Statement statement = Database.getConn().createStatement();
            ResultSet results = statement.executeQuery(GET_LOCS_QUERY);

            while (results.next()) {

                String identifier = results.getString("identifier");
                World world = Bukkit.getWorld(results.getString("world"));
                double x = results.getDouble("x");
                double y = results.getDouble("y");
                double z = results.getDouble("z");
                float pitch = results.getFloat("pitch");
                float yaw = results.getFloat("yaw");

                locations.add(new PinataLocation(
                        identifier,
                        new Location(world, x, y, z, yaw, pitch)
                ));
            }

            statement.close();
            results.close();
        };
        if (!SafeSQL.run(task)) return null;

        return locations;
    }


}
