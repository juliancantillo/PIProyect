/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailhandler;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import messages.msgUserMail;

/**
 *
 * @author Julian
 */
public class InboxTable extends AbstractTableModel {

    private ArrayList cache;
    private String[] headers;

    public InboxTable() {
        cache = new ArrayList();
        headers = new String[0];
    }
    
    public InboxTable(msgUserMail mail) {
        cache = new ArrayList();
        loadData(mail);
    }

    @Override
    public String getColumnName(int i) {
        return headers[i];
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public int getRowCount() {
        return cache.size();

    }

    @Override
    public Object getValueAt(int row, int col) {
        return ((String[]) cache.get(row))[col];
    }

    public final void loadData(msgUserMail box) {
        cache = new ArrayList();
        
        headers = box.getHeaders();
        cache = box.getData();
        
        fireTableChanged(null);
    }

}
