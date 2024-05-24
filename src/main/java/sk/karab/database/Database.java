package sk.karab.database;

import sk.karab.util.Log;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {


    private static Database database;

    private final String fileName;
    private final File parentFolder;
    private Connection connection;


    public Database(String fileName, File parentFolder) {

        this.fileName = fileName;
        this.parentFolder = parentFolder;

        if (database != null) return;
        database = this;

        File file = new File(parentFolder, fileName);
        init(file);
    }


    private void init(File file) {
        try {

            if (!file.exists())
                file.createNewFile();

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);

        } catch (IOException exception) {
            exception.printStackTrace();
            Log.err("Couldn't create the database file %file in %folder."
                    .replace("%file", fileName)
                    .replace("%folder", parentFolder.getAbsolutePath()));
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            Log.err("The JDBC driver for SQLite is not compiled in the JAR file of this plugin, your build does not contain the necessary classes.");
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.err("A SQLException occurred while initialling the database.");
        }
    }


    public String getFileName() {
        return fileName;
    }

    public File getParentFolder() {
        return parentFolder;
    }


    public Connection getConnection() {
        return connection;
    }


    public static Database getDatabase() {
        return database;
    }

    public static Connection getConn() {
        return getDatabase().getConnection();
    }


}
