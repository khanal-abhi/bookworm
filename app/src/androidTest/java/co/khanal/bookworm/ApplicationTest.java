package co.khanal.bookworm;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import co.khanal.bookworm.pojo.Book;
import co.khanal.bookworm.pojo.BookContract;
import co.khanal.bookworm.utility.BookSqliteHelper;
import co.khanal.bookworm.utility.Nullify;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    BookSqliteHelper helper;
    Context mContext;

    Book book1;
    Book book2;
    Book book3;

    public ApplicationTest() {
        super(Application.class);


    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        book1 = new Book(1, "title", 0, "genre", "author");
        book2 = new Book("title", 0, "genre", "author");
        book3 = new Book(1, "title", 1, "gemre", "author");

        mContext = getContext();
        helper = new BookSqliteHelper(mContext, BookContract.DB_NAME, null, BookContract.DB_VERSION);

        helper.deleteAllBooks();


    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        Nullify.Nullify(mContext);
        Nullify.Nullify(helper);
        Nullify.Nullify(book1);
        Nullify.Nullify(book2);
        Nullify.Nullify(book3);
    }

    public void test_no_books_are_there() throws Exception{

        assertEquals(0, helper.getBooks().size());

    }

    public void test_can_insert_get_and_delete_book() throws Exception{
        long _id = helper.insertBook(book1);
        assertFalse(-1 == _id);
        assertEquals(book1, helper.getBook(_id));
        assertFalse(-1 == helper.deleteBook(_id));
        assertTrue(helper.getBooks().size() == 0);
    }

    public void test_can_get_all_books_and_delete_all_books() throws Exception{
        helper.insertBook(book1);
        helper.insertBook(book2);
        helper.insertBook(book3);

        assertEquals(3, helper.getBooks().size());

        helper.deleteAllBooks();

        assertEquals(0, helper.getBooks().size());
    }

}