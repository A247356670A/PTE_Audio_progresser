以下是一个简单的 Java 代码示例，用于读取文件夹内的所有 WAV 文件并使用 ffmpeg 命令将其转换为 MP3 格式。确保你的系统中已经安装了 ffmpeg 并已经配置了环境变量。

        ```java
import java.io.File;
import java.io.IOException;

public class WAVToMP3Converter {

    public static void main(String[] args) {
        // 要处理的文件夹路径
        String folderPath = "your_folder_path";

        // 获取文件夹中的所有文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // 循环处理每个文件
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".wav")) {
                    try {
                        // 使用 ffmpeg 命令转换音频文件为 MP3
                        String outputFileName = file.getName().replace(".wav", ".mp3");
                        String command = "ffmpeg -i \"" + file.getAbsolutePath() + "\" -acodec mp3 -ab 192k \"" + file.getParent() + File.separator + outputFileName + "\"";
                        Process process = Runtime.getRuntime().exec(command);
                        process.waitFor();
                        System.out.println("转换完成：" + outputFileName);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

请记得修改 `folderPath` 变量为你要处理的文件夹路径。这段代码使用了 `Runtime.getRuntime().exec()` 来执行命令行命令，这可能会有一些安全风险，因此在实际项目中请谨慎使用。