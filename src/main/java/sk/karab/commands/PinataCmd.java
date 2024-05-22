package sk.karab.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import sk.karab.commands.subcmds.HelpISubCMD;
import sk.karab.commands.subcmds.SpawnPinataSubCMD;

import java.util.List;

public class PinataCmd implements TabExecutor {


    ISubCommand helpSubCMD = new HelpISubCMD();
    ISubCommand spawnPinataSubCMD = new SpawnPinataSubCMD();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        int argAmount = args.length;

        if (argAmount != 1) {
            helpSubCMD.execute(sender);
            return true;
        }

        String arg = args[0];

        if (arg.equalsIgnoreCase("help")) {
            helpSubCMD.execute(sender);
            return true;
        }

        if (arg.equalsIgnoreCase("spawn")) {
            spawnPinataSubCMD.execute(sender);
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return List.of();
    }


}
