package foxiwhitee.FoxRecipeMaker.tiles;

import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.io.*;
import java.util.Objects;

public class TileVanillaReciper extends TileReciper {
    private final static String[][] cm = new String[][]{{"item", "item", "null", "item", "item", "null", "null", "null", "null"}, {"item", "item", "null", "item", "null", "null", "null", "null", "null"}, {"item", "item", "null", "null", "item", "null", "null", "null", "null"}, {"item", "null", "null", "item", "item", "null", "null", "null", "null"}, {"null", "item", "null", "item", "item", "null", "null", "null", "null"}, {"item", "null", "null", "null", "item", "null", "null", "null", "null"}, {"null", "item", "null", "item", "null", "null", "null", "null", "null"}, {"null", "item", "item", "null", "item", "item", "null", "null", "null"}, {"null", "item", "item", "null", "item", "null", "null", "null", "null"}, {"null", "item", "item", "null", "null", "item", "null", "null", "null"}, {"null", "item", "null", "null", "item", "item", "null", "null", "null"}, {"null", "null", "item", "null", "item", "item", "null", "null", "null"}, {"null", "item", "null", "null", "null", "item", "null", "null", "null"}, {"null", "null", "item", "null", "item", "null", "null", "null", "null"}, {"null", "null", "null", "item", "item", "null", "item", "item", "null"}, {"null", "null", "null", "item", "item", "null", "item", "null", "null"}, {"null", "null", "null", "item", "item", "null", "null", "item", "null"}, {"null", "null", "null", "item", "null", "null", "item", "item", "null"}, {"null", "null", "null", "null", "item", "null", "item", "item", "null"}, {"null", "null", "null", "item", "null", "null", "null", "item", "null"}, {"null", "null", "null", "null", "item", "null", "item", "null", "null"}, {"null", "null", "null", "null", "item", "item", "null", "item", "item"}, {"null", "null", "null", "null", "item", "item", "null", "item", "null"}, {"null", "null", "null", "null", "item", "item", "null", "null", "item"}, {"null", "null", "null", "null", "item", "null", "null", "item", "item"}, {"null", "null", "null", "null", "null", "item", "null", "item", "item"}, {"null", "null", "null", "null", "item", "null", "null", "null", "item"}, {"null", "null", "null", "null", "null", "item", "null", "item", "null"}, {"item", "item", "item", "item", "item", "item", "null", "null", "null"}, {"item", "item", "item", "item", "item", "null", "null", "null", "null"}, {"item", "item", "item", "item", "null", "item", "null", "null", "null"}, {"item", "item", "item", "null", "item", "item", "null", "null", "null"}, {"item", "item", "null", "item", "item", "item", "null", "null", "null"}, {"item", "null", "item", "item", "item", "item", "null", "null", "null"}, {"null", "item", "item", "item", "item", "item", "null", "null", "null"}, {"item", "item", "item", "item", "null", "null", "null", "null", "null"}, {"item", "item", "item", "null", "item", "null", "null", "null", "null"}, {"item", "item", "item", "null", "null", "item", "null", "null", "null"}, {"item", "null", "null", "item", "item", "item", "null", "null", "null"}, {"null", "item", "null", "item", "item", "item", "null", "null", "null"}, {"null", "null", "item", "item", "item", "item", "null", "null", "null"}, {"item", "null", "item", "null", "item", "item", "null", "null", "null"}, {"item", "item", "null", "null", "item", "item", "null", "null", "null"}, {"item", "item", "null", "item", "null", "item", "null", "null", "null"}, {"item", "null", "item", "item", "null", "item", "null", "null", "null"}, {"null", "item", "item", "item", "null", "item", "null", "null", "null"}, {"item", "null", "item", "item", "item", "null", "null", "null", "null"}, {"null", "item", "item", "item", "item", "null", "null", "null", "null"}, {"item", "null", "item", "item", "null", "null", "null", "null", "null"}, {"item", "null", "null", "item", "null", "item", "null", "null", "null"}, {"item", "null", "item", "null", "null", "item", "null", "null", "null"}, {"null", "null", "item", "item", "null", "item", "null", "null", "null"}, {"null", "item", "null", "item", "null", "item", "null", "null", "null"}, {"item", "null", "item", "null", "item", "null", "null", "null", "null"}, {"null", "null", "item", "item", "null", "null", "null", "null", "null"}, {"item", "null", "null", "null", "null", "item", "null", "null", "null"}, {"null", "null", "null", "item", "item", "item", "item", "item", "item"}, {"null", "null", "null", "item", "item", "item", "item", "item", "null"}, {"null", "null", "null", "item", "item", "item", "item", "null", "item"}, {"null", "null", "null", "item", "item", "item", "null", "item", "item"}, {"null", "null", "null", "item", "item", "null", "item", "item", "item"}, {"null", "null", "null", "item", "null", "item", "item", "item", "item"}, {"null", "null", "null", "null", "item", "item", "item", "item", "item"}, {"null", "null", "null", "item", "item", "item", "item", "null", "null"}, {"null", "null", "null", "item", "item", "item", "null", "item", "null"}, {"null", "null", "null", "item", "item", "item", "null", "null", "item"}, {"null", "null", "null", "item", "null", "null", "item", "item", "item"}, {"null", "null", "null", "null", "item", "null", "item", "item", "item"}, {"null", "null", "null", "null", "null", "item", "item", "item", "item"}, {"null", "null", "null", "item", "null", "item", "null", "item", "item"}, {"null", "null", "null", "item", "item", "null", "null", "item", "item"}, {"null", "null", "null", "item", "item", "null", "item", "null", "item"}, {"null", "null", "null", "item", "null", "item", "item", "null", "item"}, {"null", "null", "null", "null", "item", "item", "item", "null", "item"}, {"null", "null", "null", "item", "null", "item", "item", "item", "null"}, {"null", "null", "null", "null", "item", "item", "item", "item", "null"}, {"null", "null", "null", "item", "null", "item", "item", "null", "null"}, {"null", "null", "null", "item", "null", "null", "item", "null", "item"}, {"null", "null", "null", "item", "null", "item", "null", "null", "item"}, {"null", "null", "null", "null", "null", "item", "item", "null", "item"}, {"null", "null", "null", "null", "item", "null", "item", "null", "item"}, {"null", "null", "null", "item", "null", "item", "null", "item", "null"}, {"null", "null", "null", "null", "null", "item", "item", "null", "null"}, {"null", "null", "null", "item", "null", "null", "null", "null", "item"}, {"item", "item", "null", "item", "item", "null", "item", "item", "null"}, {"item", "null", "null", "item", "item", "null", "item", "item", "null"}, {"item", "item", "null", "item", "null", "null", "item", "item", "null"}, {"item", "item", "null", "item", "item", "null", "item", "null", "null"}, {"null", "item", "null", "item", "item", "null", "item", "item", "null"}, {"item", "item", "null", "null", "item", "null", "item", "item", "null"}, {"item", "item", "null", "item", "item", "null", "null", "item", "null"}, {"item", "null", "null", "item", "null", "null", "item", "item", "null"}, {"item", "null", "null", "item", "item", "null", "item", "null", "null"}, {"item", "item", "null", "item", "null", "null", "item", "null", "null"}, {"null", "item", "null", "null", "item", "null", "item", "item", "null"}, {"null", "item", "null", "item", "item", "null", "null", "item", "null"}, {"item", "item", "null", "null", "item", "null", "null", "item", "null"}, {"item", "item", "null", "null", "item", "null", "item", "null", "null"}, {"null", "item", "null", "item", "item", "null", "item", "null", "null"}, {"item", "item", "null", "item", "null", "null", "null", "item", "null"}, {"item", "item", "null", "null", "null", "null", "item", "item", "null"}, {"null", "item", "null", "item", "null", "null", "item", "item", "null"}, {"item", "null", "null", "item", "item", "null", "null", "item", "null"}, {"item", "null", "null", "null", "item", "null", "item", "item", "null"}, {"item", "null", "null", "null", "null", "null", "item", "item", "null"}, {"null", "item", "null", "null", "null", "null", "item", "item", "null"}, {"item", "item", "null", "null", "null", "null", "item", "null", "null"}, {"item", "item", "null", "null", "null", "null", "null", "item", "null"}, {"null", "item", "null", "item", "null", "null", "null", "item", "null"}, {"item", "null", "null", "null", "item", "null", "item", "null", "null"}, {"null", "item", "null", "null", "null", "null", "item", "null", "null"}, {"item", "null", "null", "null", "null", "null", "null", "item", "null"}, {"null", "item", "item", "null", "item", "item", "null", "item", "item"}, {"null", "item", "null", "null", "item", "item", "null", "item", "item"}, {"null", "item", "item", "null", "item", "null", "null", "item", "item"}, {"null", "item", "item", "null", "item", "item", "null", "item", "null"}, {"null", "null", "item", "null", "item", "item", "null", "item", "item"}, {"null", "item", "item", "null", "null", "item", "null", "item", "item"}, {"null", "item", "item", "null", "item", "item", "null", "null", "item"}, {"null", "item", "null", "null", "item", "null", "null", "item", "item"}, {"null", "item", "null", "null", "item", "item", "null", "item", "null"}, {"null", "item", "item", "null", "item", "null", "null", "item", "null"}, {"null", "null", "item", "null", "null", "item", "null", "item", "item"}, {"null", "null", "item", "null", "item", "item", "null", "null", "item"}, {"null", "item", "item", "null", "null", "item", "null", "null", "item"}, {"null", "item", "item", "null", "null", "item", "null", "item", "null"}, {"null", "null", "item", "null", "item", "item", "null", "item", "null"}, {"null", "item", "item", "null", "item", "null", "null", "null", "item"}, {"null", "item", "item", "null", "null", "null", "null", "item", "item"}, {"null", "null", "item", "null", "item", "null", "null", "item", "item"}, {"null", "item", "null", "null", "item", "item", "null", "null", "item"}, {"null", "item", "null", "null", "null", "item", "null", "item", "item"}, {"null", "item", "null", "null", "null", "null", "null", "item", "item"}, {"null", "null", "item", "null", "null", "null", "null", "item", "item"}, {"null", "item", "item", "null", "null", "null", "null", "item", "null"}, {"null", "item", "item", "null", "null", "null", "null", "null", "item"}, {"null", "null", "item", "null", "item", "null", "null", "null", "item"}, {"null", "item", "null", "null", "null", "item", "null", "item", "null"}, {"null", "null", "item", "null", "null", "null", "null", "item", "null"}, {"null", "item", "null", "null", "null", "null", "null", "null", "item"}, {"item", "item", "item", "null", "null", "null", "null", "null", "null"}, {"item", "null", "item", "null", "null", "null", "null", "null", "null"}, {"null", "null", "null", "item", "item", "item", "null", "null", "null"}, {"null", "null", "null", "item", "null", "item", "null", "null", "null"}, {"null", "null", "null", "null", "null", "null", "item", "item", "item"}, {"null", "null", "null", "null", "null", "null", "item", "null", "item"}, {"item", "null", "null", "item", "null", "null", "item", "null", "null"}, {"item", "null", "null", "null", "null", "null", "item", "null", "null"}, {"null", "item", "null", "null", "item", "null", "null", "item", "null"}, {"null", "item", "null", "null", "null", "null", "null", "item", "null"}, {"null", "null", "item", "null", "null", "item", "null", "null", "item"}, {"null", "null", "item", "null", "null", "null", "null", "null", "item"}, {"item", "item", "null", "null", "null", "null", "null", "null", "null"}, {"null", "item", "item", "null", "null", "null", "null", "null", "null"}, {"null", "null", "null", "item", "item", "null", "null", "null", "null"}, {"null", "null", "null", "null", "item", "item", "null", "null", "null"}, {"null", "null", "null", "null", "null", "null", "item", "item", "null"}, {"null", "null", "null", "null", "null", "null", "null", "item", "item"}, {"item", "null", "null", "item", "null", "null", "null", "null", "null"}, {"null", "item", "null", "null", "item", "null", "null", "null", "null"}, {"null", "null", "item", "null", "null", "item", "null", "null", "null"}, {"null", "null", "null", "item", "null", "null", "item", "null", "null"}, {"null", "null", "null", "null", "item", "null", "null", "item", "null"}, {"null", "null", "null", "null", "null", "item", "null", "null", "item"}};

