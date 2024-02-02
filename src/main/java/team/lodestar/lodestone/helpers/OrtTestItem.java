package team.lodestar.lodestone.helpers;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.systems.rendering.particle.Easing;

public class OrtTestItem extends Item {
    public OrtTestItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity user = context.getPlayer();
            if (user != null) {
                serverWorld.getPlayers()
                           .stream()
                           .filter(player -> player.getWorld()
                                                   .isChunkLoaded(
                                                           new ChunkPos(user.getBlockPos()).x,
                                                           new ChunkPos(user.getBlockPos()).z
                                                   ))
                           .forEach(player -> {
                               PositionedScreenshakePacket packet = new PositionedScreenshakePacket(
                                       70,
                                       Vec3d.ofCenter(context.getBlockPos()),
                                       20f,
                                       0.3f,
                                       25f,
                                       Easing.CIRC_IN
                               );
                               packet.setIntensity(0f, 1f, 0f)
                                     .setEasing(Easing.CIRC_OUT, Easing.CIRC_IN);
                               ServerPlayNetworking.send(player, packet);
                           });
            }
        }
        return super.useOnBlock(context);
    }
}
