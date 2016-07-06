package co.khanal.bookworm.pojo;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abhi on 7/5/16.
 */
public class Book implements Parcelable{

    private long _id;
    private String book_title;
    private int year_of_publication;
    private String genre;
    private String author;

//    Explicit empty constructor for instantiations;
    public Book(){

    }

//    Constructor for new book prior to saving to the sqlite database so no _id necessary
    public Book(String book_title, int year_of_publication, String genre, String author) {
        this.book_title = book_title;
        this.year_of_publication = year_of_publication;
        this.genre = genre;
        this.author = author;
    }

//    Constructor based on the query result of the sqlite database so including _id


    public Book(long _id, String book_title, int year_of_publication, String genre, String author) {
        this._id = _id;
        this.book_title = book_title;
        this.year_of_publication = year_of_publication;
        this.genre = genre;
        this.author = author;
    }

//    Accessors and mutators

    protected Book(Parcel in) {
        _id = in.readLong();
        book_title = in.readString();
        year_of_publication = in.readInt();
        genre = in.readString();
        author = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {

            Bundle bundle_to_book = in.readBundle();

            return new Book(
                    bundle_to_book.getString(BookContract.BOOK_TITLE),
                    bundle_to_book.getInt(BookContract.YEAR_OF_PUBLICATION),
                    bundle_to_book.getString(BookContract.GENRE),
                    bundle_to_book.getString(BookContract.AUTHOR)
            );
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public int getYear_of_publication() {
        return year_of_publication;
    }

    public void setYear_of_publication(int year_of_publication) {
        this.year_of_publication = year_of_publication;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    equals and hashcode for comparisons. Not taking _id into account for general comparisons.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getYear_of_publication() != book.getYear_of_publication()) return false;
        if (!getBook_title().equals(book.getBook_title())) return false;
        if (!getGenre().equals(book.getGenre())) return false;
        return getAuthor().equals(book.getAuthor());

    }

    @Override
    public int hashCode() {
        int result = getBook_title().hashCode();
        result = 31 * result + getYear_of_publication();
        result = 31 * result + getGenre().hashCode();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }


//    to_string() for data visualization


    @Override
    public String toString() {
        return "Book{" +
                "_id=" + _id +
                ", book_title='" + book_title + '\'' +
                ", year_of_publication=" + year_of_publication +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle book_to_bundle = new Bundle();
        book_to_bundle.putLong(BookContract.ID, get_id());
        book_to_bundle.putString(BookContract.BOOK_TITLE, getBook_title());
        book_to_bundle.putInt(BookContract.YEAR_OF_PUBLICATION, getYear_of_publication());
        book_to_bundle.putString(BookContract.GENRE, getGenre());
        book_to_bundle.putString(BookContract.AUTHOR, getAuthor());

        dest.writeBundle(book_to_bundle);
    }

    public boolean isValid(){
        return book_title != null &&
                year_of_publication != 0 &&
                genre != null &&
                author != null;
    }

    public ContentValues to_content_values(){
        ContentValues cv = new ContentValues();
        cv.put(BookContract.BOOK_TITLE, book_title);
        cv.put(BookContract.YEAR_OF_PUBLICATION, year_of_publication);
        cv.put(BookContract.GENRE, genre);
        cv.put(BookContract.AUTHOR, author);

        return cv;
    }

}
