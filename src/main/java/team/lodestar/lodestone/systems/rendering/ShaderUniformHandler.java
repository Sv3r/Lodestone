package team.lodestar.lodestone.systems.rendering;

import net.minecraft.client.gl.ShaderProgram;

public interface ShaderUniformHandler {
    ShaderUniformHandler LUMITRANSPARENT = instance -> {
        instance.getUniformOrDefault("LumiTransparency").set(1f);
    };

    void updateShaderData(ShaderProgram instance);
}
