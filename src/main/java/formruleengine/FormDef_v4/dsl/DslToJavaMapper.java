package formruleengine.FormDef_v4.dsl;

import com.example.formdsl.parser.FormRulesLexer;
import com.example.formdsl.parser.FormRulesParser;
import formruleengine.FormDef_v4.FormSchema;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class DslToJavaMapper {

    public List<FormSchema.ActionRule> parseDsl(String dsl) {
        CharStream input = CharStreams.fromString(dsl);
        FormRulesLexer lexer = new FormRulesLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FormRulesParser parser = new FormRulesParser(tokens);

        FormRulesParser.RulesContext tree = parser.rules(); // root rule
        List<FormSchema.ActionRule> rules = new ArrayList<>();

        for (FormRulesParser.FormRuleContext ruleCtx : tree.formRule()) {
            FormSchema.ActionRule rule = new DslToActionRuleVisitor().visitFormRule(ruleCtx);
            rules.add(rule);
        }

        return rules;
    }
}
