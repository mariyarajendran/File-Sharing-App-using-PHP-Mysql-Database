package com.example.sharingfile;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Uplaodvideofile extends Activity implements OnClickListener {
	
	 private Button video,audio,image,files;
	    private Button buttonUpload,grid;
	    private TextView textView;
	    private TextView textViewResponse;
	    
	    SharedPreferences fortabelpreference;
	   
		
		 ConnectionDetector cd;
			Boolean isInternetPresent=false;
			JSONParser jsonParser=new JSONParser();
			ProgressDialog progressDialog;
			CommonIpadd commonIpadd;
			String Tag,pathurls,urlstype=null,usertabel,tabel;
	 
	    private static final int SELECT_VIDEO = 3,SELECT_AUDIO = 2,SELECT_IMAGE = 1,SELECT_FILE=4;
	 
	    private String selectedPath,msg=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uplaodvideofile);
		
		video= (Button) findViewById(R.id.video);
		audio= (Button) findViewById(R.id.audio);
		image= (Button) findViewById(R.id.imagess);
		files=(Button)findViewById(R.id.files);
	        buttonUpload = (Button) findViewById(R.id.buttonUpload);
	        grid=(Button)findViewById(R.id.grid);
	 
	        textView = (TextView) findViewById(R.id.textView);
	        textViewResponse = (TextView) findViewById(R.id.textViewResponse);
	 
	       video.setOnClickListener(this);
	       audio.setOnClickListener(this);
	       image.setOnClickListener(this);
	        buttonUpload.setOnClickListener(this);
	        grid.setOnClickListener(this);
	        files.setOnClickListener(this);
	        
	    	cd=new ConnectionDetector(getApplicationContext());
			commonIpadd=new CommonIpadd();
			
			fortabelpreference=getApplicationContext().getSharedPreferences("TABELNAME", 0);
			usertabel=fortabelpreference.getString("tabel", null);
			
			Toast.makeText(getApplicationContext(), usertabel, Toast.LENGTH_SHORT).show();
		
	}
	
	
	 private void chooseVideo() {
	        Intent intent = new Intent();
	        intent.setType("video/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT);
	        startActivityForResult(Intent.createChooser(intent, "Select a Audio "), SELECT_VIDEO);
	        
	        urlstype="video";
	        Toast.makeText(getApplicationContext(), urlstype, Toast.LENGTH_SHORT).show();
	    }
	 
	 private void chooseAudio() {
	        Intent intent = new Intent();
	        intent.setType("audio/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT);
	        startActivityForResult(Intent.createChooser(intent, "Select a Audio "), SELECT_AUDIO);
	        urlstype="audio";
	        Toast.makeText(getApplicationContext(), urlstype, Toast.LENGTH_SHORT).show();
	    }
	 
	 private void chooseImage() {
	        Intent intent = new Intent();
	        intent.setType("image/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT);
	        startActivityForResult(Intent.createChooser(intent, "Select a Audio "), SELECT_IMAGE);
	        
	        urlstype="image";
	        Toast.makeText(getApplicationContext(), urlstype, Toast.LENGTH_SHORT).show();
	    }
	 
	 private void chooseFiles() {
	        Intent intent = new Intent();
	        intent.setType("file/*");
	        intent.setAction(Intent.ACTION_GET_CONTENT);
	        startActivityForResult(Intent.createChooser(intent, "Select a Audio "), SELECT_FILE);
	        
	        urlstype="file";
	        Toast.makeText(getApplicationContext(), urlstype, Toast.LENGTH_SHORT).show();
	    }
	 
	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_VIDEO) {
	                System.out.println("SELECT_VIDEO");
	                Uri selectedImageUri = data.getData();
	                selectedPath = getVideoPath(selectedImageUri);
	                textView.setText(selectedPath);
	            }
	            
	            else if(requestCode==SELECT_AUDIO){
	            	 System.out.println("SELECT_VIDEO");
		                Uri selectedImageUri = data.getData();
		                try {
							selectedPath = getFilePath(this,selectedImageUri);
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "unable to upload this audio", Toast.LENGTH_SHORT).show();
						}
		                textView.setText(selectedPath);
	            }
	            
	            else if(requestCode==SELECT_IMAGE){
	            	 System.out.println("SELECT_VIDEO");
		                Uri selectedImageUri = data.getData();
		                selectedPath = getImagePath(selectedImageUri);
		                textView.setText(selectedPath);
	            }
	            
	            else if(requestCode==SELECT_FILE){
	            	 System.out.println("SELECT_VIDEO");
		                Uri selectedImageUri = data.getData();
		                try {
							selectedPath = getFilePath(this,selectedImageUri);
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
						}
		                textView.setText(selectedPath);
	            }
	        }
	    }
	 
	    public String getVideoPath(Uri uri) {
	        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
	        cursor.moveToFirst();
	        String document_id = cursor.getString(0);
	        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
	        cursor.close();
	 
	        cursor = getContentResolver().query(
	                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
	                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
	        cursor.moveToFirst();
	        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
	        cursor.close();
	 
	        return path;
	    }
	    
	    
	    public String getAudioPath(Uri uri) {
	        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
	        cursor.moveToFirst();
	        String document_id = cursor.getString(0);
	        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
	        cursor.close();
	 
	        cursor = getContentResolver().query(
	                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
	        cursor.moveToFirst();
	        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
	        cursor.close();
	 
	        return path;
	    }
	    
	    
	    public String getImagePath(Uri uri) {
	        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
	        cursor.moveToFirst();
	        String document_id = cursor.getString(0);
	        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
	        cursor.close();
	 
	        cursor = getContentResolver().query(
	                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
	        cursor.moveToFirst();
	        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
	        cursor.close();
	 
	        return path;
	    }
	    
	    
	    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
	        if ("content".equalsIgnoreCase(uri.getScheme())) {
	            String[] projection = { "_data" };
	            Cursor cursor = null;

	            try {
	                cursor = context.getContentResolver().query(uri, projection, null, null, null);
	                int column_index = cursor.getColumnIndexOrThrow("_data");
	                if (cursor.moveToFirst()) {
	                    return cursor.getString(column_index);
	                }
	            } catch (Exception e) {
	                // Eat it
	            }
	        }
	        else if ("file".equalsIgnoreCase(uri.getScheme())) {
	            return uri.getPath();
	        }

	        return null;
	    } 
	    
	 
	    private void uploadVideo() {
	        class UploadVideo extends AsyncTask<Void, Void, String> {
	 
	            ProgressDialog uploading;
	 
	            @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	                uploading = ProgressDialog.show(Uplaodvideofile.this, "Uploading File", "Please wait...", false, false);
	            }
	 
	            @Override
	            protected void onPostExecute(String s) {
	                super.onPostExecute(s);
	                uploading.dismiss();
	                textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
	                textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
	             
	                if(msg.equals("Upload-Failed")){
	               }
	                else{
	                	
	                	Tag="inserturl";
	                	
	                	//pathurls="http://"+s.substring(11);
	                	pathurls=s;
	                	 new check().execute();
	                }
	            }
	 
	            @Override
	            protected String doInBackground(Void... params) {
	                Upload u = new Upload();
	               msg = u.uploadVideo(selectedPath);
	                return msg;
	                
	              
	                
	            }
	            
	            
	        }
	        UploadVideo uv = new UploadVideo();
	        uv.execute();
	        
	       
        
	    }
	
	
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 if (v == video) {
	            chooseVideo();
	        }
	        if (v == buttonUpload) {
	            uploadVideo();
	            
	  
	            
	        }
	        if (v == audio) {
	            chooseAudio();
	        }
	        
	        if (v == image) {
	            chooseImage();
	            
	        }
	        
	        if(v==grid){
	        	Intent intent=new Intent(Uplaodvideofile.this,AllViews.class);
	        	startActivity(intent);
	        }
	        
	        if (v == files) {
	            chooseFiles();
	            
	        }
	        
	}

	
class check extends AsyncTask<String, String, String>{
		
    	

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("pathurls", pathurls));
				param.add(new BasicNameValuePair("urlstype", urlstype));
				param.add(new BasicNameValuePair("usertabel",usertabel ));
				
			
				
				JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
				try 
				{
					final String Result=json.getString("result" );
				
					
runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
							
						}
					});
					
					
				}
				catch (JSONException e) 
				{
						e.printStackTrace();
				}
				
				
		return null;
		}
		
		
	
	
	}

	
	

}
