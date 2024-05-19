package nye.progkor.service;

import nye.progkor.model.Competition;
import nye.progkor.model.Performer;

import java.util.List;

public interface CompetitionService {
    String createCompetition(Competition competition);

    List<Competition> getAllCompetition();

    String registerPerformerForCompetition(String competitionId, Performer performer);

    String deleteCompetition(String competitionId);

    String updateCompetition(Competition competition);
}
