package com.alejandro.rio.model;

public class Guild {
    private final String name;
    private final String realm;

    public Guild(String name, String realm) {
        this.name = name;
        this.realm = realm;
    }

    public String getName() {
        return name;
    }

    public String getRealm() {
        return realm;
    }


}
