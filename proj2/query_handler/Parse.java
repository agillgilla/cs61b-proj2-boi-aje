package query_handler;

import db.Database;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.StringJoiner;

public class Parse {
    private Database db;

    public Parse(Database db) {
        this.db = db;
    }
    // Various common constructs, simplifies parsing.
    private static final String REST  = "\\s*(.*)\\s*",
                                COMMA = "\\s*,\\s*",
                                AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
                                 LOAD_CMD   = Pattern.compile("load " + REST),
                                 STORE_CMD  = Pattern.compile("store " + REST),
                                 DROP_CMD   = Pattern.compile("drop table " + REST),
                                 INSERT_CMD = Pattern.compile("insert into " + REST),
                                 PRINT_CMD  = Pattern.compile("print " + REST),
                                 SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\((\\S+\\s+\\S+\\s*" +
                                               "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
                                 SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                                               "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                                               "([\\w\\s+\\-*/'<>=!]+?(?:\\s+and\\s+" +
                                               "[\\w\\s+\\-*/'<>=!]+?)*))?"),
                                 CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                                                   SELECT_CLS.pattern()),
                                 INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                                               "\\s*(?:,\\s*.+?\\s*)*)");

    public void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Expected a single query argument");
            return;
        }

        eval(args[0]);
    }

    public String parse(String query) {
        return eval(query);
    }

    private String eval(String query) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
             return createTable(m.group(1));
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
             return loadTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
             return storeTable(m.group(1));
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
             return dropTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
             return insertRow(m.group(1));
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
             return printTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
             return select(m.group(1));
        } else {
            System.err.printf("Malformed query: %s\n", query);
            return "ERROR: Malformed query: " + query;
        }
    }

    private String createTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            return createNewTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            return createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {
            System.err.printf("Malformed create: %s\n", expr);
            return "ERROR: Malformed create: " + expr;
        }
    }

    private String createNewTable(String name, String[] cols) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < cols.length-1; i++) {
            joiner.add(cols[i]);
        }

        String colSentence = joiner.toString() + " and " + cols[cols.length-1];
        System.out.printf("You are trying to create a table named %s with the columns %s\n", name, colSentence);
        return this.db.createTable(name, cols);
    }

    private String createSelectedTable(String name, String exprs, String tables, String conds) {
        System.out.printf("You are trying to create a table named %s by selecting these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", name, exprs, tables, conds);
        return "";
    }

    private String loadTable(String name) {
        System.out.printf("You are trying to load the table named %s\n", name);
        return this.db.loadTable(name);
    }

    private String storeTable(String name) {
        System.out.printf("You are trying to store the table named %s\n", name);
        return this.db.store(name);
    }

    private String dropTable(String name) {
        System.out.printf("You are trying to drop the table named %s\n", name);
        return this.db.drop(name);
    }

    private String insertRow(String expr) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return "ERROR: Malformed insert: " + expr;
        }

        System.out.printf("You are trying to insert the row \"%s\" into the table %s\n", m.group(2), m.group(1));
        try {
            return this.db.insert(m.group(1), m.group(2).split(","));
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private String printTable(String name) {
        System.out.printf("You are trying to print the table named %s\n", name);
        return this.db.printTable(name);
    }

    private String select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return "ERROR: Malformed select: " + expr;
        }

        return select(m.group(1), m.group(2), m.group(3));
    }

    private String select(String exprs, String tables, String conds) {
        System.out.printf("You are trying to select these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", exprs, tables, conds);
        if (exprs.equals("*") && conds == null) {
            String[] tablesToJoin = tables.split(",");
            return this.db.join(tablesToJoin);
        } else {
            return this.db.select(exprs, tables, conds);
        }
    }
}
