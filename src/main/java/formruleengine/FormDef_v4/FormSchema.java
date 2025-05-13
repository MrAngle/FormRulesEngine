package formruleengine.FormDef_v4;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormSchema {

    private String formId;
    private String version;
    private String formName;
    private String rulesDsl;
    private Map<String, Field> schema;
    private Map<String, Group> groups;
    private List<ActionRule> actions;
    private List<ActionRule> prefillContextRules;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {
        private String label;
        private String type;
        private List<String> possibleOptions;
        private String defaultValue;
        private Boolean required;
        private Boolean visible;
        private Boolean editable;
        private String regex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Group {
        private String label;
        private List<String> fields;
        private Boolean required;
        private Boolean visible;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActionRule {
        @JsonDeserialize(using = ConditionDeserializer.class)
        private Condition when;
        private List<Action> actions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Action {
        private String target;
        private Map<String, Object> properties;
    }

    public interface Condition {}

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SimpleCondition implements Condition {
        private String field;
        private String operator;
        private Object value;
        private String source; // optional
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GroupCondition implements Condition {
        @JsonProperty("operator")
        private String operator;

        @JsonDeserialize(contentUsing = ConditionDeserializer.class)
        private List<Condition> conditions;
    }
}
