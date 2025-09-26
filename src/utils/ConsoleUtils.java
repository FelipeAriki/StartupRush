package utils;

import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String ask(String question){
        System.out.println(question + ": ");
        return scanner.nextLine();
    }
}
