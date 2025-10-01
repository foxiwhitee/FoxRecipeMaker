package foxiwhitee.FoxRecipeMaker.tiles;

import foxiwhitee.FoxRecipeMaker.utils.inventory.FoxInternalInventory;
import foxiwhitee.FoxRecipeMaker.utils.inventory.IFoxInternalInventory;
import foxiwhitee.FoxRecipeMaker.utils.inventory.InvOperation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public abstract class TileReciper extends TileEntity implements IFoxInternalInventory {
    public byte facing = 2;

    protected FoxInternalInventory inventory = new FoxInternalInventory(this, getInventorySize());
    private final FoxInternalInventory inventoryOutput = new FoxInternalInventory(this, getInventoryOutputSize());

    @Override
    public void saveChanges() {}

    @Override
    public void onChangeInventory(IInventory var1, int var2, InvOperation var3, ItemStack var4, ItemStack var5) {}

    protected abstract int getInventorySize();

    protected abstract int getInventoryOutputSize();

    public FoxInternalInventory getInventory() {
        return inventory;
    }

    public FoxInternalInventory getInventoryOutput() {
        return inventoryOutput;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.inventory.readFromNBT(tag, "inv");
        this.inventoryOutput.readFromNBT(tag, "out");
        this.facing = tag.getByte("facing");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.inventory.writeToNBT(tag, "inv");
        this.inventoryOutput.writeToNBT(tag, "out");
        tag.setByte("facing", this.facing);
    }

    public String getName(ItemStack stack) {
        return getName(stack, true, false, false, false, false);
    }

    public String getName(ItemStack stack, boolean ore) {return getName(stack, ore, false, false, false, false); }

    public String getName(ItemStack stack, boolean ore, boolean size) {return getName(stack, ore, size, false, false, false); }

    public String getName(ItemStack stack, boolean ore, boolean name, boolean lower, boolean upper) {return getName(stack, ore, false, name, lower, upper); }

    public String getName(ItemStack stack, boolean checkOre, boolean stackSize, boolean name, boolean lower, boolean upper) {
        if (stackSize) {
            return String.valueOf(stack.stackSize);
        }
        String out;
        if (name) {
            out = stack.getUnlocalizedName().replace("item.", "").replace(".name", "");
            if (lower) {
                out = out.toLowerCase();
            }
            if (upper) {
                out = out.toUpperCase();
            }
            return out;
        }
        int oreID = -1;
        if (stack != null && checkOre) {
            oreID = OreDictionary.getOreID(stack);
        }
        if (oreID != -1) {
            out = OreDictionary.getOreName(oreID);
            if (out != "Unknown") {
                out = "<ore:" + out + ">";
                out += stack.stackSize == 1 ? "" : " * " + stack.stackSize;
                return out;
            }
        }

        if (stack == null) {
            out = "null";
        } else {
            out = "<" + (stack.getItem()).delegate.name() + ((stack.getItemDamage() != 0) ? (":" + stack.getItemDamage() + ">") : ">");
            out = isFlower(out, stack).equals("") ? out : isFlower(out, stack);
            out += stack.stackSize == 1 ? "" : " * " + stack.stackSize;
        }
        return out;
    }

    public String isFlower(String name, ItemStack stack) {
        if (name.equals("<Botania:specialFlower>") || name.equals("<Botania:floatingSpecialFlower>")) {
            return name + ".withTag(" + stack.stackTagCompound.toString() + ")";
        }
        return "";
    }

    public String generateCraft(ItemStack[] names, boolean shaped) {
        return generateCraft(names, shaped, true);
    }

    public abstract String generateCraft(ItemStack[] item, boolean shaped, boolean ore);

    public String[] genStringItem(ItemStack[] items, int clear, boolean shaped, boolean ore) {
        int count = 0;
        ItemStack[] inv = new ItemStack[items.length - clear];
        for (int i = 0; i < items.length - clear; i++) {
            inv[i] = items[i];
        }
        for (ItemStack stack : inv) {
            if (shaped) {
                count++;
            } else if (stack != null) {
                count++;
            }
        }
        String[] names = new String[count];
        count = 0;
        for (ItemStack stack2 : inv) {
            if (shaped) {
                names[count] = getName(stack2, ore);
                count++;
            } else if (stack2 != null) {
                names[count] = getName(stack2, ore);
                count++;
            }
        }
        return names;
    }

    public String getTag(ItemStack stack, boolean tag) {
        if (stack == null || !tag) {
            return "";
        }
        return getTag(stack);
    }

    public String getTag(ItemStack stack) {
        if (stack.stackTagCompound != null) {
            return ".withTag(" + stack.stackTagCompound + ")";
        }
        return "";
    }

}
