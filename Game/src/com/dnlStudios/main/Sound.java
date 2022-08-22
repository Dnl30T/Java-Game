package com.dnlStudios.main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("removal")
public class Sound {
	
	private AudioClip clip;
	
	public static final Sound musicBackground = new Sound("/music.wav");
	
	@SuppressWarnings("deprecation")
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {
			
		}
	}
	public void play() {
		try {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e){
			
		}
	}
	public void loop() {
		try {
			new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e){
			
		}
	}

}
