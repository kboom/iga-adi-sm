package edu.iga.adi.sm.core.dimension;

import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.core.direction.RunInformation;

public interface SolutionFactory {

    Solution createFinalSolution(Solution solution, RunInformation runInformation);

}
