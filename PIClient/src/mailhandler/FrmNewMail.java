/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailhandler;

import mailservice.MailSender;
import guihelpers.GBHelper;
import guihelpers.Gap;
import piclient.PIClient;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import messages.msgEmail;

/**
 *
 * @author Julian
 */
public class FrmNewMail extends JDialog implements ActionListener{
        
    private JButton btnSend, btnAttach;
    private JLabel lblTo, lblSubject, lblAttach;
    private JTextField fldTo, fldSubject;
    private JPanel pnlContent;
    private JEditorPane txtBody;
    private JScrollPane scBody;
    private final static int GAP = 5;

    public FrmNewMail(Frame owner) {
        super(owner, "Nuevo Correo");
        
        int b = 5;
        
        pnlContent = new JPanel();
        pnlContent.setLayout(new BorderLayout(b, b));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        
        pnlContent.add(createFields(), BorderLayout.CENTER);
        pnlContent.add(initToolBar(), BorderLayout.NORTH);
        
        add(pnlContent);
        
        setSize(400, 300);
        setVisible(true);
    }
    
    public final JToolBar initToolBar(){
        JToolBar toolBar = new JToolBar("Herramientas");
        
        ImageIcon icon = new ImageIcon(getClass().getResource("../icons/mail.png"));
        
        btnSend = new JButton("Enviar Correo",icon);
        btnSend.addActionListener(this);
        
        toolBar.add(btnSend);
        
        return toolBar;
    }
    
    public final JPanel createFields(){
        JPanel pnl = new JPanel();
        
        lblTo = new JLabel("Para");
        lblSubject = new JLabel("Asunto");
        
        fldTo = new JTextField();
        fldSubject = new JTextField();
        
        txtBody = new JEditorPane();
        scBody = new JScrollPane(txtBody);
        
        pnl.setLayout(new GridBagLayout());
        GBHelper pos = new GBHelper();
        
        pnl.add(lblTo, pos);
        pnl.add(new Gap(GAP), pos.nextCol());
        pnl.add(fldTo , pos.nextCol().expandW());
        
        pnl.add(new Gap(GAP) , pos.nextRow());
        
        pnl.add(lblSubject, pos.nextRow());
        pnl.add(new Gap(GAP), pos.nextCol());
        pnl.add(fldSubject , pos.nextCol().expandW());
        
        pnl.add(new Gap(GAP) , pos.nextRow());

        pnl.add(scBody, pos.nextRow().width().expandH());
        
        return pnl;
    }
    
    private msgEmail createMail(){
        String from, to, subject, body, date;
        
        from = PIClient.getUsr().getEmail();
        to = fldTo.getText();
        subject = fldSubject.getText();
        body = txtBody.getText();
        
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = sdf.format(dt);

        
        msgEmail mail = new msgEmail(from, to, subject, body, date);
        
        return mail;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSend){
            MailSender sendMail = new MailSender(createMail());
            sendMail.start();
        }
    }
    
}
