package edu.iga.adi.sm.results.storage;

import java.io.File;
import java.io.IOException;

public interface StorageProcessor {

    StorageProcessor NOOP_STORAGE_PROCESSOR = new StorageProcessor() {};

    default void afterSetUp(File directory) {};
    default void beforeTearDown(File directory) throws IOException {};


}
