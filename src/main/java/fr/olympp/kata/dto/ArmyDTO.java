package fr.olympp.kata.dto;

public class ArmyDTO {

    private String name;
    private int nbUnits;
    private int attack;
    private int defense;
    private int health;

    public ArmyDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbUnits() {
        return nbUnits;
    }

    public void setNbUnits(int nbUnits) {
        this.nbUnits = nbUnits;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
