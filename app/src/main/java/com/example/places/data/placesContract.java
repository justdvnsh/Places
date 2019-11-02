package com.example.places.data;

import android.provider.BaseColumns;

public class placesContract {

    public static final class placesEntry implements BaseColumns {

        public static final String TABLE_NAME = "places";
        public static final String  COLUMN_DATE = "date";
        public static final String COLUMN_FEATURE = "feature";
        public static final String COLUMN_ADMIN = "admin";
        public static final String COLUMN_SUB_ADMIN = "sub_admin";
        public static final String COLUMN_LOCALITY = "locality";
        public static final String COLUMN_THOROUGHFARE = "thoroughfare";
        public static final String COLUMN_POSTAL_CODE = "postal_code";
        public static final String COLUMN_COUNTRY_NAME = "country_name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";

    }

}
