package com.utopiadevelopers.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
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
	private static String TAG = "chatHead";
	private WindowManager windowManager;
	private WindowManager.LayoutParams chatHeadParams;
	private WindowManager.LayoutParams closeParams;

	private TextView chatHeadIcon;
	private TextView closeIcon;

	private LinearLayout chatHeadView;
	private LinearLayout closeView;

	private int size;
	private int screenW;
	private int screenY;
	private int textsize;

	private final int closeViewMutipler = 3;
	private int closeX;
	private int closeY;
	private int CLOSE_THRESOLD;

	private Handler mAnimationHandler = new Handler();

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate()
	{
		super.onCreate();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		screenW = windowManager.getDefaultDisplay().getWidth();
		screenY = windowManager.getDefaultDisplay().getHeight();

		size = screenW / 7;
		CLOSE_THRESOLD = (int) (1.2 * size);
		textsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 28, getResources().getDisplayMetrics());

		createChatHead();
		setOnTouchListener();
		setOnClickListener();
	}

	public void closeChatHeads()
	{
		try
		{
			if (chatHeadView != null)
				windowManager.removeView(chatHeadView);
			if (closeView != null)
				windowManager.removeView(closeView);
		}
		catch (Exception e)
		{

		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		closeChatHeads();
	}

	public void logService(String message)
	{
		Log.d(TAG, message);
	}

	// Chat Head Functions

	private void createChatHead()
	{
		// Center Point of Close Icon
		closeX = screenW / 2;
		closeY = (int) (screenY - (closeViewMutipler / 2 * size) - size);

		/*
		 * Parameters and Font Initialization
		 */

		// Font
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/webdings.ttf");

		// Center Params
		LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		centerParams.gravity = Gravity.CENTER;

		// ChatHead Parameters (size,size)
		LinearLayout.LayoutParams roundParams = new LinearLayout.LayoutParams(size, size);

		// Bottom Layout Parameters
		LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, closeViewMutipler * size);
		bottomParams.gravity = Gravity.BOTTOM;

		// Close Layout Parameters
		closeParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, closeViewMutipler * size, WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		closeParams.gravity = Gravity.BOTTOM;

		// Main Chat Head Parameters
		chatHeadParams = new WindowManager.LayoutParams(size, size, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		chatHeadParams.gravity = Gravity.TOP | Gravity.LEFT;

		closeView = new LinearLayout(this);
		closeView.setLayoutParams(centerParams);
		closeView.setBackgroundResource(R.drawable.close_view_grad);
		closeView.setGravity(Gravity.CENTER);
		closeView.setVisibility(View.INVISIBLE);

		LinearLayout crossView = new LinearLayout(this);
		crossView.setLayoutParams(roundParams);
		crossView.setBackgroundResource(R.drawable.round_drawable_white);

		closeIcon = new TextView(this);
		closeIcon.setTypeface(font);
		closeIcon.setText("X");
		closeIcon.setTextSize(textsize);
		closeIcon.setTextColor(Color.BLACK);
		closeIcon.setGravity(Gravity.CENTER);
		closeIcon.setLayoutParams(roundParams);
		closeView.setVisibility(View.INVISIBLE);

		crossView.addView(closeIcon);
		closeView.addView(crossView);
		windowManager.addView(closeView, closeParams);

		chatHeadView = new LinearLayout(this);
		chatHeadView.setLayoutParams(roundParams);
		chatHeadView.setBackgroundResource(R.drawable.round_drawable);

		chatHeadIcon = new TextView(this);
		chatHeadIcon.setTypeface(font);
		chatHeadIcon.setText("Q");
		chatHeadIcon.setTextColor(Color.WHITE);
		chatHeadIcon.setTextSize(textsize);
		chatHeadIcon.setGravity(Gravity.CENTER);
		chatHeadIcon.setLayoutParams(centerParams);

		chatHeadView.addView(chatHeadIcon);
		windowManager.addView(chatHeadView, chatHeadParams);
	}

	public void showMainChatHead()
	{
		if ((chatHeadParams.x + size / 2) < (screenW / 2))
		{
			chatHeadParams.x = 0;
		}
		else
		{
			chatHeadParams.x = screenW - size;
		}
		windowManager.updateViewLayout(chatHeadView, chatHeadParams);
	}

	public void showCross()
	{
		closeView.setVisibility(View.VISIBLE);
	}

	public void hideCross()
	{
		closeView.setVisibility(View.INVISIBLE);
	}

	private void setOnClickListener()
	{
		chatHeadView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Handle Touch
			}
		});
	}

	private void setOnTouchListener()
	{
		chatHeadView.setOnTouchListener(new View.OnTouchListener()
		{
			private int initialX;
			private int initialY;
			private float initialTouchX;
			private float initialTouchY;
			private boolean isClicked;

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					isClicked = true;
					initialX = chatHeadParams.x;
					initialY = chatHeadParams.y;
					initialTouchX = event.getRawX();
					initialTouchY = event.getRawY();
					return true;
				case MotionEvent.ACTION_UP:
					if (isClicked)
					{
						showMainChatHead();
						v.performClick();
					}
					else
					{
						if (!handleCloseIf())
						{
							showMainChatHead();
						}
						else
						{
							closeChatHeads();
						}
					}
					hideCross();
					return true;
				case MotionEvent.ACTION_MOVE:
					if (isClicked && (Math.abs(initialTouchX - event.getRawX()) > 15 || Math.abs(initialTouchY) - event.getRawY() > 15))
					{
						showCross();
						isClicked = false;
					}
					chatHeadParams.x = initialX + (int) (event.getRawX() - initialTouchX);
					chatHeadParams.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(chatHeadView, chatHeadParams);
					return true;
				}
				return true;
			}
		});
	}

	private boolean handleCloseIf()
	{
		int checkX = chatHeadParams.x + size / 2;
		int checkY = chatHeadParams.y + size / 2;

		if (Math.abs(checkX - closeX) < CLOSE_THRESOLD && Math.abs(checkY - closeY) < CLOSE_THRESOLD)
		{
			return true;
		}
		return false;
	}

	/*
	 * Animation Classes
	 */

	private class ChatHeadAnimationTimerTask extends TimerTask
	{
		// Ultimate destination coordinates toward which the tray will move
		int mInitX;
		int mInitY;
		int mDestX;
		int mDestY;

		WindowManager.LayoutParams params;
		View v;
		Timer t;

		public ChatHeadAnimationTimerTask(int x1, int y1, int x2, int y2, WindowManager.LayoutParams params, View v, Timer t)
		{
			mInitX = x1;
			mInitY = y2;

			mDestX = x2;
			mDestY = y2;

			this.params = params;
			this.v = v;
			this.t = t;

			params.x = mInitX;
			params.y = mInitY;
		}

		@Override
		public void run()
		{

			mAnimationHandler.post(new Runnable()
			{

				@Override
				public void run()
				{
					params.x = (2 * (params.x - mDestX)) / 3 + mDestX;
					params.y = (2 * (params.y - mDestY)) / 3 + mDestY;

					if (params.x < 0 || params.x > screenW)
					{
						params.x = mDestX;
					}

					if (params.y < 0 || params.x > screenY)
					{
						params.y = mDestY;
					}
					windowManager.updateViewLayout(v, params);

					// Cancel animation when the destination is reached
					if (Math.abs(params.x - mDestX) < 2 && Math.abs(params.y - mDestY) < 2)
					{
						ChatHeadAnimationTimerTask.this.cancel();
						t.cancel();
					}
				}
			});
		}
	}

}
