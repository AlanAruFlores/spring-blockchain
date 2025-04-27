package com.ar.blockchain.spring_block.services.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.ar.blockchain.spring_block.entities.Block;
import com.ar.blockchain.spring_block.entities.BlockFile;
import com.ar.blockchain.spring_block.services.BlockService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.springframework.core.io.ClassPathResource;
import java.io.File;


@Service
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class BlockServiceImpl implements BlockService {

    Logger logger = Logger.getAnonymousLogger();


    //Correr un ejemplo simple
    @Override
    public void runBlockExample() {
        List<Block> blockchain = new ArrayList<>();
        int prefix = 4; // numero del prefijo

        //Calculo el prefijo del string.
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        Block newBlock = new Block(
            "The is a New Block.", 
            blockchain.isEmpty() ? "0" : blockchain.get(blockchain.size() - 1).getHash()
            ,
            new Date().getTime());
        newBlock.mineBlock(prefix);



        
        logger.info("Informacion del bloque:" +newBlock);
        logger.info("Prefix String: "+prefixString);
        
        //Validamos que es valido 
        if(newBlock.getHash().substring(0, prefix).equals(prefixString)){
            logger.info("Son identicos");
        }else{
            logger.info("NO Son identicos");
        }
        
        blockchain.add(newBlock);
    }
    
    //Ejemplo core.
    @Override
    public void runFilesWithBlockChain(){

        //Ruta del archivo
        URL resourceUrl = getClass().getClassLoader().getResource("remitoform.pdf");

        if(resourceUrl == null){
            throw new RuntimeException("El archivo no existe");
        }else{
            Logger.getAnonymousLogger().info("Si existe el archivo");
        }

        List<BlockFile> blockchain = new ArrayList<>();
      
        //Obtengo el archivo
        File file = new File(resourceUrl.getFile());




        //Instancio un bloque del archivo
        BlockFile newBlock = new BlockFile(
            "The is a New Block.", 
            blockchain.isEmpty() ? "0" : blockchain.get(blockchain.size() - 1).getHash()
            ,
            new Date().getTime(), file);


        int prefix = 4; // Número de ceros requeridos para minar el bloque

        // Calcular el prefijo del string
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        
        newBlock.mineBlock(prefix);

        logger.info("Informacion del bloque:" +newBlock);
        logger.info("Prefix String: "+prefixString);

        //Validamos que es valido 
        if(newBlock.getHash().substring(0, prefix).equals(prefixString)){
            logger.info("Son identicos");
        }else{
            logger.info("NO Son identicos");
        }
        
        
        blockchain.add(newBlock);

    }




    //Demo (en proceso): campos interactivos
    @Override
    public void checkFormFields(){

        try (PDDocument document = PDDocument.load(new ClassPathResource("remitoform.pdf").getInputStream())) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                if (acroForm != null) {
                    for (PDField field : acroForm.getFields()) {
                        System.out.println("Campo: " + field.getFullyQualifiedName());
                    }
                } else {
                    System.out.println("Este PDF no contiene un formulario interactivo (AcroForm).");
                }

        }catch(Exception ex){
            logger.warning(ex.getMessage());
        }
    }

}
