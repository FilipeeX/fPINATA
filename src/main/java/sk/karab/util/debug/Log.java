package sk.karab.util.debug;

import org.bukkit.Bukkit;
import sk.karab.messaging.Chat;
import sk.karab.messaging.Prefix;

public class Log {


    public static void info(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + Chat.color("&f " + message));
            return;
        }

        Bukkit.getLogger().info(Chat.color(message));
    }


    public static void warn(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + Chat.color("&e " + message));
            return;
        }

        Bukkit.getLogger().warning(Chat.color(message));
    }


    public static void err(String message) {

        if (Prefix.exists()) {
            Bukkit.getConsoleSender().sendMessage(Prefix.get() + Chat.color("&c " + message));
            return;
        }

        Bukkit.getLogger().severe(Chat.color(message));
    }


}
