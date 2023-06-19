package V3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServerV3{
    private int port;
    private ArrayList<ChatProtocol> clients;
    private Thread server;
    public ChatServerV3(int port){
        this.port=port;
        clients = new ArrayList<>();
    }
    public void start() throws IOException, InterruptedException  {

        ServerSocket serverSocket = new ServerSocket(port);

        // Oberkellner-Thred: Nimmt Clinets entgegen;
        server = new Thread() {    // Die Portnummer wird reserviert (gebunden/ bind)
            public void run() {
               do{
                try {
                    clients.add(new ChatProtocol(serverSocket.accept()));
                } catch (IOException e) {
                    break;
                }
               }while(true);
            }
        };

        server.start();
        /**
         * @todo Nur noch später*/
        BufferedReader serverInput = new BufferedReader(new InputStreamReader( System.in));
        //Hauptprogaramm
            boolean finished = false;
            do {
                ChatProtocol client = null;
                for (int i = 0; i< clients.size(); i++){
                        client = clients.get(i);
                        if(client.hasData()){
                            String data = client.getData();
                            // Beenden mot Quit
                            if (data.toLowerCase().equals("quit")) {
                                client.close();
                                break;
                            }

                            for (ChatProtocol other :clients){
                                if(other != client){
                                    other.writeData(client.getName()+" : "+ data);
                                }
                            }
                        }

                }

                if(client != null && client.isClose()){clients.remove(client);}

                //Warte 1/4 Sekunde, um andere Programme arbeiten zu lassen
                Thread.sleep(250);

                if(serverInput.ready()){
                    String line = serverInput.readLine();
                    if(line.toLowerCase().equals("quit")){
                        finished = true;
                    }
                }
            } while (!finished);

        //Schließen aller übrigen Clients
        for (ChatProtocol client : clients){
            client.close();
        }

        // Beim Schließen werden noch ungesendete Daten übertragen
        serverSocket.close();
        System.out.println("Chatserver beendet");

        //kurz warten bis Thred durch Excption bendent
        Thread.sleep(100);
    }

    public  void close(){

    }
    public static void main(String[] args) throws IOException, InterruptedException {
            ChatServerV3 server = new ChatServerV3(2023);
            System.out.println("Chatserver 3.0 gestartet");
            server.start();
    }
}
