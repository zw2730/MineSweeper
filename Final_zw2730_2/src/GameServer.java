import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class GameServer  {
    private static String url;
    private ObjectInputStream inputFromClient;
    //Game serverGame;
    public static void main(String args[]){
        new GameServer();
    }
    public GameServer(){

        DataForStorage storeData;
        url = "jdbc:sqlite:assignment.db";
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server started ");

            while (true) {
                // Listen for a new connection request
                Socket socket = serverSocket.accept();
                // Create an input stream from the socket
//                inputFromClient = new ObjectInputStream(socket.getInputStream());
//
//                // Read from input
//                Object object = inputFromClient.readObject();
//
//                // Write to the file
//                //StudentAddress s = (StudentAddress)object;
//                storeData = (DataForStorage) object;
//                int id = storeGame(storeData);
//                Socket socketback = new Socket("localhost",8003);
//                DataOutputStream toClient = new DataOutputStream(socketback.getOutputStream());
//                toClient.writeInt(id);
//                socketback.close();
                new Thread(new HandleClient(socket)).start();
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                inputFromClient.close();
                //outputToFile.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //url = "jdbc:sqlite:assignment.db";
//        try {
//            storeGame();
//        }

        //catch (Exception e) { System.out.println(e); }
    }

    private int storeGame(DataForStorage storeData)

    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        PreparedStatement preparedStatement;
        Connection connection = null;
        int Id=-1;
        try {
            connection = DriverManager.getConnection(url);
            ResultSet result;

            String s = "INSERT INTO Gamedata(objectdata) VALUES(?)";

            out = new ObjectOutputStream(bos);
            out.writeObject(storeData);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setBytes(1, yourBytes);
            //preparedStatement.setString(1, "Test3");
            //result = stmt.executeQuery(s);
            preparedStatement.execute();
            String getId = "SELECT MAX(id) FROM Gamedata";
            preparedStatement = connection.prepareStatement(getId);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            Id = rs.getInt(1);
            //return Id;
//            String queryString = "SELECT objectdata FROM Servergame WHERE name = ?";
//            preparedStatement = connection.prepareStatement(queryString);
//            preparedStatement.setString(1, "Test3");
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.next();
//
//            byte[] buf = rs.getBytes(1);
//            ObjectInputStream objectIn = null;
//            if (buf != null)
//                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
//
//            Object deSerializedObject = objectIn.readObject();
//            DataForStorage receivedObject = (DataForStorage) deSerializedObject;
//            rs.close();
            preparedStatement.close();
            //Game loadGame = new Game(receivedObject);
            //loadGame.currentGame.setVisible(true);

        } // try

        catch (Exception sql) { sql.printStackTrace(); }
        return Id;
    }

    class HandleClient implements Runnable{
        private Socket socket;
        public HandleClient(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try{
                inputFromClient = new ObjectInputStream(socket.getInputStream());

                // Read from input
                Object object = inputFromClient.readObject();

                // Write to the file
                //StudentAddress s = (StudentAddress)object;
                DataForStorage storeData = (DataForStorage) object;
                int id = storeGame(storeData);
                //Socket socketback = new Socket("localhost",8003);
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                toClient.writeInt(id);
                socket.shutdownOutput();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
