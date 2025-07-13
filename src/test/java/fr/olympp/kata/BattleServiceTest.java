package fr.olympp.kata;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.BattleTurn;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.FootSoldier;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.services.BattleService;
import fr.olympp.kata.services.BattleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class BattleServiceTest {

    public static final String ATHENS = "Athens";
    public static final String TROY = "Troy";

    public static final String ARMY1 = "army1";
    public static final String ARMY2 = "army2";

    private BattleService battleService;
    private BattleReportRepository battleReportRepository;

    @BeforeEach
    void setUp() {
        battleReportRepository = mock(BattleReportRepository.class);
        battleService = new BattleServiceImpl(battleReportRepository);
    }

    @Test
    void shouldDeclareTroyWinner() {
        Clan troy = new Clan();
        troy.setName(TROY);

        Army troyArmy = new Army();
        troyArmy.setName(TROY + ARMY1);
        FootSoldier footSoldier = new FootSoldier(100, 100, 100, 100);
        troyArmy.setFootSoldiers(footSoldier);
        troy.addArmy(troyArmy);

        Army troyArmy2 = new Army();
        troyArmy2.setName(TROY + ARMY2);
        FootSoldier footSoldier2 = new FootSoldier(100, 100, 100, 100);
        troyArmy2.setFootSoldiers(footSoldier2);
        troy.addArmy(troyArmy2);

        Clan athens = new Clan();
        athens.setName(ATHENS);

        Army athensArmy = new Army();
        athensArmy.setName(ATHENS + ARMY1);
        FootSoldier footSoldier3 = new FootSoldier(50, 50, 50, 50);
        athensArmy.setFootSoldiers(footSoldier3);
        athens.addArmy(athensArmy);

        Army athensArmy2 = new Army();
        athensArmy2.setName(ATHENS + ARMY2);
        FootSoldier footSoldier4 = new FootSoldier(50, 50, 50, 50);
        athensArmy2.setFootSoldiers(footSoldier4);
        athens.addArmy(athensArmy2);

        BattleReport battleReport = battleService.battle(troy, athens);

        assertEquals(TROY, battleReport.getWinner());
        assertEquals(ResultStatus.WON, battleReport.getStatus());
        assertEquals(List.of(troy, athens), battleReport.getInitialClans());
        assertEquals(2, battleReport.getBattleTurns().size());

        BattleTurn battleTurn = battleReport.getBattleTurns().get(0);
        assertEquals(TROY + ARMY1, battleTurn.getNameArmy1());
        assertEquals(ATHENS + ARMY1, battleTurn.getNameArmy2());
        assertEquals(0, battleTurn.getDamageOnArmy1());
        assertEquals(7500, battleTurn.getDamageOnArmy2());
        assertEquals(100, battleTurn.getNbRemainingSoldiersArmy1());
        assertEquals(0, battleTurn.getNbRemainingSoldiersArmy2());

        battleTurn = battleReport.getBattleTurns().get(1);
        assertEquals(TROY + ARMY1, battleTurn.getNameArmy1());
        assertEquals(ATHENS + ARMY2, battleTurn.getNameArmy2());
        assertEquals(0, battleTurn.getDamageOnArmy1());
        assertEquals(7500, battleTurn.getDamageOnArmy2());
        assertEquals(100, battleTurn.getNbRemainingSoldiersArmy1());
        assertEquals(0, battleTurn.getNbRemainingSoldiersArmy2());
    }

    @Test
    void shouldDeclareDraw() {
        Clan troy = new Clan();
        troy.setName(TROY);

        Army troyArmy = new Army();
        troyArmy.setName(TROY + ARMY1);
        FootSoldier footSoldier = new FootSoldier(100, 200, 100, 100);
        troyArmy.setFootSoldiers(footSoldier);
        troy.addArmy(troyArmy);

        Clan athens = new Clan();
        athens.setName(ATHENS);

        Army athensArmy = new Army();
        athensArmy.setName(ATHENS + ARMY1);
        FootSoldier footSoldier3 = new FootSoldier(100, 200, 100, 100);
        athensArmy.setFootSoldiers(footSoldier3);
        athens.addArmy(athensArmy);

        BattleReport battleReport = battleService.battle(troy, athens);

        assertNull(battleReport.getWinner());
        assertEquals(ResultStatus.DRAW, battleReport.getStatus());
        assertEquals(List.of(troy, athens), battleReport.getInitialClans());
        assertEquals(1, battleReport.getBattleTurns().size());

        BattleTurn battleTurn = battleReport.getBattleTurns().get(0);
        assertEquals(TROY + ARMY1, battleTurn.getNameArmy1());
        assertEquals(ATHENS + ARMY1, battleTurn.getNameArmy2());
        assertEquals(10000, battleTurn.getDamageOnArmy1());
        assertEquals(10000, battleTurn.getDamageOnArmy2());
        assertEquals(0, battleTurn.getNbRemainingSoldiersArmy1());
        assertEquals(0, battleTurn.getNbRemainingSoldiersArmy2());
    }

    @Test
    void shouldDeclareDrawBecauseNoDamage() {
        Clan troy = new Clan();
        troy.setName(TROY);

        Army troyArmy = new Army();
        troyArmy.setName(TROY + ARMY1);
        FootSoldier footSoldier = new FootSoldier(100, 100, 100, 100);
        troyArmy.setFootSoldiers(footSoldier);
        troy.addArmy(troyArmy);

        Clan athens = new Clan();
        athens.setName(ATHENS);

        Army athensArmy = new Army();
        athensArmy.setName(ATHENS + ARMY1);
        FootSoldier footSoldier3 = new FootSoldier(100, 100, 100, 100);
        athensArmy.setFootSoldiers(footSoldier3);
        athens.addArmy(athensArmy);

        BattleReport battleReport = battleService.battle(troy, athens);

        assertNull(battleReport.getWinner());
        assertEquals(ResultStatus.DRAW, battleReport.getStatus());
        assertEquals(List.of(troy, athens), battleReport.getInitialClans());
        assertEquals(1, battleReport.getBattleTurns().size());

        BattleTurn battleTurn = battleReport.getBattleTurns().get(0);
        assertEquals(TROY + ARMY1, battleTurn.getNameArmy1());
        assertEquals(ATHENS + ARMY1, battleTurn.getNameArmy2());
        assertEquals(0, battleTurn.getDamageOnArmy1());
        assertEquals(0, battleTurn.getDamageOnArmy2());
        assertEquals(100, battleTurn.getNbRemainingSoldiersArmy1());
        assertEquals(100, battleTurn.getNbRemainingSoldiersArmy2());
    }
}

