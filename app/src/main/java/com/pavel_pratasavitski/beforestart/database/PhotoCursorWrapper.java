package com.pavel_pratasavitski.beforestart.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.pavel_pratasavitski.beforestart.Photo;

/**
 * Created by Pavel_Pratasavitski on 8/23/2017.
 */

public class PhotoCursorWrapper extends CursorWrapper{
    public PhotoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Photo getPhoto() {
        int id = getInt(getColumnIndex(DBase.PhotoTable.Cols.ID));
        String title = getString(getColumnIndex(DBase.PhotoTable.Cols.TITLE));
        String description = getString(getColumnIndex(DBase.PhotoTable.Cols.DESCRIPTION));
        double latitude = getDouble(getColumnIndex(DBase.PhotoTable.Cols.LATITUDE));
        double longitude = getDouble(getColumnIndex(DBase.PhotoTable.Cols.LONGITUDE));
        String url = getString(getColumnIndex(DBase.PhotoTable.Cols.URL));

        Photo photo = new Photo();
        photo.setId(id);
        photo.setTitle(title);
        photo.setDescription(description);
        photo.setLatitude(latitude);
        photo.setLongitude(longitude);
        photo.setUrl(url);

        return photo;
    }
}
