package table;

/**
 * Created by Arjun on 2/26/2017.
 */
public class IntType extends Type {

    public IntType(Integer value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return new IntType(this.getValue() + (Integer) other.getValue());
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return new IntType(this.getValue() - (Integer) other.getValue());
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type multiply(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return new IntType(this.getValue() * (Integer) other.getValue());
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type divide(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return new IntType(this.getValue() / (Integer) other.getValue());
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public boolean equals(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return false;
        } else {
            return (this.getValue().equals(other.getValue()));
        }
    }

    public Integer getValue() {
        return (Integer) super.getValue();
    }
}
