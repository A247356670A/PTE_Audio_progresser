import java.io.File;
import java.io.IOException;

public class WAVToMP3Converter {

    public static void main(String[] args) {
        // 要处理的文件夹路径
        String folderPath = "C:\\Users\\24735\\OneDrive\\Desktop\\PTE\\WFD";

        // 获取文件夹中的所有文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // 循环处理每个文件
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".wav")) {
                    // 构建输出文件名
                    String outputFileName = file.getName().replace(".wav", ".mp3");
                    File outputFile = new File(file.getParent(), outputFileName);

                    // 检查输出文件是否已存在
                    if (!outputFile.exists()) {
                        try {
                            // 使用 ffmpeg 命令转换音频文件为 MP3
                            String command = "ffmpeg -i \"" + file.getAbsolutePath() + "\" -acodec mp3 -ab 192k \"" + outputFile.getAbsolutePath() + "\"";
                            Process process = Runtime.getRuntime().exec(command);
                            process.waitFor();
                            System.out.println("转换完成：" + outputFileName);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("文件已存在，跳过转换：" + outputFileName);
                    }
                }
            }
        }
    }
}
