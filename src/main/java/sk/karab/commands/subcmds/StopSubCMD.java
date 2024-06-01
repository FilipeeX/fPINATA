package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;
import sk.karab.pinata.Pinata;

import java.util.ArrayList;

public class StopSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "stop";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.stop")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 1) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        Pinata.getPinatas().forEach((Pinata::despawn));
        Messaging.send(Message.STOP_SUCCESS, sender);
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }


}
