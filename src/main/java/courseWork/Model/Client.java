package courseWork.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends User implements Comparable<Client>
{
    private String email;
    private String address;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> commentList;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Chat> chatList;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Publication> ownedPublications;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Publication> borrowedPublications;
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> myComments;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PeriodicRecord> periodicRecords;
    @OneToMany(mappedBy = "chatOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Chat> myChats;

    public Client(int id, String login, String password, String name, String surname, LocalDate dateCreated, String email, String address, LocalDate birthDate) {
        super(id, login, password, name, surname, dateCreated);
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Client(int id, String login, String password, String name, String surname) {
        super(id, login, password, name, surname);
    }

    public Client(String login, String password, String name, String surname, String address, LocalDate birthDate, String email) {
        super(login, password, name, surname);
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + email + " " + address + " " + birthDate;
    }

    @Override
    public int compareTo(Client o) {

        return 0;
    }

}

