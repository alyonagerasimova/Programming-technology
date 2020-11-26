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
        //  writeRandomMatrix(firstMatrixFileName, secondMatrixFileName); // If file should be create

            FileInputStream firstFileInputStream = new FileInputStream(firstMatrixFileName);
            FileInputStream secondFileInputStream = new FileInputStream(secondMatrixFileName);

            Matrix firstMatrix = MatrixStreamHelper.read(firstFileInputStream);
            Matrix secondMatrix = MatrixStreamHelper.read(secondFileInputStream);

            firstFileInputStream.close();
            secondFileInputStream.close();

            Matrix resultMatrix = getMatrixOperationResultFromServer(firstMatrix, secondMatrix);

            FileOutputStream fileOutputStream = new FileOutputStream(resultMatrixFileName);
            if (resultMatrix == null) {
                fileOutputStream.write("Ошибка в расчетах".getBytes());// Запись в файл ошибки расчета
            } else {
                MatrixStreamHelper.write(fileOutputStream, resultMatrix);
            }
            fileOutputStream.close();

        } catch (java.util.NoSuchElementException | java.lang.NumberFormatException ex) {
            System.err.println("Wrong file format");
        } catch (ClassNotFoundException ex) {
            System.err.println("Input files not found");
        }
        catch(EOFException eof){
            System.err.println("End of file or end of stream has been reached unexpectedly");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Matrix getMatrixOperationResultFromServer(Matrix firstMatrix, Matrix secondMatrix) {
        Matrix resultMatrix = null;
        try (Socket clientSocket = new Socket(IP_ADDRESS, PORT)) {
            OutputStream outputStream = clientSocket.getOutputStream();
            MatrixStreamHelper.write(outputStream, firstMatrix);
            MatrixStreamHelper.write(outputStream, secondMatrix);

            InputStream inputStream = clientSocket.getInputStream();
            resultMatrix = MatrixStreamHelper.read(inputStream);

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
            MatrixStreamHelper.write(fileOutputStream, getRandomMatrix());
            MatrixStreamHelper.write(fileOutputStream1, getRandomMatrix());
            fileOutputStream.close();
            fileOutputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
