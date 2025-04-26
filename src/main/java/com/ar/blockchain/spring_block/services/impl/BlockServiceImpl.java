package com.ar.blockchain.spring_block.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ar.blockchain.spring_block.entities.Block;
import com.ar.blockchain.spring_block.services.BlockService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class BlockServiceImpl implements BlockService {

    Logger logger = Logger.getAnonymousLogger();

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
        
        if(newBlock.getHash().substring(0, prefix).equals(prefixString)){
            logger.info("Son identicos");
        }else{
            logger.info("NO Son identicos");
        }
        //assertTrue(newBlock.getHash().substring(0, prefix).equals(prefixString));
        blockchain.add(newBlock);
    }
    


}
