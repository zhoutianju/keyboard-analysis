package priv.zhoutj.kbanalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 按键记录
 */
public class KeyRecorder {
    /**
     * 序列化文件文件名
     */
    private static final String SERIALIZE_FILENAME = "key-record";

    /**
     * Javascript 按键记录变量文件文件名
     */
    private static final String JAVASCRIPT_VARIABLE_FILENAME = "html/key-record.js";

    /**
     * 可视化分析页面文件名
     */
    private static final String ANALYSIS_INDEX_FILENAME = "html/index.html";

    /**
     * 按键记录 StringBuilder
     */
    private static final StringBuilder keyRecord = new StringBuilder();

    /**
     * 本次按键记录数
     */
    private static long newRecordCount = 0;

    /**
     * 自动序列化触发
     */
    private static final long AUTO_SAVE_COUNT = 100;

    /**
     * 初始化
     */
    public static void init() throws IOException {
        if (Files.exists(Paths.get(SERIALIZE_FILENAME))) {
            deserialize();
        }
    }

    /**
     * 重置按键记录
     */
    public static void reset() {
        keyRecord.delete(0, keyRecord.length());
    }

    /**
     * 记录
     *
     * @param key 按键
     */
    public static void record(int key) {
        keyRecord.append(KeyMapHolder.keyMap.get(key));
        if (++newRecordCount % AUTO_SAVE_COUNT == 0) {
            try {
                serialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 序列化
     */
    public static void serialize() throws IOException {
        Files.write(Paths.get(SERIALIZE_FILENAME), keyRecord.toString().getBytes());
        if (newRecordCount > 0) {
            System.out.println();
        }
        System.out.println("save record.");
    }

    /**
     * 反序列化
     */
    private static void deserialize() throws IOException {
        keyRecord.append(new String(Files.readAllBytes(Paths.get(SERIALIZE_FILENAME))));
    }

    /**
     * 写入展示页面 javascript 变量
     */
    private static void saveJavascriptVariableFile() throws IOException {
        Files.write(Paths.get(JAVASCRIPT_VARIABLE_FILENAME), String.format("keyRecord = \"%s\";", keyRecord.toString()).getBytes());
    }

    /**
     * 打开展示页面
     */
    public static void showAnalysis() throws IOException {
        saveJavascriptVariableFile();
        Runtime.getRuntime().exec("cmd /c start " + Paths.get(ANALYSIS_INDEX_FILENAME).toAbsolutePath());
    }
}
