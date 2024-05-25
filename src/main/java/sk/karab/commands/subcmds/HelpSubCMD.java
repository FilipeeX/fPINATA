package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;

import java.util.ArrayList;

public class HelpSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "help";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Messaging.send(Message.HELP, sender);
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return null;
    }


}
