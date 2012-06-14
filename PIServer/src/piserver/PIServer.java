/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import MailService.MailService;
import UsersService.UsersService;
import dbhandler.DbHandler;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import userhandler.FrmNewUser;

/**
 *
 * @author julianacb
 */
public class PIServer extends JFrame implements ActionListener{
    
    private static JTextArea dbLog;
    private JScrollPane scLog;
    private static DbHandler db;
    private JMenu mnUsuarios;
    private JMenuItem miNuevoUsuario;

    public PIServer() {
        super("Servidor");
        
        initGUI();
        
        setJMenuBar(initMenuBar());
        
        setSize(400,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public final void initGUI(){
        dbLog = new JTextArea();
        scLog = new JScrollPane(dbLog);
        
        dbLog.setBackground(Color.black);
        
        add(scLog);
    }
    
    public final JMenuBar initMenuBar(){
        JMenuBar m = new JMenuBar();
        
        mnUsuarios = new JMenu("Usuarios");
        
        miNuevoUsuario = new JMenuItem("Nuevo Usuario");
        miNuevoUsuario.addActionListener(this);
        
        mnUsuarios.add(miNuevoUsuario);
        
        m.add(mnUsuarios);
        
        return m;
    }
    
    public static void addLog(String txt){
        dbLog.setForeground(Color.white);
        dbLog.append(txt + "\n");
    }
    
    public static void addError(String txt){
        dbLog.setForeground(Color.red);
        dbLog.append(txt + "\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {}
        
        PIServer window = new PIServer();
        
        if(db == null){
            db = new DbHandler();
        }
        
        MailService mailService = new MailService();
        mailService.start();
        
        UsersService userService = new UsersService();
        userService.start();
                
        // TODO code application logic here
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == miNuevoUsuario){
            JDialog dlgNewUser = new JDialog(this, "Nuevo Usuario");
            dlgNewUser.add(new FrmNewUser());
            dlgNewUser.pack();
            dlgNewUser.setVisible(true);
        }
    }
}
