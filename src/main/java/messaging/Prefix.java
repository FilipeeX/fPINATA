package messaging;

public class Prefix {


    private static Prefix instance;
    private String prefix;


    public Prefix() {
        instance = this;
    }


    public static String get() {
        return instance.prefix;
    }


    public static void set(String prefix) {
        instance.prefix = prefix;
    }


}
