package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;

import java.util.ArrayList;

public class VoteSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "votes";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return null;
    }


}
