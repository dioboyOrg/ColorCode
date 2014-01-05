package com.hwanee.colorcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hwanee.engine.ColorEngine;

import android.os.Bundle;
import android.R.integer;
import android.R.interpolator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	RelativeLayout mColorLayout;
	EditText mColorCodeEdit;
	EditText mRedEdit;
	EditText mGreenEdit;
	EditText mBlueEdit;
	Button mColorCodeOk;
	Button mRGBOk;
	SeekBar mRedColorBar;
	SeekBar mGreenColorBar;
	SeekBar mBlueColorBar;
	int[] mRGB = { 0, 0, 0 };

	private static String COLOR_CODE_PATTERN = "#[A-Fa-f0-9][A-Fa-f0-9][A-Fa-f0-9][A-Fa-f0-9][A-Fa-f0-9][A-Fa-f0-9]";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActivity();
	}

	private void initActivity() {
		mColorLayout = (RelativeLayout) findViewById(R.id.ColorLayout);
		mColorCodeEdit = (EditText) findViewById(R.id.ColorCode);
		mColorCodeOk = (Button) findViewById(R.id.ColorCodeOk);
		mColorCodeOk.setOnClickListener(mColorCodeOkClick);
		mRedEdit = (EditText) findViewById(R.id.RedValue);
		mGreenEdit = (EditText) findViewById(R.id.GreenValue);
		mBlueEdit = (EditText) findViewById(R.id.BlueValue);
		mRGBOk = (Button) findViewById(R.id.RGBOk);
		mRGBOk.setOnClickListener(mRGBOkClick);
		mRedColorBar = (SeekBar) findViewById(R.id.RedColorBar);
		mRedColorBar.setMax(255);
		mRedColorBar.setProgress(0);
		mGreenColorBar = (SeekBar) findViewById(R.id.GreenColorBar);
		mGreenColorBar.setMax(255);
		mGreenColorBar.setProgress(0);
		mBlueColorBar = (SeekBar) findViewById(R.id.BlueColorBar);
		mBlueColorBar.setMax(255);
		mBlueColorBar.setProgress(0);

		mRedColorBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
		mGreenColorBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
		mBlueColorBar.setOnSeekBarChangeListener(mSeekBarChangeListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private OnClickListener mColorCodeOkClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mColorCodeEdit == null) {
				return;
			}
			String color = mColorCodeEdit.getText().toString();
			if (color == null || color.length() != 7) {
				return;
			}
			setColor(color.toUpperCase());
			mRGB = ColorEngine.convertColorCodeToRGB(color.substring(1,
					color.length()));
			setRGBEdit();
		}
	};

	private OnClickListener mRGBOkClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (mRedEdit == null || mGreenEdit == null || mBlueEdit == null) {
				return;
			}
			String red = mRedEdit.getText().toString();
			String green = mGreenEdit.getText().toString();
			String blue = mBlueEdit.getText().toString();
			if (red == null || green == null || blue == null) {
				return;
			}
			mRGB[0] = Integer.valueOf(red);
			mRGB[1] = Integer.valueOf(green);
			mRGB[2] = Integer.valueOf(blue);
			if (isRGBData(mRGB)) {
				String colorCode = "#"
						+ ColorEngine.convertRGBToColorCode(mRGB);
				if (colorCode != null) {
					Toast.makeText(getBaseContext(), colorCode,
							Toast.LENGTH_SHORT).show();
					String result = getColorPattern(colorCode);
					setColor(result);
					setRGBBar();
					if (mColorCodeEdit != null) {
						mColorCodeEdit.setText(result);
					}
				}
			}
		}
	};

	OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			getRGBDProgress();
			if (isRGBData(mRGB)) {
				String colorCode = "#"
						+ ColorEngine.convertRGBToColorCode(mRGB);
				if (colorCode != null) {
					String result = getColorPattern(colorCode);
					setRGBBar();
					Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT)
							.show();
					setColor(result);
					if (mColorCodeEdit != null) {
						mColorCodeEdit.setText(result);
					}
				}
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

		}
	};

	private void setColor(String color) {

		if (color != null && color.length() != 7) {
			return;
		}

		Pattern p = Pattern.compile(COLOR_CODE_PATTERN);
		Matcher m = p.matcher(color);

		if (!m.matches()) {
			Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show();
			return;
		}

		if (mColorLayout != null) {
			mColorLayout.setBackgroundColor(Color.parseColor(color));
		}
	}

	private String getColorPattern(String code) {
		String result = code.substring(0, 7);
		return result;
	}

	private boolean isRGBData(int[] rgb) {
		for (int i = 0; i < rgb.length; i++) {
			if (rgb[i] < 0 && rgb[i] > 255) {
				return false;
			}
		}
		return true;
	}

	private void setRGBBar() {
		if (mRedColorBar == null || mGreenColorBar == null
				|| mBlueColorBar == null) {
			return;
		}
		mRedColorBar.setProgress(mRGB[0]);
		mGreenColorBar.setProgress(mRGB[1]);
		mBlueColorBar.setProgress(mRGB[2]);
	}

	private void setRGBEdit() {
		if (mRedEdit == null || mGreenEdit == null || mBlueEdit == null) {
			return;
		}
		mRedEdit.setText(String.valueOf(mRGB[0]));
		mGreenEdit.setText(String.valueOf(mRGB[1]));
		mBlueEdit.setText(String.valueOf(mRGB[2]));
	}

	private void getRGBDProgress() {
		if (mRedColorBar == null || mGreenColorBar == null
				|| mBlueColorBar == null) {
			return;
		}
		mRGB[0] = mRedColorBar.getProgress();
		mRGB[1] = mGreenColorBar.getProgress();
		mRGB[2] = mBlueColorBar.getProgress();
		setRGBEdit();
	}
}
