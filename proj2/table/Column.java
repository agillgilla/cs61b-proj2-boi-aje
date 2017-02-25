package table; /**
 * Created by Arjun on 2/22/2017.
 */
import java.util.ArrayList;

public class Column {
    private ArrayList<Object> elements;
    private String name;

    public Column(String name, Object... args) {
        this.name = name;
        this.elements = new ArrayList<>();
        for (Object element : args) {
            this.elements.add(element);
        }
    }

    public Column(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }


}
