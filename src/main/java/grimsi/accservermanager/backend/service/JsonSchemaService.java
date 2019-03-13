package grimsi.accservermanager.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.springframework.stereotype.Service;

@Service
public class JsonSchemaService {
    public String getJsonSchema(Class targetClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(new ObjectMapper());
            JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(targetClass);
            return objectMapper.writeValueAsString(jsonSchema);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
