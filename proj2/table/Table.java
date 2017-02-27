package table;

import java.util.LinkedHashMap;

/**
 * Created by Arjun on 2/17/2017.
 */

public class Table {
    private LinkedHashMap<String, Column> columns = new LinkedHashMap<>();
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
        if (elements.length != this.getWidth()) {
            throw new RuntimeException("Table rectangularity must be preserved");
        }
        int i = 0;
        for (String columnName : this.columns.keySet()) {
            this.columns.get(columnName).add(elements[i]);
            i++;
        }
    }

    public void addColumn(Column col) {
        if (col.size() != this.getHeight()) {
            throw new RuntimeException("Table rectangularity must be preserved");
        }
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
        Table sharedColumns = new Table();
        Table leftUnsharedColumns = new Table();
        Table rightUnsharedColumns = new Table();

        boolean foundShared = false;

        for (int i = 0; i < this.columns.size(); i++) {
            foundShared = false;
            for (int j = 0; j < other.columns.size(); j++) {
                if (this.columns.get(i).getName().equals(other.columns.get(j).getName())) {
                    sharedColumns.addColumn(this.columns.get(i));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                leftUnsharedColumns.addColumn(this.columns.get(i));
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
                rightUnsharedColumns.addColumn(other.columns.get(i));
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
        T1.addColumn(new Column("Z", new IntType[] {new IntType(11), new IntType(9), new IntType(4)}));
        T1.printTable();
    }
}
