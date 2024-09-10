package fr.olympp.kata.repository;

import fr.olympp.kata.models.Clan;

import java.util.List;

public interface ClanRepository {

    public void addClan(Clan clan);

    public Clan updateClan(Clan clan);

    public Clan getClan(String name);

    List<Clan> getClans();
}
