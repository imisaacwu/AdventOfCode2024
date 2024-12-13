package lib;

import java.util.ArrayList;
import java.util.List;

public class Matrix extends Grid<Double> {
    public static Matrix fromStr(String matrix) {
        List<List<Double>> m = new ArrayList<>();
        for (String s : matrix.split("\\n")) {
            List<Double> row = new ArrayList<>();
            for (String d : s.split(" ")) {
                row.add(Double.parseDouble(d));
            }
            m.add(row);
        }
        return new Matrix(m);
    }

    public Matrix(List<List<Double>> grid) {
        super(grid);
    }

    public Matrix inverse() {
        if (getHeight() != getWidth()) {
            throw new IllegalArgumentException("Matrix must be square to compute its inverse.");
        }
    
        int n = getHeight();
        // Create augmented matrix [A | I] for Gauss-Jordan elimination
        double[][] augmented = new double[n][2 * n];
        
        // Initialize augmented matrix with original matrix and identity matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = get(i, j);
                augmented[i][j + n] = (i == j) ? 1.0 : 0.0;
            }
        }
    
        // Perform Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot element
            if (Math.abs(augmented[i][i]) < 1e-12) {
                // Pivot is too small (singular matrix)
                throw new ArithmeticException("Matrix is singular and cannot be inverted.");
            }
    
            // Normalize the pivot row
            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= pivot;
            }
    
            // Eliminate other rows
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }
    
        // Extract the inverse matrix from the augmented matrix
        List<List<Double>> inverseMatrixList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = n; j < 2 * n; j++) {
                row.add(augmented[i][j]);
            }
            inverseMatrixList.add(row);
        }
    
        return new Matrix(inverseMatrixList);
    }    

    public double determinant() {
        if (getHeight() != getWidth()) {
            throw new IllegalArgumentException("Matrix is not square!");
        }

        Grid<Double> lu = new Grid<>(grid);
        int[] pivot = new int[getHeight()];
        double det = luDecomposition(lu, pivot);

        for (int i = 0; i < getHeight(); i++) {
            det *= lu.get(i, i);
        }
        return det;
    }

    private double luDecomposition(Grid<Double> lu, int[] pivot) {
        int n = lu.getHeight();
        double detSign = 1.0;

        for (int i = 0; i < n; i++) {
            pivot[i] = i;
        }

        for (int k = 0; k < n; k++) {
            // Find pivot element
            int maxIndex = k;
            double maxValue = Math.abs(lu.get(k, k));
            for (int i = k + 1; i < n; i++) {
                double value = Math.abs(lu.get(i, k));
                if (value > maxValue) {
                    maxIndex = i;
                    maxValue = value;
                }
            }

            // Swap rows if necessary
            if (maxIndex != k) {
                for (int j = 0; j < n; j++) {
                    double temp = lu.get(k, j);
                    lu.set(k, j, lu.get(maxIndex, j));
                    lu.set(maxIndex, j, temp);
                }

                int tempPivot = pivot[k];
                pivot[k] = pivot[maxIndex];
                pivot[maxIndex] = tempPivot;

                detSign = -detSign; // Swapping rows flips the sign of the determinant
            }

            // Perform elimination below pivot
            if (Math.abs(lu.get(k, k)) < 1e-12) { // Treat as singular matrix
                return 0.0;
            }

            for (int i = k + 1; i < n; i++) {
                lu.set(i, k, lu.get(i, k) / lu.get(k, k));
                for (int j = k + 1; j < n; j++) {
                    lu.set(i, j, lu.get(i, j) - lu.get(i, k) * lu.get(k, j));
                }
            }
        }

        return detSign;
    }

    public Matrix multiply(double k) {
        List<List<Double>> m = new ArrayList<>();
        for (int r = 0; r < getHeight(); r++) {
            List<Double> row = new ArrayList<>();
            for (int c = 0; c < getWidth(); c++) {
                row.add(k * get(r, c));
            }
        }
        return new Matrix(m);
    }

    public Matrix multiply(Matrix other) {
        if (this.getWidth() != other.getHeight()) {
            throw new IllegalArgumentException("Dimensions do not match");
        }

        List<List<Double>> m = new ArrayList<>();
        for (int r = 0; r < this.getHeight(); r++) {
            List<Double> row = new ArrayList<>();
            for (int c = 0; c < other.getWidth(); c++) {
                double sum = 0;
                for (int k = 0; k < this.getWidth(); k++) {
                    sum += get(r, k) * other.get(k, c);
                }
                row.add(sum);
            }
            m.add(row);
        }

        return new Matrix(m);
    }
}
