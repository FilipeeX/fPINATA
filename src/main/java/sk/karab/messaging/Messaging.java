package sk.karab.messaging;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.configuration.YmlConfig;
import sk.karab.dependencies.DependencySys;
import sk.karab.util.ListUtil;
import sk.karab.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;

public class Messaging {


    public static void send(Message id, CommandSender sender, ArrayList<Replacement> replacements) {

        List<String> message = YmlConfig.find(Language.getLanguageConfigId()).getStringList(id.name().toLowerCase());

        replacements.add(new Replacement("%prefix", Prefix.get()));
        Player player = PlayerUtil.asPlayer(sender);

        for (String line : message) {

            for (Replacement replacement : replacements) {
                line = line.replace(replacement.placeholder(), replacement.replacement());

                if (DependencySys.isLoaded("PlaceholderAPI"))
                    line = PlaceholderAPI.setPlaceholders(player, line);
            }

            sender.sendMessage(Chat.color(line));
        }
    }


    public static void send(Message id, CommandSender sender, Replacement ... replacements) {
        send(id, sender, ListUtil.toArrayList(replacements));
    }


    public static void send(Message id, Player player, ArrayList<Replacement> replacements) {

        replacements.add(new Replacement("%player", player.getDisplayName()));
        send(id, (CommandSender) player, replacements);
    }


    public static void send(Message id, Player player, Replacement ... replacements) {
        send(id, player, ListUtil.toArrayList(replacements));
    }


    public static String getSingleLine(Message id, OfflinePlayer player, ArrayList<Replacement> replacements) {

        String message = YmlConfig.find(Language.getLanguageConfigId()).getString(id.name().toLowerCase());

        replacements.add(new Replacement("%prefix", Prefix.get()));
        for (Replacement replacement : replacements)
            message = message.replace(replacement.placeholder(), replacement.replacement());

        if (DependencySys.isLoaded("PlaceholderAPI"))
            message = PlaceholderAPI.setPlaceholders(player, message);

        return Chat.color(message);
    }


    public static String getSingleLine(Message id, OfflinePlayer player, Replacement ... replacements) {
        return getSingleLine(id, player, ListUtil.toArrayList(replacements));
    }


}
