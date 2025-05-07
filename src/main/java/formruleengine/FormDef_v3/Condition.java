package formruleengine.FormDef_v3;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import formruleengine.deserializator.ConditionDeserializer;

//@JsonDeserialize(using = ConditionDeserializer.class)
public interface Condition {
    boolean isGroup();
}