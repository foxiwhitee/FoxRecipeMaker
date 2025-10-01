package foxiwhitee.FoxRecipeMaker.config.utils;

import java.util.Objects;

public class BlockInformation {
    private final String integrationName, blockID;
    private final int color, colorTop;

    public BlockInformation(String integrationName, String blockID, int color, int colorTop) {
        this.integrationName = integrationName;
        this.blockID = blockID;
        this.color = color;
        this.colorTop = colorTop;
    }

    public String getIntegrationName() {
        return integrationName;
    }

    public String getBlockID() {
        return blockID;
    }

    public int getColor() {
        return color;
    }

    public int getColorTop() {
        return colorTop;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlockInformation that = (BlockInformation) o;
        return color == that.color && colorTop == that.colorTop && Objects.equals(integrationName, that.integrationName) && Objects.equals(blockID, that.blockID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(integrationName, blockID, color, colorTop);
    }

    @Override
    public String toString() {
        return "BlockInformation{" +
                "integrationName='" + integrationName + '\'' +
                ", blockID='" + blockID + '\'' +
                ", color=" + String.format("#%06X", color) +
                ", colorTop=" + String.format("#%06X", colorTop) +
                '}';
    }
}
