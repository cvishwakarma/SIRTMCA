package com.vnetsoft.sirtmca.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar mActionBar = getActionBar();
		mActionBar.hide();
		
		new android.os.Handler().postDelayed(new Runnable()
		{

			@Override
			public void run() {
				Intent intent = new Intent(getBaseContext(),LoginSIRTMCA.class);
				startActivity(intent);
				
			}
			
		}, 2000);
	}
	public void onStop()
	{
		super.onStop();
		this.finish();
		
	}
	

}
