package com.ar.blockchain.spring_block.entities;

import java.io.UnsupportedEncodingException;
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
public class Block {
    String hash;
    String previousHash;
    String data;
    long timeStamp;
    int nonce; // numero arbitrario usado en la criptografia    
 
    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
    }

    public String calculateBlockHash(){

        //Concatenamos cada parte del bloque
        String dataHash = previousHash  
        + Long.toString(timeStamp) 
        + Integer.toString(nonce) 
        + data;



        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            //Instanciamos el tipo de algoritmo de encriptacion:
            digest = MessageDigest.getInstance("SHA-256");

            //Codificamos el hash y la obtenemos en bytes
            bytes = digest.digest(dataHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, ex.getMessage());
        }

        //Tranformamos en un string hexadecimal para el hash
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();

    }

    //Metodo para minar el bloque.
    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)) {
            nonce++;
            hash = calculateBlockHash();
        }
        return hash;
    }
}
