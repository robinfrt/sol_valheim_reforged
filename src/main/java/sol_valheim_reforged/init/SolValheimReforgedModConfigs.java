package sol_valheim_reforged.init;

import sol_valheim_reforged.configuration.CommonConfiguration;

import sol_valheim_reforged.SolValheimReforgedMod;

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SolValheimReforgedMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SolValheimReforgedModConfigs {
	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		event.enqueueWork(() -> {
			ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfiguration.SPEC, "sol_valheim_reforged-common.toml");
		});
	}
}
