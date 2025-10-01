package foxiwhitee.FoxRecipeMaker.utils.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IFoxInternalInventory {
    void saveChanges();

    void onChangeInventory(IInventory var1, int var2, InvOperation var3, ItemStack var4, ItemStack var5);
}
