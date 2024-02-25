package sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerWorker implements Runnable {
    private final Socket socket;
    private final String clientAddress;
    private final PrintWriter outToClient;

    // Constructor de la clase ChatServerWorker
    public ChatServerWorker(Socket socket, PrintWriter outToClient) {
        this.socket = socket;
        this.clientAddress = socket.getInetAddress().getHostAddress();
        this.outToClient = outToClient;
    }


    @Override
    public void run() {
        try (
                // Creamos un Scanner para leer desde el flujo de entrada del socket
                Scanner inFromClient = new Scanner(socket.getInputStream())
        ) {
            // Solicitamos al cliente que ingrese su nombre
            outToClient.println("Bienvenido al chat. Ingresa tu nombre de usuario:");
            String username = inFromClient.nextLine();
            // Mostramos en el servidor que un cliente se ha unido al chat
            System.out.println("Cliente " + username + " se ha unido al chat desde " + clientAddress);
            // Enviamos un mensaje a todos los clientes informando que el cliente se ha unido al chat
            ChatServer.broadcastMessage(username + " has joined the chat!");

            // Bucle para leer mensajes del cliente
            while (inFromClient.hasNextLine()) {
                String clientMessage = inFromClient.nextLine();
                // Si el cliente escribe "bye", llamamos al método para manejar la salida del cliente
                if (clientMessage.equalsIgnoreCase("bye")) {
                    handleClientExit(username);
                    break;
                }
                // Si no es "bye", llamamos al método para manejar el mensaje del cliente
                handleClientMessage(username, clientMessage);
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente " + clientAddress + ": " + e.getMessage());
        } finally {
            // Resto del código...
        }
    }

    // Método para manejar los mensajes del cliente
    private void handleClientMessage(String username, String message) {
        // Mostramos en el servidor el mensaje recibido
        System.out.println("Mensaje recibido de [" + username + "] : " + message);
        // Enviamos el mensaje a todos los usuarios conectados
        ChatServer.broadcastMessage("[" + username + "] : " + message);
    }

    // Método para manejar la salida del cliente
    private void handleClientExit(String username) {

        System.out.println("Cliente " + username + " se ha desconectado desde " + clientAddress);
        // Enviamos un mensaje a todos los clientes informando que el cliente ha dejado el chat
        ChatServer.broadcastMessage(username + " has left the chat!");
        try {
            // Cerramos la conexión
            socket.close();
            // Removemos al cliente de la lista de clientes conectados
            ChatServer.connectedClients.remove(outToClient);
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión con el cliente " + clientAddress + ": " + e.getMessage());
        }
    }
}
