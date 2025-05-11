package formruleengine;

import formruleengine.FormDef_v3.FormDefinition_v3;
import formruleengine.FormDef_v4.FormSchema;
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

    public FormDefinition_v3 getFormDefinition_v3(String filePath) {
        return formDefinitionProvider.getFormDefinition_v3(filePath);
    }

    public FormSchema getFormDefinition_v4(String filePath) {
        return formDefinitionProvider.getFormDefinition_v4(filePath);
    }


//    public FormDefinition_v2 process_v2(FormDefinition_v2 formDefinition_v2) {
//        formDefinition_v2.getRules().get(0).getCondition().
//
////        return formDefinitionProvider.getFormDefinition_v2(filePath);
//    }
}
