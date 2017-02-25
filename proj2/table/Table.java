package table;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by Arjun on 2/17/2017.
 */

public class Table {
    public ArrayList<Column> columns = new ArrayList<Column>();
    public ArrayList<String> columnNames;
    //public table.Table(String filename) {
    public Table(String... columns) {
        this.columnNames = new ArrayList(Arrays.asList(columns));
        for (String columnName : this.columnNames) {
            this.columns.add(new Column(columnName));
        }
    }

    public void addRow(Object... entries) {
        int columnIndex = 0;
        for (Object entry : entries) {
            this.columns.get(columnIndex).add(entry);
            columnIndex++;
        }
    }

    public void printTable() {
        for (String columnName : this.columnNames) {
            System.out.print(columnName + "\t");
        }
        System.out.println("");
        for (int row = 0; row < this.columns.get(0).size(); row++) {
            for (int column = 0; column < this.columns.size(); column++) {
                System.out.print(this.columns.get(column).get(row) + "\t");
            }
            System.out.println("");
        }
    }

    public void join(Table other) {
        ArrayList<Column> sharedColumns = new ArrayList<>();
        ArrayList<Column> leftUnsharedColumns = new ArrayList<>();
        ArrayList<Column> rightUnsharedColumns = new ArrayList<>();

        boolean foundShared = false;

        for (int i = 0; i < this.columns.size(); i++) {
            foundShared = false;
            for (int j = 0; j < other.columns.size(); j++) {
                if (this.columns.get(i).getName().equals(other.columns.get(j).getName())) {
                    sharedColumns.addLast(this.columns.get(i));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                leftUnsharedColumns.addLast(this.columns.get(i));
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
                rightUnsharedColumns.addLast(other.columns.get(i));
            }
        }
        return new Table(sharedColumns, leftUnsharedColumns, rightUnsharedColumns);

    }

    public static void main(String[] args) {
        Table T1 = new Table("A", "B");
        T1.addRow(2, 5);
        T1.addRow(8, 3);
        T1.addRow(13, 7);
        T1.printTable();
    }
}