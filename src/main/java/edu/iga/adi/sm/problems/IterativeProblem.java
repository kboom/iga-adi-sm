package edu.iga.adi.sm.problems;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;

import java.util.Optional;

public interface IterativeProblem {

    Problem getInitialProblem();

    Optional<Problem> getNextProblem(Solution solution);

}
