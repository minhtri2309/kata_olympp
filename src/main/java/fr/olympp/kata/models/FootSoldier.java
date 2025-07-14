package fr.olympp.kata.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FootSoldier implements Serializable {

    private int nbUnits;
    private int attack;
    private int defense;
    private int health;

    public FootSoldier() {
    }

    public FootSoldier(FootSoldier original) {
        this.nbUnits = original.nbUnits;
        this.attack = original.attack;
        this.defense = original.defense;
        this.health = original.health;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FootSoldier that = (FootSoldier) o;
        return nbUnits == that.nbUnits &&
                attack == that.attack &&
                defense == that.defense &&
                health == that.health;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(nbUnits);
        result = 31 * result + Integer.hashCode(attack);
        result = 31 * result + Integer.hashCode(defense);
        result = 31 * result + Integer.hashCode(health);
        return result;
    }
}
