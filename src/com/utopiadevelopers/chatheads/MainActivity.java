package com.utopiadevelopers.chatheads;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener
{
	public Button start;
	public Button stop;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		start = (Button) findViewById(R.id.btnStart);
		stop = (Button) findViewById(R.id.btnStop);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btnStart:
			start.setEnabled(false);
			stop.setEnabled(true);
			break;
		case R.id.btnStop:
			start.setEnabled(true);
			stop.setEnabled(false);
			break;
		default:
			break;
		}
	}
}
