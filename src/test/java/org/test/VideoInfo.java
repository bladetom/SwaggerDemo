package org.test;

import net.bramp.ffmpeg.FFprobe;
//import org.bytedeco.ffmpeg.ffprobe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
//import org.bytedeco.ffmpeg.ffprobe.FFprobe;

/**
 * @author tomtian
 * @create 2023-02-24 6:20 PM
 * @Description
 */
public class VideoInfo {

    public static void main(String[] args) {
        String videoPath = "D:\\BaiduNetdiskDownload\\LovELolita7\\整理[ LovELolita7 ] 调教母狗合集【43v—53G】\\JK装最后口爆吞精（全）.mp4";
        try {
            FFprobe ffprobe = new FFprobe("C:\\software\\ffmpeg-master-latest-win64-gpl\\bin\\ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(videoPath);
            System.out.println("Video resolution: " + probeResult.getStreams().get(0).width + "x" + probeResult.getStreams().get(0).height);
            System.out.println("Video duration: " + probeResult.getFormat().duration);
            System.out.println("Video bitrate: " + probeResult.getStreams().get(0).bit_rate);
            System.out.println("Video code"+probeResult.getStreams().get(0).codec_name);
//            System.out.println("Vidoe size "+probeResult.getStreams().get(0));

//            System.out.println(probeResult.getStreams().get(0).width);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}