package fr.olympp.kata;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.repository.ClanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static fr.olympp.kata.TestUtils.ARMY1;
import static fr.olympp.kata.TestUtils.ARMY2;
import static fr.olympp.kata.TestUtils.ATHENS;
import static fr.olympp.kata.TestUtils.TROY;
import static fr.olympp.kata.TestUtils.assertBattleTurn;
import static fr.olympp.kata.TestUtils.assertReportArmy;
import static fr.olympp.kata.TestUtils.battle;
import static fr.olympp.kata.TestUtils.createArmy;
import static fr.olympp.kata.TestUtils.createClan;
import static fr.olympp.kata.TestUtils.postArmy;
import static fr.olympp.kata.TestUtils.postClan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BattleIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private BattleReportRepository battleReportRepository;

    @BeforeEach
    void setUp() {
        clanRepository.deleteAll();
        battleReportRepository.deleteAll();
    }


    @Test
    void shouldDeclareTroyWinner() throws Exception {
        Clan troy = createClan(TROY);
        Clan athens = createClan(ATHENS);

        postClan(mockMvc, objectMapper, troy);
        postClan(mockMvc, objectMapper, athens);

        Army troyArmy = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        Army troyArmy2 = createArmy(TROY + ARMY2, 100, 100, 100, 100);

        Army athensArmy = createArmy(ATHENS + ARMY1, 50, 50, 50, 50);
        Army athensArmy2 = createArmy(ATHENS + ARMY2, 50, 50, 50, 50);

        postArmy(mockMvc, objectMapper, TROY, troyArmy);
        postArmy(mockMvc, objectMapper, TROY, troyArmy2);

        postArmy(mockMvc, objectMapper, ATHENS, athensArmy);
        postArmy(mockMvc, objectMapper, ATHENS, athensArmy2);

        MvcResult mvcResult = mockMvc.perform(get("/battles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        BattleReport battleReport = objectMapper.readValue(jsonResponse, BattleReport.class);

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
    void shouldDeclareDraw() throws Exception {
        Clan troy = createClan(TROY);
        Clan athens = createClan(ATHENS);

        postClan(mockMvc, objectMapper, troy);
        postClan(mockMvc, objectMapper, athens);

        Army troyArmy = createArmy(TROY + ARMY1, 100, 200, 100, 100);
        Army athensArmy = createArmy(ATHENS + ARMY1, 100, 200, 100, 100);

        postArmy(mockMvc, objectMapper, TROY, troyArmy);
        postArmy(mockMvc, objectMapper, ATHENS, athensArmy);

        MvcResult mvcResult = battle(mockMvc);

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        BattleReport battleReport = objectMapper.readValue(jsonResponse, BattleReport.class);

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
}
