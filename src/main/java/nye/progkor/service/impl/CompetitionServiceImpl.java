package nye.progkor.service.impl;

import java.util.List;

import nye.progkor.model.Competition;
import nye.progkor.model.Performer;
import nye.progkor.repository.CompetitionRepository;
import nye.progkor.service.CompetitionService;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepositoryImpl) {
        this.competitionRepository = competitionRepositoryImpl;
    }

    public String createCompetition(Competition competition) {
        boolean isSaved = competitionRepository.saveCompetition(competition);
        if (isSaved) {
            return "Competition successfully created.";
        } else {
            return "The competition already exists.";
        }
    }

    public List<Competition> getAllCompetition() {
        return competitionRepository.getAllCompetition();
    }

    public String registerPerformerForCompetition(String competitionId, Performer performer) {
        Competition competition = competitionRepository.findCompetitionById(competitionId);

        if (competition != null) {
            boolean isSaved = competition.registerPerformer(performer);
            if (isSaved) {
                return "Performer successfully registered.";
            } else {
                return "The performer already registered.";
            }
        } else {
            throw new IllegalArgumentException("The competition with the specified identifier could not be found.");
        }
    }

    public String deleteCompetition(String competitionId) {
        boolean isDeleted = competitionRepository.deleteCompetition(competitionId);
        if (isDeleted) {
            return "Competition successfully deleted.";
        } else {
            return "The competition could not be found.";
        }
    }

    public String updateCompetition(Competition competition) {
        boolean isUpdated = competitionRepository.updateCompetition(competition);
        if (isUpdated) {
            return "Competition successfully updated.";
        } else {
            return "The competition could not be found.";
        }
    }
}
