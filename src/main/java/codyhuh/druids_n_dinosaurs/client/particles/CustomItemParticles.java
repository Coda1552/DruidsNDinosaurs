package codyhuh.druids_n_dinosaurs.client.particles;

import codyhuh.druids_n_dinosaurs.registry.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomItemParticles extends BreakingItemParticle {

    protected CustomItemParticles(ClientLevel pLevel, double pX, double pY, double pZ, ItemStack pStack) {
        super(pLevel, pX, pY, pZ, pStack);
    }

    @OnlyIn(Dist.CLIENT)
    public static class SludgeProvider implements ParticleProvider<SimpleParticleType> {

        public SludgeProvider(SpriteSet pSprites) {}

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CustomItemParticles(pLevel, pX, pY, pZ, new ItemStack(ModItems.SLUDGE_BALL.get()));
        }
    }
}
