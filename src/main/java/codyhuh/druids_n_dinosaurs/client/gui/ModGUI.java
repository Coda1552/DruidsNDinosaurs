package codyhuh.druids_n_dinosaurs.client.gui;

import codyhuh.druids_n_dinosaurs.DruidsNDinosaurs;
import codyhuh.druids_n_dinosaurs.registry.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ModGUI extends ForgeGui {

    protected static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/misc/tetanus_outline.png");

    public ModGUI(Minecraft mc) {
        super(mc);
    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();

        var tetanus = this.minecraft.player.getEffect(ModEffects.TETANUS.get());
        if (tetanus != null) {
            RenderSystem.enableBlend();
            this.renderTextureOverlay(guiGraphics, PUMPKIN_BLUR_LOCATION, 1.0F);
            RenderSystem.disableBlend();
        }

        var sludged = this.minecraft.player.getEffect(ModEffects.SLUDGED.get());
        if (sludged != null){
            RenderSystem.enableBlend();
            this.renderTextureOverlay(guiGraphics, new ResourceLocation(DruidsNDinosaurs.MOD_ID, "textures/misc/sludged_outline.png"), 1);
            RenderSystem.disableBlend();
        }
    }

}
