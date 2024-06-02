package sk.karab.dependencies.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.karab.FPinata;
import sk.karab.database.databases.VoteDatabase;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;
import sk.karab.messaging.Replacement;
import sk.karab.pinata.Pinata;

public class PapiPinataExpansion extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "fpinata";
    }


    @Override
    public @NotNull String getAuthor() {
        return "Filip 'filipeex' Karab";
    }


    @Override
    public @NotNull String getVersion() {
        return FPinata.instance.getDescription().getVersion();
    }


    @Override
    public boolean persist() {
        return true;
    }


    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        switch (params) {

            case "votes_so_far":
                return Messaging.getSingleLine(Message.PLACEHOLDER_VOTES_SO_FAR, player,
                        new Replacement("%amount", String.valueOf(VoteDatabase.getVotes())));

            case "votes_needed":
                return Messaging.getSingleLine(Message.PLACEHOLDER_VOTES_NEEDED, player,
                        new Replacement("%amount", String.valueOf(32))); // todo read needed total from config

            case "votes_left":
                return Messaging.getSingleLine(Message.PLACEHOLDER_VOTES_LEFT, player,
                        new Replacement("%amount", String.valueOf(32 - VoteDatabase.getVotes())));

            case "pinata_exists":
                if (!Pinata.getPinatas().isEmpty())
                    return Messaging.getSingleLine(Message.PLACEHOLDER_PINATA_EXISTS, player);
                else
                    return Messaging.getSingleLine(Message.PLACEHOLDER_PINATA_DOESNT_EXIST, player);

            case "pinata_count":
                return Messaging.getSingleLine(Message.PLACEHOLDER_PINATA_COUNT, player,
                        new Replacement("%amount", String.valueOf(Pinata.getPinatas().size())));
        }
        return Messaging.getSingleLine(Message.PLACEHOLDER_UNKNOWN, player);
    }


}
