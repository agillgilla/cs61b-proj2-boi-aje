package db.query_handler;

import db.table.Column;
import db.table.Table;
import db.table.Type;

/**
 * Created by Arjun on 2/27/2017.
 */
public class TblCommands {

    public static Table join(Table t1, Table t2) {
        Table leftSharedColumns = new Table();
        Table leftUnsharedColumns = new Table();
        Table rightSharedColumns = new Table();
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
                    rightSharedColumns.addColumn(t2.getColumn(t2ColName));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                rightUnsharedColumns.addColumn(t2.getColumn(t2ColName));
            }
        }

        if (leftSharedColumns.getWidth() == rightSharedColumns.getWidth() && leftSharedColumns.getWidth() == 0) {
            return cartesianJoin(t1, t2);
        }

        boolean foundMatch = false;
        //Table rightSharedColumns = t2;
        //rightSharedColumns.printTable();

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

        foundMatch = false;

        for (int c = 0; c < rightSharedColumns.getWidth(); c++) {

            Column rightColumn = rightSharedColumns.getColumnByIndex(c);
            Column leftColumn = leftSharedColumns.getColumn(rightColumn.getName());

            //leftSharedColumns.printTable();;
            //rightSharedColumns.printTable();

            for (int index = 0; index < rightColumn.size(); index++) {
                //System.out.println("Index: " + index);
                //System.out.println("Size: " + rightColumn.size());
                foundMatch = false;
                //System.out.println(leftColumn);
                for (int lIndex = 0; lIndex < leftColumn.size(); lIndex++) {
                    //System.out.println("Comparing " + leftColumn.get(index) + " and " + rightColumn.get(rIndex));
                    if (rightColumn.get(index).getValue().equals(leftColumn.get(lIndex).getValue())) {
                        foundMatch = true;
                    }
                }
                if (!foundMatch) {
                    rightSharedColumns.removeRow(index);
                    rightUnsharedColumns.removeRow(index);
                    index--;
                }
            }
        }

        leftSharedColumns = new Table(leftSharedColumns, leftUnsharedColumns);

        Table joined = new Table();

        for (int c = 0; c < leftSharedColumns.getWidth(); c++) {
            Column curr = leftSharedColumns.getColumnByIndex(c);
            joined.addColumn(new Column(curr.getName(), curr.getType()));
        }

        for (int c = 0; c < rightUnsharedColumns.getWidth(); c++) {
            Column curr = rightUnsharedColumns.getColumnByIndex(c);
            joined.addColumn(new Column(curr.getName(), curr.getType()));
        }


        for (int lIndex = 0; lIndex < leftSharedColumns.getHeight(); lIndex++) {
            Type leftShared = leftSharedColumns.getColumnByIndex(0).get(lIndex);
            for (int rIndex = 0; rIndex < rightSharedColumns.getHeight(); rIndex++) {
                Type rightShared = rightSharedColumns.getColumnByIndex(0).get(rIndex);
                if (leftShared.equals(rightShared)) {
                    joined.addRow(concat(leftSharedColumns.getRowByIndex(lIndex), rightUnsharedColumns.getRowByIndex(rIndex)));
                }
            }
        }

        return joined;
        //return new Table(leftSharedColumns, leftUnsharedColumns, rightUnsharedColumns);
    }

    private static Table cartesianJoin(Table t1, Table t2) {

        Table leftHalf = new Table(t1.getColumnNames());
        Table rightHalf = new Table(t2.getColumnNames());

        for (int i = 0; i < t1.getHeight(); i++) {
            for (int j = 0; j < t2.getHeight(); j++) {
                leftHalf.addRow(t1.getRowByIndex(i));
            }
        }

        for (int i = 0; i < t1.getHeight(); i++) {
            for (int j = 0; j < t2.getHeight(); j++) {
                rightHalf.addRow(t2.getRowByIndex(j));
            }
        }

        return new Table(leftHalf, rightHalf);
    }

    public static Table joinAll(Table[] tables) {
        Table joined = tables[0];
        for (int i = 1; i < tables.length; i++) {
            joined = TblCommands.join(joined, tables[i]);
        }
        return joined;
    }

    public static Type[] concat(Type[] a, Type[] b) {
        Type[] c = new Type[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}
