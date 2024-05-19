package nye.progkor.repository.impl;

import nye.progkor.model.Competition;
import nye.progkor.repository.CompetitionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompetitionRepositoryImpl implements CompetitionRepository {
    private final List<Competition> competitions = new ArrayList<>();

    public List<Competition> getAllCompetition() {
        return competitions;
    }

    public boolean saveCompetition(Competition competition) {
        if (!competitions.contains(competition)) {
            competitions.add(competition);
            return true;
        } else {
            return false;
        }
    }

    public Competition findCompetitionById(String competitionId) {
        for (Competition competition : competitions) {
            if (competition.getId().equals(competitionId)) {
                return competition;
            }
        }
        return null;
    }

    public boolean deleteCompetition(String competitionId) {
        return competitions.removeIf(competition -> competition.getId().equals(competitionId));
    }

    public boolean updateCompetition(Competition updatedCompetition) {
        Competition existingCompetition = findCompetitionById(updatedCompetition.getId());
        if (existingCompetition != null) {
            competitions.remove(existingCompetition);
            competitions.add(updatedCompetition);
            return true;
        } else {
            return false;
        }
    }
}
