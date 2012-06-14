/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbhandler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author julianacb
 */
public class FrmConnect extends JFrame{

    private JLabel lblUser, lblPass, lblHost, lblDb, lblPort;
    private JTextField fldUser, fldHost, fldDb, fldPort;
    private JPasswordField fldPass;
    private JPanel pnlFields, pnlButtons, pnlContainer;
    private ActionListener listener;
    public JButton btnConn, btnClose;
    private String db, host, port, usr, pass;
    
    public FrmConnect(ActionListener listener) {
        
        super("Configuracion de Base de Datos");
        
        this.listener = listener;
        
        
        pnlFields = new JPanel();
        pnlButtons = new JPanel();
        pnlContainer = new JPanel();
        
        pnlContainer.setLayout(new BorderLayout(10,10));
        pnlContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        pnlFields.setLayout(new GridLayout(5, 2, 5, 5));
        pnlFields.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Datos de Conexión", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
        
        lblUser = new JLabel("Usuario");
        lblPass = new JLabel("Contraseña");
        lblHost = new JLabel("Servidor");
        lblPort = new JLabel("Puerto");
        lblDb = new JLabel("Base de Datos");
//                
//        fldUser = new JTextField();
//        fldPass = new JPasswordField();
//        fldHost = new JTextField();
//        fldDb = new JTextField();
//        fldPort = new JTextField("3306");
                
        fldUser = new JTextField("root");
        fldPass = new JPasswordField("gott.29!");
        fldHost = new JTextField("127.0.0.1");
        fldDb = new JTextField("julianacb");
        fldPort = new JTextField("3307");
        
        pnlFields.add(lblHost);
        pnlFields.add(fldHost);
        pnlFields.add(lblPort);
        pnlFields.add(fldPort);
        pnlFields.add(lblUser);
        pnlFields.add(fldUser);
        pnlFields.add(lblPass);
        pnlFields.add(fldPass);
        pnlFields.add(lblDb);
        pnlFields.add(fldDb);
                
        btnConn = new JButton("Conectar");
        btnClose = new JButton("Cancelar");
        
        btnConn.addActionListener(listener);
        btnClose.addActionListener(listener);
        
        pnlButtons.add(btnConn);
        pnlButtons.add(btnClose);
        
        pnlContainer.add(pnlFields, BorderLayout.CENTER);
        pnlContainer.add(pnlButtons, BorderLayout.SOUTH);
        add(pnlContainer);
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
    }
    
    public DbData getConnection(){
        
        DbData conn = null;
        
        db = fldDb.getText();
        usr = fldUser.getText();
        pass = new String(fldPass.getPassword());
        port = fldPort.getText();
        host = fldHost.getText();
        
        if(db.equals("") || usr.equals("") || pass.equals("") || port.equals("") || host.equals("")){
            JOptionPane.showMessageDialog(this, "Falta completar alguno de los campos solicitados","Campos Incompletos", JOptionPane.ERROR_MESSAGE);
        }else{
            conn = new DbData(host, port, db, usr, pass);
        }
        
        return conn;
    }
    
    public void close(){
        setVisible(false);
        dispose();
    }
    
    public void open(){
        setVisible(true);
        setSize(330, 260);
    }
    
}
