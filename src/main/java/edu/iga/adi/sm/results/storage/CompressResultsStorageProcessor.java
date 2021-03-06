package edu.iga.adi.sm.results.storage;

import edu.iga.adi.sm.support.ZipUtil;
import lombok.Builder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
public class CompressResultsStorageProcessor implements StorageProcessor {

    @Builder.Default
    private File archiveFile = new File(FileUtils.getUserDirectory(), "solution-archive-" + getTimestamp());

    @Builder.Default
    private boolean unpack = false;

    @Builder.Default
    private boolean pack = true;

    @Override
    public void afterSetUp(File directory) {
        if(unpack) {
            if(!directory.exists() || directory.listFiles().length == 0) {
                ZipUtil.unpack(archiveFile.getAbsolutePath(), directory.getAbsolutePath());
            }
        }
    }

    @Override
    public void beforeTearDown(File directory) throws IOException {
        if(pack) {
            ZipUtil.pack(directory.getAbsolutePath(), archiveFile.getAbsolutePath());
        }
    }

    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(new Date());
    }

}
