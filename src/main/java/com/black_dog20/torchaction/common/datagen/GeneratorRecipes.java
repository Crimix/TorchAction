package com.black_dog20.torchaction.common.datagen;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.torchaction.TorchAction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.black_dog20.torchaction.common.items.ModItems.TORCH_HOLDER;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(PackOutput packOutput) {
        super(packOutput, TorchAction.MOD_ID);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TORCH_HOLDER.get())
                .define('c', Tags.Items.CHESTS_WOODEN)
                .define('d', Tags.Items.GEMS_DIAMOND)
                .define('t', Items.TORCH)
                .pattern("ttt")
                .pattern("dcd")
                .pattern("ttt")
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(consumer);

    }

}
