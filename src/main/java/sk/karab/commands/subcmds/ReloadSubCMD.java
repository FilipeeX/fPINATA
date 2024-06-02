package sk.karab.commands.subcmds;

import org.bukkit.command.CommandSender;
import sk.karab.commands.ISubCommand;
import sk.karab.configuration.YmlConfig;
import sk.karab.messaging.Language;
import sk.karab.messaging.Message;
import sk.karab.messaging.Messaging;

import java.util.ArrayList;

public class ReloadSubCMD implements ISubCommand {


    @Override
    public String getSubArgument() {
        return "reload";
    }


    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("fpinata.reload")) {
            Messaging.send(Message.NO_PERMISSION, sender);
            return;
        }

        if (args.length != 1) {
            Messaging.send(Message.HELP, sender);
            return;
        }

        YmlConfig.getLoadedConfigs().forEach(YmlConfig::reload);
        Language.detectLanguage();
        Messaging.send(Message.RELOAD_SUCCESS, sender);
    }


    @Override
    public ArrayList<String> complete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }


}
