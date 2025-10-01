package foxiwhitee.FoxRecipeMaker.utils.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class FoxInternalInventory implements IInventory {
    private final int size;
    private final ItemStack[] inv;
    private int maxStack;
    private IFoxInternalInventory tile;

    public FoxInternalInventory(IFoxInternalInventory tile, int size) {
        this(tile, size, 64);
    }

    public FoxInternalInventory(IFoxInternalInventory tile, int size, int maxStack) {
        this.tile = tile;
        this.size = size;
        this.inv = new ItemStack[size];
        this.maxStack = maxStack;
    }

    @Override
    public final int getSizeInventory() { return size; }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inv[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int qty) {
        if (this.inv[slot] != null) {
            ItemStack split = this.getStackInSlot(slot);
            ItemStack ns = null;
            if (qty >= split.stackSize) {
                ns = this.inv[slot];
                this.inv[slot] = null;
            } else {
                ns = split.splitStack(qty);
            }

            if (this.tile != null) {
                this.tile.onChangeInventory(this, slot, InvOperation.decreaseStackSize, ns, (ItemStack)null);
            }

            this.markDirty();
            return ns;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) { return null; }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        ItemStack oldStack = this.inv[i];
        this.inv[i] = itemStack;
        if (this.tile != null) {
            ItemStack removed = oldStack;
            ItemStack added = itemStack;
            if (oldStack != null && itemStack != null && isSameItem(oldStack, itemStack)) {
                if (oldStack.stackSize > itemStack.stackSize) {
                    removed = oldStack.copy();
                    removed.stackSize -= itemStack.stackSize;
                    added = null;
                } else if (oldStack.stackSize < itemStack.stackSize) {
                    added = itemStack.copy();
                    added.stackSize -= oldStack.stackSize;
                    removed = null;
                } else {
                    added = null;
                    removed = null;
                }
            }

            this.tile.onChangeInventory(this, i, InvOperation.setInventorySlotContents, removed, added);
            this.markDirty();
        }
    }

    @Override
    public final String getInventoryName() { return "internal"; }

    @Override
    public boolean hasCustomInventoryName() { return false; }

    @Override
    public int getInventoryStackLimit() { return maxStack; }

    @Override
    public void markDirty() {
        if (this.tile != null) {
            this.tile.onChangeInventory(this, -1, InvOperation.markDirty, (ItemStack)null, (ItemStack)null);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) { return true; }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) { return true; }

    public boolean isEmpty() {
        for(int x = 0; x < this.size; ++x) {
            if (this.getStackInSlot(x) != null) {
                return false;
            }
        }

        return true;
    }

    public void setMaxStackSize(int s) {
        this.maxStack = s;
    }

    private void writeToNBT(NBTTagCompound target) {
        for(int x = 0; x < this.size; ++x) {
            try {
                NBTTagCompound c = new NBTTagCompound();
                if (this.inv[x] != null) {
                    this.inv[x].writeToNBT(c);
                }

                target.setTag("#" + x, c);
            } catch (Exception var4) {
            }
        }
    }

    private void readFromNBT(NBTTagCompound target) {
        for(int x = 0; x < this.size; ++x) {
            try {
                NBTTagCompound c = target.getCompoundTag("#" + x);
                if (c != null) {
                    this.inv[x] = ItemStack.loadItemStackFromNBT(c);
                }
            } catch (Exception e) {
            }
        }
    }

    public void readFromNBT(NBTTagCompound data, String name) {
        NBTTagCompound c = data.getCompoundTag(name);
        if (c != null) {
            this.readFromNBT(c);
        }
    }

    public void writeToNBT(NBTTagCompound data, String name) {
        NBTTagCompound c = new NBTTagCompound();
        this.writeToNBT(c);
        data.setTag(name, c);
    }

    public static boolean isSameItem(@Nullable ItemStack left, @Nullable ItemStack right) {
        return left != null && right != null && left.isItemEqual(right);
    }

    public ItemStack[] toArray() {
        return inv;
    }

    private IFoxInternalInventory getTileEntity() {
        return this.tile;
    }

    public void setTileEntity(IFoxInternalInventory te) {
        this.tile = te;
    }
}
