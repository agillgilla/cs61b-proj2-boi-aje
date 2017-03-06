package db.query_handler;

/**
 * Created by Arjun on 3/3/2017.
 */
public class StringParse {
    public static boolean isInteger(String integerString){
        try {
            Integer.parseInt(integerString);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public static boolean isFloat(String integerFloat){
        try {
            Float.parseFloat(integerFloat);
            return integerFloat.contains(".");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
