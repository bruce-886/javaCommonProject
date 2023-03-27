package com.example.projectCommon.Utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.json.JsonSanitizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SerDesHelper {

    private static final Logger logger = LoggerFactory.getLogger(SerDesHelper.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public String deserializeToString(Object object) {
        try {
            return objectWriter.writeValueAsString(object);
        }
        catch (Exception e) {
            logger.error("Fail to deserialize to string ....");
            throw new RuntimeException(e);
        }
    }

    public <T> T serializeToObject(String objectString, Class<T> T) {
        try {
            return objectMapper.readValue(JsonSanitizer.sanitize(objectString), T);
        }
        catch (Exception e) {
            logger.error("Fail to serialize to object ....");
            logger.error("Error Json : " + JsonSanitizer.sanitize(objectString));
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> serializeToListOfObject(String objectString, Class<T> T) {
        try {
            return objectMapper.readerForListOf(T).readValue(JsonSanitizer.sanitize(objectString));
        }
        catch (Exception e) {
            logger.error("Fail to serialize to object ....");
            logger.error("Error Json : " + JsonSanitizer.sanitize(objectString));
            throw new RuntimeException(e);
        }
    }


}
