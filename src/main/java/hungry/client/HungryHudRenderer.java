package hungry.client;

import hungry.init.Mod;
import hungry.init.ProgressTracker;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HungryHudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register((DrawContext context, RenderTickCounter tickDelta) -> renderProgressBar(context));
    }

    private static void renderProgressBar(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        // Check if the player has a progress tracker
        ProgressTracker t = Mod.TRACKER;
        if (!t.isActive()) return;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Draw the progress bar
        int barWidth = 180;
        int barHeight = 2;
        int x = (screenWidth - barWidth) / 2;
        int y = screenHeight - 65;

        // Draw background
        context.fill(x, y, x + barWidth, y + barHeight, 0xFF000000); // Black background

        // Draw progress
        long dTime = Math.max(0, t.getBleedTime() - System.currentTimeMillis());
        int progressWidth = (int)(dTime * barWidth / (t.getBleedTime() - t.getStartTime()));
        context.fill(x, y, x + progressWidth, y + barHeight, t.isBleeding() ? 0xFFFF5555 : 0xFF00b1ff); // Red progress
    }
}
