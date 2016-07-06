package co.khanal.bookworm.utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.khanal.bookworm.pojo.Book;
import co.khanal.bookworm.pojo.BookContract;

/**
 * Created by abhi on 7/5/16.
 */
public class BookSqliteHelper extends SQLiteOpenHelper {

    Context mContext;
    SQLiteDatabase db;

    public static String[] SELECTION = new String[]{
            BookContract.ID,
            BookContract.BOOK_TITLE,
            BookContract.YEAR_OF_PUBLICATION,
            BookContract.GENRE,
            BookContract.AUTHOR
    };


    public BookSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BookContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BookContract.DROP_TABLE);
        onCreate(db);
    }

    public long insertBook(Book book){

        db = getWritableDatabase();
        long res = 0;
        res = db.insertOrThrow(BookContract.TABLE, null, book.to_content_values());

        if(db != null){
            db.close();
            db = null;
        }

        return res;
    }

    public int deleteBook(long _id){

        db = getWritableDatabase();
        int res = -1;
        String[] args = {String.valueOf(_id)};
        String where_clause = BookContract.ID + "=?";

        res = db.delete(
                BookContract.TABLE,
                where_clause,
                args
        );

        if(db != null){
            db.close();
            db = null;
        }

        return res;
    }

    public void deleteAllBooks(){

        db = getWritableDatabase();
        db.delete(
                BookContract.TABLE,
                "1",
                null
        );

        if(db != null){
            db.close();
            db = null;
        }
    }

    public Book getBook(long _id){

        db = getWritableDatabase();

        Book res = null;
        String[] args = new String[]{String.valueOf(_id)};
        String where_clause = BookContract.ID + "=?";
        Cursor cursor = db.query(BookContract.TABLE, SELECTION, where_clause, args, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                Book book = new Book(
                        cursor.getLong(cursor.getColumnIndex(BookContract.ID)),
                        cursor.getString(cursor.getColumnIndex(BookContract.BOOK_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(BookContract.YEAR_OF_PUBLICATION)),
                        cursor.getString(cursor.getColumnIndex(BookContract.GENRE)),
                        cursor.getString(cursor.getColumnIndex(BookContract.AUTHOR))
                );

                res =  book;
            }
        }

        if(db != null){
            db.close();
            db = null;
        }

        return res;
    }

    public List<Book> getBooks(){

        db = getWritableDatabase();

        List<Book> books = new ArrayList<Book>();

        String[] args = new String[]{"1"};
        String where_clause = "1";

        // This is specifically for the newest added book to be at the top of the list.
        Cursor cursor = db.query(BookContract.TABLE, SELECTION, where_clause, null, null, null, BookContract.ID + " DESC");


        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    books.add(new Book(
                            cursor.getLong(cursor.getColumnIndex(BookContract.ID)),
                            cursor.getString(cursor.getColumnIndex(BookContract.BOOK_TITLE)),
                            cursor.getInt(cursor.getColumnIndex(BookContract.YEAR_OF_PUBLICATION)),
                            cursor.getString(cursor.getColumnIndex(BookContract.GENRE)),
                            cursor.getString(cursor.getColumnIndex(BookContract.AUTHOR))
                    ));

                }while(cursor.moveToNext());

            }
        }

        if(db != null){
            db.close();
            db = null;
        }
        return books;
    }
}
