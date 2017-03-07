package db.table; /**
 * Created by Arjun on 2/22/2017.
 */
import db.query_handler.StringParse;

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
                case "NanType":
                    this.type = this.elements.get(0).getType();
                    break;
                case "NoValType":
                    this.type = this.elements.get(0).getType();
                    break;
                default:
                    throw new RuntimeException("Unrecognized column type");
            }
        } else if (this.type == null) {
            throw new RuntimeException("Error: Cannot get column type, hasn't been assigned");
        }
        return this.type;
    }

    public Column addColumn(Column other, String name) {
        Column added = new Column(name);
        sizeMatchCheck(other);
        for (int i = 0; i < this.size(); i++) {
            added.add(this.get(i).add(other.get(i)));
        }
        return added;
    }

    public Column subtractColumn(Column other, String name) {
        Column subtracted = new Column(name);
        sizeMatchCheck(other);
        for (int i = 0; i < this.size(); i++) {
            subtracted.add(this.get(i).subtract(other.get(i)));
        }
        return subtracted;
    }

    public Column multiplyColumn(Column other, String name) {
        Column multiplied = new Column(name);
        sizeMatchCheck(other);
        for (int i = 0; i < this.size(); i++) {
            multiplied.add(this.get(i).multiply(other.get(i)));
        }
        return multiplied;
    }

    public Column divideColumn(Column other, String name) {
        Column divided = new Column(name);
        sizeMatchCheck(other);
        for (int i = 0; i < this.size(); i++) {
            divided.add(this.get(i).divide(other.get(i)));
        }
        return divided;
    }

    public boolean compareToColumn(Column other, String comparator, int index) {
        switch (comparator) {
            case "<":
                if (this.get(index).lessThan(other.get(index))) {
                    return true;
                }
                break;
            case "<=":
                if (this.get(index).lessThan(other.get(index)) || this.get(index).equals(other.get(index))) {
                    return true;
                }
                break;
            case ">":
                if (this.get(index).greaterThan(other.get(index))) {
                    return true;
                }
                break;
            case ">=":
                if (this.get(index).greaterThan(other.get(index)) || this.get(index).equals(other.get(index))) {
                    return true;
                }
                break;
            case "==":
                if (this.get(index).equals(other.get(index))) {
                    return true;
                }
                break;
            case "!=":
                if (!this.get(index).equals(other.get(index))) {
                    return true;
                }
                break;
            default:
                throw new RuntimeException("ERROR: Unknown comparator '" + comparator + "'");
        }
        return false;
    }

    public boolean compareLiteral(String literal, String comparator, int index) {
        Type literalType = StringParse.typeParse(literal);
        switch (comparator) {
            case "<":
                if (this.get(index).lessThan(literalType)) {
                    return true;
                }
                break;
            case "<=":
                if (this.get(index).lessThan(literalType) || this.get(index).equals(literalType)) {
                    return true;
                }
                break;
            case ">":
                if (this.get(index).greaterThan(literalType)) {
                    return true;
                }
                break;
            case ">=":
                if (this.get(index).greaterThan(literalType) || this.get(index).equals(literalType)) {
                    return true;
                }
                break;
            case "==":
                if (this.get(index).equals(literalType)) {
                    return true;
                }
                break;
            case "!=":
                if (!this.get(index).equals(literalType)) {
                    return true;
                }
                break;
            default:
                throw new RuntimeException("ERROR: Unknown comparator '" + comparator + "'");
        }
        return false;
    }


    public void sizeMatchCheck(Column other) {
        if (this.size() != other.size()) {
            throw new RuntimeException("ERROR: Column sizes must match!");
        }
    }

    public Column copy() {
        Column copy = new Column(this.getName(), this.getType());
        for (Type element : this.elements) {
            copy.add(element.copy());
        }
        return copy;
    }
}
