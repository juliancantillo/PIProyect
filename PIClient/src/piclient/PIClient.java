package piclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import mailhandler.FrmInbox;
import mailhandler.FrmNewMail;
import userhandler.FrmLogin;
import userhandler.User;

/**
 *
 * @author Julian
 */
public class PIClient extends JFrame implements ActionListener{
    
    private static ServerConfig config;
    private static User usr;
    private static FrmLogin login;
    
    private static Socket mailSocket;
    private static Socket userSocket;
    
    private JSplitPane split, splitMain;
    private JScrollPane inputTxt, outputTxt;
    private JEditorPane inputEditor, outputEditor;
    private JTabbedPane tabs;
    private JButton btnSend;
    private JList lstUsers;
    private JScrollPane scUsers;
    private JButton btnNewMail, btnBrowser;
    private JPanel pnlContent, pnlChat;

    public PIClient(String name) {
        super(name);
        
        int b = 5;
        
        pnlContent = new JPanel();
        pnlContent.setLayout(new BorderLayout(b, b));
        pnlContent.setBorder(BorderFactory.createEmptyBorder(0, b, b, b));
        
        tabs = new JTabbedPane();
        
        splitMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, initUsers(), initChat());
        splitMain.setBorder(null);
        splitMain.setOneTouchExpandable(true);
        
        pnlChat = new JPanel();
        pnlChat.setLayout(new BorderLayout());
        pnlChat.setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
        pnlChat.add(splitMain, BorderLayout.CENTER);
        
        tabs.addTab("Chat", pnlChat);
        tabs.addTab("Correo", initMailer());
        
        pnlContent.add(initToolBar(), BorderLayout.BEFORE_FIRST_LINE);
        pnlContent.add(tabs, BorderLayout.CENTER);
        
        add(pnlContent);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public final JComponent initUsers(){
//        JPanel pnl = new JPanel();
        //String urs[] = {"Julian", "Alberto"};
        
        lstUsers = new JList();
        scUsers = new JScrollPane(lstUsers);
        
        lstUsers.setMinimumSize(new Dimension(150, 300));
        scUsers.setMinimumSize(new Dimension(150, 300));
//        pnl.add(lstUsers);
                
        return scUsers;
    }
    
    public final JToolBar initToolBar(){
        JToolBar toolBar = new JToolBar("Herramientas");
        
        ImageIcon icon = new ImageIcon(getClass().getResource("../icons/mail.png"));
        
        btnNewMail = new JButton(icon);
        btnNewMail.setToolTipText("Nuevo Correo");
        btnNewMail.addActionListener(this);
        
        toolBar.add(btnNewMail);
        
        return toolBar;
    }
    
    public final JPanel initChat(){
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout(1, 1));
        
        inputEditor = new JEditorPane();
        outputEditor = new JEditorPane();
        
        outputEditor.setEditable(false);
        
        inputTxt = new JScrollPane(inputEditor);
        outputTxt = new JScrollPane(outputEditor);
        
        outputTxt.setMinimumSize(new Dimension(400, 200));
        inputTxt.setMinimumSize(new Dimension(400, 80));
        inputTxt.setMaximumSize(new Dimension(400, 120));
        
        inputTxt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputTxt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        outputTxt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputTxt.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        btnSend = new JButton("Enviar");
        
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, outputTxt, inputTxt);
        split.setMinimumSize(new Dimension(300,300));

        split.setBorder(null);
        
        pnl.add(split, BorderLayout.CENTER);
        pnl.add(btnSend, BorderLayout.SOUTH);
        
        return pnl;
    }
    
    public final JPanel initMailer(){
        FrmInbox pnl = new FrmInbox();
        
        return pnl;
    }
    
    public static void setUser(User usr){
        PIClient.usr = usr;        
    }

    public static User getUsr() {
        return usr;
    }

    public static void setConfig(ServerConfig config) {
        PIClient.config = config;
        if(config != null){
            try{
            mailSocket = new Socket(config.getIp(), ServerConfig.SERVER_PORT);
//            userSocket = new Socket(config.getIp(), ServerConfig.USERS_PORT);
            }catch(Exception e){}
        }
    }

    public static ServerConfig getConfig() {
        return config;
    }

    public static Socket getMailSocket() {
        return mailSocket;
    }

    public static Socket getUserSocket() {
        return userSocket;
    }
        
    public static void login(){
        login = new FrmLogin();
        login.login();
    }
    
    public static void startApp(){
        User u = PIClient.getUsr();
        login.close();
        PIClient ob = new PIClient("Bienvenido " + u.getName());
    }
    
    public static ServerConfig loadConfiguration(){
        
        ServerConfig conf = null;
        
        try {
            File f = new File("server.con");
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                
                conf = (ServerConfig) ois.readObject();
                
                ois.close();
                fis.close();
            }
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {}
        
        return conf;
    }
    
    public static void saveConfiguration(ServerConfig conf){
        try {
            File f = new File("server.con");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(conf);
            oos.close();
            fos.close();
        }catch (FileNotFoundException ex) {}
        catch (IOException ex) {}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {}
        
        PIClient.setConfig(PIClient.loadConfiguration());
        
        if(PIClient.getConfig() == null){
            FrmConfigServer cofingFrm = new FrmConfigServer(null);
        }else if(PIClient.getUsr() == null){
            PIClient.login();
        }        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == btnNewMail){
            FrmNewMail mail = new FrmNewMail(this);
        }
        
    }
}
