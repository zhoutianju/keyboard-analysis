package priv.zhoutj.kbanalysis;

import java.io.IOException;

/**
 * Shell 启动器
 */
public class ShellStarter {
    /**
     * 主函数
     *
     * @param args 主函数入参
     * @throws IOException 文件 IO 异常
     */
    public static void main(String[] args) throws IOException {
        // 注册 Shutdown Hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        // 判断输入参数
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        init();
        // 子命令处理逻辑
        switch (args[0]) {
            case "reset":
                reset();
            case "record":
                record();
                break;
            case "analysis":
                analysis();
                break;
            default:
                printUsage();
                System.exit(1);
        }
    }

    /**
     * 打印 Usage
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar target/exec.jar [record|reset|analysis]");
    }

    /**
     * 初始化
     *
     * @throws IOException 文件 IO 异常
     */
    private static void init() throws IOException {
        KeyMapHolder.init();
        KeyRecorder.init();
        KeyboardHookHandler.setHook();
    }

    /**
     * 关闭
     */
    private static void close() throws IOException {
        KeyboardHookHandler.unhook();
        KeyRecorder.serialize();
    }

    /**
     * 开始按键记录
     */
    private static void record() {
        KeyboardHookHandler.handleMessage();
    }

    /**
     * 重置按键记录
     */
    private static void reset() throws IOException {
        System.out.println("record reset.");
        KeyRecorder.reset();
    }

    /**
     * 显示分析结果
     */
    private static void analysis() throws IOException {
        KeyRecorder.showAnalysis();
    }
}
