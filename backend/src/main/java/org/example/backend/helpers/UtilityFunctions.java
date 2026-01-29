package org.example.backend.helpers;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilityFunctions {

    public String createId(){

        return UUID.randomUUID().toString().trim().substring(0, 4);
    }

}
