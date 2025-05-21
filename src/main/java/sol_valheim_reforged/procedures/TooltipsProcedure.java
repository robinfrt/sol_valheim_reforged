package sol_valheim_reforged.procedures;

import sol_valheim_reforged.configuration.CommonConfiguration;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

import java.util.List;

@Mod.EventBusSubscriber
public class TooltipsProcedure {
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		double food_duration = 0;
		if (itemstack.getItem().isEdible()) {
			if (itemstack.getItem() == Items.ROTTEN_FLESH) {
				tooltip.add(Component.literal("\u00A7a\u2620 Empties your stomach"));
			} else if (itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:low_nutrients")))) {
				tooltip.add(1, Component.literal("\u00A7c\u2764 0.5 Hearts"));
				tooltip.add(2, Component.literal("\u00A74\u2600 5 Regen"));
				tooltip.add(3, Component.literal("\u00A76\u231A 5 Minutes"));
			} else if (itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:high_nutrients")))) {
				tooltip.add(1, Component.literal("\u00A7c\u2764 7 Hearts"));
				tooltip.add(2, Component.literal("\u00A74\u2600 70 Regen"));
				tooltip.add(3, Component.literal("\u00A76\u231A 60 Minutes"));
			} else {
				tooltip.add(1, Component.literal((new java.text.DecimalFormat("\u00A7c\u2764 #0.# Hearts").format((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0) * 0.5))));
				tooltip.add(2, Component.literal((new java.text.DecimalFormat("\u00A74\u2600 ## Regen").format((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0) * 5))));
				if ((double) CommonConfiguration.FOOD_DURATION.get() >= 60 && (double) CommonConfiguration.FOOD_DURATION.get() <= 300) {
					food_duration = (double) CommonConfiguration.FOOD_DURATION.get() / 7.65;
				} else {
					food_duration = 180 / 7.65;
				}
				if (itemstack.getMaxStackSize() <= 16 && CommonConfiguration.BONUS_DURATION.get() == true) {
					if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * 2 * food_duration < 5) {
						tooltip.add(3, Component.literal("\u00A76\u231A 5 Minutes"));
					} else if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * 2 * food_duration > 60) {
						tooltip.add(3, Component.literal("\u00A76\u231A 60 Minutes"));
					} else {
						tooltip.add(3, Component.literal((new java.text.DecimalFormat("\u00A76\u231A ## Minutes").format((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * 2 * food_duration))));
					}
				} else {
					if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration < 5) {
						tooltip.add(3, Component.literal("\u00A76\u231A 5 Minutes"));
					} else if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration > 60) {
						tooltip.add(3, Component.literal("\u00A76\u231A 60 Minutes"));
					} else {
						tooltip.add(3, Component.literal((new java.text.DecimalFormat("\u00A76\u231A ## Minutes").format((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration))));
					}
				}
			}
			if (itemstack.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
				tooltip.add(Component.literal("\u00A77Always edible"));
			}
		} else if (itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:cakes"))) || itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:sliceable_food")))) {
			tooltip.add(Component.literal("\u00A77Heals 2 \u00A7c\u2764 \u00A77per slice"));
		}
	}
}
