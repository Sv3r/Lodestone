package team.lodestar.lodestone;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.OrtTestItem;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.network.screenshake.ScreenshakePacket;
import team.lodestar.lodestone.setup.LodestoneParticles;

public class LodestoneLib implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("LodestoneLib");
    public static final String MODID = "lodestone";
    public static final Vector3f VEC3F_ZERO = new Vector3f();

    public static final Random RANDOM = Random.create();
    public static final FabricLoader LOADER_INSTANCE = FabricLoader.getInstance();

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    @Override
    public void onInitialize() {
        LodestoneParticles.init();
        if (LOADER_INSTANCE.isDevelopmentEnvironment()) {
            Registry.register(Registries.ITEM, id("ort"), new OrtTestItem(new Item.Settings().rarity(Rarity.EPIC)));
        }

        PayloadTypeRegistry.playS2C().register(ScreenshakePacket.ID, ScreenshakePacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(PositionedScreenshakePacket.ID, PositionedScreenshakePacket.PACKET_CODEC);
    }
}
