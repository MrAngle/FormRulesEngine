package formruleengine.FormDef_v3.predicate;

import java.util.Map;

public class PredicateContext {
    private final Map<String, Object> metadata;
    private final Map<String, Object> fieldValues;
    private final Map<String, Object> sessionData;

    public PredicateContext(Map<String, Object> metadata, Map<String, Object> fieldValues, Map<String, Object> sessionData) {
        this.metadata = metadata;
        this.fieldValues = fieldValues;
        this.sessionData = sessionData;
    }

    public Object get(String key) {
        // Lookup priority: fieldValues > metadata > sessionData
        if (fieldValues.containsKey(key)) return fieldValues.get(key);
        if (metadata.containsKey(key)) return metadata.get(key);
        return sessionData.get(key);
    }
}