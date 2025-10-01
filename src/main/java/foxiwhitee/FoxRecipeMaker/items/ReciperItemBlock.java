package foxiwhitee.FoxRecipeMaker.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ReciperItemBlock extends ItemBlock {
    public ReciperItemBlock(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return Block.getBlockFromItem(stack.getItem()).getLocalizedName();
    }
}
