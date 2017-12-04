package edu.iga.adi.sm.support;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class MatrixUtils {

    public static double[][] withPadding(double[][] original) {
        double[][] paddedMatrix = new double[original.length + 1][original[0].length + 1];
        for (int r = 0; r < original.length; r++) {
            List<Double> collect = Arrays.stream(original[r]).boxed().collect(Collectors.toList());
            collect.add(0, 0d);
            paddedMatrix[r + 1] = ArrayUtils.toPrimitive(collect.toArray(new Double[original[r].length]));
        }
        return paddedMatrix;
    }

    public static double[][] withoutPadding(double[][] original) {
        double[][] trimmedMatrix = new double[original.length - 1][original[1].length - 1];
        for (int r = 1; r < original.length; r++) {
            trimmedMatrix[r - 1] = Arrays.copyOfRange(original[r], 1, original[r].length);
        }
        return trimmedMatrix;
    }

    public static double computeMeanOf(double[][] matrix) {
        BigDecimal total = BigDecimal.ZERO;
        final int yCount = matrix.length;
        final int xCount = matrix[1].length;
        final BigDecimal divisor = new BigDecimal(xCount * yCount);
        for (int y = 1; y < yCount; y++) {
            for (int x = 1; x < xCount; x++) {
                total = total.add(new BigDecimal(matrix[y][x]).divide(divisor, RoundingMode.HALF_EVEN));
            }
        }
        return total.doubleValue();
    }

    public static double minValueOf(double[][] matrix) {
        final int yCount = matrix.length;
        final int xCount = matrix[1].length;
        double minValue = Double.MAX_VALUE;
        for (int y = 1; y < yCount; y++) {
            for (int x = 1; x < xCount; x++) {
                double value = matrix[y][x];
                if (value < minValue) {
                    minValue = value;
                }
            }
        }
        return minValue;
    }

    public static double maxValueOf(double[][] matrix) {
        final int yCount = matrix.length;
        final int xCount = matrix[1].length;
        double maxValue = Double.MIN_VALUE;
        for (int y = 1; y < yCount; y++) {
            for (int x = 1; x < xCount; x++) {
                double value = matrix[y][x];
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        }
        return maxValue;
    }

}
