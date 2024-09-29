import java.util.LinkedHashMap;
import java.util.Map;

public class Check {
    public static void main(String[] args) {
        Map<String, Integer> wordMap = new LinkedHashMap<>();
        for (String key: wordMap.keySet()) {
            System.out.println(key);
        }
    }
}
