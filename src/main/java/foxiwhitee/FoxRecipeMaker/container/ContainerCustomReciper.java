package foxiwhitee.FoxRecipeMaker.container;

import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerCustomReciper extends ContainerReciper {

    public ContainerCustomReciper(InventoryPlayer ip, TileCustomReciper myTile) {
        super(ip, myTile);
        ContainerInformation info = myTile.getBlock().getInfoContainer();
        bindPlayerInventory(ip, info.getPlayerInvX(), info.getPlayerInvY());

        info.getSlots().forEach(slot -> {
            addSlotToContainer(new Slot(myTile.getInventory(), slot.id, slot.x, slot.y));
        });
        addSlotToContainer(new Slot(myTile.getInventoryOutput(), 0, info.getOut().x, info.getOut().y));
    }
}
