package com.jokes.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectAd extends Activity {
	private Button nativeBtn, webBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_adtype);
		nativeBtn = (Button) findViewById(R.id.nativeStream);
		webBtn = (Button) findViewById(R.id.webStream);

		nativeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(SelectAd.this, JokesMainActivity.class);
				 startActivity(intent);
			}
		});

		webBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(SelectAd.this, WebStream.class);
				 startActivity(intent);
			}
		});
	}

}
