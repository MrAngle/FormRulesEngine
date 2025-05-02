package formruleengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonFormDefinitionProvider implements FormDefinitionProvider {
    private final String filePath =
            "C:\\Users\\User\\IdeaProjects\\InitProject\\src\\test\\groovy\\com\\example\\initproject\\modelActionEngine.json";

    @Override
    public FormDefinition getFormDefinition() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), FormDefinition.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }
}
