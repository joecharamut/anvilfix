package rocks.spaghetti.anvilfix.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {
    @Shadow @Final private Property levelCost;

    @Redirect(
            method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"
            )
    )
    private int modifyMaxLevel(Enchantment instance) {
        return Integer.MAX_VALUE;
    }

    @Redirect(
            method = "updateResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/screen/AnvilScreenHandler;levelCost:Lnet/minecraft/screen/Property;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private Property modifyLevelCost(AnvilScreenHandler instance) {
        if (this.levelCost.get() >= 40) {
            this.levelCost.set(39);
        }
        return this.levelCost;
    }
}
