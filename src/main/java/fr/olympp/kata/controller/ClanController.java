package fr.olympp.kata.controller;

import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.services.ClanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clans")
public class ClanController {
    private final ClanService clanService;

    public ClanController(ClanService clanService) {
        this.clanService = clanService;
    }

    @GetMapping
    public List<Clan> getClans() {
        return this.clanService.getClans();
    }

    @PostMapping
    public void addClan(@RequestBody Clan clan) {
        this.clanService.addClan(clan);
    }

    @GetMapping("/{clanName}")
    public Clan getClan(@PathVariable String clanName) {
        return this.clanService.getClan(clanName);
    }

    @GetMapping("/{clanName}/status")
    public ClanStatusDTO getClanStatus(@PathVariable String clanName) {
        return this.clanService.getClanStatus(clanName);
    }

    @PostMapping("/{clanName}/armies")
    public void addArmy(@PathVariable String clanName, @RequestBody Army army) {
        this.clanService.addArmy(clanName, army);
    }

    @DeleteMapping("/{clanName}/armies/{armyName}")
    public void removeArmy(@PathVariable String clanName, @PathVariable String armyName) {
        this.clanService.removeArmy(clanName, armyName);
    }
}
