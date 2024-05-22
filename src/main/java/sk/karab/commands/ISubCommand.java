package sk.karab.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface ISubCommand {


    String getSubArgument();
    void execute(CommandSender sender, String[] args);
    ArrayList<String> complete(Player player, String[] args);


}
