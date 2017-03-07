package db;

import db.query_handler.Parse;
import db.query_handler.TblCommands;
import db.table.Column;
import db.table.Table;
import db.table_io.TblFileReader;
import db.table_io.TblFileWriter;

import java.util.HashMap;

public class Database {
    private HashMap<String, Table> tables;
    private Parse parser;

    private static final String[] OPERATORS = new String[] {"+", "-", "*", "/"};
    private static final String[] COMPARATORS = new String[] {">=", "<=", ">", "<", "==", "!="};

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

    public Table select(String exprsList, String tablesList, String condsList) {
        Table colExprTable = new Table();
        String[] expressions = exprsList.split(",");
        for (int i = 0; i < expressions.length; i++) {
            String expr = expressions[i];
            String exprReduced = expr.trim().replaceAll(" +", " ");
            expr = expr.replaceAll("\\s+", "");
            String operators = expr.replaceAll("[^+-/*]", "");
            if (operators.length() == 0) {
                Table joined = this.joinTable(tablesList.split(","));
                colExprTable.addColumn(joined.getColumn(exprReduced.trim()));
            } else if (operators.length() == 1) {
                int indexOfOperator = exprReduced.indexOf(operators);
                int indexOfAs = exprReduced.indexOf(" as ");
                if (indexOfAs == -1 || indexOfOperator == -1) {
                    throw new RuntimeException("ERROR: Malformed select query!");
                }
                String firstColName = exprReduced.substring(0, indexOfOperator).trim();
                String secondColName = exprReduced.substring(indexOfOperator + 1, indexOfAs).trim();
                String aliasColName = exprReduced.substring(indexOfAs + 4).trim();
                if (firstColName.equals("")
                        || secondColName.equals("")
                        || aliasColName.equals("")) {
                    throw new RuntimeException("ERROR: Malformed select query!");
                }
                Table joined = this.joinTable(tablesList.split(","));
                switch (operators) {
                    case "+":
                        colExprTable.addColumn(
                                joined.getColumn(firstColName).
                                addColumn(joined.getColumn(secondColName), aliasColName));
                        break;
                    case "-":
                        colExprTable.addColumn(
                                joined.getColumn(firstColName).
                                subtractColumn(joined.getColumn(secondColName), aliasColName));
                        break;
                    case "*":
                        colExprTable.addColumn(
                                joined.getColumn(firstColName).
                                multiplyColumn(joined.getColumn(secondColName), aliasColName));
                        break;
                    case "/":
                        colExprTable.addColumn(
                                joined.getColumn(firstColName).
                                divideColumn(joined.getColumn(secondColName), aliasColName));
                        break;
                    default:
                        throw new RuntimeException("ERROR:  Invalid operator + '"
                                + operators + "'!");
                }
            } else {
                throw new RuntimeException("ERROR: Cannot have multiple operators!");
            }
        }

        colExprTable = colExprTable.copy();

        if (condsList != null) {
            String[] condsListSeparated = condsList.split("and");
            for (String condition : condsListSeparated) {
                for (String comp : COMPARATORS) {
                    if (condition.contains(comp)) {
                        String[] conditionList = condition.split(comp);
                        Column left = colExprTable.getColumn(conditionList[0].trim());
                        String right = conditionList[1].trim();
                        if (colExprTable.containsColumn(right)) {
                            for (int row = 0; row < left.size(); row++) {
                                if (!left.compareToColumn(colExprTable.getColumn(right), comp, row)) {
                                    colExprTable.removeRow(row);
                                    row--;
                                }
                            }
                        } else {
                            for (int row = 0; row < left.size(); row++) {
                                if (!left.compareLiteral(right, comp, row)) {
                                    colExprTable.removeRow(row);
                                    row--;
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return colExprTable;

    }

    public String selectPrint(String exprsList, String tablesList, String condsList) {
        return select(exprsList, tablesList, condsList).print();
    }

    public String createTableSelect(String name, String exprsList,
                                    String tablesList, String condsList) {
        this.tables.put(name, select(exprsList, tablesList, condsList));
        return "";
    }

    public boolean tableExists(String name) {
        return this.tables.containsKey(name);
    }

}
