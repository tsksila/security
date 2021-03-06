import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class twentyfourBitBlockDecoder {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String  FileDirectory = "C:\\Users\\silal\\Desktop\\24bitEncode.txt" ; 
        System.out.println("-------------- 24 Bit-Block  Decoder--------------");



        String plaintext ;
        Path path = Paths.get(FileDirectory);

        if (Files.exists(path)) {
            System.out.print("Input the Encoding message : ");
            plaintext = ReadFile(FileDirectory);
        }
        else{
            System.out.print("Input the Encoding message : ");
             plaintext = input.next();
        }


        ArrayList<String> plaintext_array = new ArrayList<String>();

        for (int i = 0; i < plaintext.toCharArray().length/24; i++) {

            String Text = "" ;
            for (int j = i*24; j < (i*24)+24; j++) {
                Text += String.valueOf(plaintext.toCharArray()[j]);
            }

            plaintext_array.add(Text) ;

        }

         /* Decode  by function */

         String output = "";

         for (int i = 0; i < plaintext_array.size() ; i++) {        
 
             output +=  BinaryToString(XOR(Shift(SubstitionBit(Transposition(plaintext_array.get(i)))))) ;
         }
 
         String result = DeletePadding(output) ;
 
         System.out.print("Result : ");
         
         System.out.println(result);
 
 
     }

    /* ------------ FUNCTION --------------- */

    static String convToBinary(char message) {

        return String.format("%8s", Integer.toBinaryString(message)).replaceAll(" ", "0");
    }

    static String Shift(String message) {

        int key = 5; // KEY FOR SHIFT

        char[] shitfMessage = message.toCharArray();

        for (int i = 0; i < key; i++) {


            char temp = shitfMessage[0];

            for (int j = 0 ; j < shitfMessage.length - 1; j++) {
                shitfMessage[j] = shitfMessage[j + 1];
            }
            shitfMessage[shitfMessage.length - 1] = temp ;
        }

        return String.valueOf(shitfMessage);

    }

    static String Transposition(String message) {

        int[] Key = { 7, 4, 3, 5, 2, 1, 6, 0, 11, 15, 13, 10, 12, 8, 9, 14 ,19 , 16 , 20 , 22 , 17 , 18 ,21 ,23 }; // KEY FOR TRANSPOSITION
        char[] message_array = message.toCharArray();
        char[] storeValue = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' ,'0', '0', '0', '0' ,'0','0','0'};

        for (int i = 0; i < message_array.length; i++) {
            storeValue[Key[i]] = message_array[i];
        }

        return String.valueOf(storeValue);

    }

    static String SubstitionBit(String message) {

        String[] str_val = { message.substring(0, 12), message.substring(12, 24) };
        String[] col = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011","1100", "1101", "1110", "1111" };
        String[] row = { "000", "001", "010", "100","101","110","111" };
        String[][] table = {
            {"0010", "1100", "0100", "0001", "0111", "1010", "1011", "0110", "1000", "0101", "0011", "1111", "1101", "0000", "1110", "1001"},
            {"1110", "1011", "0010", "1100", "0100", "0111", "1101", "0001", "0101", "0000", "1111", "1010", "0011", "1001", "1000", "0110"},
            {"0100", "0010", "0001", "1011", "1010", "1101", "0111", "1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
            {"1011", "1000", "1100", "0111", "0001", "1110", "0010", "1101", "0110", "1111", "0000", "1001", "1010", "0100", "0101", "0011"},
            {"1010", "1101", "0111", "1000", "0100", "0010", "0001", "1011", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
            {"1111", "0100", "0010", "0001", "1011", "1001", "0111", "1000", "0110", "0011", "0000", "1110", "1100", "0101", "1010", "1101"},
            {"1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110", "0100", "0010", "0001", "1011", "1010", "1101", "0111"}
            };
            // KEY SubstitionBit

        ArrayList<Integer> index = new ArrayList<>();

        for (int i = 0; i < str_val.length; i++) {

            int match  = 0 ;

            for (int j = 0; j < row.length; j++) {
                if (str_val[i].substring(0, 3).equals(row[j])) {
                    match = j;
                }
            }

            for (int j = 0; j < table[match].length; j++) {
                if(str_val[i].substring(8,12).equals(table[match][j])) {
                    index.add(j) ;
                }
            }
        }


         String value = str_val[0].substring(0, 8) + col[  index.get(0) ];
        String value2 = str_val[1].substring(0, 8) + col[  index.get(1) ];

    
 
        return value + value2;
    }

    static String XOR(String message) {

        char[] message_array = message.toCharArray();
        char[] Key = { '1', '0', '1', '0', '0', '1', '0', '0', '1', '1', '0', '1', '0', '0', '1', '0' , '0' ,'1','1', '0', '0', '1', '1', '0' }; //KEY  XOR
        int[] val =  { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };
        for (int i = 0; i < Key.length; i++) {
            val[i] = message_array[i] ^ Key[i];
        }
        String Text = "";
        for (int i = 0; i < val.length; i++) {
            Text += String.valueOf(val[i]);
        }

        return Text;
    }

    static String BinaryToString (String message) {

        String s = message;
        String str = "";

        for (int i = 0; i < s.length() / 8; i++) {

            int a = Integer.parseInt(s.substring(8 * i, (i + 1) * 8), 2);
            str += (char) (a);
        }

        return str;
    }

    static String DeletePadding (String message) {

        
    
        for (int i = 0; i < 2 ; i++) {
            if(message.substring(message.length()-1).equals("X")) {
                message = message.substring(0,message.length()-1) ;
                System.out.println("Delete Padding");
      
                
              }
        }
        
        return message ;
      } 

    

 static String ReadFile(String path) {
    File file = new File(path);

    try {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String message = "" ;
        String line;
        while ((line = br.readLine()) != null) {
            message +=line ;
            System.out.println(line);
        }
        br.close();

        String bitmessage = "";
        for (int i = 0; i < message.toCharArray().length; i++) {
                bitmessage += convToBinary(message.toCharArray()[i]);
        }

        System.out.println("Bit message :"+bitmessage);

        return bitmessage ;
    } catch (IOException e) {
        e.printStackTrace();
    }

    return "null" ;

}

}
