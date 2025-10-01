package foxiwhitee.FoxRecipeMaker.container;

import foxiwhitee.FoxRecipeMaker.container.slots.SlotPlayerHotBar;
import foxiwhitee.FoxRecipeMaker.container.slots.SlotPlayerInv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FoxBaseContainer extends Container {
    private final InventoryPlayer invPlayer;
    private final TileEntity tileEntity;

    public FoxBaseContainer(InventoryPlayer ip, TileEntity myTile) {
        this.invPlayer = ip;
        this.tileEntity = myTile;
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer, int offsetX, int offsetY) {
        for(int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18 + offsetX, 58 + offsetY));
        }
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18 + offsetX, offsetY + i * 18));
            }
        }
    }

    public InventoryPlayer getInventoryPlayer() {
        return this.invPlayer;
    }

    public TileEntity getTileEntity() {
        return this.tileEntity;
    }

    public boolean canInteractWith(EntityPlayer entityplayer) {
        return !(this.tileEntity instanceof IInventory) || ((IInventory) this.tileEntity).isUseableByPlayer(entityplayer);
    }

    public boolean isValidForSlot(Slot s, ItemStack i) {
        return true;
    }

    private int field_94536_g;
    private final Set<Slot> field_94537_h = new HashSet();
    private int field_94535_f = -1;

    protected void func_94533_d() {
        this.field_94536_g = 0;
        this.field_94537_h.clear();
    }

    public ItemStack slotClick(int p_slotClick_1_, int p_slotClick_2_, int p_slotClick_3_, EntityPlayer p_slotClick_4_) {
        ItemStack var5 = null;
        InventoryPlayer var6 = p_slotClick_4_.inventory;
        if (p_slotClick_3_ == 5) {
            int var7 = this.field_94536_g;
            this.field_94536_g = func_94532_c(p_slotClick_2_);
            if ((var7 != 1 || this.field_94536_g != 2) && var7 != this.field_94536_g) {
                this.func_94533_d();
            } else if (var6.getItemStack() == null) {
                this.func_94533_d();
            } else if (this.field_94536_g == 0) {
                this.field_94535_f = func_94529_b(p_slotClick_2_);
                if (func_94528_d(this.field_94535_f)) {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                } else {
                    this.func_94533_d();
                }
            } else if (this.field_94536_g == 1) {
                Slot var8 = (Slot)this.inventorySlots.get(p_slotClick_1_);
                if (var8 != null && func_94527_a(var8, var6.getItemStack(), true) && var8.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(var8)) {
                    this.field_94537_h.add(var8);
                }
            } else if (this.field_94536_g == 2) {
                if (!this.field_94537_h.isEmpty()) {
                    ItemStack var22 = var6.getItemStack().copy();
                    int var9 = var6.getItemStack().stackSize;

                    for(Slot var11 : this.field_94537_h) {
                        if (var11 != null && func_94527_a(var11, var6.getItemStack(), true) && var11.isItemValid(var6.getItemStack()) && var6.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(var11)) {
                            ItemStack var12 = var22.copy();
                            int var13 = var11.getHasStack() ? var11.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, var12, var13);
                            if (var12.stackSize > var12.getMaxStackSize()) {
                                var12.stackSize = var12.getMaxStackSize();
                            }

                            if (var12.stackSize > var11.getSlotStackLimit()) {
                                var12.stackSize = var11.getSlotStackLimit();
                            }

                            var9 -= var12.stackSize - var13;
                            var11.putStack(var12);
                        }
                    }

                    var22.stackSize = var9;
                    if (var22.stackSize <= 0) {
                        var22 = null;
                    }

                    var6.setItemStack(var22);
                }

                this.func_94533_d();
            } else {
                this.func_94533_d();
            }
        } else if (this.field_94536_g != 0) {
            this.func_94533_d();
        } else if ((p_slotClick_3_ == 0 || p_slotClick_3_ == 1) && (p_slotClick_2_ == 0 || p_slotClick_2_ == 1)) {
            if (p_slotClick_1_ == -999) {
                if (var6.getItemStack() != null && p_slotClick_1_ == -999) {
                    if (p_slotClick_2_ == 0) {
                        p_slotClick_4_.dropPlayerItemWithRandomChoice(var6.getItemStack(), true);
                        var6.setItemStack((ItemStack)null);
                    }

                    if (p_slotClick_2_ == 1) {
                        p_slotClick_4_.dropPlayerItemWithRandomChoice(var6.getItemStack().splitStack(1), true);
                        if (var6.getItemStack().stackSize == 0) {
                            var6.setItemStack((ItemStack)null);
                        }
                    }
                }
            } else if (p_slotClick_3_ == 1) {
                if (p_slotClick_1_ < 0) {
                    return null;
                }

                Slot var20 = (Slot)this.inventorySlots.get(p_slotClick_1_);
                if (var20 != null && var20.canTakeStack(p_slotClick_4_)) {
                    ItemStack var27 = this.transferStackInSlot(p_slotClick_4_, p_slotClick_1_);
                    if (var27 != null) {
                        Item var32 = var27.getItem();
                        var5 = var27.copy();
                        if (var20.getStack() != null && var20.getStack().getItem() == var32) {
                            this.retrySlotClick(p_slotClick_1_, p_slotClick_2_, true, p_slotClick_4_);
                        }
                    }
                }
            } else {
                if (p_slotClick_1_ < 0) {
                    return null;
                }

                Slot var21 = (Slot)this.inventorySlots.get(p_slotClick_1_);
                if (var21 != null) {
                    ItemStack var28 = var21.getStack();
                    ItemStack var33 = var6.getItemStack();
                    if (var28 != null) {
                        var5 = var28.copy();
                    }

                    if (var28 == null) {
                        if (var33 != null && var21.isItemValid(var33)) {
                            int var39 = p_slotClick_2_ == 0 ? var33.stackSize : 1;
                            if (var39 > var21.getSlotStackLimit()) {
                                var39 = var21.getSlotStackLimit();
                            }

                            if (var33.stackSize >= var39) {
                                var21.putStack(var33.splitStack(var39));
                            }

                            if (var33.stackSize == 0) {
                                var6.setItemStack((ItemStack)null);
                            }
                        }
                    } else if (var21.canTakeStack(p_slotClick_4_)) {
                        if (var33 == null) {
                            int var38 = p_slotClick_2_ == 0 ? var28.stackSize : (var28.stackSize + 1) / 2;
                            ItemStack var42 = var21.decrStackSize(var38);
                            var6.setItemStack(var42);
                            if (var28.stackSize == 0) {
                                var21.putStack((ItemStack)null);
                            }

                            var21.onPickupFromSlot(p_slotClick_4_, var6.getItemStack());
                        } else if (var21.isItemValid(var33)) {
                            if (var28.getItem() == var33.getItem() && var28.getItemDamage() == var33.getItemDamage() && ItemStack.areItemStackTagsEqual(var28, var33)) {
                                int var37 = p_slotClick_2_ == 0 ? var33.stackSize : 1;
                                if (var37 > var21.getSlotStackLimit() - var28.stackSize) {
                                    var37 = var21.getSlotStackLimit() - var28.stackSize;
                                }

                                if (var37 > var33.getMaxStackSize() - var28.stackSize) {
                                    var37 = var33.getMaxStackSize() - var28.stackSize;
                                }

                                var33.splitStack(var37);
                                if (var33.stackSize == 0) {
                                    var6.setItemStack((ItemStack)null);
                                }

                                var28.stackSize += var37;
                            } else if (var33.stackSize <= var21.getSlotStackLimit()) {
                                var21.putStack(var33);
                                var6.setItemStack(var28);
                            }
                        } else if (var28.getItem() == var33.getItem() && var33.getMaxStackSize() > 1 && (!var28.getHasSubtypes() || var28.getItemDamage() == var33.getItemDamage()) && ItemStack.areItemStackTagsEqual(var28, var33)) {
                            int var36 = var28.stackSize;
                            if (var36 > 0 && var36 + var33.stackSize <= var33.getMaxStackSize()) {
                                var33.stackSize += var36;
                                var28 = var21.decrStackSize(var36);
                                if (var28.stackSize == 0) {
                                    var21.putStack((ItemStack)null);
                                }

                                var21.onPickupFromSlot(p_slotClick_4_, var6.getItemStack());
                            }
                        }
                    }

                    var21.onSlotChanged();
                }
            }
        } else if (p_slotClick_3_ == 2 && p_slotClick_2_ >= 0 && p_slotClick_2_ < 9) {
            Slot var19 = (Slot)this.inventorySlots.get(p_slotClick_1_);
            if (var19.canTakeStack(p_slotClick_4_)) {
                ItemStack var26 = var6.getStackInSlot(p_slotClick_2_);
                boolean var31 = var26 == null || var19.inventory == var6 && var19.isItemValid(var26);
                int var35 = -1;
                if (!var31) {
                    var35 = var6.getFirstEmptyStack();
                    var31 |= var35 > -1;
                }

                if (var19.getHasStack() && var31) {
                    ItemStack var41 = var19.getStack();
                    var6.setInventorySlotContents(p_slotClick_2_, var41.copy());
                    if ((var19.inventory != var6 || !var19.isItemValid(var26)) && var26 != null) {
                        if (var35 > -1) {
                            var6.addItemStackToInventory(var26);
                            var19.decrStackSize(var41.stackSize);
                            var19.putStack((ItemStack)null);
                            var19.onPickupFromSlot(p_slotClick_4_, var41);
                        }
                    } else {
                        var19.decrStackSize(var41.stackSize);
                        var19.putStack(var26);
                        var19.onPickupFromSlot(p_slotClick_4_, var41);
                    }
                } else if (!var19.getHasStack() && var26 != null && var19.isItemValid(var26)) {
                    var6.setInventorySlotContents(p_slotClick_2_, (ItemStack)null);
                    var19.putStack(var26);
                }
            }
        } else if (p_slotClick_3_ == 3 && p_slotClick_4_.capabilities.isCreativeMode && var6.getItemStack() == null && p_slotClick_1_ >= 0) {
            Slot var18 = (Slot)this.inventorySlots.get(p_slotClick_1_);
            if (var18 != null && var18.getHasStack()) {
                ItemStack var25 = var18.getStack().copy();
                var25.stackSize = var25.getMaxStackSize();
                var6.setItemStack(var25);
            }
        } else if (p_slotClick_3_ == 4 && var6.getItemStack() == null && p_slotClick_1_ >= 0) {
            Slot var17 = (Slot)this.inventorySlots.get(p_slotClick_1_);
            if (var17 != null && var17.getHasStack() && var17.canTakeStack(p_slotClick_4_)) {
                ItemStack var24 = var17.decrStackSize(p_slotClick_2_ == 0 ? 1 : var17.getStack().stackSize);
                var17.onPickupFromSlot(p_slotClick_4_, var24);
                p_slotClick_4_.dropPlayerItemWithRandomChoice(var24, true);
            }
        } else if (p_slotClick_3_ == 6 && p_slotClick_1_ >= 0) {
            Slot var16 = (Slot)this.inventorySlots.get(p_slotClick_1_);
            ItemStack var23 = var6.getItemStack();
            if (var23 != null && (var16 == null || !var16.getHasStack() || !var16.canTakeStack(p_slotClick_4_))) {
                int var30 = p_slotClick_2_ == 0 ? 0 : this.inventorySlots.size() - 1;
                int var34 = p_slotClick_2_ == 0 ? 1 : -1;

                for(int var40 = 0; var40 < 2; ++var40) {
                    for(int var43 = var30; var43 >= 0 && var43 < this.inventorySlots.size() && var23.stackSize < var23.getMaxStackSize(); var43 += var34) {
                        Slot var44 = (Slot)this.inventorySlots.get(var43);
                        if (var44.getHasStack() && func_94527_a(var44, var23, true) && var44.canTakeStack(p_slotClick_4_) && this.func_94530_a(var23, var44) && (var40 != 0 || var44.getStack().stackSize != var44.getStack().getMaxStackSize())) {
                            int var14 = Math.min(var23.getMaxStackSize() - var23.stackSize, var44.getStack().stackSize);
                            ItemStack var15 = var44.decrStackSize(var14);
                            var23.stackSize += var14;
                            if (var15.stackSize <= 0) {
                                var44.putStack((ItemStack)null);
                            }

                            var44.onPickupFromSlot(p_slotClick_4_, var15);
                        }
                    }
                }
            }

            this.detectAndSendChanges();
        }

        return var5;
    }
}
