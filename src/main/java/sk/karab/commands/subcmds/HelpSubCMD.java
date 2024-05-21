package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.SubCommand;
import sk.karab.messaging.MessageId;
import sk.karab.messaging.Messaging;

public class HelpSubCMD implements SubCommand {


    @Override
    public void execute(CommandSender sender) {
        Messaging.send(MessageId.HELP, sender);
    }


}
