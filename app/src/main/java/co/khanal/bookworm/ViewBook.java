package co.khanal.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import co.khanal.bookworm.pojo.Book;
import co.khanal.bookworm.pojo.BookContract;

public class ViewBook extends AppCompatActivity {

    TextView book_title;
    TextView year_of_publication;
    TextView genre;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        book_title = (TextView)findViewById(R.id.book_title);
        year_of_publication = (TextView)findViewById(R.id.year_of_publication);
        genre = (TextView)findViewById(R.id.genre);
        author = (TextView)findViewById(R.id.author);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Book book = bundle.getParcelable(BookContract.TABLE);

        assert(book != null);

        book_title.setText(book.getBook_title());
        year_of_publication.setText(String.valueOf(book.getYear_of_publication()));
        genre.setText(book.getGenre());
        author.setText(book.getAuthor());

        toolbar.setTitle(book.getBook_title());
    }

}
