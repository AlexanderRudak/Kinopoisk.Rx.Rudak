package kode.kinopoisk.rudak;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    public int actualValue;
    @Mock
    List mockedList;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void ini(){
        actualValue = 2 + 2;
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, actualValue);
    }

    @Test
    public void addition_isNotCorrect() throws Exception {
        assertEquals("Numbers isn't equals!", 5, 2 + 2);
    }

    @Test(expected = NullPointerException.class)
    public void nullStringTest() {
        String str = null;
        assertTrue(str.isEmpty());
    }



}