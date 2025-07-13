package fr.olympp.kata.exception;

public class ClanNumberLimitException extends RuntimeException {

    public ClanNumberLimitException() {
        super("Too many clans");
    }
}
