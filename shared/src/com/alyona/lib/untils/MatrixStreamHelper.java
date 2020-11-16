package com.alyona.lib.untils;

import java.io.*;

public class MatrixStreamHelper {
    private MatrixStreamHelper() {
    }

    public static void output(OutputStream outputStream, Matrix matrix) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(matrix);
        objectOutputStream.flush();
    }

    public static Matrix input(InputStream inputStream) throws IOException, ClassNotFoundException {
            ObjectInputStream objectInput = new ObjectInputStream(inputStream);
            return (Matrix)objectInput.readObject();
    }
}
