package sk.karab;

import org.bukkit.plugin.java.JavaPlugin;
import sk.karab.commands.PinataCmd;
import sk.karab.configuration.ConfigId;
import sk.karab.configuration.YmlConfig;
import sk.karab.messaging.Language;
import sk.karab.messaging.Prefix;
import sk.karab.util.debug.Log;

import java.io.File;
import java.util.Objects;

public class FPinata extends JavaPlugin {


    public static FPinata instance;


    @Override
    public void onLoad() {

        setInstance();
        setupConfigs();
        setupPrefix();

        Log.info("Successfully loaded fPINATA");
    }


    @Override
    public void onEnable() {
        Log.info("Enabling fPINATA");

        createInstances();
        registerCommands();
        registerListeners();

        Log.info("Successfully enabled fPINATA");
    }


    private void setInstance() {
        instance = this;
    }

    private void setupConfigs() {

        new YmlConfig(ConfigId.CONFIG, new File(getDataFolder(), "config.yml"));

        File langFolder = new File(getDataFolder(), "languages/");
        new YmlConfig(ConfigId.LANG_EN, new File(langFolder, "lang_EN.yml"));
        new YmlConfig(ConfigId.LANG_SK, new File(langFolder, "lang_SK.yml"));
    }


    private void setupPrefix() {
        new Prefix();
    }


    private void createInstances() {
        new Language();
    }


    private void registerCommands() {
        Objects.requireNonNull(getCommand("pinata")).setExecutor(new PinataCmd());
    }


    private void registerListeners() {
        // listeners go here
    }


}