package org.test;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author tomtian
 * @create 2023-02-27 7:17 PM
 * @Description
 */
public class BrandNewFile2Hevc {
    public static void main(String[] args) throws IOException {
        List<File> list = new ArrayList<>();
        findAllFile(new File("G:\\"),list);
        System.out.println(list);
        findH264File(list);
        System.out.println(list);
        Map<String,String> map = new HashMap<>();
        for (File f:list
             ) {
            map.put(f.toString(),f.toString()+"1a.mp4");
        }
        long oldFileSize = 0;
        long newFileSize =0;
        long startTime = System.currentTimeMillis();
        //int i = 序号巴拉巴拉
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
                //加一个ffmpeg
//                old.renameTo(new File(entry.getKey()+));
                String oldFileName = changeName(entry.getKey());
                old.renameTo(new File(oldFileName));
                System.out.println(newFile.toString()+"比原先文件大");
            }else {
                System.out.println(old.toString()+"已经成功转换");
                oldFileSize += old.length();
                newFileSize += newFile.length();
                if (!old.delete()){
                    System.out.println(old.toString()+"删除失败");
                }
                if (entry.getKey().contains("mov")||entry.getKey().contains("mov")||entry.getKey().contains("mpg")||entry.getKey().contains("avi")){
                    newFile.renameTo(new File(rename(entry.getKey())));
                }else {
                    newFile.renameTo(new File(entry.getKey()));
                }
            }
        }
        long stopTime = System.currentTimeMillis();
        System.out.println("用时"+(stopTime-startTime)/1000/60+"分钟");
//        System.out.println("耗时时间"+stopWatch.prettyPrint());
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

    public static String rename(String str){

        int dotIndex = str.lastIndexOf('.');
        if (dotIndex > 0) {
            String newFilename = str.substring(0, dotIndex) + ".mp4";
            return newFilename;
        }
        return "";
    }
    public static String changeName(String str){
        int index = str.lastIndexOf('.');
        if (index>0){
            String newFilename = str.substring(0,index)+"-ffmpeg.mp4";
            return newFilename;
        }
        return "";
    }


    public static void ffmpeg(String input,String output) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:\\Users\\tomed\\Documents\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\tomed\\Documents\\ffmpeg-master-latest-win64-gpl\\bin\\ffprobe");

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

    public static String videoInfo(String str){

        String VideoCode = null;
        try {
            FFprobe ffprobe = new FFprobe("C:\\Users\\tomed\\Documents\\ffmpeg-master-latest-win64-gpl\\bin\\ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(str);
            System.out.println("Video resolution: " + probeResult.getStreams().get(0).width + "x" + probeResult.getStreams().get(0).height);
            System.out.println("Video duration: " + probeResult.getFormat().duration);
            System.out.println("Video bitrate: " + probeResult.getStreams().get(0).bit_rate);
            VideoCode = probeResult.getStreams().get(0).codec_name;
            System.out.println("Video code "+probeResult.getStreams().get(0).codec_name);
//            System.out.println("Vidoe size "+probeResult.getStreams().get(0));

//            System.out.println(probeResult.getStreams().get(0).width);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VideoCode;
    }


    public static void findAllFile(File file, List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){
                findAllFile(f,fileList);
            }else {
                if ((f.getName().contains("mp4")
                        //||f.getName().contains("mov")
                        ||f.getName().contains("mpg")||f.getName().contains("avi"))&&f.length()>1024*1024*100){
//                    System.out.println(f.toString());
                    fileList.add(f);
                }
//                System.out.println(f.toString());
            }
        }
    }

    //筛出文件
    public static void findH264File(List<File> files){
//        for (File file:files
//             ) {
//            String code = videoInfo(file.toString());
//            if (code.equals("hevc")){
//                files.remove(file);
//            }
//        }

        Iterator<File> it = files.iterator();
        while (it.hasNext()){
            File f = it.next();
            String code = videoInfo(f.toString());

            //写错了应该是f.getname().contain，因为路径里面可能有ffmpeg
            if (code.equals("hevc")||f.toString().contains("ffmpeg")){
                it.remove();
            }
//            if (f.toString().contains("ffmpeg")){
//                it.remove();
//            }
        }
    }

}
