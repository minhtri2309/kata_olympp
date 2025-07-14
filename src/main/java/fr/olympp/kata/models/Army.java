package fr.olympp.kata.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Army(Army original) {
        this.name = original.name;
        this.footSoldiers = new FootSoldier(original.footSoldiers);
        this.clan = original.clan;
    }

    public int getArmyAttack() {
        return footSoldiers.getNbUnits() * footSoldiers.getAttack();
    }

    public int getArmyDefense() {
        return footSoldiers.getNbUnits() * footSoldiers.getDefense();
    }

    @JsonIgnore
    public int getRemainingUnits() {
        return footSoldiers.getNbUnits();
    }

    public void removeUnits(int nbUnits) {
        footSoldiers.removeUnits(nbUnits);
    }

    @JsonIgnore
    public boolean isDecimated() {
        return footSoldiers.getNbUnits() <= 0;
    }

    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Army army = (Army) o;
        if (name != null ? !name.equals(army.name) : army.name != null) {
            return false;
        }
        if (footSoldiers != null ? !footSoldiers.equals(army.footSoldiers) : army.footSoldiers != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (footSoldiers != null ? footSoldiers.hashCode() : 0);
        return result;
    }
}
