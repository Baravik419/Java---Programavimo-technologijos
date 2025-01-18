package courseWork;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cmd = "";

        while (!cmd.equals("q")) {
            System.out.println("""
                    Choose an option
                    u - work with users
                    p - work with publications
                    w - write to file as text all users
                    q - quit
                   """);

            cmd = scanner.nextLine();

            switch (cmd) {
                case "u":

            }
        }
    }
}
