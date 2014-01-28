package com.omnimd.ws.server;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.omnimd.ws.service.NPIService;

@Path("NPIServer")
public class NPIServer {
	private static Logger logger = Logger.getLogger(NPIServer.class);
	@GET	
	@Path("/NPI/{param1}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getProviderData(@PathParam("param1") String param1) throws Exception 
	{
		logger.debug("Finding NPI details for " + param1);
		
		try{
			NPIService npiService = new NPIService();
			JSONObject result = npiService.findDetails(param1);
			return result!=null?result.toString():null;

		}catch(NumberFormatException e){
			//logger.error("Not An Integer Param - " + param1 + ExceptionUtils.getStackTrace(e));
			logger.debug("Not An Integer Param - " + param1);
			return null;
		}catch(ArrayIndexOutOfBoundsException e){
			//logger.error("Entity Type Code Not Found Param - " + param1 + ExceptionUtils.getStackTrace(e));
			logger.debug("Entity Type Code Not Found Param - " + param1);
			return null;
		}catch(NullPointerException e){
			//logger.error("No Record Found Param - " + param1 + ExceptionUtils.getStackTrace(e));
			logger.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}catch(Exception e){
			logger.error(ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}

	@GET 
	@Path("/age/{j}") 
	@Produces(MediaType.TEXT_XML)
	public String userAge(@PathParam("j") int j) {
	int age = j;
	return "<User>" + "<Age>" + age + "</Age>" + "</User>";
	}
}
