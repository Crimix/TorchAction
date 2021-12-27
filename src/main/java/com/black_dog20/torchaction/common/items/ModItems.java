package com.black_dog20.torchaction.common.items;

import com.black_dog20.torchaction.TorchAction;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static CreativeModeTab itemGroup = new CreativeModeTab(TorchAction.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TORCH_HOLDER.get());
        }
    };

    public static final Item.Properties ITEM_GROUP = new Item.Properties().tab(itemGroup);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TorchAction.MOD_ID);

    public static final RegistryObject<Item> TORCH_HOLDER = ITEMS.register("torch_holder", () -> new ItemTorchHolder(ITEM_GROUP.stacksTo(1)));
}
