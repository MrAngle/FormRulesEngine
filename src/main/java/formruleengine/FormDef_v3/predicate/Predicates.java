package formruleengine.FormDef_v3.predicate;

import java.util.Map;

public class Predicates {
    private static Map<String, PredicateFunction> predicateRegistry = Map.of(
            "isNewUser", ctx -> ctx.get("userType").equals("NEW"),
            "ufgUnavailable", ctx -> Boolean.FALSE.equals(ctx.get("ufgAvailable")),
            "hasClaim", ctx -> Boolean.TRUE.equals(ctx.get("owner.hasClaimIn12Months"))
    );

    public static PredicateFunction getPredicate(String predicateName) {
        return predicateRegistry.get(predicateName);
    }
}
