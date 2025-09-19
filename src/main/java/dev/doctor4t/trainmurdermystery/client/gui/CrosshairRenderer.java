package dev.doctor4t.trainmurdermystery.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.game.TMMGameLoop;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import dev.doctor4t.trainmurdermystery.item.KnifeItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class CrosshairRenderer {
    private static final Identifier CROSSHAIR = TMM.id("hud/crosshair");
    private static final Identifier CROSSHAIR_ATTACK = TMM.id("hud/crosshair_attack");
    private static final Identifier CROSSHAIR_PROGRESS = TMM.id("hud/crosshair_progress");
    private static final Identifier CROSSHAIR_BACKGROUND = TMM.id("hud/crosshair_background");

    public static void renderCrosshair(@NotNull MinecraftClient client, @NotNull ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter) {
        if (!client.options.getPerspective().isFirstPerson()) return;
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        context.getMatrices().push();
        context.getMatrices().translate(context.getScaledWindowWidth() / 2f, context.getScaledWindowHeight() / 2f, 0);
        context.getMatrices().push();
        context.getMatrices().translate(-1.5f, -1.5f, 0);
        context.drawGuiTexture(CROSSHAIR, 0, 0, 3, 3);
        context.getMatrices().pop();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        if (player.getMainHandStack().isOf(TMMItems.KNIFE)) {
            var manager = player.getItemCooldownManager();
            if (manager.isCoolingDown(TMMItems.KNIFE) || !(KnifeItem.getKnifeTarget(player) instanceof EntityHitResult)) {
                var f = 1 - manager.getCooldownProgress(TMMItems.KNIFE, tickCounter.getTickDelta(true));
                context.drawGuiTexture(CROSSHAIR_BACKGROUND, -5, 5, 10, 7);
                context.drawGuiTexture(CROSSHAIR_PROGRESS, 10, 7, 0, 0, -5, 5, (int) (f * 10.0f), 7);
            } else {
                context.drawGuiTexture(CROSSHAIR_ATTACK, -5, 5, 10, 7);
            }
        }
        context.getMatrices().pop();
    }
}