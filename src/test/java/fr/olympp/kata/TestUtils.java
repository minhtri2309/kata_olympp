package fr.olympp.kata;

import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.BattleTurn;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.FootSoldier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static final String ATHENS = "Athens";
    public static final String TROY = "Troy";

    public static final String ARMY1 = "army1";
    public static final String ARMY2 = "army2";


    public static Clan createClan(String name) {
        Clan clan = new Clan();
        clan.setName(name);
        return clan;
    }

    public static Army createArmy(String name, int units, int attack, int defense, int health) {
        Army army = new Army();
        army.setName(name);
        army.setFootSoldiers(new FootSoldier(units, attack, defense, health));
        return army;
    }

    public static void assertInitialClansValue(Clan clan1, Clan clan2, BattleReport battleReport) {
        Clan clan1Report = battleReport.getInitialClans().get(0);
        Clan clan2Report = battleReport.getInitialClans().get(1);

        assertEquals(clan1, clan1Report);
        assertEquals(clan2, clan2Report);
    }

    public static void assertReportArmy(Army army, String name, int nbUnits, int attack, int defense, int health, int armyAttack, int armyDefense) {
        assertEquals(name, army.getName());
        assertEquals(nbUnits, army.getFootSoldiers().getNbUnits());
        assertEquals(attack, army.getFootSoldiers().getAttack());
        assertEquals(defense, army.getFootSoldiers().getDefense());
        assertEquals(health, army.getFootSoldiers().getHealth());
        assertEquals(armyAttack, army.getArmyAttack());
        assertEquals(armyDefense, army.getArmyDefense());
    }

    public static void assertBattleTurn(BattleTurn battleTurn, String armyName1, String armyName2, int damageOn1, int damageOn2, int remaining1, int remaining2) {
        assertEquals(armyName1, battleTurn.getNameArmy1());
        assertEquals(armyName2, battleTurn.getNameArmy2());
        assertEquals(damageOn1, battleTurn.getDamageOnArmy1());
        assertEquals(damageOn2, battleTurn.getDamageOnArmy2());
        assertEquals(remaining1, battleTurn.getNbRemainingSoldiersArmy1());
        assertEquals(remaining2, battleTurn.getNbRemainingSoldiersArmy2());
    }
}
