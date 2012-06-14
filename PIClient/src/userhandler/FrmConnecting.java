/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userhandler;

import animations.ConnectionAnimator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author julianacb
 */
public class FrmConnecting extends JDialog {

    private ConnectionAnimator animation;
    private JLabel text;

    public FrmConnecting(Frame owner) {
        super(owner, "Conectando");
        
        animation = new ConnectionAnimator();
        text = new JLabel("Conectando...");
        
        setLayout(new FlowLayout());
                
        add(animation);
        add(text);
        
        setSize(250,100);
        setVisible(true);
    }

}
