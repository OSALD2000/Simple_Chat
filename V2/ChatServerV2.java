package V2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServerV2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Chatserver 2.0 gestartet");

        boolean finished = false;




        // Die Portnummer wird reserviert (gebunden/ bind)
        ServerSocket server = new ServerSocket(2024);

        do {
            // Server wartet auf eingehendes SYN-paket und führt den Handshack duch
            Socket client = server.accept();

            // Die java-API bitet Streams, um den TCP-puffer zu schreiben;
            // sowie daraus zu lesen

            PrintStream toClient = new PrintStream(client.getOutputStream());
            // Moglichlkeiten Scanner und BufferReder
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));

            BufferedReader serverInput = new BufferedReader(new InputStreamReader( System.in));

            //Um Daten Pakete zu übertragung die kleiner als MMS sind benutzt man flush() auf dem Stream
            String hello = "201 Chatserver Cersion 2.0";
            toClient.println(hello);

            CLIENT:
            do {
                    //Prüfe, ob noch Daten Verfügbar
                    while (fromClient.ready()){
                        // Nimm eine Zeile von Client und gib dies aus
                        String line = fromClient.readLine();
                        // Beenden mot Quit
                        if (line.toLowerCase().equals("quit")) break CLIENT;

                        System.out.println(line);
                    }

                    // solange noch einem Ziele gibt, hole die
                    while (serverInput.ready()){
                        //Hole eine Zeile aus der Standardeingabe und schicke diese zum Client*
                        toClient.println(serverInput.readLine());

                        //* stelle sicher das alle Daten gesendet werden
                        toClient.flush();
                    }

                    //Warte 1/4 Sekunde, um andere Programme arbeiten zu lassen
                    Thread.sleep(250);
            } while (true);

            client.close();

        }while(!finished);

        // Beim Schließen werden noch ungesendete Daten übertragen
        server.close();
        System.out.println("Chatserver beendet");
    }
}
