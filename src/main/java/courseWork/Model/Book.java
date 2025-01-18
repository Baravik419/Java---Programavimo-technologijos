package courseWork.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import courseWork.Model.Enum.PublicationStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Book extends Publication
{
    private String isbn;
    private int pageCount;
    private String summary;

//    public Book(String title, String author, String publisher, LocalDate year, String type, String isbn, int pageCount, String summary) {
//        super(title, author, publisher, year, type);
//        this.isbn = isbn;
//        this.pageCount = pageCount;
//        this.summary = summary;
//    }

    public Book(String title, String author, String publisher, LocalDate year, String type, Client client, String isbn, int pageCount, String summary, PublicationStatus publicationStatus) {
        super(title, author, publisher, year, type, client, publicationStatus);
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.summary = summary;
    }

//    public Book(String isbn, int pageCount, String summary) {
//        this.isbn = isbn;
//        this.pageCount = pageCount;
//        this.summary = summary;
//    }

    @Override
    public String toString() {
        return "Book " + getTitle() + " " + getAuthor() + " " + getPublisher() + " " +
                " " + isbn +  " " + pageCount + " " + summary;
    }
}
