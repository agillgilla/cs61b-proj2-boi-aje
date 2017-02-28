package table;

import query_handler.TblCommands;

import java.util.LinkedHashMap;

/**
 * Created by Arjun on 2/17/2017.
 */

public class Table {
    private LinkedHashMap<String, Column> columns;
    //public table.Table(String filename) {

    public Table() {
        this.columns =  new LinkedHashMap<>();
    }

    public Table(String[] columnNames) {
        this.columns =  new LinkedHashMap<>();

        for (String columnName : columnNames) {
            this.columns.put(columnName, new Column(columnName));
        }
    }

    public Table(Table... tables) {
        this.columns =  new LinkedHashMap<>();
        for (Table table : tables) {
            if (table.getWidth() != 0) {
                for (String columnName : table.getColumnNames()) {
                    this.addColumn(table.getColumn(columnName));
                }
            }
        }
    }

    public void addRow(Type[] elements) {
        if (this.getWidth() == 0) {

        } else if (elements.length != this.getWidth()) {
            throw new RuntimeException("Table rectangularity must be preserved");
        }
        int i = 0;
        for (String columnName : this.columns.keySet()) {
            this.columns.get(columnName).add(elements[i]);
            i++;
        }
    }

    public void addColumn(Column col) {
        if (this.getHeight() == 0) {

        } else if (col.size() != this.getHeight()) {
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

    public Column getColumnByIndex(int index) {
        if (index >= this.getWidth()) {
            throw new RuntimeException("Index out of bounds of table columns.");
        } else {
            return this.columns.get(this.columns.keySet().toArray()[index]);
        }
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

        System.out.println("");

        Table T2 = new Table(new String[] {"A", "B"});
        T2.addRow(new IntType[] {new IntType(5), new IntType(10)});
        T2.addRow(new IntType[] {new IntType(6), new IntType(12)});
        T2.addRow(new IntType[] {new IntType(9), new IntType(0)});
        T2.addColumn(new Column("C", new IntType[] {new IntType(11), new IntType(9), new IntType(4)}));
        T2.printTable();

        System.out.println("");

        Table T1_T2 = TblCommands.join(T1, T2);
        T1_T2.printTable();

        Object string = new String("YOLO");
        System.out.println(string.getClass());

    }
}
