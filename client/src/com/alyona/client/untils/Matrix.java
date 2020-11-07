package com.alyona.client.untils;

public class Matrix {
    private double[][] matrix;
    private final int n;
    private final int m;

    public Matrix() {
        this.n = 0;
        this.m = 0;
    }

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        this.matrix = new double[n][m];
    }

    Matrix(double[][] matrix) {
        this.n = matrix.length;
        this.m = matrix[0].length;
        this.matrix = matrix;
    }

    int getN() {
        return n;
    }

    int getM() {
        return m;
    }

    double[] getColumn(int i) {
        return this.matrix[i - 1];
    }

    public void setAt(int n, int m, double value) {
        matrix[n - 1][m - 1] = value;
    }

    double getAt(int n, int m) {
        return matrix[n - 1][m - 1];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        for (double[] rows : matrix) {
            for (double a : rows) {
                stringBuilder.append(a + ",");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            stringBuilder1.append(stringBuilder.toString() + ";\n");
            stringBuilder = new StringBuilder("");
        }
        return stringBuilder1.toString();
    }
}
