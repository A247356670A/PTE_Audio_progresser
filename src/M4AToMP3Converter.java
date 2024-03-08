import java.io.File;
import java.io.IOException;

public class M4AToMP3Converter {

    public static void main(String[] args) {
        // 要处理的文件夹路径
        String folderPath = "C:\\Users\\24735\\OneDrive\\Desktop\\PTE\\WFD";

        // 获取文件夹中的所有文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // 循环处理每个文件
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".m4a")) {
                    String mp3FileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".mp3";
                    File mp3File = new File(file.getParent(), mp3FileName);

                    // 检查是否已经存在同名的 MP3 文件，如果存在则跳过
                    if (mp3File.exists()) {
                        System.out.println("已存在 MP3 文件：" + mp3File.getName() + "，跳过转换。");
                        continue;
                    }

                    try {
                        // 使用 ffmpeg 命令将 M4A 文件转换为 MP3 格式
                        String command = "ffmpeg -i \"" + file.getAbsolutePath() + "\" -acodec libmp3lame -aq 2 \"" + mp3File.getAbsolutePath() + "\"";
                        Process process = Runtime.getRuntime().exec(command);
                        process.waitFor();
                        System.out.println("转换完成：" + mp3File.getName());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
