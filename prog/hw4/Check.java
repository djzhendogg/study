import java.util.LinkedHashMap;
import java.util.Map;

public class Check {
    public static void main(String[] args) {
        char info[]=new char[10];
        char data[]=new char[10];
        char result[] = new char[info.length + data.length];
        System.arraycopy(info, 0, result, 0, info.length);
        System.arraycopy(data, 0, result, info.length, data.length);
        System.out.println(result);
    }
}
