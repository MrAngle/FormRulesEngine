package formruleengine.FormDef_v3;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import formruleengine.deserializator.ConditionContentDeserializer;
import lombok.Data;

import java.util.List;

@Data
public class GroupCondition implements Condition {

    private String operator;

    @JsonDeserialize(contentUsing = ConditionContentDeserializer.class)
    private List<Condition> conditions;

    @Override
    public boolean isGroup() {
        return true;
    }
}
