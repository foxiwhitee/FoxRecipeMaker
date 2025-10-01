package foxiwhitee.FoxRecipeMaker.config.utils;

import java.util.*;

public class ContainerInformation {
    public static class Slot {
        public final int x, y, id;
        public final boolean isBig, isBigDecorate;

        public Slot(int id, int x, int y, boolean isBig, boolean isBigDecorate) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.isBig = isBig;
            this.isBigDecorate = isBigDecorate && isBig;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Slot slot = (Slot) o;
            return x == slot.x && y == slot.y && isBig == slot.isBig && isBigDecorate == slot.isBigDecorate;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, isBig, isBigDecorate);
        }

        @Override
        public String toString() {
            return "Slot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", id=" + id +
                    ", isBig=" + isBig +
                    ", isBigDecorate=" + isBigDecorate +
                    '}';
        }
    }

    private final String blockName;
    private final int playerInvX, playerInvY;
    private final boolean isPlayerInvDecor;
    private final Set<Slot> slots;
    private final Slot out;
    private int offsetX, offsetY;

    public ContainerInformation(String blockName, int playerInvX, int playerInvY, boolean isPlayerInvDecor, Slot out, List<Slot> slots) {
        this.blockName = blockName;
        this.playerInvX = playerInvX;
        this.playerInvY = playerInvY;
        this.isPlayerInvDecor = isPlayerInvDecor;
        this.out = out;
        this.slots = new HashSet<>(slots);
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public int getPlayerInvX() {
        return playerInvX;
    }

    public int getPlayerInvY() {
        return playerInvY;
    }

    public boolean isPlayerInvDecor() {
        return isPlayerInvDecor;
    }

    public String getBlockName() {
        return blockName;
    }

    public Slot getOut() {
        return out;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public String toString() {
        return "ContainerInformation{" +
                "blockName='" + blockName + '\'' +
                ", playerInvX=" + playerInvX +
                ", playerInvY=" + playerInvY +
                ", isPlayerInvDecor=" + isPlayerInvDecor +
                ", slots=" + slots +
                ", out=" + out +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
