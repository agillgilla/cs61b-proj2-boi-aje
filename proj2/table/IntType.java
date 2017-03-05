package table;

import java.util.Arrays;

/**
 * Created by Arjun on 2/26/2017.
 */
public class IntType extends Type {

    private static final String[] VALID_TYPES = new String[] {"IntType", "FloatType", "NanType", "NoValType"};
    public IntType(Integer value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValueActual() + (Float) other.getValueActual()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.add(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                if (other.getType().equals("int")) {
                    return new IntType(this.getValueActual() + (Integer) other.getValueActual());
                } else if (other.getType().equals("float")) {
                    return new FloatType(this.getValueActual() + (Float) other.getValueActual());
                } else {
                    throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
                }
            } else {
                return new IntType(this.getValueActual() + (Integer) other.getValueActual());
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValueActual() - (Float) other.getValueActual()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.subtract(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                if (other.getType().equals("int")) {
                    return new IntType(this.getValueActual() - (Integer) other.getValueActual());
                } else if (other.getType().equals("float")) {
                    return new FloatType(this.getValueActual() - (Float) other.getValueActual());
                } else {
                    throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
                }
            } else {
                return new IntType(this.getValueActual() - (Integer) other.getValueActual());
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use - operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type multiply(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return new FloatType((this.getValueActual() * (Float) other.getValueActual()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.multiply(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                if (other.getType().equals("int")) {
                    return new IntType(this.getValueActual() * (Integer) other.getValueActual());
                } else if (other.getType().equals("float")) {
                    return new FloatType(this.getValueActual() * (Float) other.getValueActual());
                } else {
                    throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
                }
            } else {
                return new IntType(this.getValueActual() * (Integer) other.getValueActual());
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use * operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type divide(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                if ((Float) other.getValueActual() == 0.0f) {
                    return new NanType("int");
                }
                return new FloatType((this.getValueActual() / (Float) other.getValueActual()));
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.divide(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                if (other.getType().equals("int")) {
                    if ((Integer) other.getValueActual() == 0) {
                        return new NanType("int");
                    }
                    return new IntType(this.getValueActual() / (Integer) other.getValueActual());
                } else if (other.getType().equals("float")) {
                    if ((Float) other.getValueActual() == 0.0f) {
                        return new NanType("int");
                    }
                    return new FloatType(this.getValueActual() / (Float) other.getValueActual());
                } else {
                    throw new RuntimeException("ERROR: Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
                }
            } else {
                if ((Integer) other.getValueActual() == 0) {
                    return new NanType("int");
                }
                return new IntType(this.getValueActual() / (Integer) other.getValueActual());
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use / operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean equals(Type other) {
        if (!other.getClass().getSimpleName().equals("FloatType") && !other.getClass().getSimpleName().equals("IntType")) {
            return false;
        } else {
            return (this.getValueActual().equals((Integer) other.getValueActual()));
        }
    }

    public boolean lessThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return this.getValueActual() < (Float) other.getValueActual();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.greaterThan(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                return other.greaterThan(this);
            } else {
                return this.getValueActual() < (Integer) other.getValueActual();
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use < operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean greaterThan(Type other) {
        if (Arrays.asList(VALID_TYPES).contains(other.getClass().getSimpleName())) {
            if (other.getClass().getSimpleName().equals("FloatType")) {
                return this.getValueActual() > (Float) other.getValueActual();
            } else if (other.getClass().getSimpleName().equals("NanType")) {
                return other.lessThan(this);
            } else if (other.getClass().getSimpleName().equals("NoValType")) {
                return other.lessThan(this);
            } else {
                return this.getValueActual() > (Integer) other.getValueActual();
            }
        } else {
            throw new RuntimeException("ERROR: Cannot use > operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Integer getValue() {
        return (Integer) super.getValue();
    }

    public Integer getValueActual() {
        return this.getValue();
    }

    public String getType() {
        return "int";
    }

}
