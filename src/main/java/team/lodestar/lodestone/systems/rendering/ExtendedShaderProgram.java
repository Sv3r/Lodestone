package team.lodestar.lodestone.systems.rendering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidHierarchicalFileException;
import net.minecraft.util.JsonHelper;
import team.lodestar.lodestone.mixin.accessor.ShaderProgramAccessor;
import java.io.IOException;
import java.util.Arrays;

public class ExtendedShaderProgram extends ShaderProgram {
    public ExtendedShaderProgram(ResourceFactory resourceFactory, String string, VertexFormat vertexFormat) throws IOException {
        super(resourceFactory, string, vertexFormat);
    }

    public static ExtendedShaderProgram createShaderProgram(ShaderHolder shaderHolder, ResourceFactory pResourceProvider, Identifier id, VertexFormat pVertexFormat) throws IOException {
        return new ExtendedShaderProgram(
                pResourceProvider,
                FabricShaderProgram.rewriteAsId(id.toString(), id.toString()),
                pVertexFormat
        ) {
            @Override
            public ShaderHolder getHolder() {
                return shaderHolder;
            }
        };
    }

    public void setUniformDefaults() {
        getHolder().setUniformDefaults();
    }

    public ShaderHolder getHolder() {
        return null;
    }


    @Override
    public void addUniform(JsonElement pJson) throws InvalidHierarchicalFileException {
        if (getHolder().uniforms.isEmpty()) {
            super.addUniform(pJson);
            return;
        }
        JsonObject jsonobject = JsonHelper.asObject(pJson, "uniform");
        String name = JsonHelper.getString(jsonobject, "name");
        int i = GlUniform.getTypeIndex(JsonHelper.getString(jsonobject, "type"));
        int j = JsonHelper.getInt(jsonobject, "count");
        float[] afloat = new float[Math.max(j, 16)];
        JsonArray jsonarray = JsonHelper.getArray(jsonobject, "values");
        if (jsonarray.size() != j && jsonarray.size() > 1) {
            throw new InvalidHierarchicalFileException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
        } else {
            int k = 0;

            for (JsonElement jsonelement : jsonarray) {
                try {
                    afloat[k] = JsonHelper.asFloat(jsonelement, "value");
                } catch (Exception exception) {
                    InvalidHierarchicalFileException chainedjsonexception = InvalidHierarchicalFileException.wrap(
                            exception);
                    chainedjsonexception.addInvalidKey("values[" + k + "]");
                    throw chainedjsonexception;
                }

                ++k;
            }

            if (j > 1 && jsonarray.size() == 1) {
                while (k < j) {
                    afloat[k] = afloat[0];
                    ++k;
                }
            }

            int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
            GlUniform uniform = new GlUniform(name, i + l, j, this);
            if (i <= 3) {
                uniform.setForDataType((int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]);
                if (getHolder().uniforms.contains(name)) {
                    getHolder().defaultUniformData.add(new UniformData.IntegerUniformData(
                            name,
                            i,
                            new int[]{(int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]}
                    ));
                }
            } else if (i <= 7) {
                uniform.setForDataType(afloat[0], afloat[1], afloat[2], afloat[3]);
            } else {
                uniform.set(Arrays.copyOfRange(afloat, 0, j));
            }
            if (i > 3) {
                if (getHolder().uniforms.contains(name)) {
                    getHolder().defaultUniformData.add(new UniformData.FloatUniformData(name, i, afloat));
                }
            }
            ((ShaderProgramAccessor) this).getUniforms().add(uniform);
        }
    }
}
