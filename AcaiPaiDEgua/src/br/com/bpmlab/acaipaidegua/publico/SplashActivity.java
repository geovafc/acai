package br.com.bpmlab.acaipaidegua.publico;

import br.com.bpmlab.acaipaidegua.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity implements Runnable {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler splashScreen  = new Handler();
		splashScreen.postDelayed(SplashActivity.this, 2000);
		
		 ActionBar actionBar = getActionBar();

	        if (actionBar != null){
	            actionBar.hide();
	        }

		
	}

	@Override
	public void run() {
		startActivity(new Intent(SplashActivity.this, MainActivity.class));
		finish();
	}

	
}
