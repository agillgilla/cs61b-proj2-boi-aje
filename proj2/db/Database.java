package db;

import query_handler.Parse;
import query_handler.TblCommands;
import table.Column;
import table.Table;
import table_io.TblFileReader;
import table_io.TblFileWriter;

import java.util.Arrays;
import java.util.HashMap;

public class Database {
    private HashMap<String, Table> tables;
    private Parse parser;

    private static final String[] OPERATORS = new String[] {"+", "-", "*", "/"};

    public Database() {
        this.tables = new HashMap<>();
        this.parser = new Parse(this);
    }

    public String transact(String query) {
        return this.parser.parse(query);
    }

    public String createTable(String name, Table table) {
        if (this.tableExists(name)) {
            return "ERROR: Table '" + name + "' already exists!";
        } else {
            table.setName(name.trim());
            this.tables.put(name, table);
            return "";
        }
    }

    public String createTable(String name, Column[] columns) {
        if (this.tableExists(name)) {
            return "ERROR: Table '" + name + "' already exists!";
        } else {
            this.tables.put(name, new Table(columns));
            this.tables.get(name).setName(name.trim());
            return "";
        }
    }

    public String createTable(String name, String[] cols) {
        if (this.tableExists(name)) {
            return "ERROR: Table '" + name + "' already exists!";
        } else if (cols.length == 0) {
            return "ERROR: New table must have columns!";
        }
        Table newTable = new Table();
        try {
            for (String colExpr : cols) {
                colExpr = colExpr.trim();
                String[] nameAndType = colExpr.split("\\s+");
                newTable.addColumn(new Column(nameAndType[0], nameAndType[1]));
            }
            newTable.setName(name.trim());
            this.tables.put(name, newTable);
            return "";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String printTable(String name) {
        if (this.tableExists(name)) {
            return this.tables.get(name).print();
        }
        return "ERROR: Table '" + name + "' doesn't exist!";
    }

    public String loadTable(String name) {
        try {
            Table newTable = TblFileReader.readTable(name);
            if (newTable == null) {
                return "ERROR: File '" + name + ".tbl' doesn't exist!";
            } else {
                this.createTable(name, TblFileReader.readTable(name));
                return "";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String join(String[] tableNames) {
        try {
            return this.joinTable(tableNames).print();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public Table joinTable(String[] tableNames) {
        Table[] tablesToJoin = new Table[tableNames.length];
        int i = 0;
        for (String tableName : tableNames) {
            if (!this.tableExists(tableName)) {
                throw new RuntimeException("ERROR: Table '" + tableName + "' doesn't exist!");
            }
            tablesToJoin[i] = this.tables.get(tableName);
            i++;
        }
        return TblCommands.joinAll(tablesToJoin);
    }

    public String drop(String name) {
        if (this.tableExists(name)) {
            this.tables.remove(name);
            return "";
        }
        return "ERROR: Table '" + name + "' doesn't exist!";
    }

    public String store(String name) {
        try {
            if (this.tableExists(name)) {
                TblFileWriter.writeTable(this.tables.get(name));
                return "";
            } else {
                return "ERROR: Table '" + name + "' doesn't exist!";
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public String insert(String name, String[] row) {
        if (this.tableExists(name)) {
            Table table = this.tables.get(name);
            if (table.getWidth() != row.length) {
                return "ERROR: Row dimension must match number of columns!";
            }
            try {
                table.addRow(row);
                return "";
            } catch (NumberFormatException e) {
                return "ERROR: Types of row entries must match column types!";
            }
        } else {
            return "ERROR: Table '" + name + "' doesn't exist!";
        }
    }

    public String select(String exprsList, String tablesList, String condsList) {
        Table joined = this.joinTable(tablesList.split( ","));
        Table colExprTable = new Table();

        String[] expressions = exprsList.split(",");
        for (int i = 0; i < expressions.length; i++) {
            expressions[i] = expressions[i].trim().replaceAll(" +", " ");
        }

        //String[][] exprTokens = new String[expressions.length][];

        for (int i = 0; i < expressions.length; i++) {
            String[] tokens = expressions[i].split(" ");
            if (tokens.length == 1) {
                colExprTable.addColumn(joined.getColumn(tokens[0]));
            } else if (tokens.length == 5) {
                if (joined.containsColumn(tokens[0]) &&
                        joined.containsColumn(tokens[2]) &&
                        Arrays.asList(OPERATORS).contains(tokens[1]) &&
                        tokens[3].equals("as")) {

                    switch (tokens[1]) {
                        case "+":
                            colExprTable.addColumn(
                                    joined.getColumn(tokens[0]).addColumn(joined.getColumn(tokens[2]), tokens[4]));
                            break;
                        case "-":
                            colExprTable.addColumn(
                                    joined.getColumn(tokens[0]).subtractColumn(joined.getColumn(tokens[2]), tokens[4]));
                            break;
                        case "*":
                            colExprTable.addColumn(
                                    joined.getColumn(tokens[0]).multiplyColumn(joined.getColumn(tokens[2]), tokens[4]));
                            break;
                        case "/":
                            colExprTable.addColumn(
                                    joined.getColumn(tokens[0]).divideColumn(joined.getColumn(tokens[2]), tokens[4]));
                            break;
                        default:
                            throw new RuntimeException("ERROR:  Invalid operator + '" + tokens[1] + "'!");
                    }

                } else {
                    throw new RuntimeException("ERROR: Invalid column expression: '" + expressions[i] + "'!");
                }
            } else {
                throw new RuntimeException("ERROR: Malformed column expression!");
            }
        }


        return colExprTable.print();
    }

    public boolean tableExists(String name) {
        return this.tables.containsKey(name);
    }

}
