package com.alyona.server;

import com.alyona.lib.untils.Matrix;
import com.alyona.lib.untils.MatrixStreamHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static private final short PORT = 9070;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (!server.isClosed()) {
                Socket client = server.accept();
                Thread clientThread = new Thread(getRunnableMatrixTask(client));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Runnable getRunnableMatrixTask(Socket clientConnection) {
        return () -> {
            try {
                System.out.println("Client connected");
                Matrix firstMatrix = MatrixStreamHelper.input(clientConnection.getInputStream());
                Matrix secondMatrix = MatrixStreamHelper.input(clientConnection.getInputStream());

                Matrix resultMatrix = Matrix.sumMatrix(firstMatrix, secondMatrix);
                MatrixStreamHelper.output(clientConnection.getOutputStream(), resultMatrix);

                clientConnection.close();

                System.out.println("Client disconnected");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
    }
}

