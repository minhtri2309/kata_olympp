package fr.olympp.kata.repository;

import fr.olympp.kata.models.BattleReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleReportRepository extends JpaRepository<BattleReport, Long> {
    //    void create(BattleReport battleReport);
    BattleReport findTopByOrderByIdDesc();

}
