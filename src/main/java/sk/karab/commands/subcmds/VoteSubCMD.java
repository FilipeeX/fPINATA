package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.database.databases.VoteDatabase;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;
import sk.karab.messaging.Replacement;
import sk.karab.util.NumberUtil;

import java.util.ArrayList;

public class VoteSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "votes";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.votes")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length < 2) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String arg = args[1];

        switch (arg) {
            case "add":
                add(sender, args);
                break;
            case "remove":
                remove(sender, args);
                break;
            case "reset":
                reset(sender, args);
                break;
            default:
                Messaging.send(Message.HELP, sender);
        }

    }


    private void add(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.votes.add")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 3) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String arg = args[2];
        if (!NumberUtil.isNumber(arg)) {
            Messaging.send(Message.INVALID_NUMBER, sender,
                    new Replacement("%string", arg));
            return;
        }

        int voteAmount = NumberUtil.toInt(arg);
        if (voteAmount < 1) {
            Messaging.send(Message.ONLY_POSITIVE_NUMBERS, sender);
            return;
        }

        if (!VoteDatabase.addVotes(voteAmount)) Messaging.send(Message.VOTES_ADD_FAILURE, sender);
        // todo implement -s and make a default add vote message
    }


    private void remove(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.votes.remove")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 3) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        String arg = args[2];
        if (!NumberUtil.isNumber(arg)) {
            Messaging.send(Message.INVALID_NUMBER, sender,
                    new Replacement("%string", arg));
            return;
        }

        int voteAmount = NumberUtil.toInt(arg);
        if (voteAmount < 1) {
            Messaging.send(Message.ONLY_POSITIVE_NUMBERS, sender);
            return;
        }

        if (!VoteDatabase.removeVotes(voteAmount)) Messaging.send(Message.VOTES_REMOVE_FAILURE, sender);
        // todo implement -s and make a default remove vote message
    }


    private void reset(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.votes.reset")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 2) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        if (!VoteDatabase.resetVotes()) Messaging.send(Message.VOTES_RESET_FAILURE, sender);
        // todo implement -s and make a default reset vote message
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();

        switch (args.length) {
            case 2:
                suggestions.add("add");
                suggestions.add("remove");
                suggestions.add("reset");
                break;
            case 3:
                switch (args[1]) {
                    case "add", "remove":
                        suggestions.add("<amount>");
                        break;
                }
        }

        return suggestions;
    }


}
