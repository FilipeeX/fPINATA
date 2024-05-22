package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;

public class HelpISubCMD implements ISubCommand {


    @Override
    public void execute(CommandSender sender) {
        Messaging.send(Message.HELP, sender);
    }


}
