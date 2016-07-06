package co.khanal.bookworm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddView extends AppCompatActivity {

    EditText book_title;
    EditText year_of_publication;
    EditText genre;
    EditText author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.new_book));
        setSupportActionBar(toolbar);

        book_title = (EditText)findViewById(R.id.book_title);
        year_of_publication = (EditText)findViewById(R.id.year_of_publication);
        genre = (EditText)findViewById(R.id.genre);
        author = (EditText)findViewById(R.id.author);

        book_title.setError(getString(R.string.book_title_error));
        year_of_publication.setError(getString(R.string.year_of_publication_error));
        genre.setError(getString(R.string.genre_error));
        author.setError(getString(R.string.author_error));

        book_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(book_title.getText().toString().isEmpty())
                    book_title.setError(getString(R.string.book_title_error));
            }
        });
        genre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(genre.getText().toString().isEmpty())
                    genre.setError(getString(R.string.genre_error));
            }
        });
        author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(author.getText().toString().isEmpty())
                    author.setError(getString(R.string.author_error));
            }
        });
        year_of_publication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(year_of_publication.getText().toString().length() != 4)
                    year_of_publication.setError(getString(R.string.year_of_publication_error));
            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check the validation of the fields
                if(book_title.getError() == null && year_of_publication.getError() == null &&
                        genre.getError() == null && author.getError() == null){

                } else {
                    Snackbar.make(view, getString(R.string.general_error), Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
