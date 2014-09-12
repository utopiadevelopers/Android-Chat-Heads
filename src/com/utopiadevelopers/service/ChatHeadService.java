package com.utopiadevelopers.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;

import com.utopiadevelopers.chatheads.R;

public class ChatHeadService extends Service
{
	private static String TAG = "ChatHeadService";
	private static int DIAMETER = 20;
	private WindowManager windowManager;
	private ImageView chatHead;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		logService("Service Created");
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIAMETER, getResources().getDisplayMetrics());

		chatHead = new ImageView(this);
		chatHead.setImageResource(R.drawable.chat);
		chatHead.setLayoutParams(new LayoutParams(size, size));
		chatHead.requestLayout();

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		windowManager.addView(chatHead, params);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		logService("Service OnStart");
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		logService("Service Destroyed");
		if (chatHead != null)
		{
			windowManager.removeView(chatHead);
		}
	}

	public void logService(String message)
	{
		Log.d(TAG, message);
	}
}
