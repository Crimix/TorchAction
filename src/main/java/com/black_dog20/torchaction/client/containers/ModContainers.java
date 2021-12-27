package com.black_dog20.torchaction.client.containers;

import com.black_dog20.torchaction.TorchAction;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TorchAction.MOD_ID);

    public static final RegistryObject<MenuType<TorchHolderContainer>> TORCH_HOLDER_CONTAINER = CONTAINERS.register("torch_holder_container", () -> IForgeMenuType.create((windowId, inv, data) -> new TorchHolderContainer(windowId, inv, inv.player)));
}

