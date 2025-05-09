package formruleengine.FormDef_v3.formgeneratordomain;

import formruleengine.FormDef_v3.FormDataMapper;
import formruleengine.FormDef_v3.FormDefinition_v3;
import formruleengine.FormDef_v3.domain.ClaimForm;
import formruleengine.FormDef_v3.domain.DomainService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static formruleengine.FormDef_v3.FormDataMapper.fieldAccessors;

public class FormGeneratorDomain {

//    public static List<FormDefinition_v3.Rule> generatePrefillRules() {
//        ClaimForm form = DomainService.getClaimForm();
////        def prefillRules = FormGeneratorDomain.generatePrefillRules(claimForm) // <- metoda powyżej
//
//
//        return fieldAccessors.entrySet().stream()
//                .map(entry -> {
//                    String fieldKey = entry.getKey();
//                    Object value = entry.getValue().apply(form);
//                    if (value == null) return null;
//
//                    FormDefinition_v3.Action action = new FormDefinition_v3.Action();
//                    action.setField(fieldKey);
//                    action.setProperty("value");
//                    action.setValue(value);
//
//                    FormDefinition_v3.Rule rule = new FormDefinition_v3.Rule();
//                    rule.setAction(action);
//                    rule.setCondition(null);
//
//                    return rule;
//                })
//                .filter(Objects::nonNull)
//                .toList();
//    }

    public static void enrichWithPrefillRules(FormDefinition_v3 definition) {
        ClaimForm form = DomainService.getClaimForm();
        for (Map.Entry<String, Function<ClaimForm, Object>> entry : fieldAccessors.entrySet()) {
            String fieldKey = entry.getKey();
            Object value = entry.getValue().apply(form);
            if (value == null) continue;

            FormDefinition_v3.Action action = new FormDefinition_v3.Action();
            action.setField(fieldKey);
            action.setProperty("defaultValue");
            action.setValue(value);

            FormDefinition_v3.Rule rule = new FormDefinition_v3.Rule();
            rule.setAction(action);
            rule.setCondition(null); // zawsze wykonuj, brak warunku

            definition.addRule(rule);
        }
    }
}
