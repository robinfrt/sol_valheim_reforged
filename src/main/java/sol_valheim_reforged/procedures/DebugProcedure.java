package sol_valheim_reforged.procedures;

import sol_valheim_reforged.network.SolValheimReforgedModVariables;

import sol_valheim_reforged.configuration.CommonConfiguration;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

public class DebugProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal(("[Dynamic] Food Overlay Position: x= "
					+ new java.text.DecimalFormat("\u00A79##.##\u00A7r").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).overlay_x)
					+ " ; y= "
					+ new java.text.DecimalFormat("\u00A79##.##\u00A7r").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).overlay_y))),
					false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((new java.text.DecimalFormat("[Config] Regen cooldown (in ticks): \u00A79##\u00A7r").format((double) CommonConfiguration.REGEN_COOLDOWN.get()))), false);
		if ((double) CommonConfiguration.FOOD_DURATION.get() >= 60 && (double) CommonConfiguration.FOOD_DURATION.get() <= 300) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("[Config] Food duration per ham shank (in seconds) : " + new java.text.DecimalFormat("\u00A79##.##\u00A7r").format((double) CommonConfiguration.FOOD_DURATION.get()))), false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("[Config] Food duration per ham shank (in seconds) : " + new java.text.DecimalFormat("\u00A79##.##\u00A7r").format(180))), false);
		}
		if (CommonConfiguration.BONUS_DURATION.get() == true) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("[Config] Bonus duration: \u00A79Enabled\u00A7r"), false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("[Config] Bonus duration: \u00A79Disabled\u00A7r"), false);
		}
		if ((double) CommonConfiguration.BASE_HEALTH.get() >= 4 && (double) CommonConfiguration.BASE_HEALTH.get() <= 10) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(((new java.text.DecimalFormat(">Current max health: \u00A79##.##\u00A7r").format(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) + ""
						+ new java.text.DecimalFormat(" [Base (\u00A79##.##\u00A7r)").format((double) CommonConfiguration.BASE_HEALTH.get())
						+ new java.text.DecimalFormat(" + Bonus (\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r)]")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3))),
						false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(((new java.text.DecimalFormat(">Current max health: \u00A79##.##\u00A7r").format(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) + ""
						+ new java.text.DecimalFormat(" [Base (\u00A79##.##\u00A7r)").format(6)
						+ new java.text.DecimalFormat(" + Bonus (\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_1)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_2)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r)]")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).health_boost_slot_3))),
						false);
		}
		if ((double) CommonConfiguration.BASE_REGEN_RATE.get() >= 20 && (double) CommonConfiguration.BASE_REGEN_RATE.get() <= 200) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat(">Current regen factor: \u00A79##.##\u00A7r")
						.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor) + ""
						+ new java.text.DecimalFormat(" [Base (\u00A79##.##\u00A7r)").format((double) CommonConfiguration.BASE_REGEN_RATE.get())
						+ new java.text.DecimalFormat(" + Bonus (\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_1)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_2)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r)]")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_3))),
						false);
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((new java.text.DecimalFormat(">Current regen factor: \u00A79##.##\u00A7r")
						.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor) + ""
						+ new java.text.DecimalFormat(" [Base (\u00A79##.##\u00A7r)").format(100)
						+ new java.text.DecimalFormat(" + Bonus (\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_1)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_2)
						+ new java.text.DecimalFormat("+\u00A79##.##\u00A7r)]")
								.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_factor_slot_3))),
						false);
		}
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(
					Component.literal((">Current regen cooldown: "
							+ new java.text.DecimalFormat("\u00A79##\u00A7r seconds ")
									.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_cooldown / 20)
							+ new java.text.DecimalFormat("(## ticks)").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).regen_cooldown))),
					false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((">Current time left for slot 1: "
					+ new java.text.DecimalFormat("\u00A79##\u00A7r minutes ")
							.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration / 1200)
					+ new java.text.DecimalFormat("(## ticks)").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_1_duration))),
					false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((">Current time left for slot 2: "
					+ new java.text.DecimalFormat("\u00A79##\u00A7r minutes ")
							.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration / 1200)
					+ new java.text.DecimalFormat("(## ticks)").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_2_duration))),
					false);
		if (entity instanceof Player _player && !_player.level().isClientSide())
			_player.displayClientMessage(Component.literal((">Current time left for slot 3: "
					+ new java.text.DecimalFormat("\u00A79##\u00A7r minutes ")
							.format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration / 1200)
					+ new java.text.DecimalFormat("(## ticks)").format((entity.getCapability(SolValheimReforgedModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SolValheimReforgedModVariables.PlayerVariables())).last_consumed_3_duration))),
					false);
	}
}
