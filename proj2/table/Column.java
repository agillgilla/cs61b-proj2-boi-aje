package table; /**
 * Created by Arjun on 2/22/2017.
 */
import java.util.ArrayList;

public class Column {

    private ArrayList<Type> elements;
    private String name;
    private String type;

    public Column(String name, Type[] elements) {
        this.name = name;
        this.elements = new ArrayList<>();
        for (Type element : elements) {
            this.elements.add(element);
        }
    }

    public Column(String name, String type) {
        this.name = name;
        this.elements = new ArrayList<>();
        this.type = type;
    }

    public Column(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Type element) {
        this.elements.add(element);
    }

    public void remove(int index) {
        this.elements.remove(index);
    }

    public int size() {
        return this.elements.size();
    }

    public Type get(int index) {
        return this.elements.get(index);
    }

    public String getType() {
        if (this.elements.size() != 0 && this.type == null) {
            switch (this.elements.get(0).getClass().getSimpleName()) {
                case "StringType":
                    this.type = "string";
                    break;
                case "IntType":
                    this.type = "int";
                    break;
                case "FloatType":
                    this.type = "float";
                    break;
                default:
                    throw new RuntimeException("Unrecognized column type");
            }
        } else if (this.type == null) {
            throw new RuntimeException("Error: Cannot get column type, hasn't been assigned");
        }
        return this.type;
    }
}
