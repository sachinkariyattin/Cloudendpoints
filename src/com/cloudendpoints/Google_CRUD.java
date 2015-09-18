package com.cloudendpoints;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Google_CRUD extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_oper);
		
		final Button addButton=(Button) findViewById(R.id.button1);
		final Button listButton=(Button) findViewById(R.id.button2);
		
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent pushIntent=new Intent(Google_CRUD.this, MainActivity.class);
				startActivity(pushIntent);
				
			}
		});
		
		listButton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent listIntent=new Intent(Google_CRUD.this, DisplayItems.class);
				startActivity(listIntent);
			}
		});
	}


}
