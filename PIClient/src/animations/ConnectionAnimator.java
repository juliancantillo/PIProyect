/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package animations;

/**
 *
 * @author julianacb
 */
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ConnectionAnimator extends JPanel {

    protected ImageIcon imagenes[];
    private int imagenesTotales = 11;
    private int imagenActual = 0;
    private int retrasoAnimacion = 100;
    private Timer temporizadorAnimacion;
    private ManejadorAnimacion manejador;

    public ConnectionAnimator() {
        imagenes = new ImageIcon[imagenesTotales];

        for (int i = 0; i < imagenes.length; i++) {
            imagenes[i] = new ImageIcon(getClass().getResource("connect/connecting" + (i+1) + ".png"));
        }

        manejador = new ManejadorAnimacion();
        temporizadorAnimacion = new Timer(retrasoAnimacion, manejador);
        temporizadorAnimacion.start();

    }

    @Override
    public void paint(Graphics g) {
        imagenes[ imagenActual].paintIcon(this, g, 20, 0);
        imagenActual = (imagenActual + 1) % imagenesTotales;
    }

    public class ManejadorAnimacion implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            repaint();
        }
    }

    public void detenerAnimacion() {
        temporizadorAnimacion.stop();
    }
}