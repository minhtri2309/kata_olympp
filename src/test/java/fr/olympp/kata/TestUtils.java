package fr.olympp.kata;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleTurn;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.FootSoldier;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

    public static MvcResult getClan(MockMvc mockMvc, String clanName) throws Exception {
        return mockMvc.perform(get("/clans/" + clanName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    public static MvcResult getAllClans(MockMvc mockMvc) throws Exception {
        return mockMvc.perform(get("/clans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    public static MvcResult postClan(MockMvc mockMvc, ObjectMapper objectMapper, Clan clan) throws Exception {
        return mockMvc.perform(post("/clans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(clan)))
                .andReturn();
    }

    public static MvcResult postArmy(MockMvc mockMvc, ObjectMapper objectMapper, String clanName, Army army) throws Exception {
        return mockMvc.perform(post("/clans/" + clanName + "/armies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(army)))
                .andReturn();
    }

    public static MvcResult removeArmy(MockMvc mockMvc, ObjectMapper objectMapper, String clanName, String armyName) throws Exception {
        return mockMvc.perform(delete("/clans/" + clanName + "/armies/" + armyName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    public static MvcResult getClanStatus(MockMvc mockMvc, String clanName) throws Exception {
        return mockMvc.perform(get("/clans/" + clanName + "/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    public static MvcResult battle(MockMvc mockMvc) throws Exception {
        return mockMvc.perform(get("/battles")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
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
