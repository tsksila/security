package homework2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VigenereEncoder {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        String FileDirectory = "C:\\Users\\sila2\\Desktop\\"; // file directory for create
        String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z" };

        System.out.println("Vigenere Table Encoder ");

        /* Create Table Vigenere */

        ArrayList<ArrayList<String>> table = new ArrayList<>();

        for (int i = 0; i <= alphabet.length; i++) {
            ArrayList<String> list = new ArrayList<>();

            for (int j = 0; j < alphabet.length; j++) {

                list.add(alphabet[Math.abs(i + j) % alphabet.length]);
            }

            table.add(list);

        }

        /* Show Table Vigenere */
        for (int i = 0; i < table.size() - 1; i++) {
            System.out.println(table.get(i));
        }

        System.out.println("Vigenere  Encoder");

        /* Input value */

        System.out.print("Input the plaintext message : ");
        String plaintext = input.nextLine();
        ArrayList<String> plaintext_array = new ArrayList<String>();

        for (int i = 0; i < plaintext.toCharArray().length; i++) {
            plaintext_array.add(String.valueOf(plaintext.toUpperCase().toCharArray()[i]));
        }

        System.out.print("Input the Key : ");
        String key = input.nextLine();

        ArrayList<String> key_array = new ArrayList<String>();

        for (int i = 0; i < key.toCharArray().length; i++) {
            key_array.add(String.valueOf(key.toUpperCase().toCharArray()[i]));
        }

        /* check size of array between plaintext and key */

        for (int i = 0; i < plaintext_array.size() - 1; i++) {
            if (key_array.size() < plaintext_array.size()) {
                key_array.add(key_array.get(i));
            }
        }

        /* vegenere encoding process */

        ArrayList<ArrayList<Integer>> matchIndex = new ArrayList<>();

        for (int i = 0; i < plaintext_array.size(); i++) {

            ArrayList<Integer> list = new ArrayList<>();

            for (int j = 0; j < alphabet.length; j++) {

                if (plaintext_array.get(i).equals(alphabet[j])) {
                    list.add(j);
                }
            }
            for (int k = 0; k < alphabet.length; k++) {
                if (key_array.get(i).equals(alphabet[k])) {
                    list.add(k);
                }
            }

            matchIndex.add(list);
        }

        /* show value encoding */
        String EncodeMessage = "";

        System.out.print("Result : ");
        for (int i = 0; i < plaintext_array.size(); i++) {

            EncodeMessage += table.get(matchIndex.get(i).get(0)).get(matchIndex.get(i).get(1));
            System.out.print(table.get(matchIndex.get(i).get(0)).get(matchIndex.get(i).get(1)));
        }

        CreateAndWriteFile(FileDirectory, EncodeMessage);

    }

    static void CreateAndWriteFile(String Path, String message) {

        String filename = "VigenereEncode.txt";
        System.out.println();
        /* Create File */
        try {
            File myObj = new File(Path + filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        /* Write file */
        try {
            FileWriter myWriter = new FileWriter(Path + filename);
            myWriter.write(message);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
