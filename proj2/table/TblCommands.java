package table;

/**
 * Created by Arjun on 2/27/2017.
 */
public class TblCommands {

    public Table join(Table t1, Table t2) {
        Table sharedColumns = new Table();
        Table leftUnsharedColumns = new Table();
        Table rightUnsharedColumns = new Table();

        boolean foundShared = false;

        for (String t1ColName : t1.getColumnNames()) {
            foundShared = false;
            for (String t2ColName : t2.getColumnNames()) {
                if (t1.getColumn(t1ColName).equals(t2.getColumn(t2ColName))) {
                    sharedColumns.addColumn(t1.getColumn(t1ColName));
                    foundShared = true;
                }
            }
            if (!foundShared) {
                leftUnsharedColumns.addColumn(t1.getColumn(t1ColName));
            }
        }
        foundShared = false;
        for (String t2ColName : t2.getColumnNames()) {
            foundShared = false;
            for (String t1ColName : t1.getColumnNames()) {
                if (t1.getColumn(t1ColName).equals(t2.getColumn(t2ColName))) {
                    foundShared = true;
                }
            }
            if (!foundShared) {
                rightUnsharedColumns.addColumn(t2.getColumn(t2ColName));
            }
        }
        return new Table(sharedColumns, leftUnsharedColumns, rightUnsharedColumns);
    }

}
