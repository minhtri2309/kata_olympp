package fr.olympp.kata.exception;

public class ClanNotFoundException extends RuntimeException {

    public ClanNotFoundException(String clanName) {
        super("Clan not found : " + clanName);
    }
}
