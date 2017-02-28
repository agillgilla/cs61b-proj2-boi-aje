package table_io;

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
                        String[] nameAndType = colName.split("\\s+");
                        switch (nameAndType[1]) {
                            case "string":
                                table.addColumn(new Column(nameAndType[0], "string"));
                                break;
                            case "int":
                                table.addColumn(new Column(nameAndType[0], "int"));
                                break;
                            case "float":
                                table.addColumn(new Column(nameAndType[0], "float"));
                            default:
                                throw new RuntimeException("Unrecognized data type: " + nameAndType[1]);
                        }
                    }
                    firstLine = false;
                } else {
                    int columnIndex = 0;
                    for (String entry : entries) {
                        switch (table.getColumnByIndex(columnIndex).getType()) {
                            case "string":
                                table.getColumnByIndex(columnIndex).add(new StringType(entry));
                                break;
                            case "int":
                                table.getColumnByIndex(columnIndex).add(new IntType(Integer.parseInt(entry)));
                                break;
                            case "float":
                                table.getColumnByIndex(columnIndex).add(new FloatType(Float.parseFloat(entry)));
                            default:
                                throw new RuntimeException("Unknown Column Type!");
                        }
                        columnIndex++;
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return table;
    }
}
