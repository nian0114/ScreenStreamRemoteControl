package info.dvkr.screenstream;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import de.robv.android.xposed.XposedBridge;

public class XUtils {
    public static final String TAG = "Utils";

    public static void xLog(String str, String str2) {
        xLog(str, str2, null);
    }

    public static void xLog(String str, String str2, Throwable th) {
        XposedBridge.log("fork.plusplus::" + str + "::" + str2);
        if (th != null) {
            XposedBridge.log(th);
        }
        if (th != null) {
            Log.e(str, str2, th);
        } else {
            Log.e(str, str2);
        }
    }

    // 使用Root权限执行命令的函数
    public static String executeCommandWithRoot(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", command});
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            return output.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
