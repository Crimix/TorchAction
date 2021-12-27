package com.black_dog20.torchaction.common.datagen;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.torchaction.TorchAction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.black_dog20.torchaction.common.items.ModItems.TORCH_HOLDER;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator, TorchAction.MOD_ID);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(TORCH_HOLDER.get())
                .define('c', Tags.Items.CHESTS_WOODEN)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('t', Items.TORCH)
                .pattern("ttt")
                .pattern("dcd")
                .pattern("ttt")
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(consumer);

    }

    @Override
    public @NotNull String getName() {
        return String.format("Torch Action: %s", super.getName());
    }

}
