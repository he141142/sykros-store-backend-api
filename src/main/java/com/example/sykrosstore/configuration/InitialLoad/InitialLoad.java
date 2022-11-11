package com.example.sykrosstore.configuration.InitialLoad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialLoad implements IInitialLoad {
    String fileName;
    String location;

    public InitialLoad(String arg1,String arg2){
        this.location = arg1;
        this.fileName = arg2;
    }
    public InitialLoad(){
    }

    @Override
    public String getAbsolutePath() {
        return this.getLocation()+this.getFileName();
    }
}
