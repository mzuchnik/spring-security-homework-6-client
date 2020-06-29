package pl.mzuchnik.springsecurityhomework6client;

import org.apache.tomcat.util.net.jsse.PEMFile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.nio.CharBuffer;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

@SpringBootTest
class SpringSecurityHomework6ClientApplicationTests {


    @Test
    void readPrivateKey(){

        try {
            File file = new File("src\\main\\resources\\keys\\privateKey.key");

            Scanner scanner = new Scanner(new FileInputStream(file));
            String encodedPrivateKey="";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.startsWith("-----BEGIN") || line.startsWith("-----END")){
                    continue;
                }
                encodedPrivateKey += line;
            }
            System.out.println("Zakodowana wartość klucza");
            System.out.println(encodedPrivateKey);

            System.out.println("Odkodowana wartość klucza");
            Base64.Decoder decoder = Base64.getDecoder();

            System.out.println(decoder.decode(encodedPrivateKey));

        } catch (FileNotFoundException e) {
            System.err.println("Plik z kluczem prywatnym nie istnieje");
            e.printStackTrace();
        }
    }

    @Test
    void generateKey() throws GeneralSecurityException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();


        Base64.Encoder encoder = Base64.getEncoder();

        String publicKeyName = "publicKey";

        Writer out = new FileWriter(publicKeyName + ".pub");
        out.write("-----BEGIN RSA PUBLIC KEY-----\n");
        out.write(encoder.encodeToString(publicKey.getEncoded()));
        out.write("\n-----END RSA PUBLIC KEY-----\n");
        out.close();

        String privateKeyName = "privateKey";

        Writer out2 = new FileWriter(privateKeyName + ".key");
        out2.write("-----BEGIN RSA PRIVATE KEY-----\n");
        out2.write(encoder.encodeToString(privateKey.getEncoded()));
        out2.write("\n-----END RSA PRIVATE KEY-----\n");
        out2.close();





    }
}
