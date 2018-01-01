package edu.iga.adi.sm.results.visualization.drawers.surfaces;


import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.drawers.SolutionMapper;
import lombok.Builder;
import lombok.NonNull;
import org.jzy3d.plot3d.builder.Mapper;

@Builder
public class Jzy3dSolutionMapper extends Mapper {

    @NonNull
    private Solution solution;

    @NonNull
    @Builder.Default
    private SolutionMapper solutionMapper = (x, y, z) -> z;

    @Override
    public double f(double x, double y) {
        return solutionMapper.value(x, y, solution.getValue(x, y));
    }

}
