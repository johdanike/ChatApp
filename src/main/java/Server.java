import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connections;

    public Server(){
        connections = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket client = serverSocket.accept();
            ConnectionHandler connectionHandler = new ConnectionHandler(client);
            connections.add(connectionHandler);
        } catch (IOException ioException) {
//            TODO: handle
        }
    }

    public void broadcast(String message){
        for (ConnectionHandler connectionHandler: connections){
            if (connectionHandler != null) {
                connectionHandler.sendMessage(message);
            }
        }
    }


    public class ConnectionHandler implements Runnable{
        private Socket client;
        private BufferedReader reader;
        private PrintWriter writer;

        public ConnectionHandler(Socket client){
            this.client = client;
        }


        @Override
        public void run() {
            try{
                writer = new PrintWriter(client.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer.println("Please enter a nickname: ");
                String nickname = reader.readLine();
                System.out.println(nickname + " connected!");
                broadcast(nickname + " joined the chat!");
                String message;
                while((message = reader.readLine()) != null){
                    if (message.startsWith("/nick ")){
                         String[] messageSplit = message.split(" ", 2);
                         if (messageSplit.length == 2){
                             broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                             System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                             nickname = messageSplit[1];
                             writer.println("Successfully changed nickname to " + nickname);
                         } else {
                             writer.println("No nickname provided!");
                         }
                    } else if (message.startsWith("/quit")){
//                        TODO: quit
                    } else {
                        broadcast(nickname + ": " + message);
                    }
                }
            }catch (IOException e){
//                e.printStackTrace();
            }
        }
        
        public void sendMessage(String message){
            writer.println(message);
        }
    }
}
