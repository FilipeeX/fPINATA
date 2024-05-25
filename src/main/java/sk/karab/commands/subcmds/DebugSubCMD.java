package sk.karab.commands.subcmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;

import java.util.ArrayList;

public class DebugSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "debug";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        Bukkit.broadcast("bob", "");
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }


}
