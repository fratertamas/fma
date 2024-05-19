package nye.progkor.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import nye.progkor.model.Competition;
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
}
