package co.khanal.bookworm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import co.khanal.bookworm.interfaces.BooksLoadedReceiver;
import co.khanal.bookworm.pojo.Book;
import co.khanal.bookworm.pojo.BookContract;
import co.khanal.bookworm.utility.BookSqliteHelper;

public class MainView extends AppCompatActivity implements BooksLoadedReceiver{

    BookSqliteHelper helper;
    RecyclerView booksview;
    BookRecyclerView adapter;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new BookSqliteHelper(getApplicationContext(), BookContract.DB_NAME, null, BookContract.DB_VERSION);
        List<Book> bookList = new ArrayList<Book>();


        /* check to see if there are books already saved in the database. If so, load the books
        otherwise, load up the database from the included json file.
         */
        if(helper.getBooks().size() == 0){

            new load_books_from_json().execute(this);

        } else {
            bookList = helper.getBooks();
        }

        adapter = new BookRecyclerView(getApplicationContext(), bookList);

        // set up the recyclerview to load items in a linear fashion.
        booksview = (RecyclerView)findViewById(R.id.booksview);
        booksview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksview.setAdapter(adapter);
        booksview.setHasFixedSize(false);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Switch statement to go through the menu items. It is easier if more items are added
        switch (id){
            case R.id.add_book:
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBooksLoaded(List<Book> loaded_books) {

        books = loaded_books;

        // Insert each book from the list into the database for persistence
        for (Book book: books){
            helper.insertBook(book);
        }

        // Extract the books from the database
        books = helper.getBooks();

        // Set up the adapter for the recyclerview and bind it.
        adapter = new BookRecyclerView(getApplicationContext(), helper.getBooks());
        booksview.setAdapter(adapter);
    }

    public class load_books_from_json extends AsyncTask<BooksLoadedReceiver, Void, List<Book>>{

        BooksLoadedReceiver receiver;

        @Override
        protected List<Book> doInBackground(BooksLoadedReceiver... params) {
            receiver = params[0];

            String json_books = "";

            List<Book> loaded_books = new ArrayList<Book>();

            try {

                /* Saved the books.json file in assets folder so that it can be accessed when needed.
                This was the initial books list can be updated just by updating the file.
                 */
                InputStream inputStream = getAssets().open("books.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json_books = new String(buffer, "UTF-8");

                /* json_books now contains a string representation of json array so need to decode
                it to JSONArray.
                 */
                JSONArray json_books_array = new JSONArray(json_books);

                for(int i = 0; i < json_books_array.length(); i++){

                    // Extract JSONObject version of the books from the array
                    JSONObject json_book = json_books_array.getJSONObject(i);

                    // Convert the json books to java books
                    Book java_book = new Book(
                            json_book.getString(BookContract.BOOK_TITLE),
                            json_book.getInt(BookContract.YEAR_OF_PUBLICATION),
                            json_book.getString(BookContract.GENRE),
                            json_book.getString(BookContract.AUTHOR)
                    );

                    // Load the java books to an arraylist for future use.
                    loaded_books.add(java_book);

                }


            } catch (Exception e){
                e.printStackTrace();
            }

            return loaded_books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);

            /* Notify the receiver that the books have been loaded and pass the books to them. */
            receiver.onBooksLoaded(books);

        }


    }
}
