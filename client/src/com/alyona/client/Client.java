package com.alyona.client;

import com.alyona.lib.untils.Matrix;
import com.alyona.lib.untils.MatrixStreamHelper;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String IP_ADDRESS = "localhost";
    private static final short PORT = 9070;

    public static void main(String[] args) {
        System.out.println("Start client program");
        String firstMatrixFileName = args[0];
        String secondMatrixFileName = args[1];
        String resultMatrixFileName = args[2];

        try {
            FileInputStream firstFileInputStream = new FileInputStream(firstMatrixFileName);
            FileInputStream secondFileInputStream = new FileInputStream(secondMatrixFileName);

            Matrix firstMatrix = MatrixStreamHelper.input(firstFileInputStream);
            Matrix secondMatrix = MatrixStreamHelper.input(secondFileInputStream);

            firstFileInputStream.close();
            secondFileInputStream.close();

            if (firstMatrix == null || secondMatrix == null) {
                return;
            }

            Matrix resultMatrix = getMatrixOperationResultFromServer(firstMatrix, secondMatrix);
            if (resultMatrix == null) {
                return;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(resultMatrixFileName);
            MatrixStreamHelper.output(fileOutputStream, resultMatrix);
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Matrix getMatrixOperationResultFromServer(Matrix firstMatrix, Matrix secondMatrix) {
        Matrix resultMatrix = null;
        try (Socket clientSocket = new Socket(IP_ADDRESS, PORT)) {
            OutputStream outputStream = clientSocket.getOutputStream();
            MatrixStreamHelper.output(outputStream, firstMatrix);
            MatrixStreamHelper.output(outputStream, secondMatrix);

            InputStream inputStream = clientSocket.getInputStream();
            resultMatrix = MatrixStreamHelper.input(inputStream);

            outputStream.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Server returned matrix:");
        System.out.println(resultMatrix);
        return resultMatrix;
    }

    private static Matrix getRandomMatrix() {
        var matrix = new Matrix(5, 5);
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                matrix.setValueAt(i, j, Math.random() * 10);
            }
        }
        return matrix;
    }

    private static void writeRandomMatrix(String firstMatrixFileName, String secondMatrixFileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(firstMatrixFileName);
            FileOutputStream fileOutputStream1 = new FileOutputStream(secondMatrixFileName);
            MatrixStreamHelper.output(fileOutputStream, getRandomMatrix());
            MatrixStreamHelper.output(fileOutputStream1, getRandomMatrix());
            fileOutputStream.close();
            fileOutputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
