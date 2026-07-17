package codyhuh.druids_n_dinosaurs.util.proxy;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, value = {Dist.CLIENT})
public class ClientProxy extends CommonProxy{

    public ClientProxy() {
    }

    @Override
    public void init() {
    }

    public void clientInit() {
    }

    @Override
    public Level getWorld() {
        return Minecraft.getInstance().level;
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    int clientSludgedAmplifier;

    public int getSludgedAmplifier() {
        return this.clientSludgedAmplifier;
    }

    public void setSludgedAmplifier(int ticks) {
        this.clientSludgedAmplifier = ticks;
    }
}
