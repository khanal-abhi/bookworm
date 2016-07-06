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


        if(helper.getBooks().size() == 0){

            new load_books_from_json().execute(this);



        } else {
            bookList = helper.getBooks();
        }
        adapter = new BookRecyclerView(getApplicationContext(), bookList);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBooksLoaded(List<Book> loaded_books) {

        books = loaded_books;

        for (Book book: books){
            helper.insertBook(book);
        }

        books = helper.getBooks();
        Log.d(getClass().getSimpleName(), books.toString());

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
                InputStream inputStream = getAssets().open("books.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json_books = new String(buffer, "UTF-8");

                JSONArray json_books_array = new JSONArray(json_books);

                for(int i = 0; i < json_books_array.length(); i++){
                    JSONObject json_book = json_books_array.getJSONObject(i);
                    Book java_book = new Book(
                            json_book.getString(BookContract.BOOK_TITLE),
                            json_book.getInt(BookContract.YEAR_OF_PUBLICATION),
                            json_book.getString(BookContract.GENRE),
                            json_book.getString(BookContract.AUTHOR)
                    );

                    Log.d(getClass().getSimpleName(), java_book.toString());

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
            receiver.onBooksLoaded(books);

        }


    }
}
