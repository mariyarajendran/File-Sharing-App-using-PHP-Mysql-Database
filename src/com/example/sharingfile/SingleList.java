package com.example.sharingfile;

import java.util.HashMap;

import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

public class SingleList extends Activity {
ImageView imageView;
Bitmap bm;
//String path = "/sdcard/video/DD.mp4";
String path = "http://10.0.2.2/MovieLogin/uploads/9.mp4";
ProgressDialog progressDialog;


	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_list);
		
		imageView=(ImageView)findViewById(R.id.imageView1);
				

		new check().execute();
	}
	
	

	
	class check	extends AsyncTask<Void, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(SingleList.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@SuppressLint("NewApi") @Override
	    protected Bitmap doInBackground(Void... params) {
	        Bitmap bitmap = null;
	        String videoPath = "http://10.0.2.2/MovieLogin/uploads/9.mp4";
	        MediaMetadataRetriever mediaMetadataRetriever = null;
	        try {
	            mediaMetadataRetriever = new MediaMetadataRetriever();
	            if (Build.VERSION.SDK_INT >= 14)
	                // no headers included
	                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
	            else
	                mediaMetadataRetriever.setDataSource(videoPath);
	            //   mediaMetadataRetriever.setDataSource(videoPath);
	            bitmap = mediaMetadataRetriever.getFrameAtTime();
	        } catch (Exception e) {
	            e.printStackTrace();

	        } finally {
	            if (mediaMetadataRetriever != null) 
	                mediaMetadataRetriever.release();
	        }
	        return bitmap;
	    }

	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	        super.onPostExecute(bitmap);
	      
	       
	        if (bitmap != null)
	            ((ImageView) findViewById(R.id.imageView1)).setImageBitmap(bitmap);
	            
	        progressDialog.dismiss();
	            
	        
	        
	     
	        
	    }
	}
	

	}
