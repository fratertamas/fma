package nye.progkor.controller;

import java.util.List;

import nye.progkor.model.Competition;
import nye.progkor.model.Performer;
import nye.progkor.service.CompetitionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    public ResponseEntity<String> createCompetition(@RequestBody Competition competition) {
        String response = competitionService.createCompetition(competition);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @GetMapping
    public List<Competition> getAllCompetition() {
        return competitionService.getAllCompetition();
    }

    @PostMapping("/{competitionId}")
    public ResponseEntity<String> registerPerformerForCompetition(@PathVariable("competitionId") String competitionId,
                                                                  @RequestBody Performer performer) {
        String response = competitionService.registerPerformerForCompetition(competitionId, performer);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @DeleteMapping("/{competitionId}")
    public ResponseEntity<String> deleteCompetition(@PathVariable("competitionId") String competitionId) {
        String response = competitionService.deleteCompetition(competitionId);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateCompetition(@RequestBody Competition competition) {
        String response = competitionService.updateCompetition(competition);
        if (response.contains("successfully")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
