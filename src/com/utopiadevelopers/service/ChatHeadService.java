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

public class ChatHeadService extends Service
{
	private static String TAG = "ChatHeadService";
	private static int DIAMETER = 10;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private ImageView chatHead;
	private ImageView chatHead1;
	private ImageView chatHead2;
	private ImageView chatHead3;

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
		createOtherChatHeads();
		setOnClickListener();
		setOnTouchListener();
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
			windowManager.removeView(chatHead1);
			windowManager.removeView(chatHead2);
			windowManager.removeView(chatHead3);
		}
	}

	public void logService(String message)
	{
		Log.d(TAG, message);
	}

	// Chat Head Functions

	private void createOtherChatHeads()
	{

		chatHead = new ImageView(this);
		Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.chat);
		chatHead.setImageBitmap(map);

		params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;

		windowManager.addView(chatHead, params);

		chatHead1 = new ImageView(this);
		Bitmap map1 = BitmapFactory.decodeResource(getResources(), R.drawable.compressor);
		chatHead1.setImageBitmap(map1);
		chatHead1.setVisibility(View.INVISIBLE);

		WindowManager.LayoutParams params1 = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params1.gravity = Gravity.TOP | Gravity.LEFT;
		params1.x = 200;
		params1.y = 200;

		windowManager.addView(chatHead1, params1);

		chatHead2 = new ImageView(this);
		Bitmap map2 = BitmapFactory.decodeResource(getResources(), R.drawable.fina);
		chatHead2.setImageBitmap(map2);
		chatHead2.setVisibility(View.INVISIBLE);

		WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params2.gravity = Gravity.TOP | Gravity.LEFT;
		params2.x = 400;
		params2.y = 400;

		windowManager.addView(chatHead2, params2);

		chatHead3 = new ImageView(this);
		Bitmap map3 = BitmapFactory.decodeResource(getResources(), R.drawable.aperture);
		chatHead3.setImageBitmap(map3);
		chatHead3.setVisibility(View.INVISIBLE);

		WindowManager.LayoutParams params3 = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params3.gravity = Gravity.TOP | Gravity.LEFT;
		params3.x = 600;
		params3.y = 600;

		windowManager.addView(chatHead3, params3);
	}

	public void showOtherChatHeads()
	{
		chatHead1.setVisibility(View.VISIBLE);
		chatHead2.setVisibility(View.VISIBLE);
		chatHead3.setVisibility(View.VISIBLE);
	}

	private void setOnClickListener()
	{
		chatHead.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showOtherChatHeads();
			}
		});
	}

	private void setOnTouchListener()
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
					v.performClick();
					return true;
				case MotionEvent.ACTION_MOVE:
					params.x = initialX + (int) (event.getRawX() - initialTouchX);
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(chatHead, params);
					return true;
				}
				return true;
			}
		});
	}
}
