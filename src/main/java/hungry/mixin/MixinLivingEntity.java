package hungry.mixin;

import hungry.init.Mod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @Inject(method = "damage", at = @At("HEAD"))
    public void onPlayerDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // 检查是否是玩家
        if ((Object) this instanceof ServerPlayerEntity) {
            if (source.isOf(DamageTypes.STARVE)) {
                if (Mod.TRACKER.isActive()) {
                    Mod.TRACKER.startBleeding();
                }
            }
        }
    }
}