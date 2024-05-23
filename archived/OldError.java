package sk.karab.database;

import sk.karab.FPinata;

import java.util.logging.Level;

public class OldError {
    public static void execute(FPinata plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(FPinata plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
