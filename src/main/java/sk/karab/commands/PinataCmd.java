package sk.karab.commands;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import sk.karab.commands.subcmds.*;
import sk.karab.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PinataCmd implements TabExecutor {


    private final ArrayList<ISubCommand> subCommands;
    private final Random random;


    public PinataCmd() {
        subCommands = new ArrayList<>();

        subCommands.add(new HelpSubCMD());
        subCommands.add(new SpawnPinataSubCMD());
        subCommands.add(new DebugSubCMD());
        subCommands.add(new LocationSubCMD());
        subCommands.add(new VoteSubCMD());

        random = new Random();
    }


    private ISubCommand getSubCommand(String arg) {

        for (ISubCommand subCommand : subCommands)
            if (subCommand.getSubArgument().equalsIgnoreCase(arg))
                return subCommand;

        return null;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        ISubCommand subCommand;

        if (args.length < 1) subCommand = getSubCommand("help");
        else subCommand = getSubCommand(args[0]);

        if (subCommand == null) subCommand = getSubCommand("help");
        assert subCommand != null;

        subCommand.execute(sender, args);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> suggestions;

        if (args.length < 1) suggestions = getAllSubCommandArgs();
        else {
            ISubCommand subCommand = getSubCommand(args[0]);
            if (subCommand == null) suggestions = getAllSubCommandArgs();
            else suggestions = subCommand.complete(sender, args);
        }

        ArrayList<String> approvedSuggestions = new ArrayList<>();
        suggestions.forEach((suggestion) -> {
            if (suggestion.startsWith(args[args.length - 1])) approvedSuggestions.add(suggestion);
        });

        if (!PlayerUtil.isNotPlayer(sender)) {

            Player player = PlayerUtil.asPlayer(sender);
            assert player != null;

            player.playSound(player, Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, random.nextFloat(0.85f, 1.25f), 0.5f);
        }

        approvedSuggestions.sort(String::compareToIgnoreCase);
        return approvedSuggestions;
    }


    private ArrayList<String> getAllSubCommandArgs() {
        ArrayList<String> result = new ArrayList<>();
        subCommands.forEach((subCommand) -> result.add(subCommand.getSubArgument()));
        return result;
    }


}
