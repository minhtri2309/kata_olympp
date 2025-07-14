package fr.olympp.kata.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BattleReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String winner;

    private ResultStatus status;

    @OneToMany
    private List<Clan> initialClans;

    @ElementCollection
    @JsonProperty("history")
    private List<BattleTurn> battleTurns = new ArrayList<>();

    public BattleReport() {
    }

    public void addBattleTurn(BattleTurn battleTurn) {
        this.battleTurns.add(battleTurn);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public List<Clan> getInitialClans() {
        return initialClans;
    }

    public void setInitialClans(List<Clan> initialClans) {
        this.initialClans = initialClans;
    }

    public List<BattleTurn> getBattleTurns() {
        return battleTurns;
    }

    public void setBattleTurns(List<BattleTurn> battleTurns) {
        this.battleTurns = battleTurns;
    }
}
