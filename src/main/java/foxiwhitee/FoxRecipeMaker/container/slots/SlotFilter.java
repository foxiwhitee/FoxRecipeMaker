package foxiwhitee.FoxRecipeMaker.container.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFilter extends FoxSlot {
    private final SlotFilterType which;
    private final InventoryPlayer p;
    private boolean allowEdit = true;
    private int stackLimit = -1;

    public SlotFilter(SlotFilterType valid, IInventory i, int slotIndex, int x, int y, InventoryPlayer p) {
        super(i, slotIndex, x, y);
        this.which = valid;
        this.p = p;
    }

    public int getSlotStackLimit() {
        return this.stackLimit != -1 ? this.stackLimit : super.getSlotStackLimit();
    }

    public Slot setStackLimit(int i) {
        this.stackLimit = i;
        return this;
    }

    public boolean isItemValid(ItemStack i) {
        if (!this.getContainer().isValidForSlot(this, i)) {
            return false;
        } else if (i == null) {
            return false;
        } else if (i.getItem() == null) {
            return false;
        } else if (!this.inventory.isItemValidForSlot(this.getSlotIndex(), i)) {
            return false;
        } else if (!this.isAllowEdit()) {
            return false;
        } else {
            switch (this.which) {
                case EXAMPLE:
                    if (i.getItem() instanceof Item) {
                        return true;
                    }
                default:
                    return false;
            }
        }
    }

    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return this.isAllowEdit();
    }

    private boolean isAllowEdit() {
        return this.allowEdit;
    }

}
