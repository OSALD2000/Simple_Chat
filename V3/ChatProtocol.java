package V3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatProtocol {
    private  Socket client;
    private  PrintStream toClient;
    private  BufferedReader fromClient;
    private  String clientName ="";

    public  static String hello = "Chatserver 3.0 gestartet";
    public  static String whatis = "What is your Name?";

    public  ChatProtocol(Socket client) {
        this.client = client;
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1); // wird später noch elimiert
        }
    }
    private void init() throws IOException {
        toClient = new PrintStream(client.getOutputStream(), true);

        fromClient= new BufferedReader(new InputStreamReader(
                client.getInputStream()));

        toClient.println(hello);
        toClient.println(whatis);
    }

    public boolean hasName(){
        return clientName.length() > 0;
    }
    public void reqquestName(){
        clientName = getData();
    }

    public boolean hasData() {
        boolean r  = false;
        try {
            r = fromClient.ready();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  r;
    }

    public  String getData(){
        String data = "";
        try {
            data = fromClient.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!hasName()){
            clientName=data;
            data +="has joined. ";
        }
        return  data;
    }

    public  void writeData(String data){
        // PrintStream mit AutoFlush == true
        toClient.println(data);
    }

    public void close(){
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isClose(){
        return client.isClosed();
    }

    public String getName() {
        return this.clientName;
    }
}
