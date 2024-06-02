package sk.karab.util;

import sk.karab.messaging.Replacement;

import java.util.ArrayList;
import java.util.Arrays;

public class ListUtil {


    public static ListUtil instance;


    public ListUtil() {
        if (instance != null) return;
        instance = this;
    }


    private ArrayList<Dependency> _toArrayList(Dependency ... dependencies) {
        return new ArrayList<>(Arrays.asList(dependencies));
    }

    public static ArrayList<Dependency> toArrayList(Dependency ... dependencies) {
        return instance._toArrayList(dependencies);
    }


    private ArrayList<String> _toArrayList(String ... strings) {
        return new ArrayList<>(Arrays.asList(strings));
    }

    public static ArrayList<String> toArrayList(String ... strings) {
        return instance._toArrayList(strings);
    }


    private ArrayList<Replacement> _toArrayList(Replacement ... replacements) {
        return new ArrayList<>(Arrays.asList(replacements));
    }

    public static ArrayList<Replacement> toArrayList(Replacement ... replacements) {
        return instance._toArrayList(replacements);
    }


}
