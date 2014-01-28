package npiTest;

import org.junit.Assert;
import org.junit.Test;

import com.omnimd.ws.server.NPIServer;

public class NPITest {

	@Test
	public void testGetProviderData() throws Exception {
		NPIServer npiServer = new NPIServer();
		Assert.assertNotNull(npiServer.getProviderData("1003000118~2"));
	}

}
