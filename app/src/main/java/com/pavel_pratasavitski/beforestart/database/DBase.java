package com.pavel_pratasavitski.beforestart.database;

/**
 * Created by Pavel_Pratasavitski on 8/23/2017.
 */

public class DBase {
    public static final class PhotoTable {
        public static final String NAME = "photos";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String URL = "url";
        }
    }
}
