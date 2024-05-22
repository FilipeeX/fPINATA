package sk.karab.messaging;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.configuration.YmlConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Messaging {


    public static void send(Message id, CommandSender sender, ArrayList<Replacement> replacements) {

        List<String> message = YmlConfig.find(Language.getLanguageConfigId()).getStringList(id.name().toLowerCase());

        replacements.add(new Replacement("%prefix", Prefix.get()));

        for (String line : message) {

            for (Replacement replacement : replacements)
                line = line.replace(replacement.placeholder(), replacement.replacement());

            sender.sendMessage(Chat.color(line));
        }
    }


    public static void send(Message id, CommandSender sender, Replacement ... replacements) {
        send(id, sender, new ArrayList<>(Arrays.asList(replacements)));
    }


    public static void send(Message id, Player player, ArrayList<Replacement> replacements) {

        replacements.add(new Replacement("%player", player.getDisplayName()));
        send(id, (CommandSender) player, replacements);
    }


    public static void send(Message id, Player player, Replacement ... replacements) {
        send(id, player, new ArrayList<>(Arrays.asList(replacements)));
    }


}
