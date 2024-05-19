package nye.progkor.repository;

import nye.progkor.model.Competition;
import nye.progkor.repository.impl.CompetitionRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompetitionRepositoryImplTest {

    private final Calendar calendar = Calendar.getInstance();
    private static final String COMPETITION_ID_FIRST = "KT2024NY";
    private static final String COMPETITION_ID_SECOND = "VL2024T";
    private static final String LOCATION = "location1";
    private static final int MAX_PERFORMERS = 10;
    private static Date COMPETITION_DATE;
    private AutoCloseable closeable;

    @InjectMocks
    private CompetitionRepositoryImpl underTest;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        calendar.set(2024,11,02);
        calendar.set(Calendar.MILLISECOND, 0);
        COMPETITION_DATE = calendar.getTime();
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
    void saveCompetition_ShouldAddCompetitionToTheCompetitionRepository_WhenCalled() {
        //GIVEN
        Competition competition = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        //WHEN
        underTest.saveCompetition(competition);
        //THEN
        assertTrue(underTest.getAllCompetition().contains(competition));
    }
}
