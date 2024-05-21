package sk.karab;

import org.bukkit.plugin.java.JavaPlugin;
import sk.karab.debug.Log;
import sk.karab.messaging.Prefix;

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
        // todo create a config sys
    }


    private void setupPrefix() {
        new Prefix();
    }


    private void createInstances() {
        // instances
    }


}