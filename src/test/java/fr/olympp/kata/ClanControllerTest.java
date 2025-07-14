package fr.olympp.kata;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.repository.ClanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static fr.olympp.kata.TestUtils.ARMY1;
import static fr.olympp.kata.TestUtils.ATHENS;
import static fr.olympp.kata.TestUtils.TROY;
import static fr.olympp.kata.TestUtils.createArmy;
import static fr.olympp.kata.TestUtils.createClan;
import static fr.olympp.kata.TestUtils.getAllClans;
import static fr.olympp.kata.TestUtils.getClan;
import static fr.olympp.kata.TestUtils.getClanStatus;
import static fr.olympp.kata.TestUtils.postArmy;
import static fr.olympp.kata.TestUtils.postClan;
import static fr.olympp.kata.TestUtils.removeArmy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@AutoConfigureMockMvc
public class ClanControllerTest {

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
    }

    @Test
    void shouldAddClan() throws Exception {
        Clan troy = createClan(TROY);
        MockHttpServletResponse response = postClan(mockMvc, objectMapper, troy).getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        response = postClan(mockMvc, objectMapper, troy).getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void shouldNotAddClanBecauseOfLimit() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Clan athens = createClan(ATHENS);
        postClan(mockMvc, objectMapper, athens);

        Clan troyAthens = createClan(TROY + ATHENS);
        MockHttpServletResponse response = postClan(mockMvc, objectMapper, troyAthens).getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Too many clans"));
    }

    @Test
    void shouldNotAddClanBecauseOfName() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        MockHttpServletResponse response = postClan(mockMvc, objectMapper, troy).getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Clan already exists"));
    }

    @Test
    void shouldGetClan() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        MockHttpServletResponse response = getClan(mockMvc, TROY).getResponse();

        String jsonResponse = response.getContentAsString();
        Clan clanResponse = objectMapper.readValue(jsonResponse, Clan.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(clanResponse, troy);
    }

    @Test
    void shouldGetAllClan() throws Exception {
        MockHttpServletResponse response = getAllClans(mockMvc).getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().equals("[]"));

        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Clan athens = createClan(ATHENS);
        postClan(mockMvc, objectMapper, athens);

        response = getAllClans(mockMvc).getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        String jsonResponse = response.getContentAsString();
        assertTrue(jsonResponse.contains(TROY) && jsonResponse.contains(ATHENS));
    }

    @Test
    void shouldAddArmy() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Army army = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        MockHttpServletResponse response = postArmy(mockMvc, objectMapper, TROY, army).getResponse();

        String jsonResponse = response.getContentAsString();
        Clan clan = objectMapper.readValue(jsonResponse, Clan.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1, clan.getArmies().size());
    }

    @Test
    void shouldRemoveArmy() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Army army = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        postArmy(mockMvc, objectMapper, TROY, army);

        MockHttpServletResponse response = removeArmy(mockMvc, objectMapper, TROY, TROY + ARMY1).getResponse();

        String jsonResponse = response.getContentAsString();
        Clan clan = objectMapper.readValue(jsonResponse, Clan.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(0, clan.getArmies().size());
    }

    @Test
    void shouldNotRemoveArmyBecauseUnknownArmy() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        MockHttpServletResponse response = removeArmy(mockMvc, objectMapper, TROY, TROY + ARMY1).getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Army not found"));
    }

    @Test
    void shouldNotAddArmyBecauseClanNotFound() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Army army = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        MockHttpServletResponse response = postArmy(mockMvc, objectMapper, ATHENS, army).getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Clan not found"));
    }

    @Test
    void shouldGetClanStatus() throws Exception {
        Clan troy = createClan(TROY);
        postClan(mockMvc, objectMapper, troy);

        Army army = createArmy(TROY + ARMY1, 100, 100, 100, 100);
        postArmy(mockMvc, objectMapper, TROY, army);

        MockHttpServletResponse response = getClanStatus(mockMvc, TROY).getResponse();

        String jsonResponse = response.getContentAsString();
        ClanStatusDTO clanStatusDTO = objectMapper.readValue(jsonResponse, ClanStatusDTO.class);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(TROY, clanStatusDTO.getName());
        assertNull(clanStatusDTO.getLastBattleStatus());
        assertFalse(clanStatusDTO.getArmies().isEmpty());
    }

}
