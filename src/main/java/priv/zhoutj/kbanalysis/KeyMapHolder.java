package priv.zhoutj.kbanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 按键映射
 */
public class KeyMapHolder {
    /**
     * key 表示按键对应的映射码，value 表示该键打印的字符
     */
    public static Map<Integer, String> keyMap = new HashMap<>();

    /**
     * 按键映射配置文件文件名
     */
    private final static String CONFIG_FILENAME = "windows-keyboard-message-map";

    /**
     * 初始化按键映射
     * @throws IOException 文件 IO 异常
     */
    public static void init() throws IOException {
        Path path = Paths.get(CONFIG_FILENAME);
        keyMap = Files.lines(path)
                .collect(Collectors.toMap(s -> Integer.parseInt(s.split(", ")[0]), s->s.split(", ")[1]));
    }
}
