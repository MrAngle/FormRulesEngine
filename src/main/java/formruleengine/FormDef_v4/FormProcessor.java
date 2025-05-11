package formruleengine.FormDef_v4;

import formruleengine.FormDef_v4.domain.ClaimContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FormProcessor {

    public static final Map<String, Function<ClaimContext, Object>> fieldAccessors = Map.of(
            "nationalId", ClaimContext::getNationalId,
            "birthDate", ClaimContext::getBirthDate,
            "noVehicle", ClaimContext::isNoVehicle,
            "vinNumber", ClaimContext::getVinNumber,
            "registrationNumber", ClaimContext::getRegistrationNumber
    );


//    public static FormSubmissionDto prefillFromDomain(FormSchema schema, ClaimContext ctx) {
//        Map<String, Object> answers = new HashMap<>();
//
//        for (String fieldKey : schema.getSchema().keySet()) {
//            if (fieldAccessors.containsKey(fieldKey)) {
//                Object value = fieldAccessors.get(fieldKey).apply(ctx);
//                if (value != null) {
//                    answers.put(fieldKey, value);
//                }
//            }
//        }
//
//        return new FormSubmissionDto(schema.getFormId(), schema.getVersion(), answers);
//    }

    public static void prefillSchemaWithDomainData(FormSchema schema, ClaimContext ctx) {
        Map<String, FormSchema.Field> fields = schema.getSchema();

        // 1. Wypełnianie pól na podstawie kontekstu domenowego
        for (Map.Entry<String, FormSchema.Field> entry : fields.entrySet()) {
            String fieldKey = entry.getKey();
            FormSchema.Field field = entry.getValue();

            if (fieldAccessors.containsKey(fieldKey)) {
                Object value = fieldAccessors.get(fieldKey).apply(ctx);
                if (value instanceof String str && !str.isBlank()) {
                    if (field.getRegex() == null || str.matches(field.getRegex())) {
                        field.setDefaultValue(str);
                    }
                }
                if (value instanceof Boolean b) {
                    field.setDefaultValue(String.valueOf(b));
                }
            }
        }

        // 2. Przetwarzanie prefillContextRules
        if (schema.getPrefillContextRules() != null) {
            for (FormSchema.ActionRule rule : schema.getPrefillContextRules()) {
                if (evaluateCondition(rule.getWhen(), fields)) {
                    for (FormSchema.Action action : rule.getActions()) {
                        FormSchema.Field targetField = fields.get(action.getTarget());
                        if (targetField != null) {
                            action.getProperties().forEach((key, value) -> applyProperty(targetField, key, value));
                        }
                    }
                }
            }
        }
    }

    private static boolean evaluateCondition(FormSchema.Condition condition, Map<String, FormSchema.Field> fields) {
        if (condition instanceof FormSchema.SimpleCondition simple) {
            String fieldKey = simple.getField();
            Object valueInSchema = fields.containsKey(fieldKey) ? fields.get(fieldKey).getDefaultValue() : null;
            return compareValues(valueInSchema, simple.getOperator(), simple.getValue());
        }

        if (condition instanceof FormSchema.GroupCondition group) {
            List<Boolean> results = group.getConditions().stream()
                    .map(c -> evaluateCondition(c, fields))
                    .toList();
            return switch (group.getOperator()) {
                case "AND" -> results.stream().allMatch(Boolean::booleanValue);
                case "OR"  -> results.stream().anyMatch(Boolean::booleanValue);
                default -> false;
            };
        }

        return false;
    }

    private static boolean compareValues(Object actual, String operator, Object expected) {
        if (actual == null) return false;
        return switch (operator) {
            case "EQUAL"      -> actual.equals(expected);
            case "NOT_EQUAL"  -> !actual.equals(expected);
            default           -> false;
        };
    }

    private static void applyProperty(FormSchema.Field field, String property, Object value) {
        switch (property) {
            case "defaultValue" -> field.setDefaultValue(String.valueOf(value));
            case "required"     -> field.setRequired(Boolean.parseBoolean(value.toString()));
            case "visible"      -> field.setVisible(Boolean.parseBoolean(value.toString()));
            case "editable"     -> field.setEditable(Boolean.parseBoolean(value.toString()));
        }
    }


}
