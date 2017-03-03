package table_io;

import table.Column;
import table.Table;
import table.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Arjun on 2/27/2017.
 */
public class TblFileWriter {

    public static void writeTable(Table table) {
        try {
            FileWriter fw = new FileWriter(table.getName() + ".tbl");
            BufferedWriter tableWriter = new BufferedWriter(fw);

            for (int n = 0; n < table.getWidth(); n++) {
                Column column = table.getColumnByIndex(n);
                if (n == 0) {
                    tableWriter.write(column.getName() + " " + column.getType());
                } else {
                    tableWriter.write("," + column.getName() + " " + column.getType());
                }
            }

            tableWriter.newLine();

            for (int i = 0; i < table.getHeight(); i++) {
                for (int j = 0; j < table.getWidth(); j++) {
                    Type element = table.getColumnByIndex(j).get(i);
                    if (j == 0) {
                        tableWriter.write(element.toString());
                    } else {
                        tableWriter.write("," + element.toString());
                    }
                }
                tableWriter.newLine();
            }

            tableWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException("ERROR: Unable to write to file '" + table.getName() + ".tbl'");
        }
    }

}
