package V1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {

        // Bereits im Konstruktor wird eine TCP-Verbindung hergestellt
        Socket socket = new Socket("localhost",2023);

        // Die java-API bitte Streams, um den TCP-puffer zu schreiben;
        // sowie daraus zu lesen

        PrintStream toServer = new PrintStream(socket.getOutputStream());

        // Moglichlksocketeiten Scanner und BufferReder
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        Scanner input = new Scanner(System.in);

        boolean finished=false;
        do {
            System.out.println(fromServer.readLine());

            String nachricht = input.nextLine();

            toServer.println(nachricht);

            //Sende noch ungesendeter Daten
            toServer.flush();

            if(nachricht.toLowerCase().equals("quit")) {
                break;
            }
        }while(!finished);



        //Senden noch ungesendeter Daten und Verbindungsabbau
        toServer.close();
    }
}
