package sol_valheim_reforged.procedures;

import sol_valheim_reforged.network.SolValheimReforgedModVariables;

import sol_valheim_reforged.configuration.CommonConfiguration;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerResetProcedure {
	@SubscribeEvent
	public static void onPlayerRespawned(PlayerEvent.PlayerRespawnEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		{
			double _setval = 0;
			entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.regen_cooldown = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		ResetSlot1Procedure.execute(entity);
		ResetSlot2Procedure.execute(entity);
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
}
