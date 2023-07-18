package UDP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;

public class MultiChat {
    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         *
         * Verwende UDP-Multicast zum Verteilen der Nachrichten
         *
         * Das Geheimnis des Multicast ist ein Trick:
         * Gib jedem Host der Multicast-Gruppe die gleiche IP-Adresse
         * es gibt besondere Multicast Addressen, diese dürfen (im lokalen Netz) mehrfach vergeben sein.
         * es gibt Ethernet-Multicast Paket, das wird an alle geschickt (durch den Switch)
         *
         * Damit ist es klar:
         * Alle Hosts mit der gleichen Multicast-Addresse
         * (Voraussetzung: Betritt der Mzlticast-Gruppe: Setzen der zusätlichen IP)
         * nehme das Paket an.
         */

        //Binde den Socket nicht an eine IP/Port!!!
        MulticastSocket socket = new MulticastSocket(
                new InetSocketAddress(InetAddress.getByName("239.1.2.3"), 2023)
        );
        socket.joinGroup(new InetSocketAddress(InetAddress.getByName("239.1.2.3"),0), NetworkInterface.getByIndex(0));
        socket.setTimeToLive(4);
        //erlaube den Trick mehrfach IP-Address
        //socket.setReuseAddress(true);
        //socket.setOption(SocketOptions.)


        //Binde den Socket an die Multicast-Addresse
        //InetAddress multicast = InetAddress.getByName("239.1.2.3");
        //InetSocketAddress address = new InetSocketAddress(multicast,0);
        //socket.joinGroup(address, NetworkInterface.getByIndex(0));

        //BeidatagrammSocket
        //socket.bind(new InetSocketAddress(multicast,2023));

        byte [] buffer = new byte[1024];
        DatagramPacket paket = new DatagramPacket(buffer, buffer.length);

        do {
            Arrays.fill(buffer, (byte) 0);
            paket.setLength(buffer.length);
            socket.receive(paket);

            System.out.println(new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(
                                    paket.getData()
                            )
                    )
            ).readLine());
        }while (true);
    }
}
