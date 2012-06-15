package dbhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import messages.msgEmail;
import messages.msgLogin;
import messages.msgUserMail;
import userhandler.User;
import piserver.PIServer;

/**
 *
 * @author julianacb
 */
public class DbHandler implements ActionListener {

    private static Connection db;
    private static ResultSet rs;
    private static Statement stmt;
    private static DbData data;
    private static FrmConnect frmConnect;

    public DbHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            PIServer.addLog("Driver de MySQL no encontrado");
        }

        data = loadConn();
        if (data == null) {
            frmConnect = new FrmConnect(this);
            frmConnect.open();
        } else {
            connect(data);
        }

    }

    public final void connect(DbData dbData) {
        try {
            PIServer.addLog("Conectando...\n");
            db = DriverManager.getConnection("jdbc:mysql://" + dbData.host + ":" + dbData.port + "/" + dbData.db + "", dbData.user, dbData.password);
            stmt = db.createStatement();

            initTables();

            PIServer.addLog("Conexion Exitosa\n");
        } catch (SQLException e) {
            PIServer.addLog("Error de MySQL: " + e.getMessage());
        }
    }

    public final DbData loadConn() {

        DbData dbData = null;

        try {
            File f = new File("database.con");
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                dbData = (DbData) ois.readObject();
                ois.close();
                fis.close();
                PIServer.addLog("Cargado archivo de Configuración\n");
            }
        } catch (FileNotFoundException ex) {
            PIServer.addLog("No se encontro");
        } catch (IOException ex) {
            PIServer.addLog("IO " + ex);
        } catch (ClassNotFoundException ex) {
        }

        return dbData;
    }

    public final void saveConn(DbData dbData) {
        try {
            File f = new File("database.con");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dbData);
            oos.close();
            fos.close();
        } catch (FileNotFoundException ex) {
            PIServer.addError("No existe");
        } catch (IOException ex) {
            PIServer.addError("Error al abrir archivo: " + ex);
        }
    }

    /**
     * Metodo para inicializar las tablas necesarias para el juego en caso de no
     * existir
     */
    private static void initTables() {
        try {
            String usersTable = "CREATE TABLE IF NOT EXISTS users (`id` INT NOT NULL AUTO_INCREMENT , nickname VARCHAR(20), password VARCHAR(40), name VARCHAR(20), email VARCHAR(40),PRIMARY KEY (`id`));";
            String mailsTable = "CREATE TABLE IF NOT EXISTS `mail` ( `id` int(11) NOT NULL AUTO_INCREMENT, `mail_from` varchar(45) DEFAULT NULL, `mail_to` varchar(45) DEFAULT NULL, `mail_subject` varchar(100) DEFAULT NULL, `mail_body` longtext, `mail_date` datetime DEFAULT NULL, `mail_readed` tinyint(1) DEFAULT NULL, PRIMARY KEY (`id`));";
            stmt.executeUpdate(usersTable);
            stmt.executeUpdate(mailsTable);
            PIServer.addLog("Se han creado las tablas, o ya existían");
        } catch (SQLException ex) {
            PIServer.addLog("Error creando tablas: " + ex);
        }
    }

    public static void createUser(User user) {
        try {
            String usersTable = "INSERT INTO users (nickname, password, name, email) VALUES ('" + user.getNickname() + "','" + user.getPassword() + "','" + user.getName() + "','" + user.getEmail() + "')";
            stmt.executeUpdate(usersTable);
            PIServer.addLog("Se ha creado el usuario");
        } catch (SQLException ex) {
            PIServer.addLog("Insertando usuario: " + ex);
        }
    }

    public static boolean incomingMail(msgEmail mail) {
        if (userExists("email", mail.getTo())) {

            try {
                String usersTable = "INSERT INTO mail (mail_from, mail_to, mail_subject, mail_body, mail_date, mail_readed) VALUES ('" + mail.getFrom() + "','" + mail.getTo() + "','" + mail.getSubject() + "','" + mail.getBody() + "','" + mail.getDate() + "', 0)";
                stmt.executeUpdate(usersTable);
                PIServer.addLog("Se ha insertado un nuevo correo");

                return true;
            } catch (SQLException ex) {
                PIServer.addLog("Insertando correo: " + ex);
            }
        }

        return false;
    }

    public static msgUserMail getMail(String user, boolean incomming) {
        
        String query;
        int ColCount;
        msgUserMail mail = null;
        String[] headers;
        ArrayList data = new ArrayList();
        
        if(incomming){
            query = "SELECT * FROM mail WHERE mail_to = '" + user + "';";
        }else{
            query = "SELECT * FROM mail WHERE mail_from = '" + user + "';";
        }
                
        try {
            rs = stmt.executeQuery(query);
            ResultSetMetaData meta = rs.getMetaData();
            ColCount = meta.getColumnCount();

            headers = new String[ColCount];
            for (int h = 1; h <= ColCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }
            while (rs.next()) {
                String[] record = new String[ColCount];
                for (int i = 0; i < ColCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                data.add(record);
            }
            
            mail = new msgUserMail(headers,data);

        } catch (SQLException ex) {
            PIServer.addLog("Obteniendo correo: " + ex);
        }

        return mail;
    }

    public static boolean userExists(String field, String data) {
        try {
            String query = "SELECT * FROM users WHERE " + field + " = '" + data + "';";

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                return true;
            } else {
                PIServer.addLog("Usuario con datos " + data + "en el campo " + field + "no encontrado");
            }

        } catch (SQLException ex) {
            PIServer.addLog("Obteniendo usuario: " + ex);
        }

        return false;
    }

    public static Statement getStatement() {
        return stmt;
    }

    public static User getUser(msgLogin login) {
        return getUser(login.getUser(), login.getPass());
    }

    public static User getUser(String nick, String pass) {
        User usr = null;
        try {
            String query = "SELECT * FROM users WHERE nickname = '" + nick + "' AND password = '" + pass + "'";
            String nickname, name, pwd, email, id;

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                rs.first();

                id = rs.getString("id");
                nickname = rs.getString("nickname");
                name = rs.getString("name");
                pwd = rs.getString("password");
                email = rs.getString("email");

                usr = new User(id, nickname, name, pwd, email);
            } else {
                PIServer.addLog("Usuario " + nick + "no encontrado");
            }

        } catch (SQLException ex) {
            PIServer.addLog("Obteniendo usuario: " + ex);
        }
        return usr;
    }

//    public static void main(String[] args) {
//        // TODO code application logic here
//        DbHandler dbObj = new DbHandler();
//    }
    public static Connection getDb() {
        return db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmConnect.btnConn) {
            data = frmConnect.getConnection();
            connect(data);
            saveConn(data);
        }
        if (e.getSource() == frmConnect.btnClose) {
            frmConnect.close();
        }
    }
}
