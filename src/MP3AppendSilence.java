import java.io.File;
import java.io.IOException;

public class MP3AppendSilence {

    public static void main(String[] args) {
        // 要处理的文件夹路径
        String folderPath = "I:\\PTE\\WFD";

        // 获取文件夹中的所有文件
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        // 循环处理每个文件
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    try {
                        // 使用 ffprobe 命令获取 MP3 文件的时长
                        String ffprobeCommand = "ffprobe -i \"" + file.getAbsolutePath() + "\" -show_entries format=duration -v quiet -of csv=\"p=0\"";
                        Process ffprobeProcess = Runtime.getRuntime().exec(ffprobeCommand);
                        ffprobeProcess.waitFor();
                        System.out.println("s1 + " + file.getName());

                        // 读取 ffprobe 的输出，获取文件时长
                        java.util.Scanner scanner = new java.util.Scanner(ffprobeProcess.getInputStream()).useDelimiter("\\A");
                        double duration = 4.00;
                        try {
                            duration = scanner.hasNext() ? Double.parseDouble(scanner.next()) : 0;
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                        scanner.close();
                        System.out.println("s2");
                        // 使用 ffmpeg 命令在 MP3 文件后面添加与其相同长度的空音频
                        String outputFilePath = file.getParent() + File.separator + "combined_" + file.getName();
                        String silenceCommand = "ffmpeg -f lavfi -i anullsrc=channel_layout=stereo:sample_rate=44100 -t " + duration + " -c:a libmp3lame -q:a 2 -ar 44100 -ac 2 -af apad \"" + file.getParent() + File.separator + "silence.mp3\"";
                        Process silenceProcess = Runtime.getRuntime().exec(silenceCommand);
                        silenceProcess.waitFor();
                        System.out.println("s3");
                        // 合并原始音频和空音频
                        String mergeCommand = "ffmpeg -i \"" + file.getAbsolutePath() + "\" -i \"" + file.getParent() + File.separator + "silence.mp3\" -filter_complex \"[0:a][1:a]concat=n=2:v=0:a=1[out]\" -map \"[out]\" -c:a libmp3lame -q:a 2 \"" + outputFilePath + "\"";
                        Process mergeProcess = Runtime.getRuntime().exec(mergeCommand);
                        mergeProcess.waitFor();
                        System.out.println("s4");
                        // 删除生成的空音频文件
                        File silenceFile = new File(file.getParent() + File.separator + "silence.mp3");
                        silenceFile.delete();

                        System.out.println("已为 " + file.getName() + " 添加了与其相同长度的空音频。新文件保存为：" + outputFilePath);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
