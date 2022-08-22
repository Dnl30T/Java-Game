package com.dnlStudios.world;

public class Camera {

	public static int x; 
	public static int y;
	
		public static int clamp(int cur, int min, int max) {
		if(cur < min) {
			cur = min;
		}
		if(cur > max) {
			cur = max;
		}
		return cur;
	}
	
}
