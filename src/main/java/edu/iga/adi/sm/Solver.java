package edu.iga.adi.sm;

import edu.iga.adi.sm.core.Problem;
import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.RunInformation;

public interface Solver {

    Solution solveProblem(Problem problem, RunInformation runInformation);

}
