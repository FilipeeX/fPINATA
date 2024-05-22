package sk.karab.util.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUtil {


    public static boolean isNotPlayer(CommandSender sender) {
        return !(sender instanceof Player);
    }


    public static Player asPlayer(CommandSender sender) {

        if (isNotPlayer(sender)) return null;
        return (Player) sender;
    }


}
