package fr.olympp.kata.services;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;

import java.util.List;

public interface ClanService {
  Clan getClan(String clanName);

  List<Clan> getClans();

  void addArmy(String clanName, Army army);

  void removeArmy(String clanName, String armyName);
}
