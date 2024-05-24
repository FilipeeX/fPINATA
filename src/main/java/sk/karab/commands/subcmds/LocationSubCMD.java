package sk.karab.commands.subcmds;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import sk.karab.commands.ISubCommand;
import sk.karab.database.locations.LocationDatabase;
import sk.karab.database.locations.PinataLocation;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;
import sk.karab.messaging.Replacement;
import sk.karab.util.NumberUtil;
import sk.karab.util.PlayerUtil;

import java.util.ArrayList;

public class LocationSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "location";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.location")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length < 2) {
            Messaging.send(Message.HELP, sender);
        }
        String subArgument = args[1];

        switch (subArgument) {
            case "add":
                add(sender, args);
                break;
            case "remove":
                remove(sender, args);
                break;
            case "tp":
                tp(sender, args);
                break;
            case "list":
                list(sender, args);
                break;
            default:
                Messaging.send(Message.HELP, sender);
                break;
        }

    }


    @Override
    public ArrayList<String> complete(Player player, String[] args) {
        return null;
    }


    private void add(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.location.add")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (PlayerUtil.isNotPlayer(sender)) {
            Messaging.send(Message.PLAYER_ONLY, sender);
            return;
        }
        Player player = PlayerUtil.asPlayer(sender);
        assert player != null;

        if (args.length != 3) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String locationIdentifier = args[2];
        if (!isIdentifierValid(locationIdentifier)) {
            Messaging.send(Message.LOCATION_NAME_TOO_LONG, player,
                    new Replacement("%identifier", locationIdentifier),
                    new Replacement("%length", locationIdentifier.length() + "")
            );
            return;
        }

        Location location = player.getLocation();
        if (LocationDatabase.addLocation(new PinataLocation(
                locationIdentifier,
                location
        ))) Messaging.send(Message.LOCATION_ADD_SUCCESS, player,
                new Replacement("%identifier", locationIdentifier),
                new Replacement("%x", NumberUtil.round(location.getX(), 2) + ""),
                new Replacement("%y", NumberUtil.round(location.getY(), 2) + ""),
                new Replacement("%z", NumberUtil.round(location.getZ(), 2) + "")
        );
        else Messaging.send(Message.LOCATION_ADD_FAILURE, player,
                new Replacement("%identifier", locationIdentifier));
    }


    private void remove(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.location.remove")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 3) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String locationIdentifier = args[2];

        if (LocationDatabase.removeLocation(locationIdentifier))
            Messaging.send(Message.LOCATION_REMOVE_SUCCESS, sender,
                new Replacement("%identifier", locationIdentifier)
        );
        else Messaging.send(Message.LOCATION_REMOVE_FAILURE, sender,
                new Replacement("%identifier", locationIdentifier));
    }


    private void tp(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.location.tp")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (PlayerUtil.isNotPlayer(sender)) {
            Messaging.send(Message.PLAYER_ONLY, sender);
            return;
        }
        Player player = PlayerUtil.asPlayer(sender);
        assert player != null;

        if (args.length != 3) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String locationIdentifier = args[2];
        PinataLocation pinataLocation = LocationDatabase.getLocation(locationIdentifier);

        if (pinataLocation != null) {
            player.teleport(pinataLocation.location(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            Messaging.send(Message.LOCATION_TP_SUCCESS, player,
                    new Replacement("%identifier", locationIdentifier)
            );
        }
        else Messaging.send(Message.LOCATION_TP_FAILURE, player,
                new Replacement("%identifier", locationIdentifier));
    }


    private void list(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.location.list")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 2) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        ArrayList<PinataLocation> locations = LocationDatabase.getLocations();
        if (locations != null) {

            Messaging.send(Message.LOCATION_LIST_HEADER, sender);

            for (PinataLocation pinataLocation : locations) {

                Location location = pinataLocation.location();
                assert location.getWorld() != null;

                Messaging.send(Message.LOCATION_LIST_ENTRY, sender,
                        new Replacement("%identifier", pinataLocation.identifier()),
                        new Replacement("%world", location.getWorld().getName()),
                        new Replacement("%x", NumberUtil.round(location.getX(), 5) + ""),
                        new Replacement("%y", NumberUtil.round(location.getY(), 5) + ""),
                        new Replacement("%z", NumberUtil.round(location.getZ(), 5) + "")
                );
            }
            if (locations.isEmpty()) Messaging.send(Message.LOCATION_LIST_NOTHING_TO_SHOW, sender);

            Messaging.send(Message.LOCATION_LIST_FOOTER, sender);
        }
        else Messaging.send(Message.LOCATION_LIST_FAILURE, sender);
    }


    private boolean isIdentifierValid(String identifier) {
        return !identifier.isBlank() && identifier.length() < 33;
    }


}
