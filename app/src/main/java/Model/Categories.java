package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Categories {
    private static Map<String, String> categoryMap = new HashMap<>();

    public static void addEntry(String key, String value) {
        String upperKey = key.substring(0, 1).toUpperCase() + key.substring(1);
        categoryMap.put(upperKey, value);
    }

    public static ArrayList<String> getKeys() {
        ArrayList<String> categoryList = new ArrayList<>(categoryMap.keySet());
        Collections.sort(categoryList);
        return categoryList;
    }

    public static String getValueFromKey(String key) {
        return categoryMap.get(key);
    }
}
