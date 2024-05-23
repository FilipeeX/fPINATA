package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.commands.ISubCommand;
import sk.karab.pinata.Pinata;

import java.util.ArrayList;

public class DebugSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "debug";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        Pinata.getPinatas().forEach((pinata -> sender.sendMessage(pinata.toString())));
    }


    @Override
    public ArrayList<String> complete(Player player, String[] args) {
        return null;
    }


}