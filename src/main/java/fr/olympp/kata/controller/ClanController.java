package fr.olympp.kata.controller;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.services.ClanServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clans")
public class ClanController {
  private final ClanServices clanServices;

  public ClanController(ClanServices clanServices) {
    this.clanServices = clanServices;
  }

  @GetMapping
  public List<Clan> getClans() {
    return this.clanServices.getClans();
  }

  @GetMapping("/{clanName}")
  public Clan getClan(@PathVariable String clanName) {
    return this.clanServices.getClan(clanName);
  }

  @PostMapping("/{clanName}/armies")
  public void addArmy(@PathVariable String clanName, @RequestBody Army army) {
    this.clanServices.addArmy(clanName, army);
  }

  @DeleteMapping("/{clanName}/armies/{armyName}")
  public void removeArmy(@PathVariable String clanName, @PathVariable String armyName) {
    this.clanServices.removeArmy(clanName, armyName);
  }

}
