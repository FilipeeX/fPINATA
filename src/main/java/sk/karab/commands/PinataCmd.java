package sk.karab.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import sk.karab.commands.subcmds.HelpSubCMD;

import java.util.List;

public class PinataCmd implements TabExecutor {


    SubCommand helpSubCMD = new HelpSubCMD();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        int argAmount = args.length;

        if (argAmount != 1) {
            helpSubCMD.execute(sender);
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return List.of();
    }


}
