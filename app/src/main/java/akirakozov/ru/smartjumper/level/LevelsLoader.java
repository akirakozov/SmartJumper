package akirakozov.ru.smartjumper.level;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by akirakozov on 13.06.16.
 */
public class LevelsLoader {
    private static final ObjectMapper MAPPER = createObjectMapper();

    public static String storeLevels(Levels levels) throws JsonProcessingException {
        return MAPPER.writeValueAsString(levels);
    }

    public static void storeLevels(Levels levels, String path) {
        try {
            File out = new File(path);
            if (!out.exists()) {
                out.createNewFile();
            }

            FileWriter writer = null;
            try {
                writer = new FileWriter(out.getAbsoluteFile());
                writer.write(storeLevels(levels));
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Levels readLevels(String json) throws IOException {
        return MAPPER.readValue(json, Levels.class);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }
}
