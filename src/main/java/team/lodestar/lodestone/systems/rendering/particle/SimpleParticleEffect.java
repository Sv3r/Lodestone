package team.lodestar.lodestone.systems.rendering.particle;

import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;
import java.awt.Color;

public class SimpleParticleEffect {
    public static final ColorParticleData DEFAULT_COLOR = ColorParticleData.create(Color.WHITE, Color.BLACK).build();
    public static final SpinParticleData DEFAULT_SPIN = SpinParticleData.create(0).build();
    public static final GenericParticleData DEFAULT_GENERIC = GenericParticleData.create(1, 0).build();
    public ParticleSpritePicker spritePicker = ParticleSpritePicker.FIRST_INDEX;
    public ParticleDiscardFunctionType discardFunctionType = ParticleDiscardFunctionType.NONE;
    public ColorParticleData colorData = DEFAULT_COLOR;
    public GenericParticleData transparencyData = DEFAULT_GENERIC;
    public GenericParticleData scaleData = DEFAULT_GENERIC;
    public SpinParticleData spinData = DEFAULT_SPIN;
    public int lifetime = 20;
    public float gravity = 0f;

    public enum ParticleSpritePicker {
        FIRST_INDEX, LAST_INDEX, WITH_AGE, RANDOM_SPRITE
    }

    public enum ParticleDiscardFunctionType {
        NONE, INVISIBLE, ENDING_CURVE_INVISIBLE
    }
}
