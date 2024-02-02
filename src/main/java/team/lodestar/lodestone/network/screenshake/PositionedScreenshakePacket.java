package team.lodestar.lodestone.network.screenshake;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.screenshake.PositionedScreenshakeInstance;

public class PositionedScreenshakePacket extends ScreenshakePacket {
    public static final CustomPayload.Id<PositionedScreenshakePacket> ID = new CustomPayload.Id<>(LodestoneLib.id(
            "positionedscreenshake"));
    public static final PacketCodec<RegistryByteBuf, PositionedScreenshakePacket> PACKET_CODEC = PacketCodec.of(
            PositionedScreenshakePacket::write,
            PositionedScreenshakePacket::new
    );

    public final Vec3d position;
    public final float falloffDistance;
    public final float minDot;
    public final float maxDistance;
    public final Easing falloffEasing;

    public PositionedScreenshakePacket(int duration, Vec3d position, float falloffDistance, float minDot, float maxDistance, Easing falloffEasing) {
        super(duration);
        this.position = position;
        this.falloffDistance = falloffDistance;
        this.minDot = minDot;
        this.maxDistance = maxDistance;
        this.falloffEasing = falloffEasing;
    }

    public PositionedScreenshakePacket(RegistryByteBuf buf) {
        super(buf);
        position = buf.readVec3d();
        falloffDistance = buf.readFloat();
        minDot = buf.readFloat();
        maxDistance = buf.readFloat();
        falloffEasing = Easing.valueOf(buf.readString());
    }

    public PositionedScreenshakePacket(int duration, Vec3d position, float falloffDistance, float maxDistance) {
        this(duration, position, falloffDistance, 0f, maxDistance, Easing.LINEAR);
    }

    public void write(RegistryByteBuf buf) {
        super.write(buf);
        buf.writeVec3d(position);
        buf.writeFloat(falloffDistance);
        buf.writeFloat(minDot);
        buf.writeFloat(maxDistance);
        buf.writeString(falloffEasing.name);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void apply(ClientPlayNetworking.Context context) {
        ScreenshakeHandler.addScreenshake(new PositionedScreenshakeInstance(
                duration,
                position,
                falloffDistance,
                minDot,
                maxDistance,
                falloffEasing
        ).setIntensity(intensity1, intensity2, intensity3)
         .setEasing(intensityCurveStartEasing, intensityCurveEndEasing));
    }
}
