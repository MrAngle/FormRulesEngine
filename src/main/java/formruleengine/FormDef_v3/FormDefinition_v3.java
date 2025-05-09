package formruleengine.FormDef_v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import formruleengine.deserializator.ConditionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDefinition_v3 {
    private String formName;
    private Map<String, Object> metadata;
    private Map<String, Field> fields;
    private List<FieldGroup> fieldGroups;
    private List<Rule> rules;

    public void addRule(Rule rule) {
        if (rules == null) {
            rules = new ArrayList<>();
        } else if (!(rules instanceof ArrayList)) {
            rules = new ArrayList<>(rules); // kopia modyfikowalna
        }
        rules.add(rule);
    }

    @Data
    public static class Field {
        private String id;
        private String label;
        private Boolean visible;
        private Boolean required;
        private String type;
        private List<String> options;
    }

    @Data
    public static class FieldGroup {
        private String id;
        private String label;
        private List<String> fields;
    }

    @Data
    public static class Rule {
        private Action action;

        @JsonProperty("when")
        @JsonDeserialize(using = ConditionDeserializer.class)
        private Condition condition;
    }

    @Data
    public static class Action {
        private String field;
        private String property;
        private Object value;
    }

//    @JsonDeserialize(using = ConditionDeserializer.class)
//    public interface Condition {
//        boolean isGroup();
//    }

//    @Data
//    public static class SimpleCondition implements Condition {
//        private String source;
//        private String key;
//        private String operator;
//        private Object value;
//
//        @Override
//        public boolean isGroup() {
//            return false;
//        }
//    }

//    @Data
//    public static class GroupCondition implements Condition {
//        private String operator;
//
//        @JsonDeserialize(contentUsing = ConditionDeserializer.class)
//        private List<Condition> conditions;
//
//        @Override
//        public boolean isGroup() {
//            return true;
//        }
//    }
}
