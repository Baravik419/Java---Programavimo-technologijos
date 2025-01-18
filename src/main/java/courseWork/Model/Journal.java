package courseWork.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import courseWork.Model.Enum.PublicationStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Journal  extends Publication
{

    public Journal(String title, String author, String publisher, LocalDate year, String type, Client client, PublicationStatus publicationStatus) {
        super(title, author, publisher, year, type, client, publicationStatus);
    }


//    public Journal(String title, String author, String publisher, LocalDate year, String type) {
//        super(title, author, publisher, year, type);
//    }

    @Override
    public String toString() {
        return "Journal " + getTitle() + " " + getAuthor() + " " + getPublisher();
    }
}
