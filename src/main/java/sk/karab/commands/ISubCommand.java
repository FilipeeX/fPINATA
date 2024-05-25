package sk.karab.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface ISubCommand {


    String getSubArgument();
    void execute(CommandSender sender, String[] args);
    ArrayList<String> complete(CommandSender sender, String[] args);


}
