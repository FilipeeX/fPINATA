package sk.karab;

import messaging.Prefix;
import org.bukkit.plugin.java.JavaPlugin;

public class FPinata extends JavaPlugin {


    @Override
    public void onLoad() {
        setupConfigs();
    }


    @Override
    public void onEnable() {
        setupPrefix();
    }


    private void setupConfigs() {

    }


    private void setupPrefix() {
        new Prefix();
    }


}