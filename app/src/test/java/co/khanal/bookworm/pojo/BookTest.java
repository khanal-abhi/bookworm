package co.khanal.bookworm.pojo;

import junit.framework.TestCase;

/**
 * Created by abhi on 7/5/16.
 */
public class BookTest extends TestCase {

    Book book1;
    Book book2;
    Book book3;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        book1 = new Book(1, "title", 0, "genre", "author");
        book2 = new Book("title", 0, "genre", "author");
        book3 = new Book(1, "title", 1, "gemre", "author");

    }

    public void test_id_is_irrelevant_for_comparison() throws Exception{
        assertEquals(book1, book2);
    }

    public void test_every_other_field_needs_to_be_equal() throws Exception{
        assertFalse(book1.equals(book3));
    }

    public void test_has_three_constuctors() throws Exception{
        book1 = new Book();
        book2 = new Book(1, "", 1, "", "");
        book3 = new Book("", 1, "", "");

        assertTrue(true);
    }

    public void test_basic_contract_info() throws Exception{
        System.out.println(BookContract.CREATE_TABLE);
        System.out.println(BookContract.DROP_TABLE);

        assertTrue(true);
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        nullify(book1);
        nullify(book2);
        nullify(book3);

    }

//    Helper method to nullify a non-null object
    public void nullify(Object o){
        if(o != null){
            o = null;
        }
    }
}
