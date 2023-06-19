package V3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClientV3 {
    public static void main(String[] args) throws IOException {

        boolean finished=false;
        // Bereits im Konstruktor wird eine TCP-Verbindung hergestellt
        Socket socket = new Socket("localhost",2023);

        // Die java-API bitte Streams, um den TCP-puffer zu schreiben;
        // sowie daraus zu lesen

        PrintStream toServer = new PrintStream(socket.getOutputStream());

        // Moglichlksocketeiten Scanner und BufferReder
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));


        do {
            while (fromServer.ready()) {
                String line = fromServer.readLine();
                System.out.println(line);
                if(line.toLowerCase().equals("quit")){
                    finished=true;
                    break;
                }
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
