package junit.org.rapidpm.microservice.optionals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.optionals.ActiveUrlsDetector;
import org.rapidpm.microservice.optionals.ActiveUrlsHolder;
import org.rapidpm.microservice.rest.PingMe;
import junit.org.rapidpm.microservice.rest.provider.ProviderImpl;

public class ActiveUrlsDetectorTest {
  private ActiveUrlsDetector activeUrlsDetector = new ActiveUrlsDetector();

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    final Class<?> aClass = ProviderImpl.class;
    DI.activatePackages(aClass);
    DI.activatePackages(PingMe.class);
  }

  @Test
  public void testDetectUrls() {
    ActiveUrlsHolder holder = activeUrlsDetector.detectUrls();

    assertThat(holder.getRestUrls().size(), is(1));
    assertThat(holder.getRestUrls().contains("http://0.0.0.0:7081/rest/pingme"), is(true));
  }

}
