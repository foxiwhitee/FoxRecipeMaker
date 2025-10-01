package foxiwhitee.FoxRecipeMaker.tiles;

import foxiwhitee.FoxRecipeMaker.blocks.BlockCustomReciper;
import foxiwhitee.FoxRecipeMaker.config.ConfigManager;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TileCustomReciper extends TileReciper {
    private static final Pattern pattern = Pattern.compile("<slot1>\\.tag.*?\\)(?!\\.)");
    private static final Pattern patternOfPart = Pattern.compile("\\.([A-Za-z]+)\\((.*?)\\)");

    @Override
    protected int getInventorySize() {
        if (getBlock() == null) {
            return 99;
        } else {
            return getBlock().getInfoContainer().getSlots().size();
        }
    }

    @Override
    protected int getInventoryOutputSize() {
        return 1;
    }

    @Override
    public String generateCraft(ItemStack[] items, boolean shaped, boolean ore) {
        return "";
    }

    public String generateCraft(ItemStack[] items, ItemStack out, Map<Integer, Boolean> checkBoxesData, Map<Integer, Integer> slidersData) {
        String pattern = getBlock().getPattern();
        StringBuilder result = new StringBuilder();
        Pattern innerPattern = Pattern.compile("~([^~]*)~");
        Matcher matcher = innerPattern.matcher(pattern);
        int lastEnd = 0;

        while (matcher.find()) {
            result.append(pattern.substring(lastEnd, matcher.start()));
            String inner = matcher.group(1);
            String[] parts = inner.split("\\s*\\?\\s*|\\s*:\\s*");

            StringBuilder innerResult = new StringBuilder();
            boolean matched = false;
            for (int i = 0; i < parts.length - 1; i += 2) {
                String condition = parts[i].trim();
                String value = parts[i + 1].trim();
                if (value.charAt(0) == '\"') {
                    value = value.substring(1, value.length());
                }
                if (value.charAt(value.length() - 1) == '\"') {
                    value = value.substring(0, value.length() - 1);
                }
                if (condition.matches("<checkbox\\d+>")) {
                    int checkBoxNum = Integer.parseInt(condition.replaceAll("[^\\d]", ""));
                    if (checkBoxesData.getOrDefault(checkBoxNum, false)) {
                        innerResult.append(value.replace("\"\"", ""));
                        matched = true;
                        break;
                    }
                }
            }
            if (!matched && parts.length % 2 == 1) {
                String value = parts[parts.length - 1].replace("\"\"", "");
                if (value.charAt(0) == '\"') {
                    value = value.substring(1, value.length());
                }
                if (value.charAt(value.length() - 1) == '\"') {
                    value = value.substring(0, value.length() - 1);
                }
                innerResult.append(value);
            }

            result.append(innerResult.toString());
            lastEnd = matcher.end();
        }
        result.append(pattern.substring(lastEnd));
        String finalResult = result.toString(), slot = "<out>";
        if (finalResult.contains(slot + ".stack")) {
            finalResult = finalResult.replace(slot + ".stack", getName(out, false, true));
        } else if (finalResult.contains(slot + ".name")) {
            if (finalResult.contains(slot + ".name.lower")) {
                finalResult = finalResult.replace(slot + ".name.lower", getName(out, false, true, true, false));
            } else if (finalResult.contains(slot + ".name.upper")) {
                finalResult = finalResult.replace(slot + ".name.upper", getName(out, false, true, false, true));
            } else {
                finalResult = finalResult.replace(slot + ".name", getName(out, false, true, false, false));
            }
        } else {
            finalResult = finalResult.replace(slot, getName(out, false));
        }
        boolean ore = false;
        if (checkBoxesData.containsKey(-2)) {
            ore = checkBoxesData.get(-2);
        }
        for (int i = 0; i < items.length; i++) {
            slot = "<slot" + i + ">";
            if (finalResult.contains(slot + ".stack")) {
                finalResult = finalResult.replace(slot + ".stack", getName(items[i], ore, true));
            } else if (finalResult.contains(slot + ".name")) {
                if (finalResult.contains(slot + ".name.lower")) {
                    finalResult = finalResult.replace(slot + ".name.lower", getName(items[i], ore, true, true, false));
                } else if (finalResult.contains(slot + ".name.upper")) {
                    finalResult = finalResult.replace(slot + ".name.upper", getName(items[i], ore, true, false, true));
                } else {
                    finalResult = finalResult.replace(slot + ".name", getName(items[i], ore, true, false, false));
                }
            } else {
                finalResult = finalResult.replace(slot, getName(items[i], ore));
            }
        }
        for (Map.Entry<Integer, Integer> entry : slidersData.entrySet()) {
            finalResult = finalResult.replace("<slider" + entry.getKey() + ">", String.valueOf(entry.getValue()));
        }
        innerPattern = Pattern.compile("<checkbox(\\d+)>");
        matcher = innerPattern.matcher(finalResult);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            int id = Integer.parseInt(matcher.group(1));
            boolean replacement = checkBoxesData.get(id);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement ? "true" : "false"));
        }
        matcher.appendTail(sb);
        finalResult = sb.toString();

        innerPattern = Pattern.compile("\\*{1,2}(\\S)\\s*(\\d+)\\s*-\\s*(\\d+)\\s*(\\S)\\*");
        matcher = innerPattern.matcher(finalResult);

        sb = new StringBuffer();
        boolean nullable = false;
        int from, to;
        String startSymbol, endSymbol, current, match;
        while (matcher.find()) {
            match = matcher.group(0);

            nullable = match.startsWith("**");

            startSymbol = matcher.group(1);
            from = Integer.parseInt(matcher.group(2));
            to = Integer.parseInt(matcher.group(3));
            endSymbol = matcher.group(4);
            current = "";

            StringBuilder replacement = new StringBuilder();
            replacement.append(startSymbol).append(" ");

            for (int i = from; i < to && i < items.length; i++) {
                current = getName(items[i], ore);
                if (!current.equals("null")) {
                    replacement.append(current);
                    replacement.append(", ");
                } else if (nullable) {
                    replacement.append(current);
                    replacement.append(", ");
                }
            }
            int last = replacement.lastIndexOf(", ");
            if (last == replacement.length() - 2) {
                replacement.delete(last, replacement.length());
            }
            replacement.append(" ").append(endSymbol);

            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement.toString()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private Map<String, List<String>> parseTag(String tag) {
        Map<String, List<String>> map = new ConcurrentHashMap<>();

        AtomicInteger index = new AtomicInteger(0);

        results(patternOfPart.matcher(tag)).forEach(groups -> {
            String name = groups.get(0);
            String params = groups.get(1);
            int indexValue = index.incrementAndGet();
            map.put(String.valueOf(indexValue), Arrays.asList(params.split(", ")));
        });

        return Collections.unmodifiableMap(map);
    }

    private Stream<List<String>> results(Matcher matcher) {
        List<List<String>> result = new ArrayList<>();
        List<String> temp;

        while (matcher.find()) {
            if (matcher.groupCount() == 0) result.add(Collections.singletonList(matcher.group()));
            else {
                temp = new ArrayList<>();
                for (int i = 0; i < matcher.groupCount(); i++) temp.add(matcher.group(i + 1));
                result.add(temp);
            }
        }

        return result.stream();
    }

    private List<String> findTags(String text) {
        return results(pattern.matcher(text)).map(groups -> groups.get(0)).collect(Collectors.toList());
    }

    public void outputRecipeToFile(Map<Integer, Boolean> checkBoxesData, Map<Integer, Integer> slidersData) {
        String integration = getBlock().getIntegration();
        if (getInventoryOutput().getStackInSlot(0) == null) {
            return;
        }
        try {
            File file = new File("scripts/" + ConfigManager.INTEGRATIONS.get(integration) + ".zs");

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                if (ConfigManager.IMPORTS.containsKey(getBlock().getIntegration())) {
                    try (FileWriter fw = new FileWriter(file);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {

                        out.println(ConfigManager.IMPORTS.get(getBlock().getIntegration()));
                        out.println("\n\n");
                    }
                }
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            ItemStack stack = getInventoryOutput().getStackInSlot(0);
            //genRemove(stack, out);
            String outItemStack = getName(stack, false);
            out.println(generateCraft(getInventory().toArray(), getInventoryOutput().getStackInSlot(0), checkBoxesData, slidersData));
            out.println("");
            out.close();
            bw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println("ERROR " + e.getLocalizedMessage());
        }
    }

    public BlockCustomReciper getBlock() {
        if (this.blockType == null) {
            if (this.worldObj != null) {
                this.blockType = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (this.blockType == null) {
            return null;
        } else {
            return (BlockCustomReciper) this.blockType;
        }
    }
}
