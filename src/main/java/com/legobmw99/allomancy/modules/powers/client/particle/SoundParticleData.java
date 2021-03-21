package com.legobmw99.allomancy.modules.powers.client.particle;

import com.legobmw99.allomancy.modules.powers.client.PowersClientSetup;
import com.mojang.brigadier.StringReader;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.Registry;

public class SoundParticleData implements IParticleData {


    public static final IParticleData.IDeserializer<SoundParticleData> DESERIALIZER = new IParticleData.IDeserializer<SoundParticleData>() {

        @Override
        public SoundParticleData fromCommand(ParticleType<SoundParticleData> particleTypeIn, StringReader reader) {
            return new SoundParticleData(SoundCategory.AMBIENT);
        }

        @Override
        public SoundParticleData fromNetwork(ParticleType<SoundParticleData> particleTypeIn, PacketBuffer buffer) {
            return new SoundParticleData(buffer.readEnum(SoundCategory.class));
        }
    };
    private final SoundCategory type;

    public SoundParticleData(SoundCategory type) {
        this.type = type;
    }

    protected SoundCategory getSoundType() {
        return this.type;
    }

    @Override
    public ParticleType<?> getType() {
        return PowersClientSetup.SOUND_PARTICLE_TYPE.get();
    }

    @Override
    public void writeToNetwork(PacketBuffer buffer) {
        buffer.writeEnum(this.type);
    }

    @Override
    public String writeToString() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + this.type.toString();
    }
}
