package org.example.backend.helpers;

import java.util.UUID;

public class UtilityFunctions {

    public String createId(){

        return UUID.randomUUID().toString().trim().substring(0, 4);
    }

}
