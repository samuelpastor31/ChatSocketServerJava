package sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        String serverHostname = "localhost"; // Cambiar por la dirección IP del servidor si es necesario
        int port = 6789;

        try (Socket clientSocket = new Socket(serverHostname, port);
             PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
             Scanner inFromServer = new Scanner(clientSocket.getInputStream());
             Scanner userInputScanner = new Scanner(System.in))
        {
            // Solicitar al usuario que ingrese su nombre
            System.out.println("Conectado al servidor. Ingresa tu nombre de usuario:");
            String username = userInputScanner.nextLine();
            // Enviar el nombre de usuario al servidor
            outToServer.println(username);

            // Hilo para recibir mensajes del servidor
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        // Leer y mostrar mensajes del servidor
                        String serverMessage = inFromServer.nextLine();

                        //el mensaje se envía solo a los demás clientes
                        if (!serverMessage.startsWith("[" + username + "]")) {
                            System.out.println(serverMessage);
                        }
                    }
                } catch (Exception e) {
                    // Mostrar un mensaje si la conexión se cierra
                    System.err.println("Connection closed");
                }
            });
            receiveThread.start();

            // Bucle para enviar mensajes al servidor
            while (true) {
                String message = userInputScanner.nextLine();
                // Enviar mensajes al servidor
                outToServer.println(message);

                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            receiveThread.join();

        } catch (IOException | InterruptedException e) {

            System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
        }
    }
}
