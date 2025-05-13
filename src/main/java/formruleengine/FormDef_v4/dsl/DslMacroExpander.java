package formruleengine.FormDef_v4.dsl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DslMacroExpander {

    // dopasowuje: @macro(name, field:target)
    private static final Pattern MACRO_PATTERN = Pattern.compile("@macro\\(([^,]+),\\s*(field|predicate):([^)]+)\\)");

    public String expand(String dsl) {
        Matcher matcher = MACRO_PATTERN.matcher(dsl);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String macroName = matcher.group(1).trim();       // np. "enable"
            String source = matcher.group(2).trim();          // np. "field"
            String target = matcher.group(3).trim();          // np. "nationalId"

            List<String> expansion = expandMacro(macroName, source, target);

            if (expansion == null) {
                throw new IllegalArgumentException("Unknown macro: " + macroName);
            }

            String replacement = String.join(",\n    ", expansion);
            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private List<String> expandMacro(String name, String source, String target) {
        return switch (name) {
            case "enable" -> List.of(
                    target + ".visible = true",
                    target + ".required = true"
            );
            case "disable" -> List.of(
                    target + ".visible = false",
                    target + ".required = false"
            );
            case "hide" -> List.of(
                    target + ".visible = false"
            );
            case "show" -> List.of(
                    target + ".visible = true"
            );
            default -> null;
        };
    }
}
