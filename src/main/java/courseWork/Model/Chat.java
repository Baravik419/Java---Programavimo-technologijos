package courseWork.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Chat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "parentChat", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Chat> repliedChats;
    @ManyToOne
    private Chat parentChat;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Chat chat;
    @ManyToOne
    private Client chatOwner;

    public Chat(String body, Client client, Client chatOwner) {
        this.body = body;
        this.client = client;
        this.chatOwner = chatOwner;
        this.timestamp = LocalDateTime.now();
    }

    public Chat(String body, Chat parrentChat, Client chatOwner) {
        this.body = body;
        this.parentChat = parrentChat;
        this.chatOwner = chatOwner;
        this.timestamp = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return chatOwner.name + " " + body + " " + timestamp;
    }
}