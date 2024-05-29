package sk.karab.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class NumberUtil {


    public static NumberUtil instance;
    private final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");


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


    private boolean _isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        return NUMBER_PATTERN.matcher(strNum).matches();
    }

    public static boolean isNumber(String strNum) {
        return instance._isNumber(strNum);
    }


    private int _toInt(String strNum) {

        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            Log.err("Couldn't convert a string to an integer, this error is embedded in the code, and is not a result of configuration, open an issue on github.");
        }

        return -2147483648;
    }

    /**
     * @return -2147483648 when an error occurs
     */
    public static int toInt(String strNum) {
        return instance._toInt(strNum);
    }


}
