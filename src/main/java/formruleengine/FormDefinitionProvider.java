package formruleengine;

public interface FormDefinitionProvider {
    FormDefinition getFormDefinition(String filePath);
    FormDefinition_v2 getFormDefinition_v2(String filePath);
}
