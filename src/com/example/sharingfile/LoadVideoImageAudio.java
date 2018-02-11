package com.example.sharingfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class LoadVideoImageAudio extends Activity {
	String Tag;
	SharedPreferences fortabelpreference;
	String usertabel,value;
	
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	JSONParser jsonParser=new JSONParser();
	ProgressDialog progressDialog;
	CommonIpadd commonIpadd;
	
	JSONArray peoples=null;
	
	    String[] imageURLs;
	    Bitmap[] bitmaps;
	    String[] id;
	    
	   GridView listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_video_image_audio);
		
		listview=(GridView)findViewById(R.id.listView1);
		
		fortabelpreference=getApplicationContext().getSharedPreferences("TABELNAME", 0);
		usertabel=fortabelpreference.getString("tabel", null);
		
		cd=new ConnectionDetector(getApplicationContext());
		commonIpadd=new CommonIpadd();
		
		
		Intent intent=getIntent();
		value=intent.getStringExtra("value");
		
		
		
		if(value.equals("image")){
			Tag="loadimagevideoaudio";
			new imagecheck().execute();
			
		}
		else if(value.equals("video")){
		
		Tag="loadimagevideoaudio";
		new videocheck().execute();
					}
		
		else if(value.equals("audio")){
			
			Tag="loadimagevideoaudio";
			new Audiocheck().execute();
			
						}
		
	}

	
	
	  private Bitmap getImage(JSONObject jo){
	        URL url = null;
	        Bitmap image = null;
	        try {
	            url = new URL(jo.getString("pathurls"));
	            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return image;
	    }
	
	  
	  
	  @SuppressLint("NewApi") private Bitmap getVideoImage(JSONObject jo){
	        URL url = null;
	        Bitmap bitmap = null;
	      String VideoPath;
	        MediaMetadataRetriever mediaMetadataRetriever = null;
	        try {
	        	VideoPath=jo.getString("pathurls");
		            //bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
	            mediaMetadataRetriever = new MediaMetadataRetriever();
	            if (Build.VERSION.SDK_INT >= 14)
	                // no headers included
	                mediaMetadataRetriever.setDataSource(VideoPath, new HashMap<String, String>());
	            else
	                mediaMetadataRetriever.setDataSource(VideoPath);
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
	  
	
	
	
class imagecheck extends AsyncTask<String, String, String>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(LoadVideoImageAudio.this);
			progressDialog.setMessage("Logging In..");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("tag", Tag));
				param.add(new BasicNameValuePair("usertabelname", usertabel));
				param.add(new BasicNameValuePair("typecontent", value));
			
				
				
			
			
				
				JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
				try 
				{
					//final String Result=json.getString("result");
					 peoples=json.getJSONArray("result");
					 
					  bitmaps = new Bitmap[peoples.length()];
					     
				        imageURLs = new String[peoples.length()];
				       
				    id=new String[peoples.length()];
				 
					
					 
					  for(int i=0;i<peoples.length();i++){
			                JSONObject c = peoples.getJSONObject(i);
			                
			              
			              
			               id[i] = c.getString("id");
			             imageURLs[i] = c.getString("pathurls");
			              
			              
			              
			             //  bitmaps[i]=getImage(c);
			              
			               
			            }
					
					//with in doInBackground we must use runOnUiThread for display toast
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							//Toast.makeText(getApplicationContext(), " "+peoples, Toast.LENGTH_LONG).show();
							
						}
					});
					
				}
				catch (JSONException e) 
				{
						e.printStackTrace();
				}
				
				
		return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			
		   CustomList customList = new CustomList(LoadVideoImageAudio.this,imageURLs,id);
            listview.setAdapter(customList);
			
		
		}
	
	
	}


class videocheck extends AsyncTask<String, String, String>{
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(LoadVideoImageAudio.this);
		progressDialog.setMessage("Logging In..");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("tag", Tag));
			param.add(new BasicNameValuePair("usertabelname", usertabel));
			param.add(new BasicNameValuePair("typecontent", value));
		
			
			
		
		
			
			JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
			try 
			{
				//final String Result=json.getString("result");
				 peoples=json.getJSONArray("result");
				 
				  bitmaps = new Bitmap[peoples.length()];
				     
			        imageURLs = new String[peoples.length()];
			       
			    id=new String[peoples.length()];
			 
				
				 
				  for(int i=0;i<peoples.length();i++){
		                JSONObject c = peoples.getJSONObject(i);
		                
		              
		              
		               id[i] = c.getString("id");
		             imageURLs[i] = c.getString("pathurls");
		              
		              
		              
		             //  bitmaps[i]=getVideoImage(c);
		              
		               
		            }
				
				//with in doInBackground we must use runOnUiThread for display toast
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), " "+peoples, Toast.LENGTH_LONG).show();
						
					}
				});
				
			}
			catch (JSONException e) 
			{
					e.printStackTrace();
			}
			
			
	return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
		
	   CustomVideo customList = new CustomVideo(LoadVideoImageAudio.this,imageURLs,id);
        listview.setAdapter(customList);
		
	
	}


}




class Audiocheck extends AsyncTask<String, String, String>{
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(LoadVideoImageAudio.this);
		progressDialog.setMessage("Logging In..");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {
		ArrayList<NameValuePair> param=new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("tag", Tag));
			param.add(new BasicNameValuePair("usertabelname", usertabel));
			param.add(new BasicNameValuePair("typecontent", value));
		
			
			
		
		
			
			JSONObject json=jsonParser.makeHttpRequest(commonIpadd.url, "POST", param);
			try 
			{
				//final String Result=json.getString("result");
				 peoples=json.getJSONArray("result");
				 
				  bitmaps = new Bitmap[peoples.length()];
				     
			        imageURLs = new String[peoples.length()];
			       
			    id=new String[peoples.length()];
			 
				
				 
				  for(int i=0;i<peoples.length();i++){
		                JSONObject c = peoples.getJSONObject(i);
		                
		              
		              
		               id[i] = c.getString("id");
		             imageURLs[i] = c.getString("pathurls");
		              
		              
		              
		             //  bitmaps[i]=getVideoImage(c);
		              
		               
		            }
				
				//with in doInBackground we must use runOnUiThread for display toast
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), " "+peoples, Toast.LENGTH_LONG).show();
						
					}
				});
				
			}
			catch (JSONException e) 
			{
					e.printStackTrace();
			}
			
			
	return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
		
	   CustomAudio customList = new CustomAudio(LoadVideoImageAudio.this,imageURLs,id);
        listview.setAdapter(customList);
		
	
	}


}



}
