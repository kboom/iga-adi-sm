package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import lombok.SneakyThrows;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

/**
 * Note that this is the most available but also naive implementation, natively accessible in Java.
 * This will not perform well, it is just for small problems (testing purposes).
 * Can be replaced with more advanced expression parsing tools.
 */
public final class FunctionParsingProblem implements Problem {

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    private final String expression;

    public FunctionParsingProblem(String expression) {
        this.expression = expression;
    }

    @Override
    @SneakyThrows
    public double getValue(double x, double y) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", x);
        vars.put("y", y);

        return ((Number) engine.eval(expression, new SimpleBindings(vars))).doubleValue();
    }
}
