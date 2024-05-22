package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.pinata.Pinata;

public class DebugSubCMD implements ISubCommand {


    @Override
    public void execute(CommandSender sender) {
        Pinata.getPinatas().forEach((pinata -> sender.sendMessage(pinata.toString())));
    }


}
