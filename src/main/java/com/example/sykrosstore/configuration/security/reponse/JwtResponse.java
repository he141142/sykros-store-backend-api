package com.example.sykrosstore.configuration.security.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    String passwordHash;
    String userName;
    String hash;

    public JwtResponse(JwtResponseBuilder __builder) {
        this.userName = __builder.userName;
        this.hash = __builder.hash;
        this.passwordHash = __builder.passwordHash;
    }

    public JwtResponse setUserName(String _userName) {
        this.userName = _userName;
        return this;
    }

    public static class JwtResponseBuilder {
        String passwordHash;
        String userName;
        String hash;

        public JwtResponseBuilder setJwtHash(String __h) {
            this.hash = __h;
            return this;
        }

        public JwtResponseBuilder setPasswordHash(String __ph) {
            this.passwordHash = __ph;
            return this;
        }

        public JwtResponse build(String __userName) {
            return new JwtResponse(this)
                    .setUserName(__userName);
        }
    }
}
