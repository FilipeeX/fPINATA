package sk.karab;

import messaging.Prefix;
import org.bukkit.plugin.java.JavaPlugin;

public class FPinata extends JavaPlugin {


    public static FPinata instance;


    @Override
    public void onLoad() {
        setInstance();
        setupConfigs();
    }


    @Override
    public void onEnable() {
        setupPrefix();
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


}