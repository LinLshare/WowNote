package me.lshare.wownote.persistencce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存用户信息，保存文章信息
 *
 * @author Lshare
 * @date 2017/7/2
 */
public class StorageHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "wow_note.db";
  private static final int DB_VERSION = 1;
  public static final String TABLE_USERS = "users";
  public static final String COLUMN_USERS_ID = "id";
  public static final String COLUMN_USERS_NAME = "name";
  public static final String COLUMN_USERS_AVATARURL = "avatarurl";

  public static final String TABLE_REPOS = "repos";

  public StorageHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TABLE_USERS + " (" + COLUMN_USERS_ID + " INTEGER PRIMARY KEY, " +
               COLUMN_USERS_NAME + " TEXT, " + COLUMN_USERS_AVATARURL + " TEXT);");
    db.execSQL("CREATE TABLE " + TABLE_REPOS +
               " (id INTEGER PRIMARY KEY, repoId INTEGER, orgId INTEGER, " +
               "name TEXT, ownerId INTEGER, private INTEGER, fork INTEGER, description TEXT, " +
               "forks INTEGER, watchers INTEGER, language TEXT, hasIssues INTEGER, " +
               "mirrorUrl TEXT, permissions_admin INTEGER, permissions_pull INTEGER, " +
               "permissions_push INTEGER);");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPOS);
    onCreate(db);
  }
}
