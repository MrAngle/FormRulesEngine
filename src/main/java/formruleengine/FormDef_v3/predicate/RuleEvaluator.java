package formruleengine.FormDef_v3.predicate;

import formruleengine.FormDef_v3.Condition;
import formruleengine.FormDef_v3.FormDefinition_v3;
import formruleengine.FormDef_v3.GroupCondition;
import formruleengine.FormDef_v3.SimpleCondition;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RuleEvaluator {

    public static boolean evaluateCondition(Condition condition, PredicateContext context) {
        if (condition instanceof SimpleCondition simple) {
            Object actual = switch (simple.getSource()) {
                case "field", "metadata" -> context.get(simple.getKey());
                case "predicate" -> {
                    PredicateFunction function = Predicates.getPredicate(simple.getKey());
                    if (function == null) throw new IllegalArgumentException("Missing predicate: " + simple.getKey());
                    yield function.evaluate(context);
                }
                default -> throw new IllegalStateException("Unknown source: " + simple.getSource());
            };

            return compare(actual, simple.getOperator(), simple.getValue());
        }

        if (condition instanceof GroupCondition group) {
            List<Boolean> results = group.getConditions().stream()
                    .map(c -> evaluateCondition(c, context))
                    .toList();

            return switch (group.getOperator()) {
                case "AND" -> results.stream().allMatch(Boolean::booleanValue);
                case "OR" -> results.stream().anyMatch(Boolean::booleanValue);
                default -> throw new IllegalStateException("Unknown operator: " + group.getOperator());
            };
        }

        throw new IllegalStateException("Unknown condition type");
    }

    private static boolean compare(Object actual, String operator, Object expected) {
        return switch (operator) {
            case "EQUAL" -> Objects.equals(actual, expected);
            case "NOT_EQUAL" -> !Objects.equals(actual, expected);
            case "IN" -> expected instanceof Collection<?> coll && coll.contains(actual);
            case "NOT_IN" -> expected instanceof Collection<?> coll && !coll.contains(actual);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }

    public static List<FormDefinition_v3.Rule> pruneRulesByPredicates(List<FormDefinition_v3.Rule> rules, PredicateContext context) {
//        RuleEvaluator evaluator = new RuleEvaluator();

        return rules.stream()
                .filter(rule -> {
                    Condition cond = rule.getCondition();

                    if (cond instanceof GroupCondition group) {
                        if ("OR".equals(group.getOperator())) {
                            // usuń całą regułę, jeśli dowolny predykat jest spełniony
                            return group.getConditions().stream()
                                    .filter(c -> "predicate".equals(getSource(c)))
                                    .noneMatch(c -> RuleEvaluator.evaluateCondition(c, context));
                        }

                        if ("AND".equals(group.getOperator())) {
                            // usuń regułę, jeśli jakikolwiek predykat jest niespełniony
                            return group.getConditions().stream()
                                    .filter(c -> "predicate".equals(getSource(c)))
                                    .allMatch(c -> RuleEvaluator.evaluateCondition(c, context));
                        }
                    }

                    // dla prostych warunków (poza grupą) — zostaw
                    return true;
                })
                .toList();
    }

    private static String getSource(Condition condition) {
        if (condition instanceof SimpleCondition s) return s.getSource();
        return null;
    }
}
