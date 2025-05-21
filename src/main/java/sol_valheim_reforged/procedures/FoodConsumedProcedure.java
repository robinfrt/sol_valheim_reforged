package sol_valheim_reforged.procedures;

import sol_valheim_reforged.network.SolValheimReforgedModVariables;

import sol_valheim_reforged.configuration.CommonConfiguration;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class FoodConsumedProcedure {
	@SubscribeEvent
	public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity(), event.getItem());
		}
	}

	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		execute(null, world, entity, itemstack);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double local_duration = 0;
		double local_health_boost = 0;
		double local_regen_factor = 0;
		double food_duration = 0;
		if (itemstack.getItem().isEdible()) {
			if (itemstack.getItem() == Items.ROTTEN_FLESH) {
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 600);
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.STARVE)), 1);
				PlayerResetProcedure.execute(entity);
			} else {
				if (itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:low_nutrients")))) {
					local_health_boost = 1;
					local_regen_factor = 5;
					local_duration = 6000;
				} else if (itemstack.is(ItemTags.create(new ResourceLocation("sol_valheim_reforged:high_nutrients")))) {
					local_health_boost = 14;
					local_regen_factor = 70;
					local_duration = 72000;
				} else {
					local_health_boost = itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0;
					local_regen_factor = (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0) * 5;
					if ((double) CommonConfiguration.FOOD_DURATION.get() >= 60 && (double) CommonConfiguration.FOOD_DURATION.get() <= 300) {
						food_duration = (double) CommonConfiguration.FOOD_DURATION.get() / 7.65;
					} else {
						food_duration = 180 / 7.65;
					}
					if (itemstack.getMaxStackSize() <= 16 && CommonConfiguration.BONUS_DURATION.get() == true) {
						if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 2400 < 6000) {
							local_duration = 6000;
						} else if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 2400 > 72000) {
							local_duration = 72000;
						} else {
							local_duration = (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 2400;
						}
					} else {
						if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 1200 < 6000) {
							local_duration = 6000;
						} else if ((itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 1200 > 72000) {
							local_duration = 72000;
						} else {
							local_duration = (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getSaturationModifier() : 0) * food_duration * 1200;
						}
					}
				}
				if ((((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3).getItem() == ItemStack.EMPTY.getItem()
						|| ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1).getItem() == itemstack.getItem())) {
					{
						ItemStack _setval = itemstack;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_3 = _setval.copy();
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_duration;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_3_duration = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_health_boost;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.health_boost_slot_3 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_regen_factor;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.regen_factor_slot_3 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					HealthCheckProcedure.execute(entity);
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0)));
				} else if ((((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2).getItem() == ItemStack.EMPTY.getItem()
						|| ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1).getItem() == itemstack.getItem())) {
					{
						ItemStack _setval = itemstack;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_2 = _setval.copy();
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_duration;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_2_duration = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_health_boost;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.health_boost_slot_2 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_regen_factor;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.regen_factor_slot_2 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					HealthCheckProcedure.execute(entity);
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0)));
				} else if ((((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1).getItem() == ItemStack.EMPTY.getItem()
						|| ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3).getItem() == itemstack.getItem())
						&& !(((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2).getItem() == itemstack.getItem())) {
					{
						ItemStack _setval = itemstack;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_1 = _setval.copy();
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_duration;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.last_consumed_1_duration = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_health_boost;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.health_boost_slot_1 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = local_regen_factor;
						entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.regen_factor_slot_1 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					HealthCheckProcedure.execute(entity);
					if (entity instanceof LivingEntity _entity)
						_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + (itemstack.getItem().isEdible() ? itemstack.getItem().getFoodProperties().getNutrition() : 0)));
				}
			}
		}
	}
}
