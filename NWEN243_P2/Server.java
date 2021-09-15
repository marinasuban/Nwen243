import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//Based on
// https://www.geeksforgeeks.org/socket-programming-in-java/
// https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
// https://www.youtube.com/watch?v=-xKgxqG411c

public class Server {
    private static final String[] quotes = {"Act as if what you do makes a difference. It does.",
            "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            "Never bend your head. Always hold it high. Look the world straight in the eye.",
            "Believe you can and you're halfway there.",
            "When you have a dream, you've got to grab it and never let go."};
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        int serverPort = 5000;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println("Error opening server socket: " + e.getMessage());
            System.exit(1);
        }
        System.out.println("Now listening on port " + serverPort + ".");

        while (true) try {
            System.out.println("Waiting for a client ...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected" + clientSocket.getInetAddress().getHostAddress());

            // create a new thread object
            ClientHandler clientSock = new ClientHandler(clientSocket);
            new Thread(clientSock).start();

        } catch (IOException e) {
            System.out.println("Failed to send quote: " + e.getMessage());
        }
    }


    /**
     * Generate Random Quote
     *
     * @return Random Quote
     */
    private static String randomQuote() {
        int quote = (int) (quotes.length * Math.random());
        return quotes[quote];
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            BufferedReader receive=null;
            PrintWriter writer=null;

            try {
                receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println(receive.readLine());

                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                String ip = (InetAddress.getLocalHost().getHostAddress());

                writer.println(ip + " Says " + randomQuote());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (receive != null) {
                        receive.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Failed to close " + e.getMessage());

                }
            }

        }
    }
}
