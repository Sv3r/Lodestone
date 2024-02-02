package team.lodestar.lodestone.systems.rendering.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.setup.LodestoneRenderLayers;
import team.lodestar.lodestone.setup.LodestoneShaders;

public interface LodestoneWorldParticleTextureSheet extends ParticleTextureSheet {
    LodestoneWorldParticleTextureSheet ADDITIVE = new LodestoneWorldParticleTextureSheet() {
        @Override
        public RenderLayer getRenderLayer() {
            return LodestoneRenderLayers.ADDITIVE_PARTICLE;
        }

        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.setShader(LodestoneShaders.PARTICLE.getInstance());
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tesselator) {
            tesselator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }

    };
    LodestoneWorldParticleTextureSheet TRANSPARENT = new LodestoneWorldParticleTextureSheet() {

        @Override
        public RenderLayer getRenderLayer() {
            return LodestoneRenderLayers.TRANSPARENT_PARTICLE;
        }

        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(LodestoneShaders.PARTICLE.getInstance());
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tesselator) {
            tesselator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }
    };
    LodestoneWorldParticleTextureSheet LUMITRANSPARENT = new LodestoneWorldParticleTextureSheet() {
        @Override
        public RenderLayer getRenderLayer() {
            return LodestoneRenderLayers.LUMITRANSPARENT_PARTICLE;
        }

        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.setShader(LodestoneShaders.PARTICLE.getInstance());
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
        }

        @Override
        public void draw(Tessellator tessellator) {
            tessellator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }
    };

    RenderLayer getRenderLayer();

    default boolean shouldBuffer() {
        return true;
    }
}
