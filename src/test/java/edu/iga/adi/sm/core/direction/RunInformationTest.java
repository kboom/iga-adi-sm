package edu.iga.adi.sm.core.direction;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RunInformationTest {

    @Test
    public void incrementsRunNumberByOneUponCallingNext() {
        RunInformation runInformation = RunInformation.initialInformation();
        assertThat(runInformation.nextRun().getRunNumber()).isEqualTo(1);
    }

}