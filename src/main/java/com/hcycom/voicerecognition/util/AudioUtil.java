package com.hcycom.voicerecognition.util;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import org.apache.commons.io.IOUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * mp3转换pcm工具类
 *
 * @author Silence
 * @create 2019-05-17 19:39
 */
public class AudioUtil {

    /**
     * MPE转PCM，此方法转换不成功
     * @param mp3Stream 原始语音输入文件流
     * @return pcmBytes 转换后的二进制
     * @throws Exception
     */
    public static byte[] mp3ConvertPcm(InputStream mp3Stream) throws Exception{
        BufferedInputStream bufferedInputStream = new BufferedInputStream(mp3Stream);
        // 原MP3文件转AudioInputStream
        AudioInputStream mp3audioStream = AudioSystem.getAudioInputStream(bufferedInputStream);
        //audioInputStream = convertToPCM(audioInputStream);
        // 将AudioInputStream MP3文件 转换为PCM AudioInputStream
        AudioInputStream pcmaudioStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, mp3audioStream);
        byte[] pcmBytes = IOUtils.toByteArray(pcmaudioStream);
        pcmaudioStream.close();
        mp3audioStream.close();
        return pcmBytes;
    }

    /**
     * MP3转PCM，测试成功
     * @param mp3Stream
     * @return
     * @throws Exception
     */
    public static byte[] mp3ConvertPcm2(InputStream mp3Stream) throws Exception{
        //转换PCM audioInputStream 数据
        AudioInputStream pcmaudioStream = getPcmAudioInputStream(mp3Stream);
        byte[] pcmBytes = IOUtils.toByteArray(pcmaudioStream);
        pcmaudioStream.close();
        return pcmBytes;
    }

    /**
     * 获取PCM AudioInputStream 数据
     * @param mp3Stream
     * @return
     */
    private static AudioInputStream getPcmAudioInputStream(InputStream mp3Stream) {
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            //读取音频文件类
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3Stream);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }
}
