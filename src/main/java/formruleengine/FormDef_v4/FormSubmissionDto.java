package formruleengine.FormDef_v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmissionDto {
    private String formId;
    private String version;
    private Map<String, Object> answers;
}