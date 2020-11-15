package com.alyona.client;

import com.alyona.client.untils.Matrix;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private static Socket clientSocket;
    private static final String IP_ADDRESS = "localhost";
    private static final short PORT = 9070;

    public static void main(String[] args) {
        System.out.println("test");
        String firstMatrixFileName = "out/firstMatrix";
        String secondMatrixFileName = "out/secondMatrix";
        String resultMatrixFileName = "out/resultMatrix";

        System.out.println(Arrays.toString(args));

        Matrix matrix = getRandomMatrix();
//        try {
////            FileOutputStream fileOutputStream = new FileOutputStream(secondMatrixFileName);
////            Matrix.output(fileOutputStream, matrix);
////            fileOutputStream.close();
//            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Username\\Desktop\\save.ser");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//            FileInputStream fileInputStream = new FileInputStream(secondMatrixFileName);
//            System.out.println(Matrix.input(fileInputStream));
//            fileInputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            clientSocket = new Socket(IP_ADDRESS, PORT);
            System.out.println(clientSocket.isConnected());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            objectOutputStream.writeObject(matrix);

            System.out.println(objectInputStream.available());
            System.out.println(objectInputStream.readObject());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // вынести матрицу в либу. убрать все что не относится к работе в тесты
    }

    private static Matrix getRandomMatrix() {
        var matrix = new Matrix(5,5);
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                matrix.setValueAt(i, j, Math.random() * 10);
            }
        }
        return matrix;
    }
}
