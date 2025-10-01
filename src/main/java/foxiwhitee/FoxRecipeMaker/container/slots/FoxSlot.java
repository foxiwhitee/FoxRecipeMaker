package foxiwhitee.FoxRecipeMaker.container.slots;

import foxiwhitee.FoxRecipeMaker.container.FoxBaseContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FoxSlot extends Slot {
    private final int defX;
    private final int defY;
    private FoxBaseContainer myContainer = null;
    private int IIcon = -1;

    public FoxSlot(IInventory inv, int idx, int x, int y) {
        super(inv, idx, x, y);
        this.defX = x;
        this.defY = y;
    }

    public void clearStack() {
        super.putStack((ItemStack)null);
    }

    public boolean isItemValid(ItemStack par1ItemStack) {
        return this.isEnabled() && super.isItemValid(par1ItemStack);
    }

    public ItemStack getStack() {
        if (!this.isEnabled()) {
            return null;
        } else if (this.inventory.getSizeInventory() <= this.getSlotIndex()) {
            return null;
        } else {
            return this.inventory.getStackInSlot(this.getSlotIndex());
        }
    }

    public void putStack(ItemStack par1ItemStack) {
        if (this.isEnabled()) {
            super.putStack(par1ItemStack);
        }
    }

    public void onSlotChanged() {
        super.onSlotChanged();
    }

    public boolean func_111238_b() {
        return this.isEnabled();
    }

    public boolean isEnabled() {
        return true;
    }

    public int getX() {
        return this.defX;
    }

    public int getY() {
        return this.defY;
    }

    private int getIIcon() {
        return this.IIcon;
    }

    public void setIIcon(int iIcon) {
        this.IIcon = iIcon;
    }

    FoxBaseContainer getContainer() {
        return this.myContainer;
    }

    public void setContainer(FoxBaseContainer myContainer) {
        this.myContainer = myContainer;
    }

}
