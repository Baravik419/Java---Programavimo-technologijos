package courseWork.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Admin extends User
{
    private String phoneNumber;


    public Admin(int id, String login, String password, String name, String surname, LocalDate dateCreated, String phoneNumber)
    {
        super(id, login, password, name, surname, dateCreated);
        this.phoneNumber = phoneNumber;
    }

    public Admin(String login, String password, String name, String surname, String phoneNumber)
    {
        super(login, password, name, surname);
        this.phoneNumber = phoneNumber;
        dateCreated = LocalDate.now();
    }

    public Admin(int i, String s, String s1, String s2, String s3, String s4) {
        super(i, s, s1, s2, s3);
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + phoneNumber;
    }
}
