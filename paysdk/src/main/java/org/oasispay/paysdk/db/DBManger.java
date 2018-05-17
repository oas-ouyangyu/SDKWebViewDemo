package org.oasispay.paysdk.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * created by ouyangyu on 2018/5/16
 */
public class DBManger {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManger(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    /**
     * add order is processing
     * @param order_id
     */
    public void add(String order_id) {
        db.execSQL("INSERT INTO processing_orders VALUES(null, ?, ?)", new Object[]{order_id,String.valueOf(System.currentTimeMillis())});
    }


    /**
     * delete old order_id
     * @param order_id
     */
    public void deleteOldPerson(String order_id) {
        db.delete("processing_orders", "order_id= ?", new String[]{order_id});
    }


    /**
     *  order_id,create_time
     * @return WeakHashMap<String,String>
     */
    public WeakHashMap<String,String> query() {
        WeakHashMap<String,String> orders = new WeakHashMap<String,String>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            orders.put(c.getString(c.getColumnIndex("order_id")), c.getString(c.getColumnIndex("create_time")));
        }
        c.close();
        return orders;
    }

    /**
     * query all order_ids, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM processing_orders", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
