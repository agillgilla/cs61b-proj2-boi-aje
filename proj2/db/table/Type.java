package db.table;

/**
 * Created by Arjun on 2/26/2017.
 */
public abstract class Type {
    protected Object value;

    public abstract boolean equals(Type other);

    public abstract Type add(Type other);
    public abstract Type subtract(Type other);
    public abstract Type multiply(Type other);
    public abstract Type divide(Type other);

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return this.getValue().toString();
    }

    public abstract boolean lessThan(Type other);
    public abstract boolean greaterThan(Type other);

    public abstract Object getValueActual();

    public abstract String getType();

}
