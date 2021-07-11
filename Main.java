package bullscows;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
            System.out.println("Input the length of the secret code:");
            String str = scanner.nextLine();
            int userInput = 0;
            int possSecretCodeLen = 0;
            try {
                userInput = Integer.parseInt(str);
                System.out.println("Input the number of possible symbols in the code:");
                str = scanner.nextLine();
                possSecretCodeLen = Integer.parseInt(str);
            } catch ( Exception e) {
                System.out.println("Error: \"" + str + " isn't a valid number.");
                System.exit(0);
            }

        String allPossChar = "0123456789abcdefghijklmnopqrstuvwxyz";
        if (userInput > 36 || userInput == 0)  {
            System.out.println("Error: can't generate a secret number with a length of " + userInput +
                    " because there aren't enough unique digits." );
        } else if( possSecretCodeLen > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if(possSecretCodeLen < userInput) {
            System.out.format("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", userInput ,possSecretCodeLen);
        } else {
             String output = generateRandomCode(allPossChar, userInput, possSecretCodeLen);
            //System.out.println(output);
            System.out.println("Okay, let's start a game!");
            grade(output, userInput);
        }
    }

    public static String generateRandomCode(String allPossChar, int userInput , int possSecretCodeLen) {
        if (possSecretCodeLen < 11) {
            System.out.println("The secret is prepared: " + "*".repeat(userInput) + " (0-"+ allPossChar.charAt(possSecretCodeLen-1) + ").");
        } else {
            System.out.println("The secret is prepared: " + "*".repeat(userInput) + " (0-9, a-" + allPossChar.charAt(possSecretCodeLen-1) + ").");
        }

        StringBuilder secretCode = new StringBuilder();
        Set<Character> uniqCode = new HashSet<>();
        while (secretCode.length() != userInput) {
            int charIndex = (int) ((possSecretCodeLen - 1) * Math.random());
            if (!uniqCode.contains(allPossChar.charAt(charIndex))) {
                secretCode.append(allPossChar.charAt(charIndex));
                uniqCode.add(allPossChar.charAt(charIndex));
            }
        }
        return secretCode.toString();
    }

    public static String generateSecretCode(int userInput) {
        StringBuilder output = new StringBuilder();
        int arrCur = 0;
        long pseudoRandomNumber = System.nanoTime();
        while (output.length() < userInput) {
            int digit = (int) (pseudoRandomNumber % 10);
            pseudoRandomNumber = pseudoRandomNumber / 10;
            if (arrCur == 0) {
                if (digit != 0 ) {
                    output.append(digit);
                    arrCur++;
                }
            } else {
                boolean dup = false;
                char charDigit = (char) (digit + '0');
                for (int i = 0; i < arrCur; i++) {
                    if (output.charAt(i) == charDigit) {
                        dup = true;
                        break;
                    }
                }
                if (!dup) {
                    output.append(digit);
                    arrCur++;
                }
            }
            if (pseudoRandomNumber == 0) {
                pseudoRandomNumber = System.nanoTime();
            }
        }
        return  output.toString();
    }

    public static void grade(String secretCode, int userInput) {
        int bull = 0;
        int cow = 0;
        int tryCount = 1;
        Set<Character> uniqueSecCode = new HashSet<>();
        for (int i = 0; i < secretCode.length(); i++) {
            uniqueSecCode.add(secretCode.charAt(i));
        }
        while (true) {
            System.out.println("Turn " + tryCount + ":");
            Scanner sc = new Scanner(System.in);
            String guess = sc.next();

            for (int i = 0; i < guess.length(); i++) {
                if (guess.charAt(i) == secretCode.charAt(i)) {
                    bull++;
                } else if ( uniqueSecCode.contains(guess.charAt(i))) {
                    cow++;
                }
            }

            if (cow != 0 && bull != 0) {
                System.out.println("Grade: " + bull + " bull(s) and " +
                        cow + " cow(s).");
            } else if (cow == 0 && bull == 0) {
                System.out.println("Grade: None.");
            } else if (cow == 0 ) {
                System.out.println("Grade: " + bull + " bull(s).");
            } else {
                System.out.println("Grade: " + cow + " cow(s).");
            }
            tryCount++;
            if (bull == userInput) {
                break;
            }
            bull = 0;
            cow = 0;
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }
}
