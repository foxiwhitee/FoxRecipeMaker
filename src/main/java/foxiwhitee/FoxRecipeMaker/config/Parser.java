package foxiwhitee.FoxRecipeMaker.config;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Parser {

    private static final Pattern pattern = Pattern.compile("<slot1>\\.tag.*?\\)(?!\\.)");
    private static final Pattern patternOfSlots = Pattern.compile("<slots\\d-\\d>.tag.*?\\)(?!\\.)");
    private static final Pattern patternOfPart = Pattern.compile("\\.([A-Za-z]+)\\((.*?)\\)");
    private static final Pattern patternOfNumber = Pattern.compile("<slots(\\d-\\d)>");

    public static void main(String[] args) {
        String text = "\"mods.thaumcraft.Infusion.addRecipe(\\\"<out>.name.upper\\\", <slot0>, *[2-18]*, " +
                "<slots(1-18)>.tag(\"Aspects\").list(0).list(22).tag(\"key\", \"amount\", '%s:%s'), " +
                " <out>, <slot1>.stack);\"";
        System.out.println(findTags(text));
        System.out.println(parseTags(findTags(text)));
    }

    private static Stream<List<String>> results(Matcher matcher) {
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

    public static List<String> findTags(String text) {
        return Stream.concat(results(pattern.matcher(text)).map(groups -> groups.get(0)),
                results(patternOfSlots.matcher(text)).map(groups -> groups.get(0))).collect(Collectors.toList());
    }

    public static Map<String, List<String>> parseTag(String tag) {
        Map<String, List<String>> map = new ConcurrentHashMap<>();

        Matcher matcher = patternOfNumber.matcher(tag);
        if (matcher.find()) map.put("1", matcher.group(1).replaceAll("-", "").
                chars().mapToObj(c -> (char) c).map(String::valueOf).
                collect(Collectors.toList()));

        AtomicInteger index = new AtomicInteger(0);

        results(patternOfPart.matcher(tag)).forEach(groups -> {
            String name = groups.get(0);
            String params = groups.get(1);
            int indexValue = index.incrementAndGet();
            map.put(name + indexValue, Arrays.asList(params.split(", ")));
        });

        return Collections.unmodifiableMap(map);
    }

    public static List<Map<String, List<String>>> parseTags(List<String> tags) {
        return Collections.unmodifiableList(tags.parallelStream()
                .map(Parser::parseTag)
                .collect(Collectors.toList()));
    }
}
