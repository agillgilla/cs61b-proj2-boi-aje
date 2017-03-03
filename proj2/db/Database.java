package db;

import query_handler.Parse;
import table.Column;
import table.Table;

import java.util.HashMap;

public class Database {
    private HashMap<String, Table> tables;

    public Database() {
        this.tables = new HashMap<>();
    }

    public String transact(String query) {
        return Parse.parse(query);
    }

    public String createTable(String name, Table table) {
        if (this.tables.containsKey(name)) {
            return "ERROR: TABLE '" + name + "' already exists!";
        } else {
            this.tables.put(name, table);
            return "";
        }
    }

    public String createTable(String name, Column[] columns) {
        if (this.tables.containsKey(name)) {
            return "ERROR: TABLE '" + name + "' already exists!";
        } else {
            this.tables.put(name, new Table(columns));
            return "";
        }
    }

    public String printTable(String name) {
        return this.tables.get(name).print();
    }

}
