package courseWork.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import courseWork.Model.Enum.PublicationStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class Publication implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;
    private String publisher;
    private LocalDate year;
    private String type;
    @ManyToOne
    private Client owner;
    @ManyToOne
    protected Client client;
    @Enumerated
    private PublicationStatus publicationStatus;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    protected List<PeriodicRecord> records;
    protected LocalDate requestDate;

    public Publication(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Publication(String title, String author, String publisher, LocalDate year, String type) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.type = type;
    }

    public Publication(String title, String author, String publisher, LocalDate year, String type, Client client, PublicationStatus publicationStatus) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.type = type;
        this.owner = client;
        this.publicationStatus = publicationStatus;
    }



}
