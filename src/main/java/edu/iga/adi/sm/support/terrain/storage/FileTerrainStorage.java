package edu.iga.adi.sm.support.terrain.storage;

import edu.iga.adi.sm.support.terrain.TerrainPoint;
import lombok.Builder;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

// todo try this out for performance: https://stackoverflow.com/questions/9093888/fastest-way-of-reading-relatively-huge-byte-files-in-java
@Builder
public class FileTerrainStorage implements TerrainStorage {

    private static final String LINE_ENDING_PATTERN = Pattern.quote(System.getProperty("line.separator"));

    private String inFilePath;


    @Override
    @SneakyThrows
    public Stream<TerrainPoint> loadTerrainPoints() {
        final FileChannel channel = new FileInputStream(inFilePath).getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        String fullText = StandardCharsets.UTF_8.decode(buffer).toString();

        try {
            return Arrays.stream(fullText.split(LINE_ENDING_PATTERN)).parallel().map(line -> {
                String[] values = line.split(" ");
                return new TerrainPoint(Double.parseDouble(values[0]), Double.parseDouble(values[1]), Double.parseDouble(values[2]));
            });
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            channel.close();
        }
    }

    @Override
    public void saveTerrainPoints(Stream<TerrainPoint> terrainPointStream) {

    }

}
