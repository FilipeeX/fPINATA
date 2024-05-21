package sk.karab.messaging;


import sk.karab.configuration.ConfigId;
import sk.karab.configuration.YmlConfig;

public class Prefix {


    private static Prefix instance;
    private String prefix;


    public Prefix() {
        prefix = Chat.color(YmlConfig.find(ConfigId.CONFIG).getString("prefix"));
        instance = this;
    }


    public static String get() {
        return instance.prefix;
    }


    public static void set(String prefix) {
        instance.prefix = Chat.color(prefix);
    }


    public static boolean exists() {
        return instance.prefix != null && !instance.prefix.isBlank();
    }


}
