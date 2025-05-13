package formruleengine.FormDef_v4.dsl;

import formruleengine.FormDef_v4.FormSchema;
import com.example.formdsl.parser.FormRulesBaseVisitor;
import com.example.formdsl.parser.FormRulesParser;

import java.util.*;

public class DslToActionRuleVisitor extends FormRulesBaseVisitor<FormSchema.ActionRule> {


    @Override
    public FormSchema.ActionRule visitFormRule(FormRulesParser.FormRuleContext ctx) {
        FormSchema.ActionRule rule = new FormSchema.ActionRule();

        // Warunek
        FormSchema.Condition condition = toCondition(ctx.condition());
        rule.setWhen(condition);

        // Akcje
        Map<String, Map<String, Object>> grouped = new LinkedHashMap<>();

        for (FormRulesParser.ActionContext actionCtx : ctx.actionBlock().action()) {
            String target = actionCtx.target.getText();
            String property = actionCtx.property.getText();
            Object value = parseValue(actionCtx.val);

            grouped
                    .computeIfAbsent(target, k -> new LinkedHashMap<>())
                    .put(property, value);
        }

        List<FormSchema.Action> mergedActions = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : grouped.entrySet()) {
            mergedActions.add(new FormSchema.Action(entry.getKey(), entry.getValue()));
        }

        rule.setActions(mergedActions);

        return rule;
    }

    private FormSchema.Condition toCondition(FormRulesParser.ConditionContext ctx) {
        if (ctx.LOGIC_OP().isEmpty()) {
            return toSimpleCondition(ctx.expression(0));
        }

        FormSchema.GroupCondition groupCondition = new FormSchema.GroupCondition();
        groupCondition.setOperator(ctx.LOGIC_OP(0).getText());

        List<FormSchema.Condition> conditions = new ArrayList<>();
        for (FormRulesParser.ExpressionContext exprCtx : ctx.expression()) {
            conditions.add(toSimpleCondition(exprCtx));
        }

        groupCondition.setConditions(conditions);
        return groupCondition;
    }

    private FormSchema.SimpleCondition toSimpleCondition(FormRulesParser.ExpressionContext ctx) {
        String source = (ctx.source != null)
                ? ctx.source.getText().replace(":", "")  // np. "predicate:" → "predicate"
                : "field"; // domyślny source

        return new FormSchema.SimpleCondition(
                ctx.key.getText(),                        // np. isNewUser, vinNumber
                mapOperator(ctx.op.getText()),            // "==" → "EQUAL"
                parseValue(ctx.val),                      // wartość logiczna, tekst lub liczba
                source
        );
    }

    private String mapOperator(String op) {
        return switch (op) {
            case "==" -> "EQUAL";
            case "!=" -> "NOT_EQUAL";
            default -> op; // fallback (np. dla przyszłych rozszerzeń)
        };
    }

    private FormSchema.Action toAction(FormRulesParser.ActionContext ctx) {
        if (ctx == null || ctx.val == null) {
            int line = (ctx != null && ctx.getStart() != null) ? ctx.getStart().getLine() : -1;
            String text = (ctx != null) ? ctx.getText() : "null";
            throw new IllegalStateException("Null ctx.val in action at line " + line + ": " + text);
        }

        String target = ctx.target.getText();          // alias z action
        String property = ctx.property.getText();      // alias z action
        Object value = parseValue(ctx.val);            // alias z action

        Map<String, Object> props = new HashMap<>();
        props.put(property, value);

        return new FormSchema.Action(target, props);
    }

    private Object parseValue(FormRulesParser.ValueContext ctx) {
        if (ctx.BOOLEAN() != null) return Boolean.parseBoolean(ctx.getText());
        if (ctx.NUMBER() != null) return Integer.parseInt(ctx.getText());
        return unquote(ctx.getText()); // STRING
    }

    private String unquote(String s) {
        return s.replaceAll("^'(.*)'$", "$1");
    }
}
