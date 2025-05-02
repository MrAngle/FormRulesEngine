package formruleengine;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormDefinition {
    private String formName;
    private Map<String, Object> metadata;
    private Map<String, Field> fields;
    private List<Rule> rules;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Field {
        private String id;
        private String controlName;
        private String type;
        private String validator;
        private List<Option> possibleOptions;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Option {
        private String label;
        private String value;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rule {
        private Condition when;
        private Action action;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Action {
        private String field;
        private String property;
        private Object value;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Condition {
        private String logic; // optional
        private List<Condition> conditions; // if logic is used
        private String source;  // "metadata" or "field"
        private String field;
        private Operator operator;
        private Object value;
    }

    public enum Operator {
        EQUAL,
        NOT_EQUAL,
        IN,
        NOT_IN,
        GREATER_THAN,
        LESS_THAN,
        IS_NULL,
        IS_EMPTY
    }
}