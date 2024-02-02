package team.lodestar.lodestone.systems.rendering.particle.world;

import com.mojang.serialization.Codec;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.RegistryWrapper;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleEffect;
import java.util.function.Consumer;

public class WorldParticleEffect extends SimpleParticleEffect implements ParticleEffect {

    public static final Factory<WorldParticleEffect> DESERIALIZER = (type, reader, registryLookup) -> new WorldParticleEffect(
            type);
    public final ParticleType<?> type;
    public ParticleTextureSheet textureSheet;
    public Consumer<GenericParticle> actor;
    public boolean noClip = false;

    public WorldParticleEffect(ParticleType<?> type) {
        this.type = type;
    }

    public static Codec<WorldParticleEffect> codecFor(ParticleType<?> type) {
        return Codec.unit(() -> new WorldParticleEffect(type));
    }

    public static PacketCodec<RegistryByteBuf, WorldParticleEffect> packetCodecFor(ParticleType<?> type) {
        return PacketCodec.unit(new WorldParticleEffect(type));
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    public String asString(RegistryWrapper.WrapperLookup lookup) {
        return "";
    }
}
