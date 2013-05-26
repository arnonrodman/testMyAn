package com.example.cameraD;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	
	 // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.wedding, 
            R.drawable.vacation, 
            R.drawable.party,
            R.drawable.trip,
            R.drawable.birth,            
    };
    
    public Map<Integer,String> images;
    
 // Constructor
    public ImageAdapter(Context c){
        mContext = c;
        images = new HashMap<Integer, String>();
        images.put(R.drawable.wedding, "wedding");
        images.put(R.drawable.vacation, "vacation");
        images.put(R.drawable.party, "party");
        images.put(R.drawable.trip, "trip");
        images.put(R.drawable.birth, "birth");
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }
    
	


}
