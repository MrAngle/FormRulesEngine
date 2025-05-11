package formruleengine;

import formruleengine.FormDef_v3.FormDefinition_v3;
import formruleengine.FormDef_v4.FormSchema;

public interface FormDefinitionProvider {
    FormDefinition getFormDefinition(String filePath);
    FormDefinition_v2 getFormDefinition_v2(String filePath);
    FormDefinition_v3 getFormDefinition_v3(String filePath);
    FormSchema getFormDefinition_v4(String filePath);
}
