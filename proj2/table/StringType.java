package table;

/**
 * Created by Arjun on 2/26/2017.
 */
public class StringType extends Type {

    public StringType(String value) {
        this.value = value;
    }

    public Type add(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return new StringType(this.getValue() + (String) other.getValue());
        } else {
            throw new RuntimeException("Cannot use + operator on " + this.getClass().getSimpleName() + " and " + other.getClass().getSimpleName());
        }
    }

    public Type subtract(Type other) {
        throw new RuntimeException("Cannot use - operator on String");
    }

    public Type multiply(Type other) {
        throw new RuntimeException("Cannot use * operator on String");
    }

    public Type divide(Type other) {
        throw new RuntimeException("Cannot use / operator on String");
    }

    public boolean equals(Type other) {
        if (this.getClass().getSimpleName() == other.getClass().getSimpleName()) {
            return false;
        } else {
            return (this.getValue().equals(other.getValue()));
        }
    }

    public String getValue() {
        return (String) super.getValue();
    }
}
