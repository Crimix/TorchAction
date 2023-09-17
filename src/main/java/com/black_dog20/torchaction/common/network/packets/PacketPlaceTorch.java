package com.black_dog20.torchaction.common.network.packets;

import com.black_dog20.torchaction.common.items.ItemTorchHolder;
import com.black_dog20.torchaction.common.utils.ModUtils;
import com.black_dog20.torchaction.common.utils.TorchItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PacketPlaceTorch {

    private final BlockHitResult hitResult;

    public PacketPlaceTorch(BlockHitResult hitResult) {
        this.hitResult = hitResult;
    }

    public static void encode(PacketPlaceTorch msg, FriendlyByteBuf buffer) {
        buffer.writeBlockHitResult(msg.hitResult);
    }

    public static PacketPlaceTorch decode(FriendlyByteBuf buffer) {
        return new PacketPlaceTorch(buffer.readBlockHitResult());
    }

    public static class Handler {
        public static void handle(PacketPlaceTorch msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                findAndPlaceTorch(player, msg.hitResult);
            });
            ctx.get().setPacketHandled(true);
        }


        private static void findAndPlaceTorch(ServerPlayer player, BlockHitResult hitResult) {
            if (player.level().isClientSide)
                return;

            if (firstTryTorchHolder(player, hitResult))
                return;

            firstTryIventory(player, hitResult);
        }

        private static boolean firstTryTorchHolder(ServerPlayer player, BlockHitResult hitResult) {
            ItemStack holder = ModUtils.findTorchHolder(player);

            if (holder.isEmpty() || !(holder.getItem() instanceof ItemTorchHolder))
                return false;

            TorchItemHandler torchItemHandler = holder.getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(TorchItemHandler.class::isInstance)
                    .map(TorchItemHandler.class::cast)
                    .orElse(null);

            if (torchItemHandler == null) {
                return false;
            }

            ItemStack torch = torchItemHandler.getFirstNotEmptyStack();

            if (torch.isEmpty() || !(torch.getItem() instanceof StandingAndWallBlockItem))
                return false;

            return placeTorch(player, hitResult, torch, integer -> torchItemHandler.extractItemFromFirstNotEmpty(integer, false));
        }

        private static boolean firstTryIventory(ServerPlayer player, BlockHitResult hitResult) {
            int slot = findSlotWithTorch(player);
            if (slot == -1)
                return false;

            ItemStack torch = player.getInventory().items.get(slot);

            if (torch.isEmpty() || !(torch.getItem() instanceof StandingAndWallBlockItem))
                return false;

            return placeTorch(player, hitResult, torch, integer -> player.getInventory().removeItem(slot, integer));
        }

        private static int findSlotWithTorch(ServerPlayer player) {
            NonNullList<ItemStack> items = player.getInventory().items;
            for(int slot = 0; slot < items.size(); ++slot) {
                ItemStack item = items.get(slot);
                if (!item.isEmpty() && item.getItem() == Items.TORCH) {
                    return slot;
                }
            }

            return -1;
        }

        private static boolean placeTorch(ServerPlayer player, BlockHitResult hitResult, ItemStack stack, Consumer<Integer> removeItemConsumer) {
            BlockPos blockPos = hitResult.getBlockPos();
            Direction direction = hitResult.getDirection();
            if (direction != Direction.DOWN) {
                BlockState blockState = ((StandingAndWallBlockItem) Items.TORCH).getPlacementState(new BlockPlaceContext(player, InteractionHand.OFF_HAND, stack, hitResult));
                boolean placed = player.level().setBlock(blockPos.offset(direction.getNormal()), blockState, 2);
                if (placed) {
                    var soundType = Blocks.TORCH.getSoundType(Blocks.TORCH.defaultBlockState());
                    player.level().playSound(null, blockPos, soundType.getPlaceSound(), SoundSource.BLOCKS, 1.0f, 0.8F);
                    removeItemConsumer.accept(1);
                }
                return placed;
            }
            return false;
        }
    }

}
