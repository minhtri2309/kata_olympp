package fr.olympp.kata.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class FootSoldier {

    private int nbUnits;
    private int attack;
    private int defense;
    private int health;

    public FootSoldier() {
    }

    public FootSoldier(int nbUnits, int attack, int defense, int health) {
        this.nbUnits = nbUnits;
        this.attack = attack;
        this.defense = defense;
        this.health = health;
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

    public int getNbUnits() {
        return nbUnits;
    }

    public void removeUnits(int nbUnits) {
        this.nbUnits = Math.max(0, this.nbUnits - nbUnits);
    }

    public void setNbUnits(int nbUnits) {
        this.nbUnits = nbUnits;
    }
}
