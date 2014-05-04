package com.hci.loopdaloop.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * The Loop class contains all of the methods used for loops
 * 
 * @author Chris Spanos, Will Rhodes, John Friedfeld, Scotty Boutin
 *
 */
public class Loop {
	//class attributes
	MediaRecorder recorder;
	MediaPlayer player;
	File savedLocation;
	boolean recording;
	boolean hasRecorded;
	
	//constructor
	public Loop (Context appContext){
		//instantiates MediaRecorder and MediaPlayer
		recorder = new MediaRecorder();
		player = new MediaPlayer();
		
		//set up the saving location
		long timestamp = System.currentTimeMillis();
		Log.d("Loop.java",""+timestamp);
		File folder = appContext.getDir("loops", 0); //0 = private
		savedLocation = new File(folder.getAbsolutePath()+"time"+timestamp+".3gp");
		prepareRecorder();
		recording = false;
		hasRecorded = false;
	}
	
	///////////////////////////////////////////////////
	//       prepareRecorder()
	///////////////////////////////////////////////////	
	public void prepareRecorder(){
		savedLocation.delete();
		recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		
		try{
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		}
		catch(Exception e){
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		}
		recorder.setOutputFile(savedLocation.getAbsolutePath());
		 try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////////////////
	//       record()
	///////////////////////////////////////////////////
	public void record(){
		if(isPlaying()){
			delete();
		}
		recorder.start();
		recording = true;
	}
	
	///////////////////////////////////////////////////
	//       stopRecord()
	///////////////////////////////////////////////////
	public void stopRecord(){
		recorder.stop();
		recording = false;
		hasRecorded = true;
	}
	
	///////////////////////////////////////////////////
	//       isRecording()
	///////////////////////////////////////////////////
	public boolean isRecording(){
		return recording;
	}
	
	///////////////////////////////////////////////////
	//       prepare()
	///////////////////////////////////////////////////
	public void prepare() {
		
		try {
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//player.setLooping(false);
			player.setDataSource(savedLocation.getAbsolutePath());
			player.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////////////////////////////
	//       Start()
	///////////////////////////////////////////////////
	public void Start() {
		if(hasRecorded){
			player.seekTo(0);
			player.start();
		}
	}
	///////////////////////////////////////////////////
	//       mute()
	///////////////////////////////////////////////////
	public void mute()	{
		player.setVolume(0,0);
	}
	
	//////////////////////////////////////////////////
	//		resume()
	//////////////////////////////////////////////////
	public void resume(float a, float b) {
		player.setVolume(a,b);
	}
	
	///////////////////////////////////////////////////
	//       isPlaying()
	///////////////////////////////////////////////////
	public boolean isPlaying(){
		return player.isPlaying();
	}
	
	///////////////////////////////////////////////////
	//		delete()
	///////////////////////////////////////////////////	
	public void delete(){
		player.reset();
		recorder.reset();
		prepareRecorder();	
	}
}
