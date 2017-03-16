package com.hanson.android.recipe.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.hanson.android.recipe.Model.CategoryItem;
import com.hanson.android.recipe.Model.RecipeItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lily on 2017-03-14.
 */

public class DBHelper extends SQLiteOpenHelper
{
    ImageHelper imageHelper = new ImageHelper();

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("CREATE TABLE RECIPES( _id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT,"
                + "recipeName TEXT, author TEXT, uploardDate TEXT, howTo TEXT, description TEXT,"
                + "thumbnail BLOB, mainImg BLOB, likeCount INTEGER);");

        db.execSQL("CREATE TABLE INGREDIENTS( _id INTEGER PRIMARY KEY AUTOINCREMENT, recipeID INTEGER, ingreName);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void recipes_Insert(String category, String recipeName, String author,
                               String uploardDate, String howTo, String description,
                               byte[] thumbnail, byte[] mainImg, int likeCount) {

        // open read and write database
        SQLiteDatabase db = getWritableDatabase();
        // execute insert query
        //db.execSQL("INSERT INTO RECIPES VALUES(null, '" + category + "', '" + recipeName + "', " + thumbnail  + ");");
        SQLiteStatement p = db.compileStatement("INSERT INTO recipes values(?,?,?,?,?,?,?,?,?,?);");
        p.bindNull(1);
        p.bindString(2, category);
        p.bindString(3, recipeName);
        p.bindString(4, author);
        p.bindString(5, uploardDate);
        p.bindString(6, howTo);
        p.bindString(7, description);
        p.bindBlob(8, thumbnail);
        p.bindBlob(9, mainImg);
        p.bindLong(10, likeCount);
        p.execute();
        db.close();
    }

    public  void  ingredients_Insert(int recipeid, String ingreName)
    {
        // open read and write database
        SQLiteDatabase db = getWritableDatabase();
        // execute insert query
        db.execSQL("INSERT INTO INGREDIENTS VALUES(null, " + recipeid + ", '" + ingreName + "');");

        db.close();
    }

