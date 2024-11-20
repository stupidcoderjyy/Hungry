package hungry.mixin;

import hungry.init.Mod;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
    @Shadow public abstract HungerManager getHungerManager();

    @Inject(method = "eatFood", at = @At("RETURN"))
    public void hookEatFood(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (world.isClient) {
            return;
        }
        if (Mod.TRACKER.isActive()) {
            Mod.TRACKER.reset(getHungerManager()); // Reset progress on eating
        }
    }
}