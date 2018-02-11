package com.example.sharingfile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AllViews extends Activity {
Button image,video,audio;
SharedPreferences fortabelpreference;
String usertabel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_views);
		
		fortabelpreference=getApplicationContext().getSharedPreferences("TABELNAME", 0);
		usertabel=fortabelpreference.getString("tabel", null);
		
		image=(Button)findViewById(R.id.image);
		video=(Button)findViewById(R.id.video);
		audio=(Button)findViewById(R.id.audio);
		
		
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllViews.this,LoadVideoImageAudio.class);
				intent.putExtra("value","image");
				startActivity(intent);
			}
		});
		
		video.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllViews.this,LoadVideoImageAudio.class);
				intent.putExtra("value","video");
				startActivity(intent);
			}
		});
		
		audio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AllViews.this,LoadVideoImageAudio.class);
				intent.putExtra("value","audio");
				
				startActivity(intent);
			}
		});
		
	}



}
