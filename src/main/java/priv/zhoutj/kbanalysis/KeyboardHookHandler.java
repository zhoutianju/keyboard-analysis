package priv.zhoutj.kbanalysis;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

/**
 * Windows 按键事件 Hook 处理
 */
public class KeyboardHookHandler {
    /**
     * 键盘 Hook 的句柄
     */
    private static HHOOK keyboardHook;

    /**
     * 设置 Hook
     */
    public static void setHook() {
        HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        keyboardHook = User32.INSTANCE.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL,
                (LowLevelKeyboardProc) (nCode, wParam, lParam) -> {
                    int msg = wParam.intValue();
                    // 按住按键会连续触发 WM_KEYDOWN 事件，因此仅在 WM_KEYUP 时处理
                    if (msg == WinUser.WM_KEYUP || msg == WinUser.WM_SYSKEYUP) {
                        if (KeyMapHolder.keyMap.containsKey(lParam.vkCode)) {
                            // 控制台打印
                            System.out.print(KeyMapHolder.keyMap.get(lParam.vkCode));
                            // 按键记录
                            KeyRecorder.record(lParam.vkCode);
                        }
                    }
                    return User32.INSTANCE.CallNextHookEx(keyboardHook, nCode, wParam, lParam.getPointer());
                }, hMod, 0);
    }

    /**
     * 注销 Hook
     */
    public static void unhook() {
        User32.INSTANCE.UnhookWindowsHookEx(keyboardHook);
    }

    /**
     * 循环处理 Windows 消息
     */
    public static void handleMessage() {
        int result;
        MSG msg = new MSG();
        while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) { // 消息循环
            if (result == -1) {
                System.err.println("error in GetMessage!");
                KeyboardHookHandler.unhook();
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }
}
