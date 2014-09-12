package com.utopiadevelopers.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utopiadevelopers.chatheads.R;

public class ChatHeadService extends Service
{
	private static String TAG = "checkinService";
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	private WindowManager.LayoutParams params1;
	private WindowManager.LayoutParams params2;
	private WindowManager.LayoutParams params3;

	private TextView checkinIcon;
	private TextView infoIcon;
	private TextView cameraIcon;
	private TextView reviewIcon;

	private LinearLayout checkinView;
	private LinearLayout infoView;
	private LinearLayout cameraView;
	private LinearLayout reviewView;

	private int size;
	private int screenW;
	private int screenY;

	private boolean arecheckinsOpen = false;

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

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		screenW = windowManager.getDefaultDisplay().getWidth();
		screenY = windowManager.getDefaultDisplay().getHeight();

		size = screenW / 7;

		createOthercheckins();
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
		if (checkinIcon != null)
		{
			windowManager.removeView(checkinView);
			windowManager.removeView(infoView);
			windowManager.removeView(cameraView);
			windowManager.removeView(reviewView);
		}
	}

	public void logService(String message)
	{
		Log.d(TAG, message);
	}

	// Chat Head Functions

	private void createOthercheckins()
	{
		int textsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 28, getResources().getDisplayMetrics());
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/zombats-app.ttf");

		LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		tvParams.gravity = Gravity.CENTER;

		LinearLayout.LayoutParams lpParams = new LinearLayout.LayoutParams(size, size);

		checkinView = new LinearLayout(this);
		checkinView.setLayoutParams(lpParams);
		checkinView.setBackgroundResource(R.drawable.round_drawable);

		checkinIcon = new TextView(this);
		checkinIcon.setTypeface(font);
		checkinIcon.setText("P");
		checkinIcon.setTextColor(Color.WHITE);
		checkinIcon.setTextSize(textsize);
		checkinIcon.setGravity(Gravity.CENTER);
		checkinIcon.setLayoutParams(tvParams);

		params = new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;

		checkinView.addView(checkinIcon);
		windowManager.addView(checkinView, params);

		infoView = new LinearLayout(this);
		infoView.setLayoutParams(lpParams);
		infoView.setBackgroundResource(R.drawable.round_drawable_grey);
		infoView.setVisibility(View.INVISIBLE);

		infoIcon = new TextView(this);
		infoIcon.setTypeface(font);
		infoIcon.setTextColor(Color.WHITE);
		infoIcon.setText(",");
		infoIcon.setTextSize(textsize);
		infoIcon.setGravity(Gravity.CENTER);
		infoIcon.setLayoutParams(tvParams);

		params1 = new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params1.gravity = Gravity.TOP | Gravity.LEFT;

		infoView.addView(infoIcon);
		windowManager.addView(infoView, params1);

		cameraView = new LinearLayout(this);
		cameraView.setLayoutParams(lpParams);
		cameraView.setBackgroundResource(R.drawable.round_drawable_grey);
		cameraView.setVisibility(View.INVISIBLE);

		cameraIcon = new TextView(this);
		cameraIcon.setTypeface(font);
		cameraIcon.setText("|");
		cameraIcon.setTextColor(Color.WHITE);
		cameraIcon.setTextSize(textsize);
		cameraIcon.setGravity(Gravity.CENTER);
		cameraIcon.setLayoutParams(tvParams);

		params2 = new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params2.gravity = Gravity.TOP | Gravity.LEFT;

		cameraView.addView(cameraIcon);
		windowManager.addView(cameraView, params2);

		reviewView = new LinearLayout(this);
		reviewView.setLayoutParams(lpParams);
		reviewView.setBackgroundResource(R.drawable.round_drawable_grey);
		reviewView.setVisibility(View.INVISIBLE);

		reviewIcon = new TextView(this);
		reviewIcon.setTypeface(font);
		reviewIcon.setText("J");
		reviewIcon.setTextSize(textsize);
		reviewIcon.setTextColor(Color.WHITE);
		reviewIcon.setGravity(Gravity.CENTER);
		reviewIcon.setLayoutParams(tvParams);

		params3 = new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		params3.gravity = Gravity.TOP | Gravity.LEFT;

		reviewView.addView(reviewIcon);
		windowManager.addView(reviewView, params3);
	}

	public void showMaincheckin()
	{
		if (params.x < screenW / 2 - size)
		{
			params.x = 0;
		}
		else
		{
			params.x = screenW - size;
		}
		windowManager.updateViewLayout(checkinView, params);
	}

	public void showOthercheckins()
	{
		int GAP = 2 * size;
		if (params.x < screenW / 2)
		{
			params1.x = params.x + 2 * GAP;
			params1.y = params.y - GAP;

			params2.x = params.x + GAP;
			params2.y = params.y;

			params3.x = params.x + 2 * GAP;
			params3.y = params.y + GAP;
		}
		else
		{
			params1.x = params.x - (int) 1.44 * GAP;
			params1.y = params.y - (int) 1.44 * GAP;

			params2.x = params.x - GAP;
			params2.y = params.y;

			params3.x = params.x - (int) 1.44 * GAP;
			params3.y = params.y + (int) 1.44 * GAP;
		}
		windowManager.updateViewLayout(infoView, params1);
		windowManager.updateViewLayout(cameraView, params2);
		windowManager.updateViewLayout(reviewView, params3);

		infoView.setVisibility(View.VISIBLE);
		cameraView.setVisibility(View.VISIBLE);
		reviewView.setVisibility(View.VISIBLE);

		arecheckinsOpen = true;

		checkinIcon.setText("X");
	}

	public void hideOthercheckins()
	{
		infoView.setVisibility(View.INVISIBLE);
		cameraView.setVisibility(View.INVISIBLE);
		reviewView.setVisibility(View.INVISIBLE);

		arecheckinsOpen = false;

		checkinIcon.setText("P");
	}

	private void setOnClickListener()
	{
		checkinIcon.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!arecheckinsOpen)
				{
					showOthercheckins();
				}
				else
				{
					hideOthercheckins();
				}
			}
		});
	}

	private void setOnTouchListener()
	{
		checkinIcon.setOnTouchListener(new View.OnTouchListener()
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
					boolean isClick = false;
					if (Math.abs(initialX - params.x) < 15 && Math.abs(initialY - params.y) < 15)
					{
						isClick = true;
					}
					showMaincheckin();
					if (isClick)
					{
						v.performClick();
					}
					return true;
				case MotionEvent.ACTION_MOVE:
					if (arecheckinsOpen)
						hideOthercheckins();
					params.x = initialX + (int) (event.getRawX() - initialTouchX);
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(checkinView, params);
					return true;
				}
				return true;
			}
		});
	}
}
