/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piclient;

import guihelpers.GBHelper;
import guihelpers.Gap;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author julianacb
 */
public class FrmConfigServer extends JDialog implements ActionListener{
    
    private JLabel lblIP;
    private JTextField fldIP;
    private JButton btnSave, btnCancel;
    private static final int GAP = 5;

    public FrmConfigServer(Frame owner) {
        super(owner,"Configuracion del Servidor");
        
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        add(initForm());
        setSize(300,110);
        setLocationRelativeTo(owner);
        setVisible(true);
    }
      
    private JPanel initForm(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(GAP*2, GAP*2, GAP*2, GAP*2));
        
        lblIP = new JLabel("Direccion IP");
        fldIP = new JTextField(getSavedIp());
        
        btnSave = new JButton("Guardar");
        btnSave.addActionListener(this);
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(this);
        
        GBHelper pos = new GBHelper();
        
        p.add(lblIP, pos);
        p.add(new Gap(GAP), pos.nextCol());
        p.add(fldIP, pos.nextCol().expandW());
        
        p.add(new Gap(GAP), pos.nextRow().expandH());
        
        p.add(btnCancel, pos.nextRow().align(GBHelper.WEST));
        p.add(new Gap(GAP), pos.nextCol());
        p.add(btnSave, pos.nextCol().align(GBHelper.EAST));
        
        return p;
    }
    
    private String getSavedIp(){
        if(PIClient.getConfig() != null){
            return PIClient.getConfig().getIp();
        }
        return "";
    }
    
    public ServerConfig getConfig(){
        String ip = fldIP.getText();
        if(!ip.equals("")){
            ServerConfig conf = new ServerConfig(ip);
            return conf;
        }else{
            JOptionPane.showMessageDialog(this, "No ha digitado una Dirección IP válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnCancel){
            if(getOwner() != null){
                dispose();
            }else{
                System.exit(0);
            }
        }
        if(e.getSource() == btnSave){
            if(this.getConfig() != null){
                PIClient.saveConfiguration(this.getConfig());
                PIClient.setConfig(this.getConfig());
                PIClient.login();
            }
        }
    }
    
}
