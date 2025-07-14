package fr.olympp.kata;

import fr.olympp.kata.dto.ArmyDTO;
import fr.olympp.kata.dto.ClanStatusDTO;
import fr.olympp.kata.exception.ClanNotFoundException;
import fr.olympp.kata.exception.ClanNumberLimitException;
import fr.olympp.kata.models.Army;
import fr.olympp.kata.models.BattleReport;
import fr.olympp.kata.models.BattleTurn;
import fr.olympp.kata.models.Clan;
import fr.olympp.kata.models.ResultStatus;
import fr.olympp.kata.repository.BattleReportRepository;
import fr.olympp.kata.repository.ClanRepository;
import fr.olympp.kata.services.ClanService;
import fr.olympp.kata.services.ClanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static fr.olympp.kata.TestUtils.ARMY1;
import static fr.olympp.kata.TestUtils.ARMY2;
import static fr.olympp.kata.TestUtils.ATHENS;
import static fr.olympp.kata.TestUtils.TROY;
import static fr.olympp.kata.TestUtils.createArmy;
import static fr.olympp.kata.TestUtils.createClan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClanServiceTest {

    private ClanRepository clanRepository;
    private ClanService clanService;
    private BattleReportRepository battleReportRepository;

    @BeforeEach
    void setUp() {
        clanRepository = mock(ClanRepository.class);
        battleReportRepository = mock(BattleReportRepository.class);
        clanService = new ClanServiceImpl(clanRepository, battleReportRepository);
    }

    @Test
    void shouldAddNewClan() {
        Clan athens = createClan(ATHENS);
        clanService.addClan(athens);

        verify(clanRepository).save(athens);

        Clan troy = createClan(TROY);
        clanService.addClan(troy);
        verify(clanRepository).save(troy);
    }

    @Test
    void shouldNotAddNewClan() {
        when(clanRepository.count()).thenReturn(2L);
        Clan athens = createClan(ATHENS);

        assertThrows(ClanNumberLimitException.class, () -> {
            clanService.addClan(athens);
        });

        verify(clanRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllClans() {
        Clan troy = createClan(TROY);
        Clan athens = createClan(ATHENS);

        when(clanRepository.findAll()).thenReturn(List.of(troy, athens));

        List<Clan> clans = clanService.getClans();

        assertThat(clans).containsExactly(troy, athens);
    }

    @Test
    void shouldReturnClanByName() {
        Clan troy = createClan(TROY);

        when(clanRepository.findByName(TROY)).thenReturn(Optional.of(troy));

        Clan found = clanService.getClan(TROY);

        assertThat(found.getName()).isEqualTo(TROY);
    }

    @Test
    void shouldNotReturnClanByName() {
        Clan troy = createClan(TROY);

        when(clanRepository.findByName(TROY)).thenReturn(Optional.of(troy));

        assertThrows(ClanNotFoundException.class, () -> {
            clanService.getClan(ATHENS);
        });
    }


    @Test
    void shouldAddArmyToClan() {
        Clan troy = createClan(TROY);

        when(clanRepository.findByName(TROY)).thenReturn(Optional.of(troy));

        Army army1 = createArmy(ARMY1, 100, 100, 100, 100);
        Army army2 = createArmy(ARMY2, 100, 100, 100, 100);
        Army army3 = createArmy(ARMY2, 100, 100, 100, 100);

        clanService.addArmy(TROY, army1);
        clanService.addArmy(TROY, army2);
        clanService.addArmy(TROY, army3);

        ArgumentCaptor<Clan> captor = ArgumentCaptor.forClass(Clan.class);
        verify(clanRepository, times(3)).save(captor.capture());
        Clan savedClan = captor.getValue();

        assertEquals(2, savedClan.getArmies().size());
        assertThat(savedClan.getArmies()).contains(army1, army3);
        assertFalse(savedClan.getArmies().stream().anyMatch(currentArmy -> currentArmy == army2));
    }

    @Test
    void shouldRemoveArmyFromClan() {
        Clan troy = createClan(TROY);

        when(clanRepository.findByName(TROY)).thenReturn(Optional.of(troy));

        Army army1 = createArmy(ARMY1, 100, 100, 100, 100);
        Army army2 = createArmy(ARMY2, 100, 100, 100, 100);

        clanService.addArmy(TROY, army1);
        clanService.addArmy(TROY, army2);

        clanService.removeArmy(TROY, ARMY1);

        ArgumentCaptor<Clan> captor = ArgumentCaptor.forClass(Clan.class);
        verify(clanRepository, times(3)).save(captor.capture());
        Clan savedClan = captor.getValue();

        assertThat(savedClan.getArmies()).doesNotContain(army1);
        assertThat(savedClan.getArmies()).contains(army2);
    }

    @Test
    void shouldGetClanStatus() {
        Clan troy = createClan(TROY);
        Clan athens = createClan(ATHENS);

        when(clanRepository.findByName(TROY)).thenReturn(Optional.of(troy));
        when(clanRepository.findByName(ATHENS)).thenReturn(Optional.of(athens));

        Army army1 = createArmy(ARMY1, 100, 100, 100, 100);

        Army army2 = new Army();
        army2.setName(ARMY2);

        clanService.addArmy(TROY, army1);
        clanService.addArmy(ATHENS, army2);

        BattleReport battleReport = new BattleReport();
        battleReport.setWinner(TROY);
        battleReport.setStatus(ResultStatus.WON);
        battleReport.setInitialClans(Arrays.asList(troy, athens));

        BattleTurn battleTurn1 = new BattleTurn(TROY, ATHENS, 100, 200, 50, 20);
        battleReport.addBattleTurn(battleTurn1);

        BattleTurn battleTurn2 = new BattleTurn(TROY, ATHENS, 70, 150, 30, 0);
        battleReport.addBattleTurn(battleTurn2);

        when(battleReportRepository.findTopByOrderByIdDesc()).thenReturn(battleReport);
        ClanStatusDTO clanReport = clanService.getClanStatus(TROY);

        assertEquals(clanReport.getName(), TROY);
        assertEquals(clanReport.getLastBattleStatus(), ResultStatus.WON);
        List<ArmyDTO> armyDTOs = clanReport.getArmies();
        assertEquals(1, armyDTOs.size());
        ArmyDTO army1DTO = armyDTOs.getFirst();
        assertEquals(ARMY1, army1DTO.getName());
        assertEquals(100, army1DTO.getAttack());
        assertEquals(100, army1DTO.getDefense());
        assertEquals(100, army1DTO.getHealth());
        assertEquals(100, army1DTO.getNbUnits()); //todo ?

    }
}
