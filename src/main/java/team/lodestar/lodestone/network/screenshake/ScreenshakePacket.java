package team.lodestar.lodestone.network.screenshake;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class ScreenshakePacket implements CustomPayload {
    public static final CustomPayload.Id<ScreenshakePacket> ID = new CustomPayload.Id<>(LodestoneLib.id("screenshake"));
    public static final PacketCodec<RegistryByteBuf, ScreenshakePacket> PACKET_CODEC = PacketCodec.of(
            ScreenshakePacket::write,
            ScreenshakePacket::new
    );

    public final int duration;
    public float intensity1, intensity2, intensity3;
    public Easing intensityCurveStartEasing = Easing.LINEAR, intensityCurveEndEasing = Easing.LINEAR;

    public ScreenshakePacket(int duration) {
        this.duration = duration;
    }

    public ScreenshakePacket(RegistryByteBuf buf) {
        duration = buf.readInt();
        intensity1 = buf.readFloat();
        intensity2 = buf.readFloat();
        intensity3 = buf.readFloat();
        setEasing(Easing.valueOf(buf.readString()), Easing.valueOf(buf.readString()));
    }

    public ScreenshakePacket setIntensity(float intensity) {
        return setIntensity(intensity, intensity);
    }

    public ScreenshakePacket setIntensity(float intensity1, float intensity2) {
        return setIntensity(intensity1, intensity2, intensity2);
    }

    public ScreenshakePacket setIntensity(float intensity1, float intensity2, float intensity3) {
        this.intensity1 = intensity1;
        this.intensity2 = intensity2;
        this.intensity3 = intensity3;
        return this;
    }

    public ScreenshakePacket setEasing(Easing easing) {
        this.intensityCurveStartEasing = easing;
        this.intensityCurveEndEasing = easing;
        return this;
    }

    public ScreenshakePacket setEasing(Easing intensityCurveStartEasing, Easing intensityCurveEndEasing) {
        this.intensityCurveStartEasing = intensityCurveStartEasing;
        this.intensityCurveEndEasing = intensityCurveEndEasing;
        return this;
    }

    public void write(RegistryByteBuf buf) {
        buf.writeInt(duration);
        buf.writeFloat(intensity1);
        buf.writeFloat(intensity2);
        buf.writeFloat(intensity3);
        buf.writeString(intensityCurveStartEasing.name);
        buf.writeString(intensityCurveEndEasing.name);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void apply(ClientPlayNetworking.Context context) {
        ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(duration).setIntensity(
                intensity1,
                intensity2,
                intensity3
        ).setEasing(intensityCurveStartEasing, intensityCurveEndEasing));
    }
}
