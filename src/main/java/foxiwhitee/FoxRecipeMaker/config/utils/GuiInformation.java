package foxiwhitee.FoxRecipeMaker.config.utils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GuiInformation {
    public static class Checkbox {
        public final String blockName;
        public final int id, x, y;
        public final boolean value;
        public final String text;

        public Checkbox(String blockName, int id, int x, int y, boolean value, String text) {
            this.blockName = blockName;
            this.id = id;
            this.x = x;
            this.y = y;
            this.value = value;
            this.text = text;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Checkbox checkbox = (Checkbox) o;
            return id == checkbox.id && x == checkbox.x && y == checkbox.y && value == checkbox.value && Objects.equals(blockName, checkbox.blockName) && Objects.equals(text, checkbox.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blockName, id, x, y, value, text);
        }

        @Override
        public String toString() {
            return "Checkbox{" +
                    "blockName='" + blockName + '\'' +
                    ", id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    ", value=" + value +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    public static class Slider {
        public final String blockName;
        public final int id, x, y, width, height, min, max, defaultValue;
        public final String prefix, suf;

        public Slider(String blockName, int id, int x, int y, int width, int height, int min, int max, int defaultValue, String prefix, String suf) {
            this.blockName = blockName;
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.min = min;
            this.max = max;
            this.defaultValue = defaultValue;
            this.prefix = prefix;
            this.suf = suf;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Slider slider = (Slider) o;
            return id == slider.id && x == slider.x && y == slider.y && width == slider.width && height == slider.height && min == slider.min && max == slider.max && defaultValue == slider.defaultValue && Objects.equals(blockName, slider.blockName) && Objects.equals(prefix, slider.prefix) && Objects.equals(suf, slider.suf);
        }

        @Override
        public int hashCode() {
            return Objects.hash(blockName, id, x, y, width, height, min, max, defaultValue, prefix, suf);
        }

        @Override
        public String toString() {
            return "Slider{" +
                    "blockName='" + blockName + '\'' +
                    ", id=" + id +
                    ", x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    ", min=" + min +
                    ", max=" + max +
                    ", defaultValue=" + defaultValue +
                    ", prefix='" + prefix + '\'' +
                    ", suf='" + suf + '\'' +
                    '}';
        }
    }

    private final String blockName;
    private final int width, height, buttonX, buttonY, arrowX, arrowY;
    private final boolean hasArrow;
    private ContainerInformation info;
    private int offsetX, offsetY;

    public final Set<Checkbox> checkboxes = new HashSet<>();
    public final Set<Slider> sliders = new HashSet<>();

    public GuiInformation(String blockName, int width, int height, int buttonX, int buttonY, int arrowX, int arrowY, boolean hasArrow) {
        this.blockName = blockName;
        this.width = width;
        this.height = height;
        this.arrowX = arrowX;
        this.arrowY = arrowY;
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.hasArrow = hasArrow;
    }

    public String getBlockName() {
        return blockName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getButtonX() {
        return buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }

    public int getArrowX() {
        return arrowX;
    }

    public int getArrowY() {
        return arrowY;
    }

    public boolean isHasArrow() {
        return hasArrow;
    }

    public ContainerInformation getInfo() {
        return info;
    }

    public GuiInformation setInfo(ContainerInformation info) {
        this.info = info;
        return this;
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
        return "GuiInformation{" +
                "blockName='" + blockName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", buttonX=" + buttonX +
                ", buttonY=" + buttonY +
                ", arrowX=" + arrowX +
                ", arrowY=" + arrowY +
                ", hasArrow=" + hasArrow +
                ", info=" + info +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                '}';
    }
}
