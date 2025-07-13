package fr.olympp.kata.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Army {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private FootSoldier footSoldiers;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    @JsonBackReference
    private Clan clan;


    public Army() {
    }

    public int getAttackValue() {
        return footSoldiers.getNbUnits() * footSoldiers.getAttack();
    }

    public int getDefenseValue() {
        return footSoldiers.getNbUnits() * footSoldiers.getDefense();
    }

    public int getRemainingUnits() {
        return footSoldiers.getNbUnits();
    }

    //todo ?
    public void removeUnits(int nbUnits) {
        footSoldiers.removeUnits(nbUnits);
    }

    public boolean isDecimated() {
        return footSoldiers.getNbUnits() <= 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FootSoldier getFootSoldiers() {
        return footSoldiers;
    }

    public void setFootSoldiers(FootSoldier footSoldiers) {
        this.footSoldiers = footSoldiers;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }
}
