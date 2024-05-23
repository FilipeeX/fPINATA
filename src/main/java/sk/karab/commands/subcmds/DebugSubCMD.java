package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.commands.ISubCommand;
import sk.karab.database.locations.LocationDatabase;
import sk.karab.database.locations.PinataLocation;
import sk.karab.pinata.Pinata;

import java.util.ArrayList;

public class DebugSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "debug";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        player.sendMessage("---");
        player.sendMessage(LocationDatabase.locationExists(player.getDisplayName()) + "");
        player.sendMessage(LocationDatabase.addLocation(new PinataLocation(player.getDisplayName(), player.getLocation())) + "");
        player.sendMessage(LocationDatabase.locationExists(player.getDisplayName()) + "");
    }


    @Override
    public ArrayList<String> complete(Player player, String[] args) {
        return null;
    }


}
