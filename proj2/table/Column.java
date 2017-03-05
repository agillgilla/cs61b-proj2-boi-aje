package table; /**
 * Created by Arjun on 2/22/2017.
 */
import query_handler.StringParse;

import java.util.ArrayList;
import java.util.Arrays;

public class Column {

    private static final String[] VALID_TYPES = new String[] {"string", "int", "float"};
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
        if (Arrays.asList(VALID_TYPES).contains(type)) {
            this.type = type;
        } else {
            throw new RuntimeException("ERROR: Unrecognized data type '" + type + "'");
        }
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

    public void add(String element) {
        if (element.equals("NOVALUE")) {
            this.elements.add(new NoValType(this.getType()));
            return;
        }

        if (element.substring(0, 1).equals("'") && element.substring(element.length() - 1).equals("'")) {
            if (this.getType().equals("string")) {
                this.elements.add(new StringType(element));
            } else {
                throw new RuntimeException("ERROR: Cannot insert '" + element + "' into column of type: '" + this.getType() + "'");
            }
        } else if (StringParse.isFloat(element)) {
            if (this.getType().equals("float")) {
                this.elements.add(new FloatType(Float.parseFloat(element)));
            } else {
                throw new RuntimeException("ERROR: Cannot insert '" + element + "' into column of type: '" + this.getType() + "'");
            }
        } else if (StringParse.isInteger(element)) {
            if (this.getType().equals("int")) {
                this.elements.add(new IntType(Integer.parseInt(element)));
            } else {
                throw new RuntimeException("ERROR: Cannot insert '" + element + "' into column of type: '" + this.getType() + "'");
            }
        } else {
            throw new RuntimeException("ERROR: Unrecognized data type of: '" + element + "'");
        }
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
