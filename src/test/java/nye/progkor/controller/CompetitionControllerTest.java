package nye.progkor.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import nye.progkor.model.Competition;
import nye.progkor.model.Performer;
import nye.progkor.model.PerformerCategory;
import nye.progkor.service.CompetitionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.*;

@WebMvcTest(CompetitionController.class)
public class CompetitionControllerTest {
    private static final String COMPETITION_ID = "KT2024NY";
    private static final String LOCATION = "location1";
    private static final int MAX_PERFORMERS = 10;
    private static Date COMPETITION_DATE = new GregorianCalendar(2024, Calendar.AUGUST, 7).getTime();
    private static String MEMBERSHIP_ID = "K1024";
    private static String PERFORMER_NAME = "Kiss Veronika";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionService competitionService;

    @Test
    void createCompetition_ShouldReturnOK_WhenCompetitionIsCreated() throws Exception {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        ObjectMapper objectMapper = new ObjectMapper();
        String competitionJson = objectMapper.writeValueAsString(competition);

        Mockito.when(competitionService.createCompetition(Mockito.any(Competition.class)))
                .thenReturn("Competition successfully created.");

        mockMvc.perform(post("/competitions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(competitionJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void getAllCompetitions_ShouldReturnAllTheStoredCompetitions_WhenCalled() throws Exception {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        List<Competition> excepted = List.of(competition);
        given(competitionService.getAllCompetition()).willReturn(excepted);

        mockMvc.perform(MockMvcRequestBuilders.get("/competitions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(COMPETITION_ID)));
    }

    @Test
    void registerPerformerForCompetition_ShouldReturnOK_WhenRegistrationIsSuccesful() throws Exception {
        //GIVEN
        String competitionId = "CompetitionID";
        Performer performer = new Performer(MEMBERSHIP_ID, PERFORMER_NAME, PerformerCategory.VOCAL);
        ObjectMapper objectMapper = new ObjectMapper();
        String performerJson = objectMapper.writeValueAsString(performer);

        Mockito.when(competitionService.registerPerformerForCompetition(competitionId, performer))
                .thenReturn("Perfomer successfully registered.");

        mockMvc.perform(post("/competitions/{competitionId}", competitionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(performerJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void registerPerformerForCompetition_ShouldReturnConflict_WhenRegistrationFails() throws Exception {
        //GIVEN
        Performer performer = new Performer(MEMBERSHIP_ID, PERFORMER_NAME, PerformerCategory.VOCAL);
        ObjectMapper objectMapper = new ObjectMapper();
        String performerJson = objectMapper.writeValueAsString(performer);

        Mockito.when(competitionService.registerPerformerForCompetition(COMPETITION_ID, performer))
                .thenReturn("Performer registration failed.");

        mockMvc.perform(post("/competitions/{competitionId}", COMPETITION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(performerJson))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("failed")));
    }

    @Test
    void deleteCompetition_ShouldReturnOK_WhenDeletionIsSuccessful() throws Exception {
        //GIVEN
        Mockito.when(competitionService.deleteCompetition(COMPETITION_ID))
                .thenReturn("Competition successfully deleted.");

        mockMvc.perform(delete("/competitions/{competitionId}", COMPETITION_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void deleteCompetition_ShouldReturnNotFound_WhenDeletionFails() throws Exception {
        //GIVEN
        Mockito.when(competitionService.deleteCompetition(COMPETITION_ID))
                .thenReturn("Competition deletion failed.");

        mockMvc.perform(delete("/competitions/{competitionId}", COMPETITION_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("failed")));
    }

    @Test
    void updateCompetition_ShouldReturnOK_WhenUpdateIsSuccessful() throws Exception {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        ObjectMapper objectMapper = new ObjectMapper();
        String competitionJson = objectMapper.writeValueAsString(competition);

        Mockito.when(competitionService.updateCompetition(Mockito.any(Competition.class)))
                .thenReturn("Competition successfully updated.");

        mockMvc.perform(put("/competitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionJson))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }

    @Test
    void updateCompetition_ShouldReturnNotFound_WhenUpdateFails() throws Exception {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        ObjectMapper objectMapper = new ObjectMapper();
        String competitionJson = objectMapper.writeValueAsString(competition);

        Mockito.when(competitionService.updateCompetition(Mockito.any(Competition.class)))
                .thenReturn("Competition update failed.");

        mockMvc.perform(put("/competitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(competitionJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("failed")));
    }
}
