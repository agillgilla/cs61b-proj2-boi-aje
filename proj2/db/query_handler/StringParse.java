package db.query_handler;

import db.table.FloatType;
import db.table.IntType;
import db.table.StringType;
import db.table.Type;

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

    public static Type typeParse(String type) {
        if (type.substring(0, 1).equals("'") && type.substring(type.length() - 1).equals("'")) {
            return new StringType(type);
        } else if (StringParse.isFloat(type)) {
            return new FloatType(Float.parseFloat(type));
        } else if (StringParse.isInteger(type)) {
            return new IntType(Integer.parseInt(type));
        } else {
            throw new RuntimeException("ERROR: Unrecognized data type of: '" + type + "'");
        }
    }
}
