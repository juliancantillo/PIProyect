package chathandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author julianacb
 */
public class FrmPrivateChat extends JDialog implements ActionListener{
    
    private JSplitPane split;
    private JScrollPane inputTxt, outputTxt;
    private JEditorPane inputEditor, outputEditor;
    private JButton btnSend;
    private JPanel pnlContent;

    public FrmPrivateChat(Frame owner) {
        super(owner);
        
        int b = 5;
        
        pnlContent = new JPanel();
        pnlContent.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        pnlContent.setLayout(new BorderLayout(5, 5));
        
        inputEditor = new JEditorPane();
        outputEditor = new JEditorPane();
        
        outputEditor.setEditable(false);
        
        inputTxt = new JScrollPane(inputEditor);
        outputTxt = new JScrollPane(outputEditor);
        
        outputTxt.setMinimumSize(new Dimension(250, 200));
        inputTxt.setMinimumSize(new Dimension(250, 100));
        
        inputTxt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputTxt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        outputTxt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputTxt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        btnSend = new JButton("Enviar");
        
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, outputTxt, inputTxt);
        split.setMinimumSize(new Dimension(300,300));
        
        pnlContent.add(split, BorderLayout.CENTER);
        pnlContent.add(btnSend, BorderLayout.SOUTH);
        
        add(pnlContent);
                
        setVisible(true);
        setSize(260, 400);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
