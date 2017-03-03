package table;

import query_handler.TblCommands;
import table_io.TblFileReader;
import table_io.TblFileWriter;

import java.util.LinkedHashMap;

/**
 * Created by Arjun on 2/17/2017.
 */

public class Table {
    private String name;
    private LinkedHashMap<String, Column> columns;

    public Table() {
        this.columns =  new LinkedHashMap<>();
    }

    public Table(String[] columnNames) {
        this.columns =  new LinkedHashMap<>();

        for (String columnName : columnNames) {
            this.columns.put(columnName, new Column(columnName));
        }
    }

    /* TODO: Check that height of tables are matching.*/
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

    public Table(Column[] columns) {
        for (Column column : columns) {
            this.columns.put(column.getName(), column);
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

    public void addRow(String[] elements) {
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

    public void removeRow(int index) {
        for (String columnName : this.columns.keySet()) {
            this.columns.get(columnName).remove(index);
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
                System.out.print(this.columns.get(columnName).get(row) + " \t");
            }
            System.out.println("");
        }
    }

    public String print() {
        String tablePrinted = "";
        for (String columnName : this.columns.keySet()) {
            tablePrinted += columnName + " " + this.getColumn(columnName).getType() + ",";
        }
        tablePrinted = tablePrinted.substring(0, tablePrinted.length() - 1);
        tablePrinted += "\n";

        for (int rowIndex = 0; rowIndex < this.getHeight(); rowIndex++) {
            for (String columnName : this.columns.keySet()) {
                tablePrinted += this.getColumn(columnName).get(rowIndex) + ",";
            }
            tablePrinted = tablePrinted.substring(0, tablePrinted.length() - 1);
            tablePrinted += "\n";
        }
        return tablePrinted;
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

    public Type[] getRowByIndex(int index) {
        Type[] row = new Type[this.getWidth()];
        int i = 0;
        for (String columnName : this.columns.keySet()) {
            row[i] = this.getColumn(columnName).get(index);
            i++;
        }
        return row;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        /*Table T1 = new Table(new String[] {"X", "Y"});
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

        System.out.println("");

        Table T3 = TblFileReader.readTable("test");

        T3.printTable();

        System.out.println("");

        T1_T2.setName("join_test");
        TblFileWriter.writeTable(T1_T2);

        Table T1_T2Read = TblFileReader.readTable("join_test");
        T1_T2Read.printTable(); */

        /*
        Table T1 = new Table();
        T1.addColumn(new Column("X", new IntType[] {new IntType(1), new IntType(7), new IntType(1)}));
        T1.addColumn(new Column("Y", new IntType[] {new IntType(7), new IntType(7), new IntType(9)}));
        T1.addColumn(new Column("Z", new IntType[] {new IntType(2), new IntType(4), new IntType(9)}));
        T1.addColumn(new Column("W", new IntType[] {new IntType(10), new IntType(1), new IntType(1)}));

        Table T2 = new Table();
        T2.addColumn(new Column("W", new IntType[] {new IntType(1), new IntType(7), new IntType(1), new IntType(1)}));
        T2.addColumn(new Column("B", new IntType[] {new IntType(7), new IntType(7), new IntType(9), new IntType(11)}));
        T2.addColumn(new Column("Z", new IntType[] {new IntType(4), new IntType(3), new IntType(6), new IntType(9)}));

        Table T3 = TblCommands.join(T1, T2);

        T3.printTable();*/

        /*Table T1 = new Table();
        T1.addColumn(new Column("X", new IntType[] {new IntType(2), new IntType(8)}));
        T1.addColumn(new Column("Y", new IntType[] {new IntType(5), new IntType(3)}));
        T1.addColumn(new Column("Z", new IntType[] {new IntType(4), new IntType(9)}));

        Table T2 = new Table();
        T2.addColumn(new Column("A", new IntType[] {new IntType(7), new IntType(2)}));
        T2.addColumn(new Column("B", new IntType[] {new IntType(0), new IntType(8)}));

        Table T1_T2 = TblCommands.join(T1, T2);

        T1_T2.printTable();*/

        Table T = TblFileReader.readTable("loadBasic1");
        T.print();

    }
}
