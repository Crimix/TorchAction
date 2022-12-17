package com.black_dog20.torchaction.client.containers;

import com.black_dog20.torchaction.common.items.ItemTorchHolder;
import com.black_dog20.torchaction.common.utils.TorchItemHandler;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TorchHolderContainer extends AbstractContainerMenu {

    protected LazyOptional<IItemHandler> inventory;
    public ItemStack container;

    public TorchHolderContainer(int windowId, Inventory playerInventory, Player player) {
        super(ModContainers.TORCH_HOLDER_CONTAINER.get(), windowId);

        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        container = mainHandItem.getItem() instanceof ItemTorchHolder ? mainHandItem : offHandItem.getItem() instanceof ItemTorchHolder ? offHandItem : ItemStack.EMPTY;

        inventory = container.getCapability(ForgeCapabilities.ITEM_HANDLER);

        inventory.ifPresent(i -> {
            addSlots(i);
            addPlayerSlots(playerInventory);
        });

        if (!inventory.isPresent())
            player.closeContainer();

    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (inventory.isPresent()) {
            IItemHandler handler = inventory
                    .orElseGet(null);
            if (handler instanceof TorchItemHandler) {
                ((TorchItemHandler) handler).serializeNBT();
            }
        }

    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            int containerSlots = slots.size() - player.getInventory().items.size();

            if (index < containerSlots) {
                if (!this.moveItemStackTo(itemstack1, containerSlots, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        // this will prevent the player from interacting with the item that opened the inventory
        if (slotId >= 0 && getSlot(slotId) != null && getSlot(slotId).getItem() == player.getMainHandItem()) {
            return;
        }
        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    private int addSlotRange(Inventory playerInventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(playerInventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(Inventory playerInventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(playerInventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void addPlayerSlots(Inventory playerInventory) {
        // Player inventory
        addSlotBox(playerInventory, 9, 8, 85, 9, 18, 3, 18);

        // Hotbar
        addSlotRange(playerInventory, 0, 8, 143, 9, 18);
    }

    private void addSlots(IItemHandler handler) {
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new SlotItemHandler(handler, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
    }


}
