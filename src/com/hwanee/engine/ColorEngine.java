package com.hwanee.engine;

import android.util.Log;

public class ColorEngine {
	static {
		try {
			Log.i("JNI", "Trying to load ");
			System.loadLibrary("colorengine");
		} catch (UnsatisfiedLinkError ule) {
			Log.e("JNI", "Warning: Could not load ");
		}
	}

	public static native int[] convertColorCodeToRGB(String colorcode);

	public static native String convertRGBToColorCode(int[] rgb);
}
