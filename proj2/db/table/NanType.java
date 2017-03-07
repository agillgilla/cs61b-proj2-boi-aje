package db.table;

/**
 * Created by Arjun on 3/3/2017.
 */
public class NanType extends Type {
    private String type;

    public NanType(String type) {
        this.type = type;
    }

    public Type add(Type other) {
        return new NanType(this.type);
    }

    public Type subtract(Type other) {
        return new NanType(this.type);
    }

    public Type multiply(Type other) {
        return new NanType(this.type);
    }

    public Type divide(Type other) {
        return new NanType(this.type);
    }

    public boolean lessThan(Type other) {
        return false;
    }

    public boolean greaterThan(Type other) {
        if (this.getClass().getSimpleName().equals(other.getClass().getSimpleName())) {
            return false;
        } else {
            return true;
        }
    }

    public boolean equals(Type other) {
        if (!this.getClass().getSimpleName().equals(other.getClass().getSimpleName())) {
            return false;
        } else {
            return true;
        }
    }

    public String getValue() {
        return "NaN";
    }

    public String getValueActual() {
        return this.getValue();
    }

    public String getType() {
        return this.type;
    }

    public NanType copy() {
        return new NanType(this.getType());
    }
}
