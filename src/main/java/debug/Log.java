package debug;

import messaging.Prefix;
import org.bukkit.Bukkit;

public class Log {


    public static void info(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + " §f" + Chat.color(message));
            return;
        }

        Bukkit.getLogger().info(Chat.color(message));
    }


    public static void warn(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + " §e" + Chat.color(message));
            return;
        }

        Bukkit.getLogger().warning(Chat.color(message));
    }


    public static void err(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + " §c" + Chat.color(message));
            return;
        }

        Bukkit.getLogger().severe(Chat.color(message));
    }


}
