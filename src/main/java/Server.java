import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connections
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket client = serverSocket.accept();
        } catch (IOException ioException) {
//            TODO: handle
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

            }catch (IOException e){
//                e.printStackTrace();
            }
        }
    }
}
