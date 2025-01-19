package courseWork.Utils;

import courseWork.Model.BookExchange;
import courseWork.Model.Client;
import courseWork.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReadWriteOperation {

    private static String fileName;

    public static void writeToFile(String fileName, BookExchange bookExchange)
    {
        try {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectoutputstream.writeObject(bookExchange);
            objectoutputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BookExchange readFromFile()
    {
        BookExchange bookExchange = null;
        try {
            ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream("data.txt"));
            bookExchange = (BookExchange)objectinputstream.readObject();
            objectinputstream.close();
        } catch (IOException | NoClassDefFoundError | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookExchange;
    }

    public static void writeToFileAsText(List<User> users)
    {
        try (FileWriter fileWriter = new FileWriter( "users.txt")){
            for(User u : users)
                //filewriter.append
                fileWriter.append(u.getId() + ":" + u.getLogin() + ":" + u.getPassword() + ":" + u.getName() + ":" + u.getSurname() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getUsersFromFile()
    {
        List<User> users = new ArrayList<>();
        try(Scanner sc = new Scanner(new File("users.txt"))){
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] info = line.split(":");
                int id = Integer.parseInt(info[0]);
                Client client = new Client(id, info[1], info[2], info[3], info[4]);
                users.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


}
