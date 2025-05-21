package sol_valheim_reforged.procedures;

import sol_valheim_reforged.network.SolValheimReforgedModVariables;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

public class ResetSlot3Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			ItemStack _setval = ItemStack.EMPTY;
			entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.last_consumed_3 = _setval.copy();
				capability.syncPlayerVariables(entity);
			});
		}
		{
			double _setval = 0;
			entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.last_consumed_3_duration = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		{
			double _setval = 0;
			entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.health_boost_slot_3 = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		{
			double _setval = 0;
			entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.regen_factor_slot_3 = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
