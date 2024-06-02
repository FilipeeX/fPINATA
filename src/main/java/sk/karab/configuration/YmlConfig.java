package sk.karab.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import sk.karab.util.PluginFileTool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YmlConfig {


    private static final HashMap<ConfigId, YmlConfig> storedConfigs = new HashMap<>();


    public static YmlConfig find(ConfigId identifier) {
        return storedConfigs.get(identifier);
    }


    private final ConfigId identifier;
    private final File file;
    private YamlConfiguration config;


    public YmlConfig(ConfigId identifier, File file) {

        this.identifier = identifier;
        this.file = file;

        create();
        load();

        storedConfigs.put(identifier, this);
    }


    public void create() {

        File parentFolder = file.getParentFile();
        parentFolder.mkdirs();

        if (!file.exists()) {
            PluginFileTool.copyFileFromRes(parentFolder, file.getName());
        }
    }


    public void load() {
        config = YamlConfiguration.loadConfiguration(file);
    }


    public void unload() {
        config = null;
    }


    public void reload() {
        unload();
        create();
        load();
    }


    public ConfigId getId() {
        return identifier;
    }


    public Object get(String key) {
        return config.get(key);
    }


    public String getString(String key) {
        return config.getString(key);
    }


    public int getInt(String key) {
        return config.getInt(key);
    }


    public List<String> getStringList(String key) {
        return config.getStringList(key);
    }


    public static ArrayList<YmlConfig> getLoadedConfigs() {
        return new ArrayList<>(storedConfigs.values());
    }


}
