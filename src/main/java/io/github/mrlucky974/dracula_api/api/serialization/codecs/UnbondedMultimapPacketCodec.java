package io.github.mrlucky974.dracula_api.api.serialization.codecs;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;

public record UnbondedMultimapPacketCodec<B extends ByteBuf, K, V>(
        StreamCodec<? super B, K> keyCodec,
        StreamCodec<? super B, V> elementCodec
) implements StreamCodec<B, Multimap<K, V>> {

    public static <B extends ByteBuf, K, V> UnbondedMultimapPacketCodec<B, K, V> packetCodec(
            final StreamCodec<? super B, K> keyCodec,
            final StreamCodec<? super B, V> elementCodec
    ) {
        return new UnbondedMultimapPacketCodec<>(keyCodec, elementCodec);
    }

    @Override
    public Multimap<K, V> decode(B byteBuf) {
        Multimap<K, V> modifiers = ArrayListMultimap.create();

        int size = byteBuf.readInt();
        for (int i = 0; i < size; i++) {
            K key = keyCodec.decode(byteBuf);
            V element = elementCodec.decode(byteBuf);
            modifiers.put(key, element);
        }

        return modifiers;
    }

    @Override
    public void encode(B byteBuf, Multimap<K, V> value) {
        byteBuf.writeInt(value.size());

        for (Map.Entry<K, V> entry : value.entries()) {
            keyCodec.encode(byteBuf, entry.getKey());
            elementCodec.encode(byteBuf, entry.getValue());
        }
    }
}