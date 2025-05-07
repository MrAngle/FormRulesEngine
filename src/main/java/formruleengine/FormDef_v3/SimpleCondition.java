package formruleengine.FormDef_v3;

import lombok.Data;

@Data
public class SimpleCondition implements Condition {
    private String source;
    private String key;
    private String operator;
    private Object value;

    @Override
    public boolean isGroup() {
        return false;
    }
}
