package com.alyona.lib.untils;

import java.io.*;

public class MatrixStreamHelper {
    private MatrixStreamHelper() {
    }

    public static void write(OutputStream outputStream, Matrix matrix) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(matrix.getArray());
        objectOutputStream.flush();
    }

    public static Matrix read(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);
        return new Matrix((double[][])objectInput.readObject());
    }
}
