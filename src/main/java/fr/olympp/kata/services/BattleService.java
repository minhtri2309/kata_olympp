package fr.olympp.kata.services;

import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.Clan;

public interface BattleService {
  // responsible to solve the battle between two clans
  BattleReport battle(Clan clan1, Clan clan2);
}
