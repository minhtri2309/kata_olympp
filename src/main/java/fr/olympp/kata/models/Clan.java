package fr.olympp.kata.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "name")
    @JsonManagedReference
    private Map<String, Army> armies = new TreeMap<>();

    public Clan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArmy(Army army) {
        armies.put(army.getName(), army);
        army.setClan(this);
    }

    public void removeArmy(String armyName) {
        Army removedArmy = armies.remove(armyName);
        if (removedArmy != null) {
            removedArmy.setClan(null);
        }
    }

    public List<Army> getArmies() {
        return new ArrayList<>(armies.values());
    }

    public boolean isAlive() {
        return getArmies().stream().anyMatch(currentClan -> !currentClan.isDecimated());
    }

}
