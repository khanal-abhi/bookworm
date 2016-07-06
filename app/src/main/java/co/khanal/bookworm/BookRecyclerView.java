package co.khanal.bookworm;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.khanal.bookworm.interfaces.RecyclerClickListener;
import co.khanal.bookworm.interfaces.RecyclerLongClickListener;
import co.khanal.bookworm.pojo.Book;

/* Recycler View Adapter for the books.
 */
public class BookRecyclerView extends RecyclerView.Adapter<BookRecyclerView.BookViewHolder> {

    List<Book> books;
    Context mContext;
    MainView mainView;

    public BookRecyclerView(Context mContext, List<Book> books, MainView mainView){
        this.mContext = mContext;
        this.books = books;
        this.mainView = mainView;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, null);
        return new BookViewHolder(view);
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

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView book_title, year_of_publication, genre, author;

        public BookViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            book_title = (TextView)itemView.findViewById(R.id.book_title);
            year_of_publication = (TextView)itemView.findViewById(R.id.year_of_publication);
            genre = (TextView)itemView.findViewById(R.id.genre);
            author = (TextView)itemView.findViewById(R.id.author);
        }

        @Override
        public void onClick(View v) {
            ((RecyclerClickListener)mainView).onBookClicked(books.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            Snackbar.make(itemView, v.getContext().getString(R.string.delete_book), Snackbar.LENGTH_SHORT)
                    .setAction(v.getContext().getString(R.string.delete) + " " + books.get(getAdapterPosition()).getBook_title(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((RecyclerLongClickListener)mainView).onBookLongClicked(books.get(getAdapterPosition()));
                        }
                    }).show();

            return true;
        }
    }
}
