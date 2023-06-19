package V2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClientV2 {
    public static void main(String[] args) throws IOException {

        boolean finished=false;
        // Bereits im Konstruktor wird eine TCP-Verbindung hergestellt
        Socket socket = new Socket("localhost",2024);

        // Die java-API bitte Streams, um den TCP-puffer zu schreiben;
        // sowie daraus zu lesen

        PrintStream toServer = new PrintStream(socket.getOutputStream());

        // Moglichlksocketeiten Scanner und BufferReder
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));


        do {
            while (fromServer.ready()) {
                System.out.println(fromServer.readLine());
            }

            while (input.ready()){
                String nachricht = input.readLine();
                toServer.println(nachricht);

                //Sende noch ungesendeter Daten
                toServer.flush();

                if(nachricht.toLowerCase().equals("quit")) {
                    finished=true; break;
                }
            }
        }while(!finished);



        //Senden noch ungesendeter Daten und Verbindungsabbau
        toServer.close();
    }
}
