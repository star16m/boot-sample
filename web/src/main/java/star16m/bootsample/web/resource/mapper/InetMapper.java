package star16m.bootsample.web.resource.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.net.InetAddress;

//@JsonComponent
public class InetMapper {

    public static class InetAddressSerializer extends JsonSerializer<InetAddress> {
        @Override
        public void serialize(InetAddress inetAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("ipAddress", inetAddress.toString());
            jsonGenerator.writeEndObject();
        }
    }

    public static class InetAddressDeserializer extends JsonDeserializer<InetAddress> {
        @Override
        public InetAddress deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return InetAddress.getByName(jsonParser.getText());
        }
    }
}
