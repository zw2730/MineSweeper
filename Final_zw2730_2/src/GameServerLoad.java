import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class GameServerLoad  {
    private static String url;
    private DataInputStream inputFromClient;
    private ObjectOutputStream outputToClient;
    //Game serverGame;
    public static void main(String args[]){
        new GameServerLoad();
    }
    public GameServerLoad(){

        DataForStorage storeData;
        url = "jdbc:sqlite:assignment.db";
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8001);
            System.out.println("Server started ");

            while (true) {
                // Listen for a new connection request
                Socket socket = serverSocket.accept();
                // Create an input stream from the socket
//                inputFromClient = new DataInputStream(socket.getInputStream());
//
//                // Read from input
//                int id = inputFromClient.readInt();
//
//                // Write to the file
//                //StudentAddress s = (StudentAddress)object;
//                //storeData = (DataForStorage) object;
//                DataForStorage gameData = loadGame(id);
//                Socket socketback = new Socket("localhost",8002);
//                ObjectOutputStream toClient = new ObjectOutputStream(socketback.getOutputStream());
//                toClient.writeObject(gameData);
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

    private DataForStorage loadGame(int id)

    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        PreparedStatement preparedStatement;
        Connection connection = null;
        DataForStorage receivedObject = null;
        try {
            connection = DriverManager.getConnection(url);
            ResultSet result;

//            String s = "INSERT INTO Gamedata(objectdata) VALUES(?)";
//
//            out = new ObjectOutputStream(bos);
//            out.writeObject(storeData);
//            out.flush();
//            byte[] yourBytes = bos.toByteArray();
//            preparedStatement = connection.prepareStatement(s);
//            preparedStatement.setBytes(1, yourBytes);
//            //preparedStatement.setString(1, "Test3");
//            //result = stmt.executeQuery(s);
//            preparedStatement.execute();

            String queryString = "SELECT objectdata FROM Gamedata WHERE id = ?";
            preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null)
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));

            Object deSerializedObject = objectIn.readObject();
            receivedObject = (DataForStorage) deSerializedObject;
            rs.close();
            preparedStatement.close();
            //Game loadGame = new Game(receivedObject);
            //loadGame.currentGame.setVisible(true);

        } // try

        catch (Exception sql) { sql.printStackTrace(); }
        return receivedObject;

    }
    class HandleClient implements Runnable{
        private Socket socket;
        public HandleClient(Socket socket){
            this.socket = socket;
        }
        public void run(){
            try{
                inputFromClient = new DataInputStream(socket.getInputStream());

                // Read from input
                int id = inputFromClient.readInt();

                // Write to the file
                //StudentAddress s = (StudentAddress)object;
                //storeData = (DataForStorage) object;
                DataForStorage gameData = loadGame(id);
                //Socket socketback = new Socket("localhost",8002);
                ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                toClient.writeObject(gameData);
                socket.shutdownOutput();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
