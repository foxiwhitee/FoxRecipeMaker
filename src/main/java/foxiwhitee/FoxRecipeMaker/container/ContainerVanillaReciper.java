package foxiwhitee.FoxRecipeMaker.container;

import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerVanillaReciper extends ContainerReciper {

    public ContainerVanillaReciper(InventoryPlayer ip, TileReciper myTile) {
        super(ip, myTile);
        bindPlayerInventory(ip, 40, 145);

        for (int i1 = 0; i1 < 3; i1++) {
            for (int col = 0; col < 3; col++) {
                addSlotToContainer(new Slot(myTile.getInventory(), col + i1 * 3, 63 + 18 * col, 59 + 18 * i1));
            }
        }
        addSlotToContainer(new Slot(myTile.getInventoryOutput(), 0, 165, 77));
    }
}
