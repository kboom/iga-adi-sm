package edu.iga.adi.sm.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MeshTest {

    @Test
    public void dofsXIsEqualToXLengthPlusSplineOrder() {
        assertThat(Mesh.aMesh().withOrder(2).withElementsX(12).build().getDofsX()).isEqualTo(14);
        assertThat(Mesh.aMesh().withOrder(3).withElementsX(12).build().getDofsX()).isEqualTo(15);
    }

    @Test
    public void dofsYIsEqualToYLengthPlusSplineOrder() {
        assertThat(Mesh.aMesh().withOrder(2).withElementsY(12).build().getDofsY()).isEqualTo(14);
        assertThat(Mesh.aMesh().withOrder(3).withElementsY(12).build().getDofsY()).isEqualTo(15);
    }

}