package com.black_dog20.torchaction.common.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static com.black_dog20.torchaction.common.utils.NBTTags.TAG_TORCH_HOLDER_CONTAINER;

public class TorchItemHandler extends ItemStackHandler {

    private static int SIZE = 27;
    private final ItemStack container;

    public TorchItemHandler(ItemStack container) {
        super(SIZE);
        this.container = container;
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.serializeNBT();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        boolean valid = super.isItemValid(slot, stack);

       return valid && item == Items.TORCH;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.load();
        super.setStackInSlot(slot, stack);
        this.save();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        this.load();
        return super.getStackInSlot(slot);
    }

    @Nonnull
    public ItemStack getFirstNotEmptyStack() {
        this.load();
        int slot = -1;
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                slot = i;
                break;
            }
        }

        if (slot == -1)
            return ItemStack.EMPTY;
        return super.getStackInSlot(slot);
    }

    public int getCountOfItems() {
        this.load();
        return stacks.stream()
                .filter(i -> !i.isEmpty())
                .map(ItemStack::getCount)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getMaxCountOfItems() {
        this.load();
        return stacks.stream()
                .map(ItemStack::getMaxStackSize)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public boolean hasRoom() {
        return getMaxCountOfItems() != getCountOfItems();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        this.load();
        ItemStack ret = super.insertItem(slot, stack, simulate);
        this.save();
        return ret;
    }

    @Nonnull
    public ItemStack insertItemStack(@Nonnull ItemStack stack, boolean simulate) {
        this.load();
        ItemStack ret = stack;
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).getCount() < stacks.get(i).getMaxStackSize()) {
                ret = super.insertItem(i, ret, simulate);
            }
        }
        this.save();
        return ret;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        this.load();
        ItemStack ret = super.extractItem(slot, amount, simulate);
        this.save();
        return ret;

    }

    @Nonnull
    public ItemStack extractItemFromFirstNotEmpty(int amount, boolean simulate) {
        this.load();

        int slot = -1;
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                slot = i;
                break;
            }
        }

        if (slot == -1)
            return ItemStack.EMPTY;

        ItemStack ret = super.extractItem(slot, amount, simulate);
        this.save();
        return ret;
    }

    private void load() {
        CompoundTag compoundNBT = container.getOrCreateTag();
        if (compoundNBT.contains(TAG_TORCH_HOLDER_CONTAINER)) {
            super.deserializeNBT(compoundNBT.getCompound(TAG_TORCH_HOLDER_CONTAINER));
        }
    }

    private void save() {
        serializeNBT();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        container.getOrCreateTag().put(TAG_TORCH_HOLDER_CONTAINER, nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag compoundNBT = container.getOrCreateTag();
        if (compoundNBT.contains(TAG_TORCH_HOLDER_CONTAINER)) {
            super.deserializeNBT(compoundNBT.getCompound(TAG_TORCH_HOLDER_CONTAINER));
        }
    }

}
