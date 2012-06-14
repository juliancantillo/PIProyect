package dbhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import userhandler.User;

/**
 * 
 * @author julianacb
 */
public class DbHandler implements ActionListener{
    
    private static Connection db;
    private static ResultSet rs;
    private static Statement stmt;
    private static DbData data;
    private static FrmConnect frmConnect;

    public DbHandler() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print("Driver de MySQL no encontrado");
        }
        
        data = loadConn();
        if(data == null){
            frmConnect = new FrmConnect(this);
            frmConnect.open();
        }else{
            connect(data);
        }
          
    }
    
    public final void connect(DbData dbData){
        try{
            System.out.print("Conectando...\n");
            db = DriverManager.getConnection("jdbc:mysql://"+dbData.host+":"+dbData.port+"/"+dbData.db+"", dbData.user, dbData.password);
            stmt = db.createStatement();
            
            initTables();
            
            System.out.print("Conexion Exitosa\n");
        }catch(SQLException e){
            System.out.print("Error de MySQL: "+ e.getMessage());
        }
    }
    
    public final DbData loadConn(){
        
        DbData dbData = null;
        
        try {
            File f = new File("database.con");
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                dbData = (DbData) ois.readObject();
                ois.close();
                fis.close();
                System.out.print("Cargado");
            }
        } catch (FileNotFoundException ex) {
            System.out.print("No se encontro");
        } catch (IOException ex) {
            System.out.print("IO " + ex);
        } catch (ClassNotFoundException ex) {}
        
        return dbData;
    }
    
    public final void saveConn(DbData dbData){
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
            System.err.println("No existe");
        } catch (IOException ex) {
            System.err.println("Error al abrir archivo: " + ex);
        }
    }
    
    /**
     * Metodo para inicializar las tablas necesarias para el juego en caso de no existir
     */
    private static void initTables(){
        try {
            String usersTable = "CREATE TABLE IF NOT EXISTS users (`id` INT NOT NULL AUTO_INCREMENT , nickname VARCHAR(20), password VARCHAR(40), name VARCHAR(20), email VARCHAR(40),PRIMARY KEY (`id`));";
            String mailsTable = "CREATE TABLE IF NOT EXISTS `mail` ( `id` int(11) NOT NULL AUTO_INCREMENT, `from` varchar(45) DEFAULT NULL, `to` varchar(45) DEFAULT NULL, `subject` varchar(100) DEFAULT NULL, `body` longtext, `date` datetime DEFAULT NULL, `readed` tinyint(1) DEFAULT NULL, PRIMARY KEY (`id`));";
            stmt.executeUpdate(usersTable);
            System.out.println("Se han creado las tablas, o ya existían");
        } catch (SQLException ex) {
            System.out.println("Error creando tablas: " + ex);
        }
    }
    
    public static void createUser(User user){
        try {
            String usersTable = "INSERT INTO users (nickname, password, name, email) VALUES ('"+user.getNickname()+"','"+user.getPassword()+"','"+user.getName()+"','"+user.getEmail()+"')";
            stmt.executeUpdate(usersTable);
            System.out.println("Se ha creado el usuario");
        } catch (SQLException ex) {
            System.out.println("Insertadon usuario: " + ex);
        }
    }

    public static Statement getStatement() {
        return stmt;
    }
    
    public static User getUser(String nick, String pass){
        User usr = null;
        try {
            String query = "SELECT * FROM users WHERE nickname = '"+nick+"' AND password = '"+pass+"'";
            String nickname, name, pwd, email, id;
            
            rs = stmt.executeQuery(query);
            rs.first();
            
            id = rs.getString("id");
            nickname = rs.getString("nickname");
            name = rs.getString("name");
            pwd = rs.getString("password");
            email = rs.getString("email");
            
            
            usr = new User(id, nickname, name, pwd, email );
        } catch (SQLException ex) {
            System.out.println("Obteniendo usuario: " + ex);
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
        if(e.getSource() == frmConnect.btnConn){
            data = frmConnect.getConnection();
            connect(data);
            saveConn(data);
        }
        if(e.getSource() == frmConnect.btnClose){
            frmConnect.close();
        }
    }
    
}
