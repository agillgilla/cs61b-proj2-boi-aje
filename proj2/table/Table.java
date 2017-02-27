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

    public Column getColumn(String columnName) {
        return this.columns.get(columnName);
    }

    public String[] getColumnNames() {
        String[] columnNames = new String[this.getWidth()];
        int i = 0;
        for (String columnName : this.columns.keySet()) {
            columnNames[i] = columnName;
            i++;
        }
        return columnNames;
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
