package sk.karab.util;

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


}
