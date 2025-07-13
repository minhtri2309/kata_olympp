package fr.olympp.kata.dto;

import fr.olympp.kata.models.ResultStatus;

import java.util.List;

public class ClanStatusDTO {

    private String name;
    private List<ArmyDTO> armies;
    private ResultStatus lastBattleStatus;

    public ClanStatusDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArmyDTO> getArmies() {
        return armies;
    }

    public void setArmies(List<ArmyDTO> armies) {
        this.armies = armies;
    }

    public ResultStatus getLastBattleStatus() {
        return lastBattleStatus;
    }

    public void setLastBattleStatus(ResultStatus lastBattleStatus) {
        this.lastBattleStatus = lastBattleStatus;
    }
}
