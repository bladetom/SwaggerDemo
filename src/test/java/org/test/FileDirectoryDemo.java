package org.test;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tomtian
 * @create 2023-01-22 8:40 PM
 * @Description h.264变成hevc视频
 */
public class FileDirectoryDemo {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        StopWatch stopWatch =new StopWatch();
        stopWatch.start();
        List<File> files = new ArrayList<>();
        File directory =new File("D:\\coser");
//        String path = "F:\\一男一女 丝袜高跟";
//        ArrayList<File> list = getFiles(path);
//        System.out.println(replacementInfo("asdfas.mp4",".","-1"));
        findAllFile(directory,files);
        Map<String,String> map = new HashMap<>();
//        list.stream().forEach(System.out::println);
//        int i =10086;
        for (File f :files
        ) {
            //1.mp4不太好，可能重复，应该用很大的数字
            map.put(f.toString(),f.toString()+"1a.mp4");
        }

        long oldFileSize = 0;
        long newFileSize =0;
        for (Map.Entry<String,String> entry:map.entrySet()
             ) {
            ffmpeg(entry.getKey(),entry.getValue());
            //先删除旧的，再改名
            File old = new File(entry.getKey());
            //计算一下大小比列，然后再决定是否删除
            //计算一下压缩数据
//            old.delete();
            File newFile = new File(entry.getValue());

            if (newFile.length()>old.length()){
                newFile.delete();
                System.out.println(newFile.toString()+"比原先文件大");
            }else {
                System.out.println(old.toString()+"已经成功转换");
                oldFileSize += old.length();
                newFileSize += newFile.length();
                if (!old.delete()){
                    System.out.println(old.toString()+"删除失败");
                }
                newFile.renameTo(new File(entry.getKey()));
            }
        }

        stopWatch.stop();
//        stopWatch.
        long stopTime = System.currentTimeMillis();
        System.out.println("用时"+(stopTime-startTime)/1000/60+"分钟");
        System.out.println("耗时时间"+stopWatch.prettyPrint());
        BigDecimal oldDecimal = BigDecimal.valueOf(oldFileSize);
        BigDecimal newDecimal = BigDecimal.valueOf(newFileSize);

        System.out.println("老文件"+oldDecimal.divide(BigDecimal.valueOf(1024*1024*1024),2, RoundingMode.CEILING) +"GB");
        System.out.println("新文件"+newDecimal.divide(BigDecimal.valueOf(1024*1024*1024),2,RoundingMode.CEILING) +"GB");

        System.out.println("压缩比例"+newDecimal.divide(oldDecimal,2,RoundingMode.CEILING));
        System.out.println(",--.  ,--.  ,--.               ,--.,--. ,------.                           \n" +
                "|  |,-'  '-.|  |,---.   ,--,--.|  ||  | |  .-.  \\  ,---. ,--,--, ,---.     \n" +
                "|  |'-.  .-'`-'(  .-'  ' ,-.  ||  ||  | |  |  \\  :| .-. ||      \\ .-. :    \n" +
                "|  |  |  |     .-'  `) \\ '-'  ||  ||  | |  '--'  /' '-' '|  ||  \\   --.--. \n" +
                "`--'  `--'     `----'   `--`--'`--'`--' `-------'  `---' `--''--'`----'--' \n" +
                "                                                                           ");
    }

    /*
    *
    * */

    public static void findAllFile(File file, List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){
                findAllFile(f,fileList);
            }else {
                if (f.getName().contains("mp4")&&f.length()>1024*1024*100){
                    System.out.println(f.toString());
                    fileList.add(f);
                }
//                System.out.println(f.toString());
            }
        }
    }

    public static ArrayList<File> getFiles(String path) {
        ArrayList<File> files = new ArrayList<File>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                //mp4 ts mov mkv avi
                if (tempList[i].getName().contains("mp4")||tempList[i].getName().contains("mov")||tempList[i].getName().contains("mpg")||tempList[i].getName().contains("avi")){
                    files.add(tempList[i]);
                }
              System.out.println("文     件：" + tempList[i]);

            }
            if (tempList[i].isDirectory()) {
              System.out.println("文件夹：" + tempList[i]);
            }
        }
        return files;
    }

    public static void ffmpeg(String input,String output) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:\\software\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\software\\ffmpeg-master-latest-win64-gpl\\bin\\ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()

                .setInput(input)     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(false) // Override the output if it exists

                .addOutput(output)   // Filename for the destination
                .setFormat("mp4")        // Format is inferred from filename, or can be set
//                .setTargetSize(250_000)  // Aim for a 250KB file

                .disableSubtitle()       // No subtiles

//                .setAudioChannels(1)         // Mono audio
//                .setAudioCodec("aac")        // using the aac codec
//                .setAudioSampleRate(48_000)  // at 48KHz
//                .setAudioBitRate(32768)      // at 32 kbit/s
                .setAudioCodec("copy")

                .setVideoCodec("hevc_nvenc")     // Video using x264
//                .setVideoFrameRate(24, 1)     // at 24 frames per second
//                .setVideoResolution(640, 480) // at 640x480 resolution

                //strict模式要试一下
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

// Run a one-pass encode
        executor.createJob(builder).run();
        //这个也要试一下
// Or run a two-pass encode (which is better quality at the cost of being slower)
//        executor.createTwoPassJob(builder).run();

    }

    public static String replacementInfo(String str, String keyword, String before) {
        StringBuilder sb = new StringBuilder(str);
        String rs = str;
        try {
            //字符第一次出现的位置
            int index = sb.indexOf(keyword);
            while (index != -1) {
                sb.insert(index, before);
                //下一次出现的位置，
                index = sb.indexOf(keyword, index + before.length() + 1);
            }
            rs = sb.toString();
        } catch (Exception e) {
            System.out.println("更换字符错误！！！");
            e.printStackTrace();
        }
        return rs;
    }

//    public static void test(String path){
//        File file =  new File(path);
//        File[] files = file.listFiles();
//        for (File f:files
//             ) {
//            System.out.println(f.getUsableSpace());
//            System.out.println(f.getFreeSpace());
//        }
//    }

}
