package sk.karab.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {


    public static NumberUtil instance;


    public NumberUtil() {
        if (instance == null) instance = this;
    }


    private double _round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double round(double value, int places) {
        return instance._round(value, places);
    }


}
