package table;

/**
 * Created by Arjun on 3/4/2017.
 */
public class NoValType extends Type {
    private String type;

    public NoValType(String type) {
        this.type = type;
    }

    public Type add(Type other) {
        return this.getTypeObject().add(other);
    }

    public Type subtract(Type other) {
        return this.getTypeObject().subtract(other);
    }

    public Type multiply(Type other) {
        return this.getTypeObject().multiply(other);
    }

    public Type divide(Type other) {
        return this.getTypeObject().divide(other);
    }

    public boolean equals(Type other) {
        return false;
    }

    public boolean lessThan(Type other) {
        return false;
    }

    public boolean greaterThan(Type other) {
        return false;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Type getTypeObject() {
        switch (this.getType()) {
            case "string":
                return new StringType("");
            case "int":
                return new IntType(0);
            case "float":
                return new FloatType(0.0f);
            default:
                throw new RuntimeException("ERROR: Unknown NOVALUE type!");
        }
    }

    public Object getValueActual() {
        switch (this.getType()) {
            case "string":
                return "";
            case "int":
                return 0;
            case "float":
                return 0.0f;
            default:
                throw new RuntimeException("ERROR: Unknown NOVALUE type!");
        }
    }

    public String getValue() {
        return "NOVALUE";
    }
}
