/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userhandler;

import guihelpers.GBHelper;
import guihelpers.Gap;
import iclient.FrmConfigServer;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import loginservice.LoginService;
import messages.msgLogin;

/**
 *
 * @author julianacb
 */
public class FrmLogin extends JFrame implements ActionListener, KeyListener{
    
    private JPanel pnlContainer, pnlLogin;
    private JButton btnLogin, btnCancel, btnConfigServer;
    private JTextField fldUser;
    private JPasswordField fldPass;
    private JLabel lblUser, lblPass, lblIcon;
    private ImageIcon iconPwd;
    private static final int GAP = 5;

    public FrmLogin() {
        super("Ingreso de Usuarios");
                
        int b = 5;
        pnlContainer = new JPanel();
        pnlContainer.setLayout(new BorderLayout());
        pnlContainer.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        
        iconPwd = new ImageIcon(getClass().getResource("../icons/password.png"));
        
        pnlLogin = new JPanel();
        pnlLogin.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        pnlLogin.setLayout(new BorderLayout(5,5));
        
        pnlLogin.add(pnlLoginFields(), BorderLayout.NORTH);
        pnlLogin.add(pnlLoginButtons(), BorderLayout.SOUTH);
        
        pnlContainer.add(pnlLogin, BorderLayout.CENTER);
        add(pnlContainer);
        
        pnlContainer.addKeyListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO Delete this line
        
        setAlwaysOnTop(true);
        setSize(400, 210);
        setResizable(false);
        setLocationRelativeTo(null);
                
    }
    
    public final JPanel pnlLoginFields(){
        JPanel panel = new JPanel();
                
        int b = 5;
        
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        
        lblIcon = new JLabel(iconPwd);
        
        lblUser = new JLabel("Usuario");
        lblPass = new JLabel("Contraseña");
        
        fldUser = new JTextField();
        fldPass = new JPasswordField();
        
        GBHelper pos = new GBHelper();
        
        panel.add(lblIcon, pos.height(3).expandH().align(GBHelper.NORTH));
        
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(lblUser, pos.nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(fldUser, pos.nextCol().expandW());
        
        panel.add(new Gap(GAP) , pos.nextRow());
        
        panel.add(lblPass, pos.nextRow().nextCol().nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(fldPass, pos.nextCol().expandW());
        
        panel.addKeyListener(this);

        return panel;
    }
    
    public final JPanel pnlLoginButtons(){
        JPanel panel = new JPanel();
                
        btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(this);
        
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(this);
        
        btnConfigServer = new JButton("Configurar Servidor");
        btnConfigServer.addActionListener(this);
        
        panel.add(btnConfigServer);
        panel.add(new Gap(50));
        panel.add(btnLogin);
        panel.add(btnCancel);
        
        return panel;
    }
    
    private boolean validateForm(){
        String user = fldUser.getText();
        String pass = new String(fldPass.getPassword());
        
        if(user.equals("") && pass.equals("")){
            JOptionPane.showMessageDialog(this, "El campo usuario y/o contraseña esta vacio", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public void cleanForm(){
        this.fldUser.setText("");
        this.fldPass.setText("");
    }
    
    public void login(){
        setVisible(true);
    }
        
    private void startLoginService(){
        String user = fldUser.getText();
        String pass = new String(fldPass.getPassword());
        
        if(validateForm()){
            msgLogin auth = new msgLogin(user, pass);

            new LoginService(auth).start();
        }
    }
        
    public void close(){
        this.setVisible(false);
        this.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLogin){
            startLoginService();
        }
        if(e.getSource() == btnCancel){
            System.exit(0);
        }
        if(e.getSource() == btnConfigServer){
            FrmConfigServer config = new FrmConfigServer(this);
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.print(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.print(e.getKeyCode());
    }
    
}
