package foxiwhitee.FoxRecipeMaker.container;

import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerReciper extends FoxBaseContainer {
    public ContainerReciper(InventoryPlayer ip, TileReciper myTile) {
        super(ip, myTile);
    }
}
