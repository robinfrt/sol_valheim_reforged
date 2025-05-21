package sol_valheim_reforged.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfiguration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	public static final ForgeConfigSpec.ConfigValue<Double> BASE_HEALTH;
	public static final ForgeConfigSpec.ConfigValue<Double> BASE_REGEN_RATE;
	public static final ForgeConfigSpec.ConfigValue<Double> REGEN_COOLDOWN;
	public static final ForgeConfigSpec.ConfigValue<Double> FOOD_DURATION;
	public static final ForgeConfigSpec.ConfigValue<Boolean> BONUS_DURATION;
	static {
		BUILDER.push("starting health");
		BASE_HEALTH = BUILDER.comment("Number of hearts the player starts with (2 health = 1 heart) (range 4-10, default 6)").define("base_health", (double) 6);
		BUILDER.pop();
		BUILDER.push("regen factor");
		BASE_REGEN_RATE = BUILDER.comment("Time it takes (in ticks) for the player to passively regain 1 health (½ heart) (range 20-200, default 100)").define("base_regen_rate", (double) 100);
		BUILDER.pop();
		BUILDER.push("regen cooldown");
		REGEN_COOLDOWN = BUILDER.comment("Delay (in ticks) before regeneration starts after taking damage (range 20-600, default 60) (0 to disable the cooldown)").define("regen_cooldown", (double) 60);
		BUILDER.pop();
		BUILDER.push("food duration");
		FOOD_DURATION = BUILDER.comment("Time (in seconds) that food should last per saturation level (range 60-300, default 180)").define("food_duration", (double) 180);
		BUILDER.pop();
		BUILDER.push("unstackable food duration");
		BONUS_DURATION = BUILDER.comment("Increased duration for food items that don't stack up to 64 (boolean, default = true)").define("bonus_duration", true);
		BUILDER.pop();

		SPEC = BUILDER.build();
	}

}
