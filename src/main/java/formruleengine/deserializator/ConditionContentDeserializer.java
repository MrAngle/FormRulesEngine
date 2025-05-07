package formruleengine.deserializator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import formruleengine.FormDef_v3.Condition;
import formruleengine.FormDef_v3.GroupCondition;
import formruleengine.FormDef_v3.SimpleCondition;

import java.io.IOException;

public class ConditionContentDeserializer extends JsonDeserializer<Condition> {
    @Override
    public Condition deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        JsonParser parser = node.traverse(codec);
        parser.nextToken();

        if (node.has("conditions") && node.has("operator")) {
            return ctxt.readValue(parser, GroupCondition.class);
        } else {
            return ctxt.readValue(parser, SimpleCondition.class);
        }
    }
}
