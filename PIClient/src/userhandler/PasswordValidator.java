/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userhandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;

/**
 *
 * @author julianacb
 */
public class PasswordValidator extends JComponent implements KeyListener {

    private static final String NOOFCHAR = "^.*(?=.{6,}).*$";
    private static final String CHECKSPACE = "\\s";
    private static final String BEST = "^.*(?=.{6,})(?=.*[A-Z])(?=.*[\\d])(?=.*[\\W]).*$";
    private static final String STRONG = "^[a-zA-Z\\d\\W_]*(?=[a-zA-Z\\d\\W_]{6,})(((?=[a-zA-Z\\d\\W_]*[A-Z])(?=[a-zA-Z\\d\\W_]*[\\d]))|((?=[a-zA-Z\\d\\W_]*[A-Z])(?=[a-zA-Z\\d\\W_]*[\\W_]))|((?=[a-zA-Z\\d\\W_]*[\\d])(?=[a-zA-Z\\d\\W_]*[\\W_])))[a-zA-Z\\d\\W_]*$";
    private static final String WEAK = "^[a-zA-Z\\d\\W_]*(?=[a-zA-Z\\d\\W_]{6,})(?=[a-zA-Z\\d\\W_]*[A-Z]|[a-zA-Z\\d\\W_]*[\\d]|[a-zA-Z\\d\\W_]*[\\W_])[a-zA-Z\\d\\W_]*$";
    private static final String BAD = "^((^[a-z]{6,}$)|(^[A-Z]{6,}$)|(^[\\d]{6,}$)|(^[\\W_]{6,}$))$";
    private JPasswordField fldPass;
    private JProgressBar scoreBar;
    private int Score = 0;
    private Pattern patNoOfChar, patCheckSpace, patBest, patStrong, patWeak, patBad;
    private Matcher matNoOfChar, matCheckSpace, matBest, matStrong, matWeak, matBad;
    private boolean valid;
    private String error = "";

    public PasswordValidator(JPasswordField fldPass) {
        patNoOfChar = Pattern.compile(NOOFCHAR);
        patCheckSpace = Pattern.compile(CHECKSPACE);
        patStrong = Pattern.compile(STRONG);
        patBest = Pattern.compile(BEST);
        patBad = Pattern.compile(BAD);
        patWeak = Pattern.compile(WEAK);
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.fldPass = fldPass;
        scoreBar = new JProgressBar(0, 40);
        
        scoreBar.setToolTipText("Fortaleza de la Contraseña");
        
        fldPass.addKeyListener(this);

        add(scoreBar);

    }

    public int getStrength() {
        
        String hex = new String(fldPass.getPassword());
        int score = 0;
        
        matCheckSpace = patCheckSpace.matcher(hex);
        matNoOfChar = patNoOfChar.matcher(hex);
        matWeak = patWeak.matcher(hex);
        matBad = patBad.matcher(hex);
        matBest = patBest.matcher(hex);
        matStrong = patStrong.matcher(hex);
	
        if(matCheckSpace.matches()){
            score = -1;
            valid = false;
            error = "La contraseña no debe tener espacios";
        }else if(!matNoOfChar.matches()){
            score = 0;
            valid = false;
            error = "La contraseña debe tener al menos 6 caracteres.";
        }else if(matBest.matches()){
            score = 30;
            valid = true;
        }else if(matStrong.matches()){
            score = 40;
            valid = true;
        }else if(matWeak.matches() && !matBad.matches()){
            score = 20;
            valid = true;
        }else if(matBad.matches()){
            score = 10;
            valid = true;
        }

        return score;
    }
    
    public boolean isValidPassword(){
        return valid;
    }

    public String getWeakness() {
        if(valid){
            error = "";
        }
        return error;
    }
    
    public boolean matches(JPasswordField fldConf){
        String pass = new String(fldPass.getPassword());
        String conf = new String(fldConf.getPassword());
        if(pass == null ? conf == null : pass.equals(conf)){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Score = getStrength();
        if(Score < 0){
            Container parent = this.getParent();
            JOptionPane.showMessageDialog(parent, "La contraseña no debe contener espacios", "Error en la contraseña", JOptionPane.ERROR_MESSAGE);
        }else{
            scoreBar.setValue(Score);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }
}
