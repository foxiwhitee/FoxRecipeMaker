package foxiwhitee.FoxRecipeMaker.config.parser.statements;

import foxiwhitee.FoxRecipeMaker.ModBlocks;
import foxiwhitee.FoxRecipeMaker.blocks.BlockCustomReciper;
import foxiwhitee.FoxRecipeMaker.config.utils.BlockInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;

import java.util.List;
import java.util.Set;

public class AddContainerStatement implements Statement {
    private final String blockName;
    private final int playerInvX, playerInvY;
    private final boolean isPlayerInvDecor;
    private final List<ContainerInformation.Slot> slots;
    private final ContainerInformation.Slot out;

    public AddContainerStatement(String blockName, int playerInvX, int playerInvY, boolean isPlayerInvDecor, List<ContainerInformation.Slot> slots, ContainerInformation.Slot out) {
        this.blockName = blockName;
        this.playerInvX = playerInvX;
        this.playerInvY = playerInvY;
        this.isPlayerInvDecor = isPlayerInvDecor;
        this.slots = slots;
        this.out = out;
    }

    @Override
    public void execute() {
        System.out.println(new ContainerInformation(blockName, playerInvX, playerInvY, isPlayerInvDecor, out, slots));
        ModBlocks.infoContainer.put(blockName, new ContainerInformation(blockName, playerInvX, playerInvY, isPlayerInvDecor, out, slots));
    }

    @Override
    public String toString() {
        return "AddContainerStatement{" +
                "blockName='" + blockName + '\'' +
                ", playerInvX=" + playerInvX +
                ", playerInvY=" + playerInvY +
                ", isPlayerInvDecor=" + isPlayerInvDecor +
                ", slots=" + slots +
                ", out=" + out +
                '}';
    }
}