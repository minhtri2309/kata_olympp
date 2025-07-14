package fr.olympp.kata.exception;

public class ArmyNotFoundException extends RuntimeException {

    public ArmyNotFoundException(String armyName) {
        super("Army not found : " + armyName);
    }
}
