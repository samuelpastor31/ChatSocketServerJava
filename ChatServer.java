package sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.*;

import java.util.Set;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 6789;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    public static final Set<PrintWriter> connectedClients = new CopyOnWriteArraySet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de Chat iniciado y escuchando en el puerto " + PORT);

            // Agregar un shutdown hook para manejar SIGTERM
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Cerrando el servidor de manera ordenada...");
                // Notificar a todos los clientes conectados sobre el cierre inminente
                broadcastMessage("El servidor se cerrará. ¡Hasta luego!");

                // Cerrar el executorService
                executorService.shutdown();

                // Cerrar todas las conexiones de los clientes
                for (PrintWriter client : connectedClients) {
                    try {
                        client.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                connectedClients.add(outToClient);

                executorService.execute(new ChatServerWorker(clientSocket, outToClient));
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public static void broadcastMessage(String message) {
        for (PrintWriter client : connectedClients) {
            client.println(message);
        }
    }
}
