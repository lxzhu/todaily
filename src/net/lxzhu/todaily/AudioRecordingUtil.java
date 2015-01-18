package net.lxzhu.todaily;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;

public class AudioRecordingUtil {

	// 音频获取源
	private int audioSource = MediaRecorder.AudioSource.MIC;
	// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
	private static int sampleRateInHz = 44100;
	// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
	private static int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
	// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
	private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	// 缓冲区字节大小
	private int bufferSizeInBytes = 0;
	private AudioRecord audioRecord;
	private boolean isRecording = false;// 设置正在录制的状态

	private String rawAudioDataFilePath = "";
	private String wavAudioDataFilePath;
	protected Context context;
	protected FileOutputStream fileStream;

	public AudioRecordingUtil(Context context) {
		this.context = context;
	}

	public void start(String wavAudioDataFilePath) {
		rawAudioDataFilePath = Environment.getDataDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString();
		this.wavAudioDataFilePath = wavAudioDataFilePath;
		this.bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
		this.audioRecord = new AudioRecord(audioSource, audioSource, audioSource, audioSource, audioSource);
		this.audioRecord.startRecording();
		this.isRecording = true;
		new Thread(new AudioDataReaderThread()).start();
	}

	class AudioDataReaderThread implements Runnable {

		@Override
		public void run() {
			openFileStream();
			while (isRecording) {
				readDataFromMIC();
			}
			closeFileStream();
			makeWavFile();
		}

	}

	protected void openFileStream() {
		try {
			this.fileStream = new FileOutputStream(this.wavAudioDataFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void addFileHeader() {
		if (this.fileStream != null) {

		}
	}

	protected void closeFileStream() {
		if (this.fileStream != null) {
			try {
				this.fileStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 这里得到可播放的音频文件
	private void makeWavFile() {
		FileInputStream in = null;
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = sampleRateInHz;
		int channels = 2;
		long byteRate = 16 * sampleRateInHz * channels / 8;
		byte[] data = new byte[bufferSizeInBytes];
		try {
			in = new FileInputStream(this.rawAudioDataFilePath);
			out = new FileOutputStream(this.wavAudioDataFilePath);
			totalAudioLen = in.getChannel().size();
			totalDataLen = totalAudioLen + 36;
			WriteWaveFileHeader(out, totalAudioLen, totalDataLen, longSampleRate, channels, byteRate);
			while (in.read(data) != -1) {
				out.write(data);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这里提供一个头信息。插入这些信息就可以得到可以播放的文件。 为我为啥插入这44个字节，这个还真没深入研究，不过你随便打开一个wav
	 * 音频的文件，可以发现前面的头文件可以说基本一样哦。每种格式的文件都有 自己特有的头文件。
	 */
	private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate,
			int channels, long byteRate) throws IOException {
		byte[] header = new byte[44];
		header[0] = 'R'; // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f'; // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16; // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1; // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = 16; // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
		out.write(header, 0, 44);
	}

	protected void readDataFromMIC() {
		try {
			byte[] audioBuffer = new byte[this.bufferSizeInBytes];
			int size = this.audioRecord.read(audioBuffer, 0, this.bufferSizeInBytes);
			if (size > 0) {
				this.fileStream.write(audioBuffer, 0, size);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		isRecording = false;
		this.audioRecord.stop();

	}

	public boolean isRecording() {
		return this.isRecording;
	}

	public void replay() {
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz, channelConfig, audioFormat,
				bufferSizeInBytes, AudioTrack.MODE_STREAM);
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				audioTrack.play();
			}
			
		}).start();
		
	}

}
