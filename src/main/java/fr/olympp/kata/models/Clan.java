package fr.olympp.kata.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKey(name = "name")
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Army> armies = new TreeMap<>();

    public Clan() {
    }

    public Clan(Clan original) {
        this.name = original.name;
        this.armies = new TreeMap<>();
        for (Map.Entry<String, Army> entry : original.armies.entrySet()) {
            this.armies.put(entry.getKey(), new Army(entry.getValue()));
        }
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

    public void setArmies(List<Army> armies) {
        for (Army currentArmy : armies) {
            this.armies.put(currentArmy.getName(), currentArmy);
            currentArmy.setClan(this);
        }
    }

    @JsonIgnore
    public boolean isAlive() {
        return getArmies().stream().anyMatch(currentClan -> !currentClan.isDecimated());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Clan otherClan = (Clan) o;

        if (name != null) {
            if (!name.equals(otherClan.name)) {
                return false;
            }
        } else {
            if (otherClan.name != null) {
                return false;
            }
        }

        List<Army> thisArmies = this.getArmies();
        List<Army> otherArmies = otherClan.getArmies();
        if (thisArmies.size() != otherArmies.size()) {
            return false;
        }
        for (int i = 0; i < thisArmies.size(); i++) {
            if (!thisArmies.get(i).equals(otherArmies.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + getArmies().hashCode();
        return result;
    }

}
