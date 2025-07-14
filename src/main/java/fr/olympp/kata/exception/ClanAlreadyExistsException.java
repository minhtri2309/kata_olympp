package fr.olympp.kata.exception;

public class ClanAlreadyExistsException extends RuntimeException {

    public ClanAlreadyExistsException(String clanName) {
        super("Clan already exists : " + clanName);
    }
}