    @Override
    protected int getInventorySize() {
        return 9;
    }

    @Override
    protected int getInventoryOutputSize() {
        return 1;
    }

    @Override
    public String generateCraft(ItemStack[] items, boolean shaped, boolean ore) {
        if (items == null) {
            return null;
        }
        String[] names = genStringItem(items, 0, shaped, ore);
        String out = "";
        if (shaped) {
            out = "[[" + names[0] + ", " + names[1] + ", " + names[2] + "], [" + names[3] + ", " + names[4] + ", " + names[5] + "], [" + names[6] + ", " + names[7] + ", " + names[8] + "]]";
        } else {

            for (int i = 0; i < names.length; i++) {
                if (names.length == 1) {
                    out = out + "[" + names[0] + "]";
                } else {

                    out = out + ((i == 0) ? ("[" + names[0]) : ((i == names.length - 1) ? (", " + names[i] + "]") : (", " + names[i])));
                }
            }
        }
        return out;
    }

    public void outputRecipeToFile(boolean shaped, boolean mirror, boolean ore, boolean tag) {
        if (getInventoryOutput().getStackInSlot(0) == null) {
            return;
        }
        try {
            File file = new File("scripts/CraftingTable.zs");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            ItemStack stack = getInventoryOutput().getStackInSlot(0);
            //genRemove(stack, out);
            String outItemStack = getName(stack, false);
            out.println("recipes." + (mirror ? "addShapedMirrored" : (shaped ? "addShaped" : "addShapeless")) + "(" + outItemStack + getTag(stack, tag) + ", " + (mirror ? generateCraftMirror(getInventory().toArray(), ore) : generateCraft(getInventory().toArray(), shaped, ore)) + ");");
            out.println("");
            out.close();
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("ERROR " + e.getLocalizedMessage());
        }
    }

