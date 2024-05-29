package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sk.karab.commands.ISubCommand;
import sk.karab.database.locations.LocationDatabase;
import sk.karab.database.locations.PinataLocation;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;
import sk.karab.messaging.Replacement;
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

        switch (args.length) {
            case 1:
                spawnPinataAtPlayer(sender);
                break;
            case 2:
                spawnPinataAtLocation(sender, args);
                break;
            default:
                Messaging.send(Message.HELP, sender);
                break;
        }
    }


    private void spawnPinataAtPlayer(CommandSender sender) {

        if (PlayerUtil.isNotPlayer(sender)) {
            Messaging.send(Message.PLAYER_ONLY, sender);
            return;
        }
        Player player = PlayerUtil.asPlayer(sender);
        assert player != null;

        new Pinata(player.getLocation());

        Messaging.send(Message.SPAWN_PLAYER_SUCCESS, player);
    }


    private void spawnPinataAtLocation(CommandSender sender, String[] args) {

        String locationIdentifier = args[1];
        PinataLocation pinataLocation = LocationDatabase.getLocation(locationIdentifier);

        if (pinataLocation == null) {
            Messaging.send(Message.INVALID_LOCATION, sender,
                    new Replacement("%identifier", locationIdentifier));
            return;
        }

        new Pinata(pinataLocation.location());
        Messaging.send(Message.SPAWN_LOCATION_SUCCESS, sender,
                new Replacement("%identifier", locationIdentifier));
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();

        if (args.length == 2)
            LocationDatabase.getLocations().forEach((location) -> suggestions.add(location.identifier()));

        return suggestions;
    }


}
