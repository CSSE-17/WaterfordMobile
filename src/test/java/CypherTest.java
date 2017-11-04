import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Cypher;

import static org.junit.Assert.*;

public class CypherTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void generateMD5Test() throws Exception {
        Assert.assertEquals("1c63129ae9db9c60c3e8aa94d3e00495", Cypher.generateMD5("1qaz2wsx"));;
        Assert.assertEquals("25f9e794323b453885f5181f1b624d0b", Cypher.generateMD5("123456789"));;
    }
}