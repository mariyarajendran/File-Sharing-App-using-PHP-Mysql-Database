package com.example.sharingfile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;

public class Getallimagess {
	  
	 public static String[] imageURLs;
	    public static Bitmap[] bitmaps;
	  
	    public static String[] id;
	 
	  //  public static final String JSON_ARRAY="result";
	  //  public static final String IMAGE_URL = "url";
	    private JSONArray urls;
	
	
	    public Getallimagess(String json){
	        try {
	            JSONObject jsonObject = new JSONObject(json);
	            urls = jsonObject.getJSONArray("result");
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    
	    private Bitmap getImage(JSONObject jo){
	        URL url = null;
	        Bitmap image = null;
	        try {
	            url = new URL(jo.getString("url"));
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
	        	VideoPath=jo.getString("url");
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
	    
	    
	    public void getAllImages() throws JSONException {
	        bitmaps = new Bitmap[urls.length()];
	     
	        imageURLs = new String[urls.length()];
	       
	    id=new String[urls.length()];
	 
	        for(int i=0;i<urls.length();i++){
	        	 
	            imageURLs[i] = urls.getJSONObject(i).getString("url");
	      
	        
	           id[i]=urls.getJSONObject(i).getString("id");
	            JSONObject jsonObject = urls.getJSONObject(i);
	            bitmaps[i]=getVideoImage(jsonObject);
	        }
	    }
	    
	    
	    public void getAllVideoImages() throws JSONException {
	        bitmaps = new Bitmap[urls.length()];
	     
	        imageURLs = new String[urls.length()];
	       
	    id=new String[urls.length()];
	 
	        for(int i=0;i<urls.length();i++){
	        	 
	            imageURLs[i] = urls.getJSONObject(i).getString("url");
	      
	        
	           id[i]=urls.getJSONObject(i).getString("id");
	            JSONObject jsonObject = urls.getJSONObject(i);
	            bitmaps[i]=getImage(jsonObject);
	        }
	    }
	    

}
