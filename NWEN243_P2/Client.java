import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Based on
// https://www.geeksforgeeks.org/socket-programming-in-java/
// https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
// https://www.youtube.com/watch?v=-xKgxqG411c

class Client {
     public static void main(String[] args){
         String hostID = args[0];
         int portID = Integer.parseInt(args[1]);
         try {
             Socket clientSocket = new Socket(hostID, portID);
             PrintWriter send = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

             send.println("Client : " + hostID);
             System.out.println("CLIENT: " + "Made connection to " + portID + "\nRESPONSE: '" + receive.readLine() + "'");
             clientSocket.close();

         } catch (UnknownHostException e) {
             System.out.println("Unknown host: " +  hostID);
             e.printStackTrace();
         } catch (IOException e) {
             System.out.println("IOException: " + e.getMessage());
             e.printStackTrace();
         }
     }
     }

