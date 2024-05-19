package nye.progkor.repository;

import java.util.List;

import nye.progkor.model.Competition;

public interface CompetitionRepository {
    List<Competition> getAllCompetition();

    boolean saveCompetition(Competition competition);

    Competition findCompetitionById(String competitionId);

    boolean deleteCompetition(String competitionId);

    boolean updateCompetition(Competition updatedCompetition);
}