package co.khanal.bookworm.pojo;

/**
 * Created by abhi on 7/5/16.
 */
public class BookContract {

//    Database Name
    public static String DB_NAME = "bookwornm";

//    Database Version
    public static int DB_VERSION = 1;

//    Table Name
    public static String TABLE = "book";

//    Book fields labels
    public static String ID = "id";
    public static String BOOK_TITLE = "book_title";
    public static String YEAR_OF_PUBLICATION = "year_of_publication";
    public static String GENRE = "genre";
    public static String AUTHOR = "author";

//    CREATE Table SQL
    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
        TABLE + " ( " +
        ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        BOOK_TITLE + " TEXT, " +
        YEAR_OF_PUBLICATION + " INTEGER, " +
        GENRE + " TEXT, " +
        AUTHOR + " TEXT );";

//    DROP Table SQL
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE +  ";";

}
