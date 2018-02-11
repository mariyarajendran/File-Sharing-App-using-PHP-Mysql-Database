package com.example.sharingfile;

import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{
	 
	  private String[] urls;
	
	  private String[] idd;
	    private Bitmap[] bitmaps;
	    private Activity context;
	    int xDim, yDim;	
	   Context context2;
	
	 
	
	    public CustomList(Activity context, String[] urls,String[] idd) {
	        super(context, R.layout.image_list_view, urls);
	        this.context = context;
	       
	        this.urls= urls;
	        this.bitmaps= bitmaps;
	       
	        this.idd=idd;
	   
	    }
	

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
	        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.txtserver);
	        TextView texturl= (TextView) listViewItem.findViewById(R.id.txturl);
	      
	       TextView ids = (TextView) listViewItem.findViewById(R.id.ids);
	        
	        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageserver);
	      
	        textViewURL.setText(urls[position].substring(urls[position].lastIndexOf("/")+1));
	        
	        texturl.setText(urls[position]);
	        ids.setText(idd[position]);
	       /* Bitmap bmThumbnail;
	        bmThumbnail = ThumbnailUtils.createVideoThumbnail(urls[position], Thumbnails.MICRO_KIND);
	        image.setImageBitmap(bmThumbnail);*/
	       // getResizedBitmap(bitmaps[position], 100, 100);
	        
	        Glide.with(context)
	        
            .load((urls[position]))
           
             .placeholder(R.drawable.place) // can also be a drawable
    .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image);
	        
	      //image.setImageBitmap(bitmaps[position]);
	        
	     
	        
	      //  image.setImageBitmap(
	       // 	    decodeSampledBitmapFromResource(Resources,bitmaps[position] , 100, 100));
	     
	  
	       
	       
	        
	        return  listViewItem;
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
		
		
	    
	    
}
