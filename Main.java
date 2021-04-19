import java.util.Calendar;
import java.util.Scanner;


public class Main {

    public static void main(String args[]) throws Exception{
        int option=1;
        Scanner input = new Scanner(System.in);
        Calendar cal = Calendar.getInstance();

        System.out.println("Welcome. Enter value(1-5) for below actions.\n 1. AES encryption/decryption \n 2. RSA encryption and decryption \n 3. RSA Signature \n 4. SHA-256 hashing \n 5. Exit.\n");
        
        while(option>=1 && option<5){
            System.out.print("Enter your option: ");
            option = input.nextInt();

            switch(option){
                case 1: //AES encryption/decryption with two key lengths, 128 and 256 bits, and two modes ECB and CFB
                    //© https://www.baeldung.com/java-aes-encryption-decryption?fbclid=IwAR3eYH4d8m4ufJn7hwwWq-9HENowH4xiHFXYP_ObqWQUulVYtSy1dGoCoHA
                    System.out.println("AES encryption/decryption with two key lengths, 128 and 256 bits, and two modes ECB and CFB");
                    System.out.println("Enter value(1-2) for below actions.\n 1. Encryption \n 2. Decryption");
                    Scanner inp1 = new Scanner(System.in);
                    System.out.print("Enter your option: ");
                    int opt1 = inp1.nextInt(); 
                    if(opt1 == 1){
                        Scanner inp2 = new Scanner(System.in);
                        System.out.print("Enter encryption mode(ECB/CFB): ");
                        String opt2 = inp2.nextLine();
                        Scanner inp3 = new Scanner(System.in);
                        System.out.print("Enter key length(128/256): ");
                        int opt3 = inp3.nextInt();
                        long startTime = System.nanoTime();
                        AES_EncrypDecryp.encryption(opt2, opt3);
                        long endTime = System.nanoTime();
                        System.out.println("Execution time: "+ (endTime - startTime)/1000000 + " millisec");

                    }
                    else{
                        long startTime = System.nanoTime();
                        AES_EncrypDecryp.decryption();
                        long endTime = System.nanoTime();
                        System.out.println("Execution time: "+ (endTime - startTime)/1000000 + " millisec");
                    }

                    break;

                case 2: //RSA encryption and decryption
                    //© https://www.novixys.com/blog/rsa-file-encryption-decryption-java/?fbclid=IwAR2Nu92vDGPPbz6Oo5GDgxEoWjBAq1G7zTIgc7SkOvGOeJRcmcwXqEhjHDI
                    //© https://stackoverflow.com/questions/31944374/badpaddingexception-decryption-error
                    System.out.println("RSA encryption and decryption");
                    System.out.println("Enter value(1-2) for below actions.\n 1. Encryption \n 2. Decryption");
                    System.out.print("Enter your option: ");
                    Scanner inp = new Scanner(System.in);
                    int opt = inp.nextInt(); 
                    if(opt == 1){
                        long startTime = System.nanoTime();
                        System.out.println("RSA encryption");
                        RSA_EncrypDecryp.encryption();
                        long endTime = System.nanoTime();
                        System.out.println("Execution time: "+ (endTime - startTime)/1000000 + " millisec");
                    } 
                    else{
                        long startTime = System.nanoTime();;
                        System.out.println("RSA decryption");
                        RSA_EncrypDecryp.decryption();
                        long endTime = System.nanoTime();
                        System.out.println("Execution time: "+ (endTime - startTime)/1000000 + " millisec");
                    }
                    break;
                
                case 3: //RSA signature 
                    //© https://niels.nu/blog/2016/java-rsa?fbclid=IwAR3mKaOCJNk5v3ric_xnZJ2H3Rd1W8mgoh0jWiJJ-3K_d6LoEp25jcD9ZuU
                    //© https://www.baeldung.com/java-digital-signature?fbclid=IwAR3j1OP4785ZVvL0pX5K9YrJ2Bw0iHR0fI5WZ_mOHpoRJ-h1O690u-u16rc
                    long startTime = System.nanoTime();
                    System.out.println("RSA signature");
                    RSA_Signature.init();
                    RSA_Signature.createSign();
                    RSA_Signature.verification();
                    long endTime = System.nanoTime();
                    System.out.println("Execution time: "+ (endTime - startTime)/1000000 + " millisec");
                    break;

                case 4: //SHA-256   
                    //© https://mkyong.com/java/java-sha-hashing-example/
                    long startT = System.nanoTime();
                    System.out.println("SHA-256 hashing");
                    SHA_Hashing.hash();
                    long endT = System.nanoTime();
                    System.out.println("Execution time: "+ (endT - startT)/1000000 + " millisec");
                    break;
            }
           
        } 
        System.out.println("Exiting.");
        input.close();

    }

}
