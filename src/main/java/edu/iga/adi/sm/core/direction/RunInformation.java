package edu.iga.adi.sm.core.direction;

import java.io.Serializable;

public class RunInformation implements Serializable {

    private final int runNumber;

    public RunInformation(int runNumber) {
        this.runNumber = runNumber;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public RunInformation nextRun() {
        return new RunInformation(this.runNumber + 1);
    }

    public static RunInformation initialInformation() {
        return new RunInformation(0);
    }

}
