package com.ar.blockchain.spring_block.entities;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;



@Getter @Setter
@ToString
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class BlockFile {
    String hash;
    String previousHash;
    String data;
    long timeStamp;
    int nonce; // numero arbitrario usado en la criptografia    
 
    public BlockFile(String data, String previousHash, long timeStamp, File file) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateFileHash(file);
    }


    public String calculateFileHash(File file) {
        try{
            byte[] bytesFiles = Files.readAllBytes(file.toPath());        
            this.data =  getStringHash(bytesFiles);
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
        }
        return calculateFileBlockHash();
    }

    private String calculateFileBlockHash(){
        String dataHash = previousHash 
                          + Long.toString(timeStamp) 
                          + Integer.toString(nonce)
                          + data; // Aquí incluimos el hash del archivo

        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            // Instanciamos el tipo de algoritmo de encriptación
            digest = MessageDigest.getInstance("SHA-256");

            // Codificamos el hash y obtenemos los bytes
            bytes = digest.digest(dataHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | IOException ex) {
            ex.printStackTrace();
        }

        // Transformamos en un string hexadecimal para el hash
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    // Método para calcular el hash de una cadena de bytes
    private String getStringHash(byte[] inputBytes) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputBytes);
            for (byte b : hash) {
                hexString.append(String.format("%02x", b)); // Convertimos a formato hexadecimal
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    //Metodo para minar el bloque.
    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateFileBlockHash();
        }
        return hash;
    }
}
