package edu.iga.adi.sm.results.visualization.drawers.jzy3d;


import edu.iga.adi.sm.core.Solution;
import edu.iga.adi.sm.results.visualization.drawers.SolutionMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jzy3d.plot3d.builder.Mapper;

import java.util.Optional;

@Builder
@Getter
@Setter
public class Jzy3dSolutionMapper extends Mapper {

    private Solution solution;

    @NonNull
    @Builder.Default
    private SolutionMapper solutionMapper = (x, y, z) -> z;

    @Override
    public double f(double x, double y) {
        return solutionMapper.value(x, y, Optional.ofNullable(solution).map(solution -> solution.getValue(x, y)).orElse(0D));
    }

    public Jzy3dSolutionMapper mappedWith(SolutionMapper extraMapper) {
        return new Jzy3dSolutionMapper(solution, solutionMapper) {

            @Override
            public double f(double x, double y) {
                return extraMapper.value(x, y, Jzy3dSolutionMapper.this.f(x, y));
            }

        };
    }

}
