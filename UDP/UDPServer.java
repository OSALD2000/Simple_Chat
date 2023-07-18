package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;

public class UDPServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket(2023);

        System.out.println("UDBserver ready");

        //Das Datenpaket ist an einem Paketpuffer (byte-Array) gebunden
        DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
        boolean finished = false;
        while (!finished) try{
            //Ein Aufruf von receivewartet höchstens 1s auf ein eingehendes Paket
            socket.setSoTimeout(1000);
            //Diese Methode blokiert so lange, bis ein Paket in UDP-Puffer verfügbar ist
            //das Paket aus dem UDP-Puffer wird in den Paketpuffer kopiert
            //aktuell: der Paketpuffer ist NULL! ein Kopieren ist unmöglich
            socket.receive(packet);
            byte[] buffer = packet.getData();
            BufferedReader reder = new BufferedReader(new StringReader(new String(buffer)));
            System.out.println(reder.readLine());

            //Austausch des Paketpuffers
            //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            //PrintStream output = new PrintStream(bytes);
            //output.println(input.nextLine());
            //packet.setData(bytes.toByteArray());
            //Sendet das Paket zurück an den Absender
            //socket.send(packet);


            //Löschen ist dann nötig, wenn dieser wiederverwendet wird.
            Arrays.fill(buffer, (byte) 0);
            //Rücktausch des PaketpuffersH
            packet.setData(buffer);

        }catch (Exception e){
            packet.setAddress(InetAddress.getByName("localhost"));
            packet.setPort(2023);
            packet.setLength(7); //UDP-Length (only Data)
            String txt  = "Hallo? "+System.lineSeparator();
            System.arraycopy(txt.getBytes(),0 , packet.getData(), 0, txt.length());
            socket.send(packet);
        }

            System.out.println("UDPServer stoped");

        }

}
