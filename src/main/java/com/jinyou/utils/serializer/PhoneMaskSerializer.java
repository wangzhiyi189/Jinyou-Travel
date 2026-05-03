package com.jinyou.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class PhoneMaskSerializer extends StdSerializer<String> {
    public PhoneMaskSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String phone, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (phone != null && phone.length() == 11) {
            // 前3位 + **** + 后4位
            gen.writeString(phone.substring(0, 3) + "****" + phone.substring(7));
        } else {
            gen.writeString(phone);
        }
    }
}
