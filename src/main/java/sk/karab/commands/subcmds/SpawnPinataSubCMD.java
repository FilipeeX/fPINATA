package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.commands.ISubCommand;
import sk.karab.pinata.Pinata;
import sk.karab.util.PlayerUtil;

import java.util.ArrayList;

public class SpawnPinataSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "spawn";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (PlayerUtil.isNotPlayer(sender)) {
            // todo implement locations and allow console to spawn pinata at a location
            return;
        }

        Player player = PlayerUtil.asPlayer(sender);
        assert player != null;

        new Pinata(player.getLocation());

        // todo message
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return null;
    }


}
