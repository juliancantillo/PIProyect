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
import javax.swing.border.EtchedBorder;

/**
 *
 * @author julianacb
 */
public class FrmNewUser extends JPanel implements ActionListener{
    
    private JTextField fldNick, fldName, fldEmail;
    private JPasswordField fldPwd, fldPwdConf;
    private PasswordValidator passValid; //PRUEBA!!
    private JLabel lblNick, lblPwd, lblPwdStrenght, lblPwdConf, lblName, lblEmail;
    private ImageIcon iconNew;
    private EmailValidator validator;
    private JButton btnSave, btnCancel;

    public FrmNewUser() {
        
        int b = 10;
        
        setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        setLayout(new BorderLayout(5,5));
        
        add(pnlNewUser(), BorderLayout.CENTER);
        add(pnlNewUserButtons(), BorderLayout.SOUTH);
    }
        
    public final JPanel pnlNewUser(){
        JPanel panel = new JPanel();
        JPanel panelBorde = new JPanel();
        
        int b = 5;
        
        panel.setLayout(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        
        panelBorde.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        fldName = new JTextField();
        fldNick = new JTextField();
        fldEmail = new JTextField();
        fldPwd = new JPasswordField();
        fldPwdConf = new JPasswordField();
        passValid = new PasswordValidator(fldPwd);
        
        lblName = new JLabel("Nombre");
        lblNick = new JLabel("Usuario");
        lblEmail = new JLabel("E-mail");
        lblPwd = new JLabel("Contraseña");
        lblPwdStrenght = new JLabel(" ");
        lblPwdConf = new JLabel("Confirmar Contraseña");
                
        panel.add(lblName);
        panel.add(fldName);
        panel.add(lblNick);
        panel.add(fldNick);
        panel.add(lblEmail);
        panel.add(fldEmail);
        panel.add(lblPwd);
        panel.add(fldPwd);
        panel.add(lblPwdStrenght);
        panel.add(passValid);
        panel.add(lblPwdConf);
        panel.add(fldPwdConf);
        
        panelBorde.add(panel);
        
        return panelBorde;
    }
    
    public final JPanel pnlNewUserButtons(){
        JPanel panel = new JPanel();
                
        btnSave = new JButton("Guardar");
        btnSave.addActionListener(this);
                
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(this);
        
        panel.add(btnSave);
        panel.add(btnCancel);
        
        return panel;
    }
    
    public User getUserData(){
        User usr = null;
        String name, nick, email, pass;
        
        if(validateForm()){    
            email = fldEmail.getText();
            name = fldName.getText();
            nick = fldNick.getText();
            pass = new String(fldPwd.getPassword());
            
            usr = new User(null, nick, name, pass, email);
        }
        
        return usr;
    }
    
    private boolean validateForm(){
        String name, nick, email;
        email = fldEmail.getText();
        name = fldName.getText();
        nick = fldNick.getText();
        
        validator = new EmailValidator();
        
        if(name.equals("")){
            JOptionPane.showMessageDialog(this, "El campo Nombre no debe estar vacio", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }else if(!validator.validate(email)){
            JOptionPane.showMessageDialog(this, "El campo E-mail no es una direccion de correo valida", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }else if(!passValid.isValidPassword()){
            JOptionPane.showMessageDialog(this, passValid.getWeakness(), "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }else if(!passValid.matches(fldPwdConf)){
            JOptionPane.showMessageDialog(this, "Las contraseñas no coninciden", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public void cleanForm(){
        this.fldName.setText("");
        this.fldNick.setText("");
        this.fldEmail.setText("");
        this.fldPwd.setText("");
        this.fldPwdConf.setText("");
    }
        
    public void createUser(User usr){
        DbHandler.createUser(usr);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnSave){
            createUser(getUserData());
            JOptionPane.showMessageDialog(this, "Registro exitoso\nAhora puede ingresar", "Login", JOptionPane.INFORMATION_MESSAGE);
            cleanForm();
        }
    }
    
}
