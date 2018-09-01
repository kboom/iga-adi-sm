package edu.iga.adi.sm;

import edu.iga.adi.sm.core.dimension.SolutionFactory;
import edu.iga.adi.sm.problems.IterativeProblem;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class Task {

    @NonNull
    TimeMethodType timeMethodType;

    @NonNull
    SolutionFactory solutionFactory;

    @NonNull
    IterativeProblem problem;

}
