package fr.olympp.kata.services;

import fr.olympp.kata.dto.ArmyDTO;
import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.exception.ClanNotFoundException;
import fr.olympp.kata.exception.ClanNumberLimitException;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.FootSoldier;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.repository.ClanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClanServiceImpl implements ClanService {

    private ClanRepository clanRepository;
    private BattleReportRepository battleReportRepository;

    @Autowired
    public ClanServiceImpl(ClanRepository clanRepository, BattleReportRepository battleReportRepository) {
        this.clanRepository = clanRepository;
        this.battleReportRepository = battleReportRepository;
    }


    @Override
    public void addClan(Clan clan) {
        if (clanRepository.count() < 2) {
            clanRepository.save(clan);
        } else {
            throw new ClanNumberLimitException();
        }
    }

    @Override
    public List<Clan> getClans() {
        return clanRepository.findAll();
    }

    @Override
    public Clan getClan(String clanName) {
        return clanRepository.findByName(clanName)
                .orElseThrow(() -> new ClanNotFoundException(clanName));
    }

    @Override
    public ClanStatusDTO getClanStatus(String clanName) {
        Clan clan = getClan(clanName);
        ClanStatusDTO clanStatusDTO = new ClanStatusDTO();
        clanStatusDTO.setName(clanName);

        List<ArmyDTO> armyDTOs = clan.getArmies().stream().map(army -> {
            FootSoldier footSoldier = army.getFootSoldiers();
            ArmyDTO currentArmyDTO = new ArmyDTO();
            currentArmyDTO.setName(army.getName());
            currentArmyDTO.setNbUnits(footSoldier.getNbUnits());
            currentArmyDTO.setAttack(footSoldier.getAttack());
            currentArmyDTO.setDefense(footSoldier.getDefense());
            currentArmyDTO.setHealth(footSoldier.getHealth());
            return currentArmyDTO;
        }).toList();

        clanStatusDTO.setArmies(armyDTOs);

        BattleReport lastReport = battleReportRepository.findTopByOrderByIdDesc();
        if (lastReport != null) {
            if (lastReport.getWinner() != null) {
                if (lastReport.getWinner().equals(clanName)) {
                    clanStatusDTO.setLastBattleStatus(ResultStatus.WON);
                } else {
                    clanStatusDTO.setLastBattleStatus(ResultStatus.LOST);
                }
            } else {
                clanStatusDTO.setLastBattleStatus(ResultStatus.DRAW);
            }

        }

        return clanStatusDTO;
    }


    @Override
    public Clan addArmy(String clanName, Army army) {
        Clan clan = getClan(clanName);
        clan.addArmy(army);
        clanRepository.save(clan);
        return clan;
    }

    @Override
    public Clan removeArmy(String clanName, String armyName) {
        Clan clan = getClan(clanName);
        clan.removeArmy(armyName);
        clanRepository.save(clan);
        return clan;
    }
}
