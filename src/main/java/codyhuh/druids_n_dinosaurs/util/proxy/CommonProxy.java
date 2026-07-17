package codyhuh.druids_n_dinosaurs.util.proxy;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = DruidsNDinosaurs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {


    public CommonProxy() {
    }

    public void init() {
    }

    public void clientInit() {

    }

    public Level getWorld() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }

    public Player getClientSidePlayer() {
        return null;
    }

    int commonSludgedAmplifier;

    public int getSludgedAmplifier() {
        return this.commonSludgedAmplifier;
    }

    public void setSludgedAmplifier(int ticks) {
        this.commonSludgedAmplifier = ticks;
    }

    public int maxSludgedAmplifier() {
        return 3;
    }
}
