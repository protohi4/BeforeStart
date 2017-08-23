package com.pavel_pratasavitski.beforestart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pavel_pratasavitski.beforestart.database.DBase;
import com.pavel_pratasavitski.beforestart.database.DBase.PhotoTable;
import com.pavel_pratasavitski.beforestart.database.DBaseHelper;
import com.pavel_pratasavitski.beforestart.database.PhotoCursorWrapper;

public class PhotoLst {
    private static PhotoLst sPhotoLst;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PhotoLst get(Context context) {
        if (sPhotoLst == null) {
            sPhotoLst = new PhotoLst(context);
        }
        return sPhotoLst;
    }

    private PhotoLst(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DBaseHelper(mContext).getWritableDatabase();
    }

    public List<Photo> getPhotos() {
        List<Photo> photos = new ArrayList<>();

        PhotoCursorWrapper cursor = queryPhotos(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                photos.add(cursor.getPhoto());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return photos;
    }

    public int getPhotoCount() {
        PhotoCursorWrapper cursor = queryPhotos(null, null);
        try {
            return cursor.getCount();
        } finally {
            cursor.close();
        }
    }

    public Photo getPhoto(Integer id) {
        PhotoCursorWrapper cursor = queryPhotos(PhotoTable.Cols.ID + " = ?",
                new String[] { id.toString() });

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPhoto();
        } finally {
            cursor.close();
        }
    }

    public void addPhoto(Photo photo) {
        ContentValues values = getContentValues(photo);

        mDatabase.replace(PhotoTable.NAME, null, values);
    }

    public void setPhotos(List<Photo> photos) {
        for (Photo photo: photos) {
            addPhoto(photo);
        }
    }

    public void updatePhoto(Photo photo) {
        String idString = photo.getId().toString();
        ContentValues values = getContentValues(photo);

        mDatabase.update(PhotoTable.NAME, values,
                PhotoTable.Cols.ID + " = ?", new String[] { idString });
    }

    private static ContentValues getContentValues(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(PhotoTable.Cols.ID, photo.getId().toString());
        values.put(PhotoTable.Cols.TITLE, photo.getTitle());
        values.put(PhotoTable.Cols.DESCRIPTION, photo.getDescription());
        values.put(PhotoTable.Cols.LATITUDE, photo.getLatitude().toString());
        values.put(PhotoTable.Cols.LONGITUDE, photo.getLongitude().toString());
        values.put(PhotoTable.Cols.URL, photo.getUrl());

        return values;
    }

    private PhotoCursorWrapper queryPhotos(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PhotoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new PhotoCursorWrapper(cursor);
    }

}