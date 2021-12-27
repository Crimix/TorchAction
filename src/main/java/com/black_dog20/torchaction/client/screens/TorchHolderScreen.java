package com.black_dog20.torchaction.client.screens;

import com.black_dog20.torchaction.client.containers.TorchHolderContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TorchHolderScreen extends AbstractContainerScreen<TorchHolderContainer> {

    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
    private final Inventory inventory;
    private final int containerRows = 3;

    public TorchHolderScreen(TorchHolderContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.inventory = playerInventory;
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        this.blit(poseStack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