    private int checkCraftMirror(String[] craft) {
        int count = 0;
        for (String[] c : this.cm) {
            int exit = 0;
            for (int i = 0; i < craft.length; i++) {
                if (((!Objects.equals(craft[i], "null")) ? "item" : "null").equals(c[i])) {
                    exit++;
                }
            }
            if (exit == 9) {
                return count;
            }
            count++;
        }
        return 0;
    }

    public String generateCraftMirror(ItemStack[] items, boolean ore) {
        String[] craft = {getName(items[0], ore), getName(items[1], ore), getName(items[2], ore), getName(items[3], ore), getName(items[4], ore), getName(items[5], ore), getName(items[6], ore), getName(items[7], ore), getName(items[8], ore)};
        int nc = checkCraftMirror(craft);
        System.out.println(nc);
        int number = (nc < 7) ? 0 : ((nc < 14) ? 1 : ((nc < 21) ? 2 : ((nc < 28) ? 3 : ((nc < 56) ? 4 : ((nc < 84) ? 5 : ((nc < 112) ? 6 : ((nc < 140) ? 7 : ((nc < 142) ? 8 : ((nc < 144) ? 9 : ((nc < 146) ? 10 : ((nc < 148) ? 11 : ((nc < 150) ? 12 : ((nc < 152) ? 13 : (14 + nc - 152))))))))))))));
        System.out.println(number);
        switch (number) {
            case 0:
                return "[[" + craft[0] + ", " + craft[1] + "], [" + craft[3] + ", " + craft[4] + "]]";

            case 1:
                return "[[" + craft[1] + ", " + craft[2] + "], [" + craft[4] + ", " + craft[5] + "]]";

            case 2:
                return "[[" + craft[3] + ", " + craft[4] + "], [" + craft[6] + ", " + craft[7] + "]]";

            case 3:
                return "[[" + craft[4] + ", " + craft[5] + "], [" + craft[7] + ", " + craft[8] + "]]";

            case 4:
                return "[[" + craft[0] + ", " + craft[1] + ", " + craft[2] + "], [" + craft[3] + ", " + craft[4] + ", " + craft[5] + "]]";

            case 5:
                return "[[" + craft[3] + ", " + craft[4] + ", " + craft[5] + "], [" + craft[6] + ", " + craft[7] + ", " + craft[8] + "]]";

            case 6:
                return "[[" + craft[0] + ", " + craft[1] + "], [" + craft[3] + ", " + craft[4] + "], [" + craft[6] + ", " + craft[7] + "]]";

            case 7:
                return "[[" + craft[1] + ", " + craft[2] + "], [" + craft[4] + ", " + craft[5] + "], [" + craft[7] + ", " + craft[8] + "]]";

            case 8:
                return "[[" + craft[0] + ", " + craft[1] + ", " + craft[2] + "]]";

            case 9:
                return "[[" + craft[3] + ", " + craft[4] + ", " + craft[5] + "]]";

            case 10:
                return "[[" + craft[6] + ", " + craft[7] + ", " + craft[8] + "]]";

            case 11:
                return "[[" + craft[0] + "], [" + craft[3] + "], [" + craft[6] + "]]";

            case 12:
                return "[[" + craft[1] + "], [" + craft[4] + "], [" + craft[7] + "]]";

            case 13:
                return "[[" + craft[2] + "], [" + craft[5] + "], [" + craft[8] + "]]";

            case 14:
                return "[[" + craft[0] + ", " + craft[1] + "]]";

            case 15:
                return "[[" + craft[1] + ", " + craft[2] + "]]";

            case 16:
                return "[[" + craft[3] + ", " + craft[4] + "]]";

            case 17:
                return "[[" + craft[4] + ", " + craft[5] + "]]";

            case 18:
                return "[[" + craft[6] + ", " + craft[7] + "]]";

            case 19:
                return "[[" + craft[7] + ", " + craft[8] + "]]";

            case 20:
                return "[[" + craft[0] + "], [" + craft[3] + "]]";

            case 21:
                return "[[" + craft[1] + "], [" + craft[4] + "]]";

            case 22:
                return "[[" + craft[2] + "], [" + craft[5] + "]]";

            case 23:
                return "[[" + craft[3] + "], [" + craft[6] + "]]";

            case 24:
                return "[[" + craft[4] + "], [" + craft[7] + "]]";

            case 25:
                return "[[" + craft[5] + "], [" + craft[8] + "]]";
        }

        return null;
    }
}
