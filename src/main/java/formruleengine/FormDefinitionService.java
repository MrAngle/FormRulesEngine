package formruleengine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FormDefinitionService {
    private final FormDefinitionProvider formDefinitionProvider;

    public FormDefinition getFormDefinition(String filePath) {
        return formDefinitionProvider.getFormDefinition(filePath);
    }
    public FormDefinition_v2 getFormDefinition_v2(String filePath) {
        return formDefinitionProvider.getFormDefinition_v2(filePath);
    }
}
