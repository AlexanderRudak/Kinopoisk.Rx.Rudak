package kode.kinopoisk.rudak;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.stfalcon.demoapp", appContext.getPackageName());
    }
}
