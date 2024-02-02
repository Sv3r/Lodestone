package team.lodestar.lodestone.systems.postprocess;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlImportProcessor;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.LodestoneLib;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LodestoneGlslPreprocessor extends GlImportProcessor {
    @Nullable
    @Override
    public String loadImport(boolean inline, String name) {
        LodestoneLib.LOGGER.debug("Loading moj_import in EffectProgram: " + name);

        Identifier id = new Identifier(name);
        Identifier id1 = new Identifier(id.getNamespace(), "shaders/include/" + id.getPath() + ".glsl");

        try {
            InputStream resource1 = MinecraftClient.getInstance()
                                                   .getResourceManager()
                                                   .getResourceOrThrow(id1)
                                                   .getInputStream();

            String s2;
            try {
                s2 = IOUtils.toString(resource1, StandardCharsets.UTF_8);
            } catch (Throwable throwable1) {
                if (resource1 != null) {
                    try {
                        resource1.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }

                throw throwable1;
            }

            resource1.close();

            return s2;
        } catch (IOException ioexception) {
            LodestoneLib.LOGGER.error("Could not open GLSL import {}: {}", name, ioexception.getMessage());
            return "#error " + ioexception.getMessage();
        }
    }
}
