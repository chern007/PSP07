/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp07_tarea;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            FileOutputStream fos = new FileOutputStream("fichero.cifrado"); //fichero de salida

            byte[] textoCifrado = cifrador.doFinal(textoSinCifrar.getBytes());

            System.out.println("2) TEXTO CIFRADO (HEXADECIMAL)");

            //------------------------------------------------------------------
            // pasamos a hexadecimal el texto para imprimirlo caracter a caracter
            for (byte b : textoCifrado) {
                System.out.print(Integer.toHexString(0xFF & b));
            }
            System.out.println();
            //------------------------------------------------------------------           

            fos.write(textoCifrado);

            fos.close();

            //**************************************************************************************
            //***PASAMOS A DESENCRIPTAR EL CONTENIDO DEL ARCHIVO PARA PODERLO IMPRIMIR EN VENTANA***
            //**************************************************************************************
            cifrador.init(Cipher.DECRYPT_MODE, clave); 
            
            byte[] bufferDescifrado;
            Path ruta = Paths.get("fichero.cifrado");
            byte[] bufferCifrado = Files.readAllBytes(ruta);
            
            bufferDescifrado = cifrador.doFinal(bufferCifrado); //desciframos todos los bytes
            
            System.out.println("3) TEXTO DESCIFRADO DEL ARCHIVO CIFRADO:\n" + new String(bufferDescifrado));
            

        //controlamos e identificamos todas las excepciones
        
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("El algoritmo de cifrado no está disponible.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            System.err.println("El algoritmo de cifrado no está disponible.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            System.err.println("La clave no es correcta.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);            
        } catch (IllegalBlockSizeException ex) {
            System.err.println("La longitud de la clave no es de 128 bits.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            System.err.println("La clave no es correcta.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            System.err.println("No se ha podido encontrar el fichero.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {            
            System.err.println("No se ha podido acceder al fichero.");
            Logger.getLogger(PSP07_tarea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String sacaCadena() {

        char[] abecedario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        String cadena = "";
        
        //la cadena tendrá una longitud de entre 5 y 25 caracteres
        
        int min = 5;
        int max = 25;       

        int numeroDeLetras = new Random().nextInt(max-min) + min;     

        for (int i = 0; i < numeroDeLetras; i++) {

            cadena += abecedario[new Random().nextInt(27)];

        }

        return cadena;
    }

}
