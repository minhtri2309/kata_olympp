package fr.olympp.kata.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class BattleTurn {

    private String nameArmy1;
    private String nameArmy2;
    private int damageOnArmy1;
    private int damageOnArmy2;
    private int nbRemainingSoldiersArmy1;
    private int nbRemainingSoldiersArmy2;

    public BattleTurn() {
    }

    public BattleTurn(String nameArmy1, String nameArmy2, int damageOnArmy1, int damageOnArmy2, int nbRemainingSoldiersArmy1, int nbRemainingSoldiersArmy2) {
        this.nameArmy1 = nameArmy1;
        this.nameArmy2 = nameArmy2;
        this.damageOnArmy1 = damageOnArmy1;
        this.damageOnArmy2 = damageOnArmy2;
        this.nbRemainingSoldiersArmy1 = nbRemainingSoldiersArmy1;
        this.nbRemainingSoldiersArmy2 = nbRemainingSoldiersArmy2;
    }

    public String getNameArmy1() {
        return nameArmy1;
    }

    public void setNameArmy1(String nameArmy1) {
        this.nameArmy1 = nameArmy1;
    }

    public String getNameArmy2() {
        return nameArmy2;
    }

    public void setNameArmy2(String nameArmy2) {
        this.nameArmy2 = nameArmy2;
    }

    public int getDamageOnArmy1() {
        return damageOnArmy1;
    }

    public void setDamageOnArmy1(int damageOnArmy1) {
        this.damageOnArmy1 = damageOnArmy1;
    }

    public int getDamageOnArmy2() {
        return damageOnArmy2;
    }

    public void setDamageOnArmy2(int damageOnArmy2) {
        this.damageOnArmy2 = damageOnArmy2;
    }

    public int getNbRemainingSoldiersArmy1() {
        return nbRemainingSoldiersArmy1;
    }

    public void setNbRemainingSoldiersArmy1(int nbRemainingSoldiersArmy1) {
        this.nbRemainingSoldiersArmy1 = nbRemainingSoldiersArmy1;
    }

    public int getNbRemainingSoldiersArmy2() {
        return nbRemainingSoldiersArmy2;
    }

    public void setNbRemainingSoldiersArmy2(int nbRemainingSoldiersArmy2) {
        this.nbRemainingSoldiersArmy2 = nbRemainingSoldiersArmy2;
    }
}

