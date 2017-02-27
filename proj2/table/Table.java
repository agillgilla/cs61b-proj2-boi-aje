package table;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Arjun on 2/17/2017.
 */

public class Table {
    public LinkedHashMap<String, Column> columns = new LinkedHashMap<>();
    //public table.Table(String filename) {
    public Table(String[] columnNames) {
        for (String columnName : columnNames) {
            this.columns.put(columnName, new Column(columnName));
        }
    }

    public Table(Table... tables) {
        for (Table table : tables) {

        }
    }

    public void addRow(Type[] elements) {
        int i = 0;
        for(String columnName : this.columns.keySet()){
            this.columns.get(columnName).add(elements[i]);
            i++;
        }
    }

    public void addColumn(Column col) {
        this.columns.put(col.getName(), col);
    }

    public void printTable() {
        for (String columnName : this.columns.keySet()) {
            System.out.print(columnName + "\t");
        }
        System.out.println("");
        for (int row = 0; row < this.getHeight(); row++) {
            for (String columnName : this.columns.keySet()) {
                System.out.print(this.columns.get(columnName).get(row) + "\t");
            }
            System.out.println("");
        }
    }

    public Table join(Table other) {
        ArrayList<Column> sharedColumns = new ArrayList<>();
        ArrayList<Column> leftUnsharedColumns = new ArrayList<>();
        ArrayList<Column> rightUnsharedColumns = new ArrayList<>();

        boolean foundShared = false;

        for (int i = 0; i < this.columns.size(); i++) {
            foundShared = false;
            for (int j = 0; j < other.columns.size(); j++) {
                if (this.columns.get(i).getName().equals(other.columns.get(j).getName())) {
                    sharedColumns.add(this.columns.get(i));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                leftUnsharedColumns.add(this.columns.get(i));
            }
        }
        foundShared = false;
        for (int i = 0; i < other.columns.size(); i++) {
            foundShared = false;
            for (int j = 0; j < this.columns.size(); j++) {
                if (this.columns.get(i).getName().equals(other.columns.get(j).getName())) {
                    foundShared = true;
                }
            }
            if (!foundShared) {
                rightUnsharedColumns.add(other.columns.get(i));
            }
        }
        //return new Table(sharedColumns, leftUnsharedColumns, rightUnsharedColumns);
        return null;

    }

    public int getHeight() {
        if (this.columns.keySet().isEmpty()) {
            return 0;
        } else {
            return this.columns.get(this.columns.keySet().toArray()[0]).size();
        }
    }

    public int getWidth() {
        if (this.columns.keySet().isEmpty()) {
            return 0;
        } else {
            return this.columns.keySet().toArray().length;
        }
    }

    public static void main(String[] args) {
        Table T1 = new Table(new String[] {"X", "Y"});
        T1.addRow(new IntType[] {new IntType(2), new IntType(5)});
        T1.addRow(new IntType[] {new IntType(8), new IntType(3)});
        T1.addRow(new IntType[] {new IntType(13), new IntType(7)});
        T1.printTable();
    }
}