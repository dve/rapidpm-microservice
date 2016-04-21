package junit.org.rapidpm.microservice.propertyservice.impl;

import junit.org.rapidpm.microservice.propertyservice.BaseDITest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.propertyservice.api.PropertyService;
import org.rapidpm.microservice.propertyservice.impl.PropertyServiceImpl;

import java.util.Set;

public class PropertyServiceTest003 extends BaseDITest {

  /*@Inject
  PropertyService service01;
  @Inject
  PropertyService service02;
*/

  private PropertyService service01;
  private PropertyService service02;

  @Override
  @Before
  public void setUp() throws Exception {
    service01 = new PropertyServiceImpl();
    service02 = new PropertyServiceImpl();
    super.setUp();
    DI.activateDI(service01);
    DI.activateDI(service02);
    System.setProperty("file", this.getClass().getResource("").getPath());
    System.setProperty("distributed", "true");
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
    service01.shutdown();
    service02.shutdown();
  }

  @Test
  public void test001() throws Exception {
    System.setProperty("mapname", "test001");
    service01.initFromCmd();
    service01.loadProperties("example");
    service02.initFromCmd();
    final String singleProperty = service02.getSingleProperty("example.part01.001");
    Assert.assertNotNull(singleProperty);
    //Assert.assertFalse(singleProperty.isEmpty());
    //Assert.assertEquals("test001", singleProperty);
  }

  @Test
  public void test002() throws Exception {
    System.setProperty("mapname", "test002");
    service01.initFromCmd();
    service02.initFromCmd();
    service02.loadProperties("example");
    final String singleProperty = service01.getSingleProperty("example.part01.001");
    Assert.assertNotNull(singleProperty);
    //Assert.assertFalse(singleProperty.isEmpty());
  }

  @Test
  public void test003() throws Exception {
    System.setProperty("mapname", "test003");
    service01.initFromCmd();
    service02.initFromCmd();
    service01.loadProperties("example");

    final Set<String> indexToDomain = service01.getIndexOfScope("example");
    Assert.assertNotNull(indexToDomain);
    //Assert.assertTrue(indexToDomain.size() == 2);
  }


}
