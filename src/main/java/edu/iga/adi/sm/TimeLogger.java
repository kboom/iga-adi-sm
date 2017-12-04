package edu.iga.adi.sm;

public class TimeLogger {

    private long totalCreationMs;
    private long totalInitializationMs;
    private long totalFactorizationMs;
    private long totalBackwardSubstitutionMs;

    private long startTime;


    public TimeLogger() {

    }

    public final void logCreation() {
        startTime = getCurrentMillis();
    }

    public final void logInitialization() {
        long newTime = getCurrentMillis();
        totalCreationMs += newTime - startTime;
        startTime = newTime;
    }

    public final void logFactorization() {
        long newTime = getCurrentMillis();
        totalInitializationMs += newTime - startTime;
        startTime = newTime;
    }

    public final void logBackwardSubstitution() {
        long newTime = getCurrentMillis();
        totalFactorizationMs += newTime - startTime;
        startTime = newTime;

    }

    public final void logSolution() {
        long newTime = getCurrentMillis();
        totalBackwardSubstitutionMs += newTime - startTime;
        startTime = newTime;
    }

    long getTotalCreationMs() {
        return totalCreationMs;
    }

    long getTotalInitializationMs() {
        return totalInitializationMs;
    }

    long getTotalFactorizationMs() {
        return totalFactorizationMs;
    }

    public long getTotalBackwardSubstitutionMs() {
        return totalBackwardSubstitutionMs;
    }

    long getTotalSolutionMs() {
        return totalCreationMs + totalInitializationMs + totalFactorizationMs + totalBackwardSubstitutionMs;
    }

    private long getCurrentMillis() {
        return System.currentTimeMillis();
    }

}
