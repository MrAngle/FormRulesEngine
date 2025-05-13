package formruleengine.FormDef_v4;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.HashMap;

public class FormValidator {

//    public static boolean validateSubmission(FormSchema schema, FormSubmissionDto submission) {
//        Map<String, Object> answers = new HashMap<>(submission.getAnswers());
//        Map<String, FormSchema.Field> fields = schema.getSchema();
//
//        // Apply actions based on answers (simulate evaluation)
//        if (schema.getActions() != null) {
//            for (FormSchema.ActionRule rule : schema.getActions()) {
//                if (evaluateCondition(rule.getWhen(), answers)) {
//                    for (FormSchema.Action action : rule.getActions()) {
//                        String target = action.getTarget();
//                        Map<String, Object> properties = action.getProperties();
//                        if (fields.containsKey(target)) {
//                            FormSchema.Field field = fields.get(target);
//                            if (properties.containsKey("required")) {
//                                field.setRequired((Boolean) properties.get("required"));
//                            }
//                            if (properties.containsKey("visible")) {
//                                field.setVisible((Boolean) properties.get("visible"));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        for (Map.Entry<String, FormSchema.Field> entry : fields.entrySet()) {
//            String fieldKey = entry.getKey();
//            FormSchema.Field field = entry.getValue();
//
//            // Sprawdź czy pole należy do jakiejś grupy i czy grupa nie jest wymagana lub widoczna
//            boolean skipDueToGroup = schema.getGroups().entrySet().stream()
//                    .filter(g -> g.getValue().getFields().contains(fieldKey))
//                    .anyMatch(g -> Boolean.FALSE.equals(g.getValue().getVisible()) || Boolean.FALSE.equals(g.getValue().getRequired()));
//
//            if (skipDueToGroup) continue;
//            if (Boolean.FALSE.equals(field.getVisible())) continue;
//
//            Object value = answers.get(fieldKey);
//
//            if (Boolean.TRUE.equals(field.getRequired())) {
//                if (value == null || (value instanceof String && ((String) value).isBlank())) {
//                    System.out.println("Missing required field: " + fieldKey);
//                    return false;
//                }
//            }
//
//            if (field.getRegex() != null && value instanceof String && !((String) value).isBlank()) {
//                if (!Pattern.matches(field.getRegex(), (String) value)) {
//                    System.out.println("Invalid format for field: " + fieldKey);
//                    return false;
//                }
//            }
//        }
//
////        // Now validate after actions applied
////        for (Map.Entry<String, FormSchema.Field> entry : fields.entrySet()) {
////            String key = entry.getKey();
////            FormSchema.Field field = entry.getValue();
////
////            if (Boolean.FALSE.equals(field.getVisible())) continue;
////
////            Object value = answers.get(key);
////
////            if (Boolean.TRUE.equals(field.getRequired())) {
////                if (value == null || (value instanceof String && ((String) value).isBlank())) {
////                    System.out.println("Missing required field: " + key);
////                    return false;
////                }
////            }
////
////            if (field.getRegex() != null && value instanceof String && !((String) value).isBlank()) {
////                if (!Pattern.matches(field.getRegex(), (String) value)) {
////                    System.out.println("Invalid format for field: " + key);
////                    return false;
////                }
////            }
////        }
//
//        return true;
//    }

    public static boolean validateSubmission(FormSchema schema, FormSubmissionDto submission) {
        Map<String, Object> answers = new HashMap<>(submission.getAnswers());
        Map<String, FormSchema.Field> fields = schema.getSchema();
        Map<String, FormSchema.Group> groups = schema.getGroups();

        // Apply actions
        if (schema.getActions() != null) {
            for (FormSchema.ActionRule rule : schema.getActions()) {
                if (evaluateCondition(rule.getWhen(), answers)) {
                    for (FormSchema.Action action : rule.getActions()) {
                        String target = action.getTarget();
                        Map<String, Object> props = action.getProperties();

                        if (groups.containsKey(target)) {
                            FormSchema.Group group = groups.get(target);
                            if (props.containsKey("required")) group.setRequired((Boolean) props.get("required"));
                            if (props.containsKey("visible")) group.setVisible((Boolean) props.get("visible"));
                        } else if (fields.containsKey(target)) {
                            FormSchema.Field field = fields.get(target);
                            if (props.containsKey("required")) field.setRequired((Boolean) props.get("required"));
                            if (props.containsKey("visible")) field.setVisible((Boolean) props.get("visible"));
                        }
                    }
                }
            }
        }

        // Validation
        for (Map.Entry<String, FormSchema.Field> entry : fields.entrySet()) {
            String key = entry.getKey();
            FormSchema.Field field = entry.getValue();

            // Skip if hidden
            if (Boolean.FALSE.equals(field.getVisible())) continue;

            // Skip if group is not required/visible
            boolean skipDueToGroup = false;
            if (groups != null) {
                for (FormSchema.Group group : groups.values()) {
                    if (group.getFields().contains(key)) {
                        if (Boolean.FALSE.equals(group.getVisible()) || Boolean.FALSE.equals(group.getRequired())) {
                            skipDueToGroup = true;
                            break;
                        }
                    }
                }
            }

            if (skipDueToGroup) continue;

            Object value = answers.get(key);

            if (Boolean.TRUE.equals(field.getRequired())) {
                if (value == null || (value instanceof String && ((String) value).isBlank())) {
                    System.out.println("Missing required field: " + key);
                    return false;
                }
            }

            if (field.getRegex() != null && value instanceof String && !((String) value).isBlank()) {
                if (!Pattern.matches(field.getRegex(), (String) value)) {
                    System.out.println("Invalid format for field: " + key);
                    return false;
                }
            }
        }

        return true;
    }


    private static boolean evaluateCondition(FormSchema.Condition condition, Map<String, Object> answers) {
        if (condition instanceof FormSchema.SimpleCondition simple) {
            Object actualValue = answers.get(simple.getTarget());
            return compare(actualValue, simple.getOperator(), simple.getValue());
        }

        if (condition instanceof FormSchema.GroupCondition group) {
            boolean result;
            if ("AND".equalsIgnoreCase(group.getOperator())) {
                result = group.getConditions().stream().allMatch(c -> evaluateCondition(c, answers));
            } else if ("OR".equalsIgnoreCase(group.getOperator())) {
                result = group.getConditions().stream().anyMatch(c -> evaluateCondition(c, answers));
            } else {
                throw new IllegalArgumentException("Unsupported operator: " + group.getOperator());
            }
            return result;
        }

        return false;
    }

    private static boolean compare(Object actual, String operator, Object expected) {
        if ("EQUAL".equalsIgnoreCase(operator)) {
            return actual != null && actual.equals(expected);
        }
        if ("NOT_EQUAL".equalsIgnoreCase(operator)) {
            return actual != null && !actual.equals(expected);
        }
        return false;
    }
}
