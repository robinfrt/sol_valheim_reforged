package sol_valheim_reforged.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameRules;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class WorldUnloadingProcedure {
	@SubscribeEvent
	public static void onWorldUnload(net.minecraftforge.event.level.LevelEvent.Unload event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		world.getLevelData().getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(true, world.getServer());
	}
}
