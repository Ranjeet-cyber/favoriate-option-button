package com.example42041.gkhindishorttrick.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example42041.gkhindishorttrick.model.DataContent;
import com.example42041.gkhindishorttrick.model.MainContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "gk_tricks.sqlite";
    public static String DB_PATH;

    /* renamed from: db */
    public static volatile SQLiteDatabase f27db;
    private String DB_FULL_PATH = "";
    Context context;

    public DbHelper(Context context2) {
        super(context2, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context2;
        DB_PATH = "/data/data/" + this.context.getPackageName() + "/databases/";
        this.DB_FULL_PATH = DB_PATH + DB_NAME;
        Log.v("DB PATH", this.DB_FULL_PATH);
    }

    public static void deleteDb() {
        f27db = openDataBase();
        try {
            Log.d("Database :", "Old Data Base Deleted" + new File(DB_PATH + DB_NAME).delete());
        } catch (Exception e) {
            Log.e("Error:", e.getMessage());
        }
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDb(boolean versionChange) {
        boolean chkverres = checkversion();
        if (!checkDb() || chkverres) {
            copyDataBase();
        }
        f27db = openDataBase();
    }

    public boolean checkversion() {
        try {
            String Versionname = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
            SharedPreferences miscPrefs = this.context.getSharedPreferences("MiscPrefsFile", 0);
            String currentVersion = miscPrefs.getString("Current Version", (String) null);
            Log.v("getting version name", "getAppVersionToPrefs: got " + currentVersion);
            miscPrefs.edit().putString("Current Version", Versionname).commit();
            Log.v("settinf version name", "setAppVersionToPrefs: set app version to prefs" + Versionname);
            if (Versionname.equals(currentVersion)) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    public boolean checkDb() {
        f27db = null;
        try {
            f27db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, (SQLiteDatabase.CursorFactory) null, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (f27db != null) {
            f27db.close();
        }
        if (f27db == null) {
            return false;
        }
        return true;
    }

    @SuppressLint("WrongConstant")
    public static SQLiteDatabase openDataBase() throws SQLException {
        try {
            if (f27db == null) {
                f27db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, (SQLiteDatabase.CursorFactory) null, 268435472);
            } else if (!f27db.isOpen()) {
                f27db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, (SQLiteDatabase.CursorFactory) null, 268435472);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f27db;
    }

    public void copyDataBase() {
        byte[] arrByte = new byte[1024];
        try {
            InputStream in = this.context.getAssets().open(DB_NAME);
            File dbFolder = new File(DB_PATH);
            if (!dbFolder.exists()) {
                dbFolder.mkdir();
            }
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            while (true) {
                int length = in.read(arrByte);
                if (length > 0) {
                    os.write(arrByte, 0, length);
                } else {
                    os.flush();
                    in.close();
                    os.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<MainContent> getMainListName() {
        Cursor mCursor = null;
        List<MainContent> conversationListDto = new ArrayList<>();
        try {
            f27db = openDataBase();
            Cursor mCursor2 = f27db.rawQuery("SELECT * FROM main ", (String[]) null);
            while (mCursor2.moveToNext()) {
                conversationListDto.add(new MainContent(mCursor2.getInt(mCursor2.getColumnIndexOrThrow("id")), mCursor2.getString(mCursor2.getColumnIndexOrThrow("hindi"))));
            }
            if (mCursor2 != null && !mCursor2.isClosed()) {
                mCursor2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        } catch (Throwable th) {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            throw th;
        }
        return conversationListDto;
    }

    public static List<DataContent> getContentListName(int mainIdpos) {
        String sqlQuery = "SELECT * FROM content WHERE main_id=" + mainIdpos;
        Cursor mCursor = null;
        List<DataContent> conversationListDto = new ArrayList<>();
        try {
            f27db = openDataBase();
            Cursor mCursor2 = f27db.rawQuery(sqlQuery, (String[]) null);
            mCursor2.moveToFirst();
            do {
                conversationListDto.add(new DataContent(mCursor2.getInt(mCursor2.getColumnIndexOrThrow("id")), mCursor2.getInt(mCursor2.getColumnIndexOrThrow("main_id")), mCursor2.getString(mCursor2.getColumnIndexOrThrow("text_value")), mCursor2.getInt(mCursor2.getColumnIndexOrThrow("fav"))));
            } while (mCursor2.moveToNext());
            if (mCursor2 != null && !mCursor2.isClosed()) {
                mCursor2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        } catch (Throwable th) {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            throw th;
        }
        return conversationListDto;
    }

    public static boolean makeFavUnfavData(int position, int update) {
        f27db = openDataBase();
        ContentValues data = new ContentValues();
        data.put("fav", Integer.valueOf(update));
        f27db.update("content", data, "id=" + position, (String[]) null);
        return true;
    }

    public static List<DataContent> getFavoritesList() {
        Cursor mCursor = null;
        List<DataContent> conversationListDto = new ArrayList<>();
        try {
            f27db = openDataBase();
            Cursor mCursor2 = f27db.rawQuery("SELECT * FROM content WHERE fav=1", (String[]) null);
            mCursor2.moveToFirst();
            do {
                conversationListDto.add(new DataContent(mCursor2.getInt(mCursor2.getColumnIndexOrThrow("id")), mCursor2.getInt(mCursor2.getColumnIndexOrThrow("main_id")), mCursor2.getString(mCursor2.getColumnIndexOrThrow("text_value")), mCursor2.getInt(mCursor2.getColumnIndexOrThrow("fav"))));
            } while (mCursor2.moveToNext());
            if (mCursor2 != null && !mCursor2.isClosed()) {
                mCursor2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
        } catch (Throwable th) {
            if (mCursor != null && !mCursor.isClosed()) {
                mCursor.close();
            }
            throw th;
        }
        return conversationListDto;
    }
}
