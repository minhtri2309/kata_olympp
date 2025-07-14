package fr.olympp.kata.services;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.BattleTurn;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleServiceImpl implements BattleService {

    private BattleReportRepository repository;

    @Autowired
    public BattleServiceImpl(BattleReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public BattleReport battle(Clan clan1, Clan clan2) {
        BattleReport battleReport = new BattleReport();

        battleReport.setInitialClans(List.of(new Clan(clan1), new Clan(clan2)));

        processBattleTurns(clan1, clan2, battleReport);
        computeBattleResult(clan1, clan2, battleReport);


        return battleReport;
    }

    private void processBattleTurns(Clan clan1, Clan clan2, BattleReport battleReport) {
        List<Army> armies1 = clan1.getArmies();
        List<Army> armies2 = clan2.getArmies();

        int index1 = 0;
        int index2 = 0;

        while (index1 < armies1.size() && index2 < armies2.size()) {
            Army currentArmy1 = armies1.get(index1);
            Army currentArmy2 = armies2.get(index2);

            BattleTurn battleTurn = computeTurn(currentArmy1, currentArmy2);
            battleReport.addBattleTurn(battleTurn);

            if (currentArmy1.isDecimated()) {
                index1++;
            }

            if (currentArmy2.isDecimated()) {
                index2++;
            }


        }

    }

    private BattleTurn computeTurn(Army army1, Army army2) {
        int damageOnArmy1 = Math.max(0, army2.getArmyAttack() - army1.getArmyDefense());
        int damageOnArmy2 = Math.max(0, army1.getArmyAttack() - army2.getArmyDefense());

        //if armies attack deal no damage to each other because they are equally strong,
        //deal damage without considering defense, considering they all kill each other
        if (damageOnArmy1 == 0 && damageOnArmy2 == 0) {
            damageOnArmy1 = army2.getArmyAttack();
            damageOnArmy2 = army1.getArmyAttack();
        }

        int army1KilledUnits = damageOnArmy1 / army1.getFootSoldiers().getHealth();
        int army2KilledUnits = damageOnArmy2 / army2.getFootSoldiers().getHealth();

        army1.removeUnits(army1KilledUnits);
        army2.removeUnits(army2KilledUnits);

        return new BattleTurn(army1.getName(), army2.getName(), damageOnArmy1, damageOnArmy2, army1.getRemainingUnits(), army2.getRemainingUnits());
    }

    private void computeBattleResult(Clan clan1, Clan clan2, BattleReport battleReport) {
        boolean isClan1Alive = clan1.isAlive();
        boolean isClan2Alive = clan2.isAlive();

        if (isClan1Alive && isClan2Alive || !isClan1Alive && !isClan2Alive) {
            battleReport.setStatus(ResultStatus.DRAW);
        } else if (!isClan1Alive) {
            battleReport.setStatus(ResultStatus.WON);
            battleReport.setWinner(clan2.getName());
        } else {
            battleReport.setStatus(ResultStatus.WON);
            battleReport.setWinner(clan1.getName());
        }
    }

}
