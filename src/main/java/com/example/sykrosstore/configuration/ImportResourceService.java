package com.example.sykrosstore.configuration;

import com.example.sykrosstore.configuration.InitialLoad.Constants;
import com.example.sykrosstore.configuration.InitialLoad.RoleInitialLoad;

public class ImportResourceService {
    private RoleInitialLoad roleInitialLoad;

    public RoleInitialLoad getRoleInitialLoad() {
        return roleInitialLoad;
    }

    public void setRoleInitialLoad(RoleInitialLoad roleInitialLoad) {
        this.roleInitialLoad = roleInitialLoad;
    }

    public String getInitialFilePath(Constants.LOAD_TYPE load_type) {
        switch (load_type) {
            case ROLE:
                return this.roleInitialLoad.getLocation() + this.roleInitialLoad.getFileName();
            default:
                return "";
        }
    }

    public String getFileName(Constants.LOAD_TYPE load_type) {
        if (load_type == Constants.LOAD_TYPE.ROLE) {
            return this.roleInitialLoad.getFileName();
        }
        return "";
    }

    public String getFileLocation(Constants.LOAD_TYPE load_type) {
        if (load_type == Constants.LOAD_TYPE.ROLE) {
            return this.roleInitialLoad.getLocation();
        }
        return "";
    }
}
