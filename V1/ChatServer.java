package V1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Chatserver 1.0 gestartet");

        boolean finished = false;




        // Die Portnummer wird reserviert (gebunden/ bind)
        ServerSocket server = new ServerSocket(2023);

        do {
            // Server wartet auf eingehendes SYN-paket und führt den Handshack duch
            Socket client = server.accept();

            // Die java-API bitet Streams, um den TCP-puffer zu schreiben;
            // sowie daraus zu lesen

            PrintStream toClient = new PrintStream(client.getOutputStream());
            // Moglichlkeiten Scanner und BufferReder
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));

            Scanner serverInput = new Scanner(System.in);

            //Um Daten Pakete zu übertragung die kleiner als MMS sind benutzt man flush() auf dem Stream
            String hello = "201 Chatserver Cersion 1.0";
            toClient.println(hello);

            do {

                // Warte auf eine Zeile von Client und gib dieses aus
                String line = fromClient.readLine();
                // Beenden mot Quit
                if (line.toLowerCase().equals("quit")) break;

                System.out.println(line);

                //Hole eine Zeile aus der Standardeingabe und schicke diese zum Client*
                toClient.println(serverInput.nextLine());

                //* stelle sicher das alle Daten gesendet werden
                toClient.flush();

            } while (true);

            client.close();

        }while(!finished);

        // Beim Schließen werden noch ungesendete Daten übertragen
        server.close();
        System.out.println("Chatserver beendet");
    }
}
