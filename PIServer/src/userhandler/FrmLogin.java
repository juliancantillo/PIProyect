/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userhandler;

import dbhandler.DbHandler;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author julianacb
 */
public class FrmLogin extends JFrame implements ActionListener{
    
    private JTabbedPane tabs;
    private JPanel pnlContainer, pnlLogin;
    private JButton btnLogin, btnCancel;
    private JTextField fldUser;
    private JPasswordField fldPass;
    private JLabel lblUser, lblPass;
    private ImageIcon iconPwd;
    

    public FrmLogin() {
        super("Ingreso de Usuarios");
                
        int b = 5;
        pnlContainer = new JPanel();
        pnlContainer.setLayout(new BorderLayout());
        pnlContainer.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        
        tabs = new JTabbedPane();
        
        iconPwd = new ImageIcon(getClass().getResource("../icons/password_s.png"));
        
        
        pnlLogin = new JPanel();
        pnlLogin.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        pnlLogin.setLayout(new BorderLayout(5,5));
        
        pnlLogin.add(pnlLoginFields(), BorderLayout.NORTH);
        pnlLogin.add(pnlLoginButtons(), BorderLayout.SOUTH);
                
        tabs.addTab("Ingresar", iconPwd, pnlLogin, "Ingreso de Usuarios");
        
        pnlContainer.add(tabs, BorderLayout.CENTER);
        add(pnlContainer);
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//TODO Delete this line
        
        //setSize(350, 210);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
                
    }
    
    public final JPanel pnlLoginFields(){
        JPanel panel = new JPanel();
        
        panel.setLayout(new GridLayout(2, 2, 5, 5));
        
        lblUser = new JLabel("Usuario");
        lblPass = new JLabel("Contraseña");
        
        fldUser = new JTextField();
        fldPass = new JPasswordField();
        
        panel.add(lblUser);
        panel.add(fldUser);
        panel.add(lblPass);
        panel.add(fldPass);
                
        return panel;
    }
    
    public final JPanel pnlLoginButtons(){
        JPanel panel = new JPanel();
                
        btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(this);
        
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(this);
        
        panel.add(btnLogin);
        panel.add(btnCancel);
        
        return panel;
    }
    
    public void login(){
        setVisible(true);
    }
    
    private void do_login(){
        String nick = fldUser.getText();
        String pass = new String(fldPass.getPassword());
        User u = DbHandler.getUser(nick, pass);
        if(u != null){
//            IClient.setUser(u);
//            IClient.startApp();
            this.close();
        }else{
            
        }
    }
    
    private boolean validateForm(){
        //TODO Validate Login        
        return true;
    }
    
    public void cleanForm(){
        this.fldUser.setText("");
        this.fldPass.setText("");
    }
    
    public void close(){
        this.setVisible(false);
        this.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLogin){
            do_login();
        }
        if(e.getSource() == btnCancel){
            this.close();
        }
    }
    
}
