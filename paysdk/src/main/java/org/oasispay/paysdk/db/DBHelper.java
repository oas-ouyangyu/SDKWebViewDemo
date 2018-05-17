package org.oasispay.paysdk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * created by ouyangyu on 2018/5/16
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "payorder.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS processing_orders" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, order_id VARCHAR, create_time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
