/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp07_tarea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;

/**
 *
 * @author chern007
 */
public class PSP07_tarea {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Por favor, introduce tu nombre de usuario:");
        String nombreUsuario = entrada.nextLine();
        System.out.println("Por favor, introduce tu contraseña:");
        String contraseñaUsuario = entrada.nextLine();

        String textoSinCifrar = sacaCadena();

        System.out.println("1) TEXTO SIN CIFRAR\n" + textoSinCifrar);

        String semilla = nombreUsuario + contraseñaUsuario;

        try {

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(semilla.getBytes());

            keyGen.init(128, secureRandom); // 128 is the safest one to 

            SecretKey clave = keyGen.generateKey(); //genera la clave privada

            //Se Crea el objeto Cipher para cifrar, utilizando el algoritmo AES
            //Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //Se inicializa el cifrador en modo CIFRADO o ENCRIPTACIÓN
            cifrador.init(Cipher.ENCRYPT_MODE, clave);

            //declaración  de objetos
            FileOutputStream fs = new FileOutputStream("fichero.cifrado"); //fichero de salida

            byte[] textoCifrado = cifrador.doFinal(textoSinCifrar.getBytes());

            System.out.println("2) TEXTO CIFRADO");

            //------------------------------------------------------------------
            // Se escribe byte a byte en hexadecimal el texto
            // encriptado para ver su pinta.
            for (byte b : textoCifrado) {
                System.out.print(Integer.toHexString(0xFF & b));
            }
            System.out.println();
            //------------------------------------------------------------------            

            fs.write(textoCifrado);

            fs.close();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String sacaCadena() {

        char[] abecedario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        String palabra = "";

        int numeroDeLetras = new Random().nextInt(25);//25 maxima logitud de la palabra      

        for (int i = 0; i < numeroDeLetras; i++) {

            palabra += abecedario[new Random().nextInt(27)];

        }

        return palabra;
    }

}
