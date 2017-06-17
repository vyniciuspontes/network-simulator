/*
*
* Simulador De Redes
* Redes I - Universidade Federal Fluminense
*
 */

import com.vpontes.simulator.objects.router.Router;
import com.vpontes.simulator.utils.IPV4Util;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vynicius
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void testVirtualAddress() throws UnknownHostException {
        
        IPV4Util virtualAddress = new IPV4Util("10.0.5.0", "255.255.255.0");
        //IPV4Utils virtualAddress = new IPV4Utils("192.168.100.2/24");
        System.out.println("Network Address = " + virtualAddress.getNetworkAddress());
        //assertEquals(virtualAddress.getNetworkAddress(), "200.20.10.64");
    }
    
    @Test
    public void testIP(){
        assertEquals(true,IPV4Util.verifyIp("123.201.020.120"));
    }
    
    //@Test
    public void testRouter(){
        Router router = Router.getInstance();
        
        System.out.println(router.getOriginNode("10.0.5.1"));
    }
    
    
}
