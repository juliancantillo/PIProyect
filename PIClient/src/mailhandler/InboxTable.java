/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailhandler;

import dbhandler.DbHandler;
import piclient.PIClient;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Julian
 */
public class InboxTable extends AbstractTableModel {

    private Vector cache;
    private int ColCount;
    private String[] headers;
    private Statement stmt;

    public InboxTable() {
        cache = new Vector();
        stmt = DbHandler.getStatement();
        loadData();
    }

    @Override
    public String getColumnName(int i) {
        return headers[i];
    }

    @Override
    public int getColumnCount() {
        return ColCount;
    }

    @Override
    public int getRowCount() {
        return cache.size();

    }

    @Override
    public Object getValueAt(int row, int col) {
        return ((String[]) cache.elementAt(row))[col];
    }

    public final void loadData() {
        cache = new Vector();
        String user = PIClient.getUsr().getEmail();
        try {
            String query = "SELECT m.mail_from, m.mail_subject, m.mail_date FROM mail as m WHERE m.mail_to = '"+user+"'";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            ColCount = meta.getColumnCount();

            headers = new String[ColCount];
            for (int h = 1; h <= ColCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }
            while (rs.next()) {
                String[] record = new String[ColCount];
                for (int i = 0; i < ColCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                cache.addElement(record);
            }
            fireTableChanged(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Database still can't be loaded");
        }

    }

    public void closeDB() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println("No se pudo cerrar la conexion");
            e.printStackTrace();
        }
    }

}
