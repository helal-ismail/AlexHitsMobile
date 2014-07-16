package com.alexhits.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;

public class AssetsHelper extends ContentProvider{

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
	    AssetManager am = getContext().getAssets();
	    String file_name = uri.getLastPathSegment();
	    if(file_name == null) 
	        throw new FileNotFoundException();
	    AssetFileDescriptor afd = null;
	    try {
	        afd = am.openFd(file_name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return afd;//super.openAssetFile(uri, mode);
	}

}
