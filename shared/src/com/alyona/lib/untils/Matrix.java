package com.alyona.lib.untils;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Matrix implements Serializable {
    private double[][] matrix;
    private final int rowsCount;
    private final int columnsCount;

    public Matrix() {
        this.rowsCount = 0;
        this.columnsCount = 0;
    }

    public Matrix(int rowsCount, int columnsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.matrix = new double[rowsCount][columnsCount];
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    double[] getColumn(int i) {
        return this.matrix[i - 1];
    }

    public void setValueAt(int row, int column, double value) {
        matrix[row][column] = value;
    }

    double getValueAt(int row, int column) {
        return matrix[row][column];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || o.hashCode() != this.hashCode()) {
            return false;
        }
        Matrix compareMatrix = (Matrix) o;
        return rowsCount == compareMatrix.getRowsCount() &&
                columnsCount == compareMatrix.getColumnsCount() &&
                Arrays.equals(matrix, compareMatrix.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rowsCount, columnsCount);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        for (double[] rows : matrix) {
            for (double a : rows) {
                stringBuilder.append(a).append("\t");
            }
            stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
            stringBuilder1.append(stringBuilder.toString()).append("\n");
            stringBuilder = new StringBuilder("");
        }
        return stringBuilder1.toString();
    }

    public static Matrix sumMatrix(Matrix a, Matrix b) {
        if (a.getRowsCount() != b.getColumnsCount() || a.getRowsCount() != b.getRowsCount()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        var result = new Matrix(a.getRowsCount(), a.getColumnsCount());
        for (int i = 0; i < a.getRowsCount(); i++) {
            for (int j = 0; j < b.getColumnsCount(); j++) {
                double aValue = a.getValueAt(i, j);
                double bValue = b.getValueAt(i, j);
                result.setValueAt(i, j, aValue + bValue);
            }
        }
        return result;
    }
}
