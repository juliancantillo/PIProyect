/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailhandler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import messages.msgUserMail;

/**
 *
 * @author Julian
 */
public class FrmInbox extends JPanel{
    
    private JTable tblInbox;
    private InboxTable tblModel;
    private JScrollPane scPane;

    public FrmInbox() {
        tblModel = new InboxTable();
        tblInbox = new JTable(tblModel);
        scPane = new JScrollPane(tblInbox);
        
        add(scPane);
    }
    
    public void updateTable(msgUserMail mail){
        tblModel.loadData(mail);
    }
    
}
