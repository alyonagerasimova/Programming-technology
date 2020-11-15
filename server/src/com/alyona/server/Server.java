package com.alyona.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static private final short PORT = 9070;
    static volatile private ArrayList<Object> clientOutputStreams;

    public Server() throws IOException {
    }

    public static void main(String[] args) {
        clientOutputStreams = new ArrayList<>();

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (!server.isClosed()) {
                Socket client = server.accept();
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                clientOutputStreams.add(outputStream);

                Thread clientThread = new Thread(() -> {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                        System.out.println(objectInputStream.readObject());
                        outputStream.writeObject(new String("test"));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                clientThread.start();
                System.out.println("Client connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//            System.out.print("Connection accepted.");
//            DataOutputStream out = new DataOutputStream(client.getOutputStream());
//            System.out.println("DataOutputStream  created");
//
//            DataInputStream in = new DataInputStream(client.getInputStream());
//            System.out.println("DataInputStream created");
//
//            while(!client.isClosed()){
//                System.out.println("Server reading from channel");
//                String entry = in.readUTF();
//                System.out.println("READ from client message - "+entry);
//                System.out.println("Server try writing to channel");
//
//// инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову       - quit
//                if(entry.equalsIgnoreCase("quit")){
//                    System.out.println("Client initialize connections suicide ...");
//                    out.writeUTF("Server reply - "+entry + " - OK");
//                    out.flush();
//                    Thread.sleep(3000);
//                    break;
//                }
//
//                out.writeUTF("Server reply - "+entry + " - OK");
//                System.out.println("Server Wrote message to client.");
//
//               out.flush();
//
//            }
//            System.out.println("Client disconnected");
//            System.out.println("Closing connections & channels.");
//
//            // закрываем сначала каналы сокета !
//            in.close();
//            out.close();
//            client.close();
//            System.out.println("Closing connections & channels - DONE.");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

