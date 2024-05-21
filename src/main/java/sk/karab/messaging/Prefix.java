package sk.karab.messaging;


public class Prefix {


    private static Prefix instance;
    private String prefix;


    public Prefix() {

        // todo setup prefix from config, make it colorful

        instance = this;
    }


    public static String get() {
        return instance.prefix;
    }


    public static void set(String prefix) {
        instance.prefix = Chat.color(prefix);
    }


    public static boolean exists() {
        return instance.prefix == null || instance.prefix.isBlank();
    }


}
