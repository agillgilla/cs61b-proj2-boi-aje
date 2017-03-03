package table_io;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import table.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Arjun on 2/21/2017.
 */
public class TblFileReader {

    public static Table readTable(String tableName) {

        Table table = new Table();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(tableName + ".tbl")))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                String[] entries = line.split(",");
                if (firstLine) {
                    for (String colName : entries) {
                        colName = colName.trim();
                        String[] nameAndType = colName.split("\\s+");
                        try {
                            nameAndType[0] = nameAndType[0].trim();
                            nameAndType[1] = nameAndType[1].trim();
                        } catch (ArrayIndexOutOfBoundsException e) {
                            throw new RuntimeException("ERROR: Malformed table file: '" + tableName + ".tbl'");
                        }
                        switch (nameAndType[1]) {
                            case "string":
                                table.addColumn(new Column(nameAndType[0], "string"));
                                break;
                            case "int":
                                table.addColumn(new Column(nameAndType[0], "int"));
                                break;
                            case "float":
                                table.addColumn(new Column(nameAndType[0], "float"));
                                break;
                            default:
                                throw new RuntimeException("Unrecognized data type: " + nameAndType[1]);
                        }
                    }
                    firstLine = false;
                } else {
                    int columnIndex = 0;
                    for (String entry : entries) {
                        entry = entry.trim();
                        switch (table.getColumnByIndex(columnIndex).getType()) {
                            case "string":
                                table.getColumnByIndex(columnIndex).add(new StringType(entry));
                                break;
                            case "int":
                                table.getColumnByIndex(columnIndex).add(new IntType(Integer.parseInt(entry)));
                                break;
                            case "float":
                                table.getColumnByIndex(columnIndex).add(new FloatType(Float.parseFloat(entry)));
                                break;
                            default:
                                throw new RuntimeException("Unknown Column Type!");
                        }
                        columnIndex++;
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
            return null;
        } catch (IOException ioe) {
            return null;
        }

        return table;
    }
}
