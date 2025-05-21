package sol_valheim_reforged.procedures;

import sol_valheim_reforged.network.SolValheimReforgedModVariables;

import sol_valheim_reforged.configuration.CommonConfiguration;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.registries.Registries;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class FoodDepletionProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player.level(), event.player);
		}
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double compute_regen_factor = 0;
		if (entity instanceof Player _player)
			_player.getFoodData().setFoodLevel(10);
		if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration > 2) {
			{
				double _setval = (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration - 1;
				entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.last_consumed_3_duration = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration <= 2
				&& (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration != 0) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.STARVE)), 1);
			ResetSlot3Procedure.execute(entity);
			HealthCheckProcedure.execute(entity);
			if ((double) CommonConfiguration.BASE_HEALTH.get() >= 4 && (double) CommonConfiguration.BASE_HEALTH.get() <= 10) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((double) CommonConfiguration.BASE_HEALTH.get()
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) (6 + (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			}
		}
		if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration >= 2) {
			{
				double _setval = (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration - 1;
				entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.last_consumed_2_duration = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration <= 2
				&& (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration != 0) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.STARVE)), 1);
			ResetSlot2Procedure.execute(entity);
			HealthCheckProcedure.execute(entity);
			if ((double) CommonConfiguration.BASE_HEALTH.get() >= 4 && (double) CommonConfiguration.BASE_HEALTH.get() <= 10) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((double) CommonConfiguration.BASE_HEALTH.get()
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) (6 + (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			}
		}
		if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration >= 2) {
			{
				double _setval = (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration - 1;
				entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.last_consumed_1_duration = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration <= 2
				&& (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration != 0) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.STARVE)), 1);
			ResetSlot1Procedure.execute(entity);
			HealthCheckProcedure.execute(entity);
			if ((double) CommonConfiguration.BASE_HEALTH.get() >= 4 && (double) CommonConfiguration.BASE_HEALTH.get() <= 10) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((double) CommonConfiguration.BASE_HEALTH.get()
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			} else {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) (6 + (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2
							+ (entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3));
			}
		}
	}
}
