package co.khanal.bookworm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.khanal.bookworm.pojo.Book;

/**
 * Created by abhi on 7/5/16.
 */
public class BookRecyclerView extends RecyclerView.Adapter<BookRecyclerView.BookViewHolder> {

    List<Book> books;
    Context mContext;

    public BookRecyclerView(Context mContext, List<Book> books){
        this.mContext = mContext;
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, null);
        BookViewHolder holder = new BookViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.book_title.setText(books.get(position).getBook_title());
        holder.genre.setText(books.get(position).getGenre());
        holder.author.setText(books.get(position).getAuthor());

        int yop = books.get(position).getYear_of_publication();
        String s_yop = String.valueOf(yop);
        holder.year_of_publication.setText(s_yop);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        TextView book_title, year_of_publication, genre, author;

        public BookViewHolder(View itemView) {
            super(itemView);
            book_title = (TextView)itemView.findViewById(R.id.book_title);
            year_of_publication = (TextView)itemView.findViewById(R.id.year_of_publication);
            genre = (TextView)itemView.findViewById(R.id.genre);
            author = (TextView)itemView.findViewById(R.id.author);
        }
    }
}
