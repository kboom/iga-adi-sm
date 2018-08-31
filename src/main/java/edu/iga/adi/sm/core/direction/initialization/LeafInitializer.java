package edu.iga.adi.sm.core.direction.initialization;

import edu.iga.adi.sm.core.direction.Vertex;
import edu.iga.adi.sm.core.direction.productions.Production;

import java.util.List;

public interface LeafInitializer {

    List<Production> initializeLeaves(List<Vertex> leafLevelVertices);

}
