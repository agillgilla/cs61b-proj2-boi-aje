package table;

import java.util.Arrays;

/**
 * Created by Arjun on 2/26/2017.
 */
public class StringType extends Type {

    private static final String[] VALID_TYPES = new String[] {"StringType", "NanType"};
    public StringType(String value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("NanType")) {
                return other.add(this);
            } else {
                return new StringType(this.getValue().substring(0, this.getValue().length() - 1) + ((String) other.getValue()).substring(1));
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        throw new RuntimeException("ERROR: Cannot use - operator on String");
    }

    public Type multiply(Type other) {
        throw new RuntimeException("ERROR: Cannot use * operator on String");
    }

    public Type divide(Type other) {
        throw new RuntimeException("ERROR: Cannot use / operator on String");
    }

    public boolean equals(Type other) {
        if (!this.getClass().getSimpleName().equals(other.getClass().getSimpleName())) {
            return false;
        } else {
            return (this.getValue().equals((String) other.getValue()));
        }
    }

    public boolean lessThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("NanType")) {
                return other.greaterThan(this);
            } else {
                return ((String) this.getValue()).compareTo((String) other.getValue()) < 0;
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use < operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean greaterThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("NanType")) {
                return other.lessThan(this);
            } else {
                return ((String) this.getValue()).compareTo((String) other.getValue()) > 0;
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use > operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public String getValue() {
        return (String) super.getValue();
    }

    public String getValueActual() {
        return this.getValue();
    }
}
