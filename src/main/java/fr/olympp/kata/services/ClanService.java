package fr.olympp.kata.services;

import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;

import java.util.List;

public interface ClanService {
    void addClan(Clan clan);

    Clan getClan(String clanName);

    ClanStatusDTO getClanStatus(String clanName);

    List<Clan> getClans();

    void addArmy(String clanName, Army army);

    void removeArmy(String clanName, String armyName);
}
