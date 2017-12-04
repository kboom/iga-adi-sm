package edu.iga.adi.sm.core.dimension;

import edu.iga.adi.sm.core.Solution;

public interface SolutionFactory {

    Solution createFinalSolution(Solution solution);

}
