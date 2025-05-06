package formruleengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonFormDefinitionProvider implements FormDefinitionProvider {

    final static String sourcePath = "C:\\Users\\User\\IdeaProjects\\InitProject\\src\\test\\groovy\\com\\example\\initproject\\";

    @Override
    public FormDefinition getFormDefinition(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(sourcePath + filePath), FormDefinition.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }
}
