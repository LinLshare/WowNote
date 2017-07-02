package me.lshare.wownote.persistencce;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import me.lshare.wownote.model.WowUser;

import static me.lshare.wownote.persistencce.StorageHelper.COLUMN_USERS_AVATARURL;
import static me.lshare.wownote.persistencce.StorageHelper.COLUMN_USERS_ID;
import static me.lshare.wownote.persistencce.StorageHelper.COLUMN_USERS_NAME;
import static me.lshare.wownote.persistencce.StorageHelper.TABLE_USERS;

/**
 * @author Lshare
 * @date 2017/7/2
 */
public class AccountManager {

  private StorageHelper storageHelper;
  private static WowUser wowUser;

  public static WowUser currentUser() {
    return wowUser;
  }

  public AccountManager(Context context) {
    storageHelper = new StorageHelper(context);
  }

  public void store(int id, String name, String avatarurl) {
    SQLiteDatabase writableDatabase = storageHelper.getWritableDatabase();
    writableDatabase.execSQL(
        "INSERT INTO users (" + COLUMN_USERS_ID + ", " + COLUMN_USERS_NAME + ", " +
        COLUMN_USERS_AVATARURL + ") VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE " + COLUMN_USERS_NAME +
        "=?, " + COLUMN_USERS_AVATARURL + "=?;",
        new Object[] {id, name, avatarurl, name, avatarurl});
    wowUser = new WowUser(id, name, avatarurl);
  }

  public WowUser query(int id) {
    WowUser wowUser = null;
    SQLiteDatabase readableDatabase = storageHelper.getReadableDatabase();
    Cursor cursor = readableDatabase.rawQuery(
        "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_ID + " = ?;",
        new String[] {id + ""});
    if (cursor != null) {
      cursor.moveToFirst();
      String name = cursor.getString(cursor.getColumnIndex("name"));
      String avatarurl = cursor.getString(cursor.getColumnIndex("avatarurl"));
      wowUser = new WowUser(id, name, avatarurl);
    }
    return wowUser;
  }
}
