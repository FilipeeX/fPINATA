package sk.karab;

import org.bukkit.plugin.java.JavaPlugin;
import sk.karab.configuration.ConfigId;
import sk.karab.configuration.YmlConfig;
import sk.karab.debug.Log;
import sk.karab.messaging.Prefix;

import java.io.File;

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
        // instances
    }


}