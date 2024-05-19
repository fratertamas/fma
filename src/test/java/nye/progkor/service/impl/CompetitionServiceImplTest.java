package nye.progkor.service.impl;
import nye.progkor.model.Competition;
import nye.progkor.model.Performer;
import nye.progkor.model.PerformerCategory;
import nye.progkor.repository.CompetitionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;


public class CompetitionServiceImplTest {

    private final Calendar calendar = Calendar.getInstance();
    private static final String COMPETITION_ID = "KT2024NY";
    private static final String LOCATION = "location1";
    private static final int MAX_PERFORMERS = 10;
    private static Date COMPETITION_DATE;
    private static String MEMBERSHIP_ID = "K1024";
    private static String PERFORMER_NAME = "Kiss Veronika";
    private AutoCloseable closeable;

    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private CompetitionServiceImpl underTest;

    private Competition competition;
    private Performer performer;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        calendar.set(2024,11,02);
        calendar.set(Calendar.MILLISECOND, 0);
        COMPETITION_DATE = calendar.getTime();
        underTest = new CompetitionServiceImpl(competitionRepository);
    }

    @AfterEach
    void tearDown()  {
        try {
            closeable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test

    public void createCompetition_ShouldReturnPositiveMessage_WhenTheCompetitionCanBeAdd() {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        Mockito.when(competitionRepository.saveCompetition(Mockito.any(Competition.class))).thenReturn(true);

        //WHEN
        String result = underTest.createCompetition(competition);

        //THEN
        Assertions.assertEquals("Competition successfully created.", result);
    }

    @Test
    public void createCompetition_ShouldReturnNegativeMessage_WhenTheCompetitionCannotBeAdd() {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        Mockito.when(competitionRepository.saveCompetition(Mockito.any(Competition.class))).thenReturn(false);

        //WHEN
        String result = underTest.createCompetition(competition);

        //THEN
        Assertions.assertEquals("The competition already exists.", result);
    }

     @Test
    public void getAllCompetition_ShouldReturnRegisteredCompetition_WhenCalled() {
        //GIVEN
         List<Competition> excepted = new ArrayList<>(List.of(new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS)));
         Mockito.when(competitionRepository.getAllCompetition()).thenReturn(excepted);

         //WHEN
         List<Competition> actual = underTest.getAllCompetition();

         //THEN
         Assertions.assertEquals(excepted, actual);
     }

     @Test
    public void registerPerformerForCompetition_ShouldAddThePerformerToTheCompetition_WhenTheCompetitionExistAndThePerformerNotAlreadyRegistered() {
        //GIVEN
         performer = new Performer(MEMBERSHIP_ID, PERFORMER_NAME, PerformerCategory.VOCAL);
         Competition competition = new Competition(COMPETITION_ID, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
         Mockito.when(competitionRepository.findCompetitionById(COMPETITION_ID)).thenReturn(competition);

         //WHEN
         String actual = underTest.registerPerformerForCompetition(COMPETITION_ID,performer);

         //THEN
         Assertions.assertEquals("Performer successfully registered.", actual);
     }
}