    public ArrayList<String> ingredients_SelectByRecipeId(int id)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> ingredients = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT ingreName FROM INGREDIENTS WHERE recipeID = " + id, null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                ingredients.add(cursor.getString(0));
            }
        }
        cursor.close();
        db.close();

        return ingredients;


    }

    public int recipes_GetIdByName(String name)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        //variable id
        int id= -1;
        Cursor cursor = db.rawQuery("SELECT _id FROM RECIPES WHERE recipeName = '" + name + "' ORDER BY _id DESC LIMIT 1", null);
        if(cursor != null)
        {
            while (cursor.moveToNext()){
                id = cursor.getInt(0);
            }
        }

        cursor.close();
        db.close();
        return id;
    }

    public ArrayList<RecipeItem> recipes_SelectNew()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY _id DESC LIMIT 3", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public ArrayList<CategoryItem> recipes_SelectCategory()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<CategoryItem> categoryList = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT category, mainImg FROM RECIPES GROUP BY category HAVING max(_id)", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                categoryList.add(new CategoryItem(
                        cursor.getString(0),
                        cursor.getBlob(1)
                ));
            }
        }
        cursor.close();
        db.close();
        return categoryList;
    }

    public ArrayList<RecipeItem> recipes_SelectBest()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES ORDER BY likeCount DESC LIMIT 3", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public ArrayList<RecipeItem> recipes_SelectAll()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public ArrayList<RecipeItem> recipes_SelectByCategory(String category)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeItem> allRecipes = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE category = '" + category + "'", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allRecipes.add(new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                ));
            }
        }
        cursor.close();
        db.close();

        return allRecipes;
    }

    public RecipeItem recipes_SelectByName(String name)
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE recipeName = '" + name + "'", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                RecipeItem recipe = new RecipeItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getBlob(8),
                        cursor.getInt(9)
                );
                cursor.close();
                db.close();
                return recipe;
            }


        }
        return  null;
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + "\n";
        }
        cursor.close();
        db.close();
        return result;
    }


}
//    private static final String DATABASE_NAME = "recipesDB.db";
//    private static final int DATABASE_VERSION = 1;
//    public static SQLiteDatabase mDB;
//    private DBbaseHelper mDBHelper;
//    private Context mCtx;
//
//    private class DBbaseHelper extends SQLiteOpenHelper
//    {
//        /**
//         * database helper constructor
//         * @param context   context
//         * @param name      Db Name
//         * @param factory   CursorFactory
//         * @param version   Db Version
//         */
//        public DBbaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//            super(context, name, factory, version);
//        }
//
//        //creating database just first time
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(Databases.CreateDB._RECIPESCREATE);
//        }
//
//        //버전이 업데이트 되었을 경우 DB를 다시 만들어주는 메소드
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            //업데이트를 했는데 DB가 존재할 경우 onCreate를 다시 불러온다
//            db.execSQL("DROP TABLE IF EXISTS " + Databases.CreateDB._RECIPES);
//            onCreate(db);
//        }
//    }
//
//    //DBHelper constructor
//    public DBHelper(Context context) {
//        this.mCtx = context;
//    }
//
//    //open database
//    public DBHelper open() throws SQLException {
//        mDBHelper = new DBbaseHelper(mCtx,DATABASE_NAME, null, DATABASE_VERSION);
//        mDB = mDBHelper.getWritableDatabase();
//        return this;
//    }
//
//    //close database
//    public void close() {
//        mDB.close();
//    }
//
//    /**
//     *  데이터베이스에 사용자가 입력한 값을 insert하는 메소드
//     * @param category          이름
//     * @param recipename       전화번호
//     * @param author         이메일
//     * @return              SQLiteDataBase에 입력한 값을 insert
//     * CATEGORY = "category";
//    RECIPENAME = "recipename";
//    AUTHOR = "author";
//    UPLOADDATE = "uploaddate";
//    HOWTO = "howto";
//    DESCRIPTION = "description";
//    THUMBNAIL = "thumbnail";
//    MAINIMG = "mainimg";
//    LIKECOUNT = "likecount";
//    _RECIPESTABLENAME = "recipes";
//     */
//    public long recipes_insertColumn(String category, String recipename, String author,
//                                     String uploaddate, String howto, String description,
//                                     byte[] thumbnail, byte[] mainimg, int likecount) {
//        ContentValues values = new ContentValues();
//        values.put(Databases.CreateDB.CATEGORY, category);
//        values.put(Databases.CreateDB.RECIPENAME, recipename);
//        values.put(Databases.CreateDB.AUTHOR, author);
//        values.put(Databases.CreateDB.UPLOADDATE, uploaddate);
//        values.put(Databases.CreateDB.HOWTO, howto);
//        values.put(Databases.CreateDB.AUTHOR, author);
//        values.put(Databases.CreateDB.DESCRIPTION, description);
//        values.put(Databases.CreateDB.THUMBNAIL, thumbnail);
//        values.put(Databases.CreateDB.MAINIMG, mainimg);
//        values.put(Databases.CreateDB.LIKECOUNT, likecount);
//        return mDB.insert(Databases.CreateDB._RECIPES, null, values);
//    }
//
//
//    //커서 전체를 선택하는 메소드
//    public Cursor getAllColumns() {
//        return mDB.query(Databases.CreateDB._RECIPES, null, null, null, null, null, null);
//    }
//
//    public void recipes_statemet(String category, String recipename, String author,
//                                 String uploaddate, String howto, String description,
//                                  int likecount){
//        SQLiteStatement p = mDB.compileStatement("INSERT INTO recipes values(?,?,?,?,?,?,?,?,?);");
//        p.bindString(1, category);
//        p.bindString(2, recipename);
//        p.bindString(3, author);
//        p.bindString(4, uploaddate);
//        p.bindString(5, howto);
//        p.bindString(6, description);
////        p.bindBlob(7, thumbnail);
////        p.bindBlob(8, mainimg);
//        p.bindLong(7, likecount);
//        p.execute();
//    }
//
//    public byte[] getByteArrayFromDrawable(Drawable d) {
//        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] data = stream.toByteArray();
//
//        return data;
//    }
//
//    public byte[] getByteArrayFromDrawable(Bitmap d) {
//
//// convert bitmap to byte
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//        d.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//        byte imageInByte[] = stream.toByteArray();
//        return imageInByte;
//    }
//
//    public Bitmap getAppIcon(byte[] b) {
//        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//        return bitmap;
//    }
//}
