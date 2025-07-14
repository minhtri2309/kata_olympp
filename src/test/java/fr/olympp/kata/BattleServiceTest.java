package fr.olympp.kata;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.services.BattleService;
import fr.olympp.kata.services.BattleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.olympp.kata.TestUtils.ARMY1;
import static fr.olympp.kata.TestUtils.ARMY2;
import static fr.olympp.kata.TestUtils.ATHENS;
import static fr.olympp.kata.TestUtils.TROY;
import static fr.olympp.kata.TestUtils.assertBattleTurn;
import static fr.olympp.kata.TestUtils.assertReportArmy;
import static fr.olympp.kata.TestUtils.createArmy;
import static fr.olympp.kata.TestUtils.createClan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class BattleServiceTest {


    private BattleService battleService;
    private BattleReportRepository battleReportRepository;

    @BeforeEach
    void setUp() {
        battleReportRepository = mock(BattleReportRepository.class);
        battleService = new BattleServiceImpl(battleReportRepository);
    }

    @Test
    void shouldDeclareTroyWinner() {
        Clan troy = createClan(TROY);

        Army troyArmy = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        troy.addArmy(troyArmy);

        Army troyArmy2 = createArmy(TROY + ARMY2, 100, 100, 100, 100);
        troy.addArmy(troyArmy2);

        Clan athens = createClan(ATHENS);

        Army athensArmy = createArmy(ATHENS + ARMY1, 50, 50, 50, 50);
        athens.addArmy(athensArmy);

        Army athensArmy2 = createArmy(ATHENS + ARMY2, 50, 50, 50, 50);
        athens.addArmy(athensArmy2);

        BattleReport battleReport = battleService.battle(troy, athens);
        assertEquals(TROY, battleReport.getWinner());
        assertEquals(ResultStatus.WON, battleReport.getStatus());
        assertEquals(2, battleReport.getBattleTurns().size());

        Clan clanReport1 = battleReport.getInitialClans().get(0);
        Clan clanReport2 = battleReport.getInitialClans().get(1);

        assertEquals(TROY, clanReport1.getName());
        assertReportArmy(clanReport1.getArmies().get(0), TROY + ARMY1, 100, 100, 100, 100, 10000, 10000);
        assertReportArmy(clanReport1.getArmies().get(1), TROY + ARMY2, 100, 100, 100, 100, 10000, 10000);

        assertEquals(ATHENS, clanReport2.getName());
        assertReportArmy(clanReport2.getArmies().get(0), ATHENS + ARMY1, 50, 50, 50, 50, 2500, 2500);
        assertReportArmy(clanReport2.getArmies().get(1), ATHENS + ARMY2, 50, 50, 50, 50, 2500, 2500);

        assertBattleTurn(battleReport.getBattleTurns().get(0), TROY + ARMY1, ATHENS + ARMY1, 0, 7500, 100, 0);
        assertBattleTurn(battleReport.getBattleTurns().get(1), TROY + ARMY1, ATHENS + ARMY2, 0, 7500, 100, 0);


    }

    @Test
    void shouldDeclareDraw() {
        Clan troy = createClan(TROY);
        Army troyArmy = createArmy(TROY + ARMY1, 100, 200, 100, 100);
        troy.addArmy(troyArmy);

        Clan athens = createClan(ATHENS);
        Army athensArmy = createArmy(ATHENS + ARMY1, 100, 200, 100, 100);
        athens.addArmy(athensArmy);

        BattleReport battleReport = battleService.battle(troy, athens);

        assertNull(battleReport.getWinner());
        assertEquals(ResultStatus.DRAW, battleReport.getStatus());
        assertEquals(1, battleReport.getBattleTurns().size());

        Clan clanReport1 = battleReport.getInitialClans().get(0);
        Clan clanReport2 = battleReport.getInitialClans().get(1);

        assertEquals(TROY, clanReport1.getName());
        assertReportArmy(clanReport1.getArmies().get(0), TROY + ARMY1, 100, 200, 100, 100, 20000, 10000);

        assertEquals(ATHENS, clanReport2.getName());
        assertReportArmy(clanReport2.getArmies().get(0), ATHENS + ARMY1, 100, 200, 100, 100, 20000, 10000);

        assertBattleTurn(battleReport.getBattleTurns().get(0), TROY + ARMY1, ATHENS + ARMY1, 10000, 10000, 0, 0);
    }

    @Test
    void shouldDeclareDrawBecauseNoDamage() {
        Clan troy = createClan(TROY);
        Army troyArmy = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        troy.addArmy(troyArmy);

        Clan athens = createClan(ATHENS);
        Army athensArmy = createArmy(ATHENS + ARMY1, 100, 100, 100, 100);
        athens.addArmy(athensArmy);

        BattleReport battleReport = battleService.battle(troy, athens);

        assertNull(battleReport.getWinner());
        assertEquals(ResultStatus.DRAW, battleReport.getStatus());
        assertEquals(1, battleReport.getBattleTurns().size());

        Clan clanReport1 = battleReport.getInitialClans().get(0);
        Clan clanReport2 = battleReport.getInitialClans().get(1);

        assertEquals(TROY, clanReport1.getName());
        assertReportArmy(clanReport1.getArmies().get(0), TROY + ARMY1, 100, 100, 100, 100, 10000, 10000);

        assertEquals(ATHENS, clanReport2.getName());
        assertReportArmy(clanReport2.getArmies().get(0), ATHENS + ARMY1, 100, 100, 100, 100, 10000, 10000);

        assertBattleTurn(battleReport.getBattleTurns().get(0), TROY + ARMY1, ATHENS + ARMY1, 10000, 10000, 0, 0);
    }

}

