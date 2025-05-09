package formruleengine.FormDef_v3.predicate;

@FunctionalInterface
public interface PredicateFunction {
    Object evaluate(PredicateContext context);
}
