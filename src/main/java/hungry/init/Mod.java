package hungry.init;

import com.mojang.brigadier.context.CommandContext;
import hungry.client.HungryHudRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class Mod implements ModInitializer {
    public static final ProgressTracker TRACKER = new ProgressTracker();
    public static ServerPlayerEntity player;

    @Override
    public void onInitialize() {
        // Register the /starve command
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(literal("starve").executes(this::starveSelf)));

        // Handle the progress bar shrinking over time
        ServerTickEvents.END_SERVER_TICK.register(server -> {
                if (player != null && player.isAlive() && !player.isRemoved()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, -1, 255, true, false, false));
                }
        });
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, source) -> {
            if (entity instanceof ServerPlayerEntity) {
                TRACKER.deactivate();
                player = null;
            }
        });
        HungryHudRenderer.register();
    }

    private int starveSelf(CommandContext<ServerCommandSource> context) {
        try {
            ServerCommandSource source = context.getSource();
            if (!(source.getEntity() instanceof ServerPlayerEntity sp)) {
                source.sendError(Text.literal("This command can only be executed by a player."));
                return 0;
            }
            player = sp;
            TRACKER.reset(player.getHungerManager());
            TRACKER.activate();
            return 1; // Command success
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("Failed to activate starving mode."));
            return 0; // Command failure
        }
    }
}