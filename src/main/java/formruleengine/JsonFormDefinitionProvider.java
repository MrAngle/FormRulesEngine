package formruleengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import formruleengine.FormDef_v3.FormDefinition_v3;
import formruleengine.FormDef_v4.FormSchema;
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

    @Override
    public FormDefinition_v2 getFormDefinition_v2(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(sourcePath + filePath), FormDefinition_v2.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }


    @Override
    public FormDefinition_v3 getFormDefinition_v3(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(sourcePath + filePath), FormDefinition_v3.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }

    @Override
    public FormSchema getFormDefinition_v4(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(sourcePath + filePath), FormSchema.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON file: " + filePath, e);
        }
    }

}
