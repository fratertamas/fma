package nye.progkor.repository;

import nye.progkor.model.Competition;

import java.util.List;

public interface CompetitionRepository {
    List<Competition> getAllCompetition();

    boolean saveCompetition(Competition competition);

    Competition findCompetitionById(String competitionId);

    boolean deleteCompetition(String competitionId);

    boolean updateCompetition(Competition updatedCompetition);
}