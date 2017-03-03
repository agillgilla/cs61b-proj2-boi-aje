package db;

import query_handler.Parse;
import query_handler.TblCommands;
import table.Column;
import table.Table;
import table_io.TblFileReader;
import table_io.TblFileWriter;

import java.util.HashMap;

public class Database {
    private HashMap<String, Table> tables;
    private Parse parser;

    public Database() {
        this.tables = new HashMap<>();
        this.parser = new Parse(this);
    }

    public String transact(String query) {
        return this.parser.parse(query);
    }

    public String createTable(String name, Table table) {
        if (this.tables.containsKey(name)) {
            return "ERROR: Table '" + name + "' already exists!";
        } else {
            this.tables.put(name, table);
            return "";
        }
    }

    public String createTable(String name, Column[] columns) {
        if (this.tables.containsKey(name)) {
            return "ERROR: Table '" + name + "' already exists!";
        } else {
            this.tables.put(name, new Table(columns));
            return "";
        }
    }

    public String printTable(String name) {
        return this.tables.get(name).print();
    }

    public String loadTable(String name) {
        Table newTable = TblFileReader.readTable(name);
        if (newTable == null) {
            return "ERROR: File '" + name + ".tbl' doesn't exist!";
        } else {
            this.createTable(name, TblFileReader.readTable(name));
            return "";
        }
    }

    public String join(String[] tableNames) {
        Table[] tablesToJoin = new Table[tableNames.length];
        int i = 0;
        for (String tableName : tableNames) {
            if (!this.tables.containsKey(tableName)) {
                return "ERROR: Table '" + tableName + "' doesn't exist!";
            }
            tablesToJoin[i] = this.tables.get(tableName);
            i++;
        }
        return TblCommands.joinAll(tablesToJoin).print();
    }

    public String drop(String name) {
        if (this.tables.containsKey(name)) {
            this.tables.remove(name);
            return "";
        }
        return "ERROR: Table '" + name + "' doesn't exist!";
    }

    public String store(String name) {
        TblFileWriter.writeTable(this.tables.get(name));
        return "";
    }

}
