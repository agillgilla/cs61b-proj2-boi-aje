package query_handler;

import table.Column;
import table.Table;

/**
 * Created by Arjun on 2/27/2017.
 */
public class TblCommands {

    public static Table join(Table t1, Table t2) {
        Table leftSharedColumns = new Table();
        Table leftUnsharedColumns = new Table();
        Table rightUnsharedColumns = new Table();

        boolean foundShared = false;

        for (String t1ColName : t1.getColumnNames()) {
            foundShared = false;
            for (String t2ColName : t2.getColumnNames()) {
                if (t2ColName.equals(t1ColName)) {
                    leftSharedColumns.addColumn(t1.getColumn(t1ColName));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                leftUnsharedColumns.addColumn(t1.getColumn(t1ColName));
            }
        }
        for (String t2ColName : t2.getColumnNames()) {
            foundShared = false;
            for (String t1ColName : t1.getColumnNames()) {
                if (t1ColName.equals(t2ColName)) {
                    foundShared = true;
                }
            }
            if (!foundShared) {
                rightUnsharedColumns.addColumn(t2.getColumn(t2ColName));
            }
        }

        Table rightSharedColumns = t2;
        boolean foundMatch = false;

        for (int c = 0; c < leftSharedColumns.getWidth(); c++) {

            Column leftColumn = leftSharedColumns.getColumnByIndex(c);
            Column rightColumn = rightSharedColumns.getColumn(leftColumn.getName());

            for (int index = 0; index < leftColumn.size(); index++) {
                foundMatch = false;
                for (int rIndex = 0; rIndex < rightColumn.size(); rIndex++) {
                    //System.out.println("Comparing " + leftColumn.get(index) + " and " + rightColumn.get(rIndex));
                    if (leftColumn.get(index).getValue().equals(rightColumn.get(rIndex).getValue())) {
                        foundMatch = true;
                    }
                }
                if (!foundMatch) {
                    leftSharedColumns.removeRow(index);
                    leftUnsharedColumns.removeRow(index);
                    index--;
                }
            }



        }
        
        return new Table(leftSharedColumns, leftUnsharedColumns);
    }

}
