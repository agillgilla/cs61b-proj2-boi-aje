package table;

import java.util.Arrays;

/**
 * Created by Arjun on 2/26/2017.
 */
public class IntType extends Type {

    private static final String[] VALID_TYPES = new String[] {"IntType", "FloatType", "NanType"};
    public IntType(Integer value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValue() + (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.add(this);
            } else {
                return new IntType(this.getValue() + (Integer) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValue() - (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.subtract(this);
            } else {
                return new IntType(this.getValue() - (Integer) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use - operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type multiply(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValue() * (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.multiply(this);
            } else {
                return new IntType(this.getValue() * (Integer) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use * operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type divide(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValue() / (Float) other.getValue()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.divide(this);
            } else {
                return new IntType(this.getValue() / (Integer) other.getValue());
            }
        } else {
            throw new RuntimeException("Cannot use / operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean equals(Type other) {
        if (!other.getClass().getSimpleName().equals("FloatType") && !other.getClass().getSimpleName().equals("IntType")) {
            return false;
        } else {
            return (this.getValue().equals((Integer) other.getValue()));
        }
    }

    public boolean lessThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return this.getValue() < (Float) other.getValue();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.greaterThan(this);
            } else {
                return this.getValue() < (Integer) other.getValue();
            }
        } else {
            throw new RuntimeException("Cannot use < operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean greaterThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return this.getValue() > (Float) other.getValue();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.lessThan(this);
            } else {
                return this.getValue() > (Integer) other.getValue();
            }
        } else {
            throw new RuntimeException("Cannot use > operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Integer getValue() {
        return (Integer) super.getValue();
    }

}
