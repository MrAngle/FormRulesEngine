package formruleengine;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDefinition_v2 {
    private String formName;
    private Map<String, Object> metadata;
    private Map<String, Field> fields;
    private List<Rule> rules;
    private Map<String, PredicateDefinition> predicates;

    @FunctionalInterface
    public interface PredicateFunction {
        Object evaluate();
    }

    Map<String, PredicateFunction> predicateRegistry = Map.of(
//            "isNewUser", () -> userService.isNewUser(),  // może być lambda
//            "hasLoyaltyPoints", () -> loyaltyChecker.hasPoints()
    );

    @Data
    public static class Field {
        private String id;
        private String controlName;
        private String type;
        private String label;
        private Boolean visible;
        private List<String> options;
    }

    @Data
    public static class Rule {
        private Action action;

        @JsonProperty("when")
        private Condition condition;
    }

    @Data
    public static class Action {
        private String field;
        private String property;
        private Object value;
    }

    // Sealed interface replacement
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SimpleCondition.class, name = "simple"),
            @JsonSubTypes.Type(value = GroupCondition.class, name = "group")
    })
    public interface Condition {}

    @Data
    public static class SimpleCondition implements Condition {
        private String source;     // field, predicate, metadata
        private String key;
        private String operator;
        private Object value;
    }

    @Data
    public static class GroupCondition implements Condition {
        private String operator; // AND, OR, NOT
        private List<Condition> conditions;
    }

    @Data
    public static class PredicateDefinition {
        private String type;     // metadata, field, etc.
        private String key;
        private String operator;
        private Object value;
    }
}