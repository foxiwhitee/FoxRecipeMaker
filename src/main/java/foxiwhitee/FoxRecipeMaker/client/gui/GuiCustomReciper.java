package foxiwhitee.FoxRecipeMaker.client.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import cpw.mods.fml.client.config.GuiSlider;
import foxiwhitee.FoxRecipeMaker.FoxRecipeMaker;
import foxiwhitee.FoxRecipeMaker.config.utils.ContainerInformation;
import foxiwhitee.FoxRecipeMaker.config.utils.GuiInformation;
import foxiwhitee.FoxRecipeMaker.container.ContainerCustomReciper;
import foxiwhitee.FoxRecipeMaker.network.NetworkManager;
import foxiwhitee.FoxRecipeMaker.network.packets.CustomCraftPacket;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuiCustomReciper extends GuiReciper {
    private final GuiInformation info;
    public final HashMap<Integer, GuiCheckBox> checkBoxes = new HashMap<>();
    public final HashMap<Integer, GuiSlider> sliders = new HashMap<>();
    public final HashMap<Integer, Integer> slidersValues = new HashMap<>();

    public GuiCustomReciper(ContainerCustomReciper container) {
        super(container, ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getWidth(),
                ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getHeight(),
                ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getButtonX(),
                ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui().getButtonY());
        info = ((TileCustomReciper)container.getTileEntity()).getBlock().getInfoGui();
    }

    @Override
    protected String getBackground() {
        return "gui/gui.png";
    }

    @Override
    public void drawBackground(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FoxRecipeMaker.MODID.toLowerCase(), this.getBackground());

        // Кути
        Utility.drawTexture(offsetX, offsetY, 0, 0, 11, 11, 11, 11, 256, 256);
        Utility.drawTexture(offsetX - 11 + info.getWidth(), offsetY, 12, 0, 11, 11, 11, 11, 256, 256);
        Utility.drawTexture(offsetX, offsetY - 11 + info.getHeight(), 0, 12, 11, 11, 11, 11, 256, 256);
        Utility.drawTexture(offsetX - 11 + info.getWidth(), offsetY - 11 + info.getHeight(), 12, 12, 11, 11, 11, 11, 256, 256);

        // Рамка
        Utility.drawTexture(offsetX + 11, offsetY + 1, 24, 1, info.getWidth() - 22, 4, 5, 4, 256, 256);
        Utility.drawTexture(offsetX + 1, offsetY + 11, 24, 6, 4, info.getHeight() - 22,  4, 5, 256, 256);
        Utility.drawTexture(offsetX - 5 + info.getWidth(), offsetY + 11, 29, 6, 4, info.getHeight() - 22,  4, 5, 256, 256);
        Utility.drawTexture(offsetX + 11, offsetY - 5 + info.getHeight(), 30, 1, info.getWidth() - 22, 4, 5, 4, 256, 256);

        // Фон
        Utility.drawTexture(offsetX + 11, offsetY + 5, 8, 8, info.getWidth() - 22, 2, 2, 2, 256, 256);
        Utility.drawTexture(offsetX + 7, offsetY + 7, 8, 8, info.getWidth() - 14, 4, 1, 1, 256, 256);
        Utility.drawTexture(offsetX + 5, offsetY + 11, 8, 8, info.getWidth() - 10, info.getHeight() - 22, 1, 1, 256, 256);
        Utility.drawTexture(offsetX + 7, offsetY - 11 + info.getHeight(), 8, 8, info.getWidth() - 14, 4, 1, 1, 256, 256);
        Utility.drawTexture(offsetX + 11, offsetY - 7 + info.getHeight(), 8, 8, info.getWidth() - 22, 2, 2, 2, 256, 256);

        // Кути декорація
        if (info.getHeight() >= 50 && info.getWidth() >= 100) {
            Utility.drawTexture(offsetX + 5, offsetY + 5, 36, 0, 32, 32, 32, 32, 256, 256);
            Utility.drawTexture(offsetX - 37 + info.getWidth(), offsetY + 5, 69, 0, 32, 32, 32, 32, 256, 256);

            if (info.getHeight() >= 100) {
                Utility.drawTexture(offsetX + 5, offsetY - 37 + info.getHeight(), 36, 33, 32, 32, 32, 32, 256, 256);
                Utility.drawTexture(offsetX - 37 + info.getWidth(), offsetY - 37 + info.getHeight(), 69, 33, 32, 32, 32, 32, 256, 256);
            }
        }

        // Стрілка
        if (info.isHasArrow()) {
            Utility.drawTexture(offsetX + info.getArrowX(), offsetY + info.getArrowY(), 209, 3, 22, 15, 22, 15, 256, 256);
        }

        if (info.getInfo() != null) {
            ContainerInformation cInfo = info.getInfo();

            // Інвентарь
            if (info.getWidth() >= 215 && cInfo.isPlayerInvDecor()) {
                Utility.drawTexture(offsetX - 15 + cInfo.getPlayerInvX(), offsetY - 2 + info.getHeight(), 175, 1, 15, 46, 15, 46, 256, 256);
                Utility.drawTexture(offsetX + cInfo.getPlayerInvX(), offsetY - 2 + info.getHeight(), 189, 1, 176, 46, 1, 46, 256, 256);
                Utility.drawTexture(offsetX + 176 + cInfo.getPlayerInvX(), offsetY - 2 + info.getHeight(), 191, 1, 15, 46, 15, 46, 256, 256);
            }

            Utility.drawTexture(offsetX + cInfo.getPlayerInvX(), offsetY - 8 + cInfo.getPlayerInvY(), 0, 166, 176, 90, 176, 90, 256, 256);

            // Слот вихода
            ContainerInformation.Slot outSlot = cInfo.getOut();
            if (outSlot.isBigDecorate) {
                Utility.drawTexture(offsetX - 13 + outSlot.x, offsetY - 13 + outSlot.y, 104, 2, 42, 42, 42, 42, 256, 256);
            } else if (outSlot.isBig) {
                Utility.drawTexture(offsetX - 5 + outSlot.x, offsetY - 5 + outSlot.y, 148, 1, 26, 26, 26, 26, 256, 256);
            } else {
                Utility.drawTexture(offsetX - 1 + outSlot.x, offsetY - 1 + outSlot.y, 148, 28, 18, 18, 18, 18, 256, 256);
            }

            // Слоти
            int finalOffsetX = offsetX;
            int finalOffsetY = offsetY;
            cInfo.getSlots().forEach(slot -> {
                Utility.drawTexture(finalOffsetX - 1 + slot.x, finalOffsetY - 1 + slot.y, 148, 28, 18, 18, 18, 18, 256, 256);
            });
        }
    }

    public void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == -1) {
            TileEntity tile = container.getTileEntity();
            Map<Integer, Boolean> checkBoxesData = new HashMap<>();
            checkBoxes.forEach((integer, guiCheckBox) -> {
                checkBoxesData.put(integer, guiCheckBox.isChecked());
            });
            Map<Integer, Integer> slidersData = new HashMap<>();
            sliders.forEach((integer, guiSlider) -> {
                slidersData.put(integer, guiSlider.getValueInt());
            });
            try {
                NetworkManager.instance.sendToServer(new CustomCraftPacket(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, checkBoxesData, slidersData));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft += info.getOffsetX();
        this.guiTop += info.getOffsetY();

        if (info.getInfo().getPlayerInvY() + 90 > this.ySize) {
            if (info.getInfo().getPlayerInvY() > this.ySize) {
                this.ySize += this.xSize - info.getInfo().getPlayerInvY() + 90;
            } else {
                this.ySize += 90 - (this.xSize - info.getInfo().getPlayerInvY());
            }
        }

        info.checkboxes.forEach(checkbox -> {
            checkBoxes.put(checkbox.id, new GuiCheckBox(checkbox.id, guiLeft + (checkbox.x), guiTop + (checkbox.y), checkbox.text, checkbox.value));
            buttonList.add(checkBoxes.get(checkbox.id));
        });

        info.sliders.forEach(slider -> {
            slidersValues.put(slider.id, slider.defaultValue);
            sliders.put(slider.id, new GuiSlider(slider.id, guiLeft + (slider.x), guiTop + (slider.y), slider.width, slider.height, slider.prefix, slider.suf, slider.min, slider.max, slidersValues.get(slider.id), true, true));
            buttonList.add(sliders.get(slider.id));
        });
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
}
