import java.io.File;
import java.io.IOException;

public class NormalizeMP3Volume {

    public static void main(String[] args) {
        // 要处理的文件夹路径
        String folderPath = "I:\\PTE\\test";

        // 获取文件夹中的所有文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // 循环处理每个文件
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    try {
                        // 使用 FFmpeg 命令调整音量
                        String outputFilePath = folderPath + File.separator + "normalized_" + file.getName();
                        String ffmpegCommand = "ffmpeg -i \"" + file.getAbsolutePath() + "\" -af volume=3dB \"" + outputFilePath + "\"";
                        Process ffmpegProcess = Runtime.getRuntime().exec(ffmpegCommand);
                        ffmpegProcess.waitFor();

                        System.out.println("已调整 " + file.getName() + " 的音量。新文件保存为：" + outputFilePath);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
