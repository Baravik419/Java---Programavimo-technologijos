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
@AllArgsConstructor
@Entity
public class Manga  extends Publication
{
    private String illustrator;
    private String originalLanguage;
    private int volumeNumber;
    private boolean isColor;

    public Manga(String title, String author, String publisher, LocalDate year, String type, Client client, String illustrator, String originalLanguage, int volumeNumber, boolean isColor, PublicationStatus publicationStatus) {
        super(title, author, publisher, year, type, client, publicationStatus);
        this.illustrator = illustrator;
        this.originalLanguage = originalLanguage;
        this.volumeNumber = volumeNumber;
        this.isColor = isColor;
    }

//    public Manga(String title, String author, String publisher, LocalDate year, String type, Client client, PublicationStatus publicationStatus) {
//        super(title, author, publisher, year, type, client, publicationStatus);
//    }
//
//
//    public Manga(String title, String author, String publisher, LocalDate year, String type) {
//        super(title, author, publisher, year, type);
//    }
//
//    public Manga(String title, String author, String publisher, LocalDate year, String type, String illustrator, String originalLanguage, int volumeNumber, boolean isColor) {
//        super(title, author, publisher, year, type);
//        this.illustrator = illustrator;
//        this.originalLanguage = originalLanguage;
//        this.volumeNumber = volumeNumber;
//        this.isColor = isColor;
//    }

    @Override
    public String toString() {
        return "Manga " + getTitle() + " " + getAuthor() + " " + getPublisher() + " " +
                illustrator + " " + originalLanguage + " " + volumeNumber + " " + isColor;
    }
}
