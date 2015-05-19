package com.example.imageviewing;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	public final static int DISPLAYWIDTH = 200;
	public final static int DISPLAYHEIGHT = 200;
	TextView titleTextView;
	ImageButton imageButton;
	Cursor cursor;
	Bitmap bmp;
	String imageFilePath;
	int fileColumn;
	int titleColumn;
	int displayColumn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		titleTextView = (TextView) this.findViewById(R.id.TitleTextView);
		imageButton = (ImageButton) this.findViewById(R.id.ImageButton);
		
		String[] columns = { Media.DATA, Media._ID, Media.TITLE, Media.DISPLAY_NAME };
		cursor = managedQuery(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
		
		fileColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
		displayColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
		
		if (cursor.moveToFirst()) {
			//titleTextView.setText(cursor.getString(titleColumn));
			titleTextView.setText(cursor.getString(displayColumn));
			imageFilePath = cursor.getString(fileColumn);
			bmp = getBitmap(imageFilePath);
			// Display it
			imageButton.setImageBitmap(bmp);
			}
		
		imageButton.setOnClickListener(
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (cursor.moveToNext())
						{
							//titleTextView.setText(cursor.getString(titleColumn));
							titleTextView.setText(cursor.getString(displayColumn));
							imageFilePath = cursor.getString(fileColumn);
							bmp = getBitmap(imageFilePath);
							imageButton.setImageBitmap(bmp);
						}
					}
				});
		
		
	}

	private Bitmap getBitmap(String imageFilePath) {
		BitmapFactory.Options bmpfactoryoption = new BitmapFactory.Options();
		bmpfactoryoption.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(imageFilePath,bmpfactoryoption);
		int heightRatio = (int) Math.ceil(bmpfactoryoption.outHeight/(float) DISPLAYHEIGHT);
				int widthRatio = (int) Math.ceil(bmpfactoryoption.outWidth/(float) DISPLAYWIDTH);
				Log.v("HEIGHTRATIO", "" + heightRatio);
				Log.v("WIDTHRATIO", "" + widthRatio);
				// If both of the ratios are greater than 1, one of the sides of
				// the image is greater than the screen
				if (heightRatio > 1 && widthRatio > 1) {
				if (heightRatio > widthRatio) {
				// Height ratio is larger, scale according to it
				bmpfactoryoption.inSampleSize = heightRatio;
				} else {
				// Width ratio is larger, scale according to it
				bmpfactoryoption.inSampleSize = widthRatio;
				}
				}
				// Decode it for real
				bmpfactoryoption.inJustDecodeBounds = false;
				bmp = BitmapFactory.decodeFile(imageFilePath, bmpfactoryoption);
				return bmp;
	}

	
}
