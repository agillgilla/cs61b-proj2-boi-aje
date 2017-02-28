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

    public int size() {
        return this.elements.size();
    }

    public Type get(int index) {
        return this.elements.get(index);
    }

    public String getType() {
        return this.type;
    }
}
