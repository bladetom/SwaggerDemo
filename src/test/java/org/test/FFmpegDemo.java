package org.test;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

/**
 * @author tomtian
 * @create 2023-01-22 1:11 PM
 * @Description
 */
public class FFmpegDemo {
    public static void main(String[] args) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:\\Users\\tomed\\Documents\\ffmpeg-master-latest-win64-gpl\\bin\\ffmpeg");
        FFprobe ffprobe = new FFprobe("C:\\Users\\tomed\\Documents\\ffmpeg-master-latest-win64-gpl\\bin\\ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()

                .setInput("F:\\baidu disk\\【网曝门事件】最近火爆全网电竞主持人Gatita被土豪花美金调教各种玩弄流出 身材让人喷血 乳头粉红\\xsafd.mp4")     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists

                .addOutput("D:\\test.mp4")   // Filename for the destination
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


}
