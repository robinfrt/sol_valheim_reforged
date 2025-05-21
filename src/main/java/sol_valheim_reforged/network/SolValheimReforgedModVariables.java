package sol_valheim_reforged.network;

import sol_valheim_reforged.SolValheimReforgedMod;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SolValheimReforgedModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		SolValheimReforgedMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			clone.last_consumed_1 = original.last_consumed_1;
			clone.last_consumed_2 = original.last_consumed_2;
			clone.last_consumed_3 = original.last_consumed_3;
			clone.last_consumed_1_duration = original.last_consumed_1_duration;
			clone.last_consumed_2_duration = original.last_consumed_2_duration;
			clone.last_consumed_3_duration = original.last_consumed_3_duration;
			clone.health_boost_slot_1 = original.health_boost_slot_1;
			clone.health_boost_slot_2 = original.health_boost_slot_2;
			clone.health_boost_slot_3 = original.health_boost_slot_3;
			clone.regen_factor_slot_1 = original.regen_factor_slot_1;
			clone.regen_factor_slot_2 = original.regen_factor_slot_2;
			clone.regen_factor_slot_3 = original.regen_factor_slot_3;
			clone.regen_factor = original.regen_factor;
			clone.overlay_x = original.overlay_x;
			clone.overlay_y = original.overlay_y;
			clone.regen_cooldown = original.regen_cooldown;
			if (!event.isWasDeath()) {
			}
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("sol_valheim_reforged", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public ItemStack last_consumed_1 = ItemStack.EMPTY;
		public ItemStack last_consumed_2 = ItemStack.EMPTY;
		public ItemStack last_consumed_3 = ItemStack.EMPTY;
		public double last_consumed_1_duration = 0;
		public double last_consumed_2_duration = 0;
		public double last_consumed_3_duration = 0;
		public double health_boost_slot_1 = 0;
		public double health_boost_slot_2 = 0;
		public double health_boost_slot_3 = 0;
		public double regen_factor_slot_1 = 0;
		public double regen_factor_slot_2 = 0;
		public double regen_factor_slot_3 = 0;
		public double regen_factor = 100.0;
		public double overlay_x = 0;
		public double overlay_y = 0;
		public double regen_cooldown = 0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				SolValheimReforgedMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.put("last_consumed_1", last_consumed_1.save(new CompoundTag()));
			nbt.put("last_consumed_2", last_consumed_2.save(new CompoundTag()));
			nbt.put("last_consumed_3", last_consumed_3.save(new CompoundTag()));
			nbt.putDouble("last_consumed_1_duration", last_consumed_1_duration);
			nbt.putDouble("last_consumed_2_duration", last_consumed_2_duration);
			nbt.putDouble("last_consumed_3_duration", last_consumed_3_duration);
			nbt.putDouble("health_boost_slot_1", health_boost_slot_1);
			nbt.putDouble("health_boost_slot_2", health_boost_slot_2);
			nbt.putDouble("health_boost_slot_3", health_boost_slot_3);
			nbt.putDouble("regen_factor_slot_1", regen_factor_slot_1);
			nbt.putDouble("regen_factor_slot_2", regen_factor_slot_2);
			nbt.putDouble("regen_factor_slot_3", regen_factor_slot_3);
			nbt.putDouble("regen_factor", regen_factor);
			nbt.putDouble("overlay_x", overlay_x);
			nbt.putDouble("overlay_y", overlay_y);
			nbt.putDouble("regen_cooldown", regen_cooldown);
			return nbt;
		}

		public void readNBT(Tag tag) {
			CompoundTag nbt = (CompoundTag) tag;
			last_consumed_1 = ItemStack.of(nbt.getCompound("last_consumed_1"));
			last_consumed_2 = ItemStack.of(nbt.getCompound("last_consumed_2"));
			last_consumed_3 = ItemStack.of(nbt.getCompound("last_consumed_3"));
			last_consumed_1_duration = nbt.getDouble("last_consumed_1_duration");
			last_consumed_2_duration = nbt.getDouble("last_consumed_2_duration");
			last_consumed_3_duration = nbt.getDouble("last_consumed_3_duration");
			health_boost_slot_1 = nbt.getDouble("health_boost_slot_1");
			health_boost_slot_2 = nbt.getDouble("health_boost_slot_2");
			health_boost_slot_3 = nbt.getDouble("health_boost_slot_3");
			regen_factor_slot_1 = nbt.getDouble("regen_factor_slot_1");
			regen_factor_slot_2 = nbt.getDouble("regen_factor_slot_2");
			regen_factor_slot_3 = nbt.getDouble("regen_factor_slot_3");
			regen_factor = nbt.getDouble("regen_factor");
			overlay_x = nbt.getDouble("overlay_x");
			overlay_y = nbt.getDouble("overlay_y");
			regen_cooldown = nbt.getDouble("regen_cooldown");
		}
	}

	public static class PlayerVariablesSyncMessage {
		private final PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
					variables.last_consumed_1 = message.data.last_consumed_1;
					variables.last_consumed_2 = message.data.last_consumed_2;
					variables.last_consumed_3 = message.data.last_consumed_3;
					variables.last_consumed_1_duration = message.data.last_consumed_1_duration;
					variables.last_consumed_2_duration = message.data.last_consumed_2_duration;
					variables.last_consumed_3_duration = message.data.last_consumed_3_duration;
					variables.health_boost_slot_1 = message.data.health_boost_slot_1;
					variables.health_boost_slot_2 = message.data.health_boost_slot_2;
					variables.health_boost_slot_3 = message.data.health_boost_slot_3;
					variables.regen_factor_slot_1 = message.data.regen_factor_slot_1;
					variables.regen_factor_slot_2 = message.data.regen_factor_slot_2;
					variables.regen_factor_slot_3 = message.data.regen_factor_slot_3;
					variables.regen_factor = message.data.regen_factor;
					variables.overlay_x = message.data.overlay_x;
					variables.overlay_y = message.data.overlay_y;
					variables.regen_cooldown = message.data.regen_cooldown;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
