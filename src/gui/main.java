package gui;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class main {
	
	private static final String HMAC_ALGO = "HmacSHA256";
	
	public main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		
		String[] argsArr = Arrays.copyOf(args, args.length);
		if (argsArr.length < 1 || argsArr.length % 2 == 0) {
			System.out.println("Invalid input data!");
			System.out.println("Enter an odd number of lines of 3 or more.\nThe characters in the string should not be repeated.");
			System.out.println("Example: \"abc def hjo\" or \" 1 2 3 4 5 6 7\"");
			System.out.println("Please try again!");
			return;
		}		
		if (!isUnique(argsArr)) {
				System.out.println("Invalid input data!");
				System.out.println("Enter an odd number of lines of 3 or more.\nThe characters in the string should not be repeated.");
				System.out.println("Example: \"abc def hjo\" or \" 1 2 3 4 5 6 7\"");
				System.out.println("Please try again!");
				return;
			}		
		
		while (true) {			
				        
			//Creating a 128-bit key
	        SecureRandom secureRandom = new SecureRandom();
	        byte []bytes = new byte[16];
	        secureRandom.nextBytes(bytes);	        
	        
	        //Move PC
	        Random randomMove = new Random();
			int index = randomMove.nextInt(argsArr.length);
			int pcmove;
			pcmove = index;
			
			//Creating an HMAC
			Mac signer = Mac.getInstance(HMAC_ALGO);
	        SecretKeySpec keySpec = new SecretKeySpec(bytes, HMAC_ALGO);
	        signer.init(keySpec);
	        byte[] digest = signer.doFinal(argsArr[pcmove].getBytes("utf-8"));
	        System.out.println("HMAC: " + bytesToHex(digest).toUpperCase());
			
	        //Output of possible moves
	        int usermove = -1;
			while (usermove == -1) {
				System.out.println("Available moves:");
				for (int i1 = 0; i1 < argsArr.length; i1++) {
					System.out.println(i1 + 1 + ". - " + argsArr[i1]);
				}
				System.out.println("0. - exit");
			
				//Reading user input
				Scanner scanner = new Scanner(System.in);
				System.out.print("Enter your move: ");
				if (scanner.hasNextInt()) {
					int userindex = scanner.nextInt();
					if (userindex > -1 & userindex <= argsArr.length) {
						usermove = userindex;
				} else {
					continue;
			}}}
			
			//Output of the user's choice
            String strWord = Integer.toString(usermove);
            if ("0".equalsIgnoreCase(strWord)) {
                break;
            } else {
                System.out.println("Your move: " + argsArr[usermove - 1]); 
            }
            System.out.println("Computer move: " + argsArr[pcmove]);
            
            //Determining the winner
            String []winArr = new String[argsArr.length];
            String []winArr1 = Arrays.copyOf(argsArr, argsArr.length);
            while (winArr[0] != argsArr[usermove - 1]) {
            	winArr[0] = winArr1[winArr1.length - 1];
            	for (int i = 1; i < winArr1.length; i++) {
            		winArr[i] = winArr1[i - 1];
            	}
            	winArr1 = Arrays.copyOf(winArr, winArr.length);
            }			
            int pcWinIndex = 0;
            for (int i = 0; i < winArr.length; i++) {
            	if (winArr[i] == argsArr[pcmove]) {
            		pcWinIndex = i;
            		break;
            	}
            }
            if (pcmove == usermove - 1) {
            	System.out.println("Draw!");
            } else if (pcWinIndex >= 1 && pcWinIndex <= argsArr.length / 2) {
            	System.out.println("You loss!");
            } else {
            	System.out.println("You win!");
            }
            
            //HMAC key
            System.out.println("HMAC key: " + bytesToHex(bytes).toUpperCase());
			
            //Cyclic shift of the array by 1 step
			String []tempArr = new String[argsArr.length];
			tempArr[0] = argsArr[argsArr.length - 1];
			for (int i = 1; i < argsArr.length; i++) {
				tempArr[i] = argsArr[i - 1];
			}
			argsArr = tempArr;
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}}
	static boolean isUnique(String[] strings) {
	    HashSet<String> set = new HashSet<>();
	    for (String s : strings) {
	        if (! set.add(s)) {
	            return false;
	        }
	    }
	    return true;
    }
	//Bytes in HEX
	public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length*2);
        for(byte b: bytes) {
           sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
