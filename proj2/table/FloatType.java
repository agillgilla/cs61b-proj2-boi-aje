package table;

import java.util.Arrays;

/**
 * Created by Arjun on 2/26/2017.
 */
public class FloatType extends Type {

    private static final String[] VALID_TYPES = new String[] {"IntType", "FloatType", "NanType"};
    public FloatType(Float value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return new FloatType((this.getValue() + (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.add(this);
            } else {
                return new FloatType(this.getValue() + (Float) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return new FloatType((this.getValue() - (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.subtract(this);
            } else {
                return new FloatType(this.getValue() - (Float) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use - operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type multiply(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return new FloatType((this.getValue() * (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.multiply(this);
            } else {
                return new FloatType(this.getValue() * (Float) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use * operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type divide(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return new FloatType((this.getValue() / (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.divide(this);
            } else {
                return new FloatType(this.getValue() / (Float) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use / operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean equals(Type other) {
        if (!other.getClass().getSimpleName().equals("FloatType") && !this.getClass().getSimpleName().equals("IntType")) {
            return false;
        } else {
            return (this.getValue().equals((Float) other.getValue()));
        }
    }

    public boolean lessThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return this.getValue() < (Float) other.getValue();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.greaterThan(this);
            } else {
                return this.getValue() < (Float) other.getValue();
            }
        } else {
            throw new RuntimeException("Cannot use < operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean greaterThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("IntType")) {
                return this.getValue() > (Float) other.getValue();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.lessThan(this);
            } else {
                return this.getValue() > (Float) other.getValue();
            }
        } else {
            throw new RuntimeException("Cannot use > operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Float getValue() {
        return Float.parseFloat(String.format("%.3f", this.getValueActual()));
    }

    public Float getValueActual() {
        return (Float) super.getValue();
    }
}
