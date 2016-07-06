package co.khanal.bookworm.interfaces;

import java.util.List;

import co.khanal.bookworm.pojo.Book;

/**
 * Created by abhi on 7/5/16.
 */
public interface BooksLoadedReceiver {
    /* This is the BooksLoadedReceiver that can receives the loaded books from the asynctask so as
    to avoid the ANR. This would not matter much with a few books being loaded from a JSON file but
    is a huge deal when doing massive background work.
     */

    public void onBooksLoaded(List<Book> loaded_books);
}
