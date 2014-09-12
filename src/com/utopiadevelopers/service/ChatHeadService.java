package com.utopiadevelopers.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.utopiadevelopers.chatheads.R;
import com.utopiadevelopers.utils.Utils;

public class ChatHeadService extends Service
{
	private static String TAG = "ChatHeadService";
	private static int DIAMETER = 10;
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

		chatHead = new ImageView(this);
		Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.chat);
		chatHead.setImageBitmap(Utils.loadMaskedBitmap(map, DIAMETER, DIAMETER));

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		setOnClickListener(params);
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

	// Chat Head Functions

	private void setOnClickListener(final WindowManager.LayoutParams params)
	{
		chatHead.setOnTouchListener(new View.OnTouchListener()
		{
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					initialX = params.x;
					initialY = params.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX + (int) (event.getRawX() - initialTouchX);
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(chatHead, params);
					return true;
				}
				return false;
			}
		});
	}
}
