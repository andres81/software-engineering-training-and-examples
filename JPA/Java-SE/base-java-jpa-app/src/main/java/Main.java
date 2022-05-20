import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, world!");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");

        TestDao dao = new TestDao();
        dao.postEntity(emf);
        dao.postEntity(emf);
        dao.postEntity(emf);

        List<Book> books = dao.getAllBooks(emf);

        System.out.println("Books returned: " + books.size());


    }

    private static void test1() {

    }

}
