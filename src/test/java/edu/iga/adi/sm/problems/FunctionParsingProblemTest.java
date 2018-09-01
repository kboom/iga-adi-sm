package edu.iga.adi.sm.problems;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class FunctionParsingProblemTest {

    private final String expression;
    private final int x;
    private final int y;
    private final int expectedResult;

    public FunctionParsingProblemTest(String expression, int x, int y, int expectedResult) {
        this.expression = expression;
        this.x = x;
        this.y = y;
        this.expectedResult = expectedResult;
    }

    @Test
    public void evaluatesExpression() {
        FunctionParsingProblem func = new FunctionParsingProblem(expression);
        assertThat(func.getValue(x, y)).isEqualTo(expectedResult);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"x", 5, 0, 5},
                {"y", 0, 5, 5},
                {"x + y", 0, 1, 1},
                {"x + y", 1, 0, 1},
                {"x + y", 1, 1, 2},
                {"x - y", 1, 1, 0},
                {"x*x + y*y", 2, 3, 13}
        });
    }

}