package edu.iga.adi.sm;

import com.sun.istack.internal.NotNull;
import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Task {

    @NotNull
    TimeMethodType timeMethodType;

    @NotNull
    SolutionFactory solutionFactory;

    @NotNull
    IterativeProblem problem;

}
