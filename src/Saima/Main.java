package Saima;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        String word = cin.nextLine();
        int num = cin.nextInt();
        cin.close();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= word.length(); i++) {
            if (i == word.length()) {
                builder.append(word.charAt(i - 1));
                break;
            }
            if (num > 0 && word.charAt(i) < word.charAt(i - 1)) {
                num--;
                while (num > 0 && builder.length() > 0) {
                    if (word.charAt(i) < builder.charAt(builder.length() - 1)) {
                        num--;
                        builder.deleteCharAt(builder.length() - 1);
                    }else
                        break;
                }
            } else {
                builder.append(word.charAt(i - 1));
            }
        }
        System.out.println(builder.substring(0, builder.length() - num));
    }
}
