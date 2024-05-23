package sk.karab.database;

import sk.karab.util.debug.Log;

import java.sql.SQLException;

public class SafeSQL {


    public static SafeSQL instance;


    public SafeSQL() {
        if (instance != null) return;
        instance = this;
    }


    private boolean _run(ISQLTask task) {
        try {
            task.perform();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            Log.err("An SQLException occurred while trying to safely run SQL code.");
        }
        return false;
    }


    public static SafeSQL getInstance() {
        return instance;
    }


    public static boolean run(ISQLTask task) {
        return instance._run(task);
    }


}
