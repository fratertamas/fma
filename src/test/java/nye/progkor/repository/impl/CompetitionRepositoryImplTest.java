package nye.progkor.repository.impl;

import nye.progkor.model.Competition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompetitionRepositoryImplTest {

    private final Calendar calendar = Calendar.getInstance();
    private static final String COMPETITION_ID_FIRST = "KT2024NY";
    private static final String COMPETITION_ID_SECOND = "VL2024T";
    private static final String LOCATION = "location1";
    private static final String UPDATED_LOCATION = "location2";
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

    @Test
    void findCompetition_ShouldReturnTheCompetition_WhenTheCompetitionIsAlreadyStored() {
        //Given:
        Competition excepted = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        underTest.saveCompetition(excepted);

        //WHEN
        Competition actual = underTest.findCompetitionById(COMPETITION_ID_FIRST);

        //THEN
        Assertions.assertEquals(actual, excepted);
    }

    @Test
    void updateCompetition_ShouldUpdateExistingCompetition_WhenCompetitionExists() {
        // GIVEN
        Competition original = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        underTest.saveCompetition(original);

        Competition updated = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, UPDATED_LOCATION, MAX_PERFORMERS);

        // WHEN
        boolean result = underTest.updateCompetition(updated);

        // THEN
        assertTrue(result);
        Competition actual = underTest.findCompetitionById(COMPETITION_ID_FIRST);
        Assertions.assertEquals(UPDATED_LOCATION, actual.getLocation());
    }

    @Test
    void updateCompetition_ShouldReturnFalse_WhenCompetitionDoesNotExist() {
        // GIVEN
        Competition updated = new Competition(COMPETITION_ID_SECOND, COMPETITION_DATE, UPDATED_LOCATION, MAX_PERFORMERS);

        // WHEN
        boolean result = underTest.updateCompetition(updated);

        // THEN
        assertFalse(result);
    }
    @Test
    void deleteCompetition_ShouldRemoveCompetitionFromRepository_WhenCompetitionExists() {
        // GIVEN
        Competition competition = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        underTest.saveCompetition(competition);

        // WHEN
        boolean result = underTest.deleteCompetition(COMPETITION_ID_FIRST);

        // THEN
        assertTrue(result);
        Assertions.assertNull(underTest.findCompetitionById(COMPETITION_ID_FIRST));
    }

    @Test
    void deleteCompetition_ShouldReturnFalse_WhenCompetitionDoesNotExist() {
        // WHEN
        boolean result = underTest.deleteCompetition(COMPETITION_ID_SECOND);

        // THEN
        assertFalse(result);
    }

    @Test
    void getAllCompetition_ShouldReturnAllCompetitions_WhenCalled() {
        // GIVEN
        Competition competition1 = new Competition(COMPETITION_ID_FIRST, COMPETITION_DATE, LOCATION, MAX_PERFORMERS);
        Competition competition2 = new Competition(COMPETITION_ID_SECOND, COMPETITION_DATE, UPDATED_LOCATION, MAX_PERFORMERS);
        underTest.saveCompetition(competition1);
        underTest.saveCompetition(competition2);

        // WHEN
        List<Competition> allCompetitions = underTest.getAllCompetition();

        // THEN
        Assertions.assertTrue(allCompetitions.contains(competition1));
        Assertions.assertTrue(allCompetitions.contains(competition2));
    }
}
