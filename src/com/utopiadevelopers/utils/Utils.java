package com.utopiadevelopers.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

public class Utils
{
	public static Bitmap loadMaskedBitmap(Bitmap bmap, int containerHeight, int containerWidth)
	{

		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;

		int photoH = bmOptions.outHeight;
		int photoW = bmOptions.outWidth;

		int scaleFactor = Math.min(photoH / containerHeight, photoW / containerHeight);
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap targetBitmap = getCircularBitmapWithWhiteBorder(bmap, 0);
		Canvas canvas = new Canvas(targetBitmap);

		final int color = Color.BLACK;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bmap.getWidth(), bmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setShadowLayer(5.5f, 2.0f, 4.0f, Color.BLACK);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawOval(rectF, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bmap, 0, 0, paint);
		bmap.recycle();

		return targetBitmap;
	}

	public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap, int borderWidth)
	{
		if (bitmap == null || bitmap.isRecycled())
		{
			return null;
		}
		final int width = bitmap.getWidth() + borderWidth;
		final int height = bitmap.getHeight() + borderWidth;

		Bitmap canvasBitmap = Bitmap.createBitmap(width + 8, height + 8, Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		Canvas canvas = new Canvas(canvasBitmap);
		float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;

		canvas.drawCircle((width / 2), (height / 2), radius - 4.0f, paint);
		canvas.drawCircle((width / 2), (height / 2), (radius - borderWidth / 2), paint);
		return canvasBitmap;
	}

	public static int dpToPixels(int dp, Resources res)
	{
		return (int) (res.getDisplayMetrics().density * dp + 0.5f);
	}
}
