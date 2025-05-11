package formruleengine.FormDef_v4;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ConditionDeserializer extends StdDeserializer<FormSchema.Condition> {

    public ConditionDeserializer() {
        super(FormSchema.Condition.class);
    }

    @Override
    public FormSchema.Condition deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = (ObjectMapper) parser.getCodec();
        ObjectNode node = mapper.readTree(parser);

        if (node.has("operator") && node.has("conditions")) {
            return mapper.treeToValue(node, FormSchema.GroupCondition.class);
        } else {
            return mapper.treeToValue(node, FormSchema.SimpleCondition.class);
        }
    }
}
