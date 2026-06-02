package io.github.mrlucky974.dracula_api.api.serialization.codecs;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.Utf8String;

import java.util.UUID;

public interface ExtendedPacketCodecs {
    StreamCodec<ByteBuf, UUID> UUID = uuid(36);

    static StreamCodec<ByteBuf, UUID> uuid(int maxLength) {
        return new StreamCodec<>() {
            public UUID decode(ByteBuf byteBuf) {
                String value = Utf8String.read(byteBuf, maxLength);
                return java.util.UUID.fromString(value);
            }

            public void encode(ByteBuf byteBuf, UUID uuid) {
                Utf8String.write(byteBuf, uuid.toString(), maxLength);
            }
        };
    }
}
