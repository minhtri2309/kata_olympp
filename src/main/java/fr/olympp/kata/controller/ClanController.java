package fr.olympp.kata.controller;

import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.exception.ArmyNotFoundException;
import fr.olympp.kata.exception.ClanAlreadyExistsException;
import fr.olympp.kata.exception.ClanNotFoundException;
import fr.olympp.kata.exception.ClanNumberLimitException;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.services.ClanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> addClan(@RequestBody Clan clan) {
        try {
            this.clanService.addClan(clan);
            return ResponseEntity.ok(clan);
        } catch (ClanNumberLimitException | ClanAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(clan.getName() + " not added : " + e.getMessage());
        }
    }

    @GetMapping("/{clanName}")
    public ResponseEntity<?> getClan(@PathVariable String clanName) {
        try {
            Clan clan = this.clanService.getClan(clanName);
            return ResponseEntity.ok(clan);
        } catch (ClanNotFoundException e) {
            return ResponseEntity.badRequest().body(clanName + " not found : " + e.getMessage());
        }
    }

    @GetMapping("/{clanName}/status")
    public ResponseEntity<?> getClanStatus(@PathVariable String clanName) {
        try {
            ClanStatusDTO clanStatus = this.clanService.getClanStatus(clanName);
            return ResponseEntity.ok(clanStatus);
        } catch (ClanNotFoundException e) {
            return ResponseEntity.badRequest().body(clanName + " not found : " + e.getMessage());
        }
    }

    @PostMapping("/{clanName}/armies")
    public ResponseEntity<?> addArmy(@PathVariable String clanName, @RequestBody Army army) {
        try {
            Clan clan = this.clanService.addArmy(clanName, army);
            return ResponseEntity.ok(clan);
        } catch (ClanNotFoundException e) {
            return ResponseEntity.badRequest().body(clanName + " not found : " + e.getMessage());
        }
    }

    @DeleteMapping("/{clanName}/armies/{armyName}")
    public ResponseEntity<?> removeArmy(@PathVariable String clanName, @PathVariable String armyName) {
        try {
            Clan clan = this.clanService.removeArmy(clanName, armyName);
            return ResponseEntity.ok(clan);
        } catch (ClanNotFoundException e) {
            return ResponseEntity.badRequest().body(clanName + " not found : " + e.getMessage());
        } catch (ArmyNotFoundException e) {
            return ResponseEntity.badRequest().body(armyName + " not found : " + e.getMessage());
        }
    }
}
