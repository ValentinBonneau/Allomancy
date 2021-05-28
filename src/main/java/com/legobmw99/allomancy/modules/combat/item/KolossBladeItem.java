package com.legobmw99.allomancy.modules.combat.item;

import com.legobmw99.allomancy.Allomancy;
import com.legobmw99.allomancy.modules.powers.data.AllomancyCapability;
import com.legobmw99.allomancy.util.Metal;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class KolossBladeItem extends SwordItem {
    public KolossBladeItem() {
        super(ItemTier.STONE, 9, -2.6F, new Item.Properties().tab(ItemGroup.TAB_COMBAT));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (entityIn != null && entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;

            if (isSelected && (player.getOffhandItem() != stack)) {
                player.getCapability(AllomancyCapability.PLAYER_CAP).ifPresent(data -> {
                    if (!data.isBurning(Metal.PEWTER)) {
                        player.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 10, 10, true, false));
                        player.addEffect(new EffectInstance(Effects.WEAKNESS, 10, 10, true, false));
                        player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 10, 0, true, false));
                    }
                });
            }
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return (attacker instanceof PlayerEntity) && (attacker.getCapability(AllomancyCapability.PLAYER_CAP).filter(d -> d.isBurning(Metal.PEWTER)).isPresent());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        ITextComponent lore = Allomancy.addColorToText("item.allomancy.koloss_blade.lore", TextFormatting.GRAY);
        tooltip.add(lore);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return false;
    }
}
