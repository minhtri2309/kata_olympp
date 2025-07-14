package fr.olympp.kata.services;

import fr.olympp.kata.dto.ArmyDTO;
import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.exception.ArmyNotFoundException;
import fr.olympp.kata.exception.ClanAlreadyExistsException;
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

    public static final int MAX_CLAN_COUNT = 2;
    private ClanRepository clanRepository;
    private BattleReportRepository battleReportRepository;

    @Autowired
    public ClanServiceImpl(ClanRepository clanRepository, BattleReportRepository battleReportRepository) {
        this.clanRepository = clanRepository;
        this.battleReportRepository = battleReportRepository;
    }


    @Override
    public void addClan(Clan clan) {
        boolean clanExists = clanRepository.findByName(clan.getName()).isPresent();
        if (!clanExists) {
            if (clanRepository.count() < MAX_CLAN_COUNT) {
                clanRepository.save(clan);
            } else {
                throw new ClanNumberLimitException();
            }
        } else {
            throw new ClanAlreadyExistsException(clan.getName());
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

        List<ArmyDTO> armyDTOs = clan.getArmies().stream().map(this::computeArmyDTO).toList();
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
        if (clan.doesArmyExists(armyName)) {
            clan.removeArmy(armyName);
            clanRepository.save(clan);
            return clan;
        } else {
            throw new ArmyNotFoundException(armyName);
        }
    }

    private ArmyDTO computeArmyDTO(Army army) {
        FootSoldier foot = army.getFootSoldiers();
        ArmyDTO dto = new ArmyDTO();
        dto.setName(army.getName());
        dto.setNbUnits(foot.getNbUnits());
        dto.setAttack(foot.getAttack());
        dto.setDefense(foot.getDefense());
        dto.setHealth(foot.getHealth());
        return dto;
    }
}
