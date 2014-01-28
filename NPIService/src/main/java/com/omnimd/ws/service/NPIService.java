package com.omnimd.ws.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.omnimd.ws.dao.NPILookUpDAO;

public class NPIService {
	
	public JSONObject findDetails(String rowNPI) throws Exception{
		String npi[] = rowNPI.split("~");
		NPILookUpDAO npiLookUpDAO = new NPILookUpDAO();
		
		int entityTypeCode = Integer.parseInt(npi[1]);
		
		JSONObject result = npiLookUpDAO.findProviderDetails(Integer.parseInt(npi[0]),entityTypeCode);
		if(result!=null){
			if(entityTypeCode == 2)
				return filterPracticeData(result);
			else if(entityTypeCode == 1)
				return filterProviderData(result);
		}
		return null;
	}
	
	 private JSONObject filterPracticeData(JSONObject processedRow) {
			// TODO Auto-generated method stub
		 	JSONObject json_obj= filterCommonData(processedRow);
	    	
	    	json_obj.put("Name", processedRow.opt("Provider Organization Name (Legal Business Name)"));
	    	
	    	{	/** Finding Practice Taxonomy **/

	    		Object obj;
		    	boolean flag = false;
		    	for(int j = 1 ; j <= 15 ; j++){
		    		obj = processedRow.opt("Healthcare Provider Taxonomy Group_" + j);
		    		if(obj != null && StringUtils.isNotBlank((obj.toString()))){
		    			json_obj.put("Taxonomy",obj.toString().substring(0, 10));
		    			flag = true;
		    			break;
		    		}	
		    	}
		    	
		    	if(!flag){
		    		/* Now Looking for Healthcare Provider Taxonomy Code */
		    		for(int j = 1 ; j <= 15 ; j++){
			    		obj = processedRow.opt("Healthcare Provider Primary Taxonomy Switch_" + j);
			    		if(
			    				obj != null	&& (StringUtils.isNotBlank((obj.toString())) && "Y".equalsIgnoreCase(obj.toString()))
			    		){
			    			json_obj.put("Taxonomy",processedRow.opt("Healthcare Provider Taxonomy Code_" + j));
			    			break;
			    		}	
			    	}
		    	}
		    	
	    	}   /** Finding Practice Taxonomy END **/
	    	
			return json_obj;
	}
	 
	 private JSONObject filterProviderData(JSONObject processedRow) {
			// TODO Auto-generated method stub
	    	JSONObject json_obj= filterCommonData(processedRow);
	    	
	    	String Name = processedRow.opt("Provider Name Prefix Text")!=null?processedRow.opt("Provider Name Prefix Text").toString() + " ":"";
	    	Name += processedRow.opt("Provider First Name")!=null?processedRow.opt("Provider First Name").toString() + " ":"";
	    	Name += processedRow.opt("Provider Middle Name")!=null?processedRow.opt("Provider Middle Name").toString() + " ":"";
	    	Name += processedRow.opt("Provider Last Name (Legal Name)")!=null?processedRow.opt("Provider Last Name (Legal Name)").toString() : "";
	    	json_obj.put("Name", Name);
	    	json_obj.put("Title", processedRow.opt("Provider Name Prefix Text"));
	    	json_obj.put("FirstName", processedRow.opt("Provider First Name"));
	    	json_obj.put("MiddleName", processedRow.opt("Provider Middle Name"));
	    	json_obj.put("LastName", processedRow.opt("Provider Last Name (Legal Name)"));
	    	json_obj.put("StateLicenseNumber", processedRow.opt("Provider License Number_1"));
	    	{	/** Finding Practice Taxonomy **/

	    		Object obj;
		    	
		    		for(int j = 1 ; j <= 50 ; j++){
			    		obj = processedRow.opt("Other Provider Identifier Type Code_" + j);
			    		if(
			    				obj != null	&& (StringUtils.isNotBlank((obj.toString())) && "02".equalsIgnoreCase(obj.toString()))
			    		){
			    			json_obj.put("UPINNumber",processedRow.opt("Other Provider Identifier_" + j));
			    			break;
			    		}	
			    	}
		    		
		    		/* Now Looking for Healthcare Provider Taxonomy Code */
		    		for(int j = 1 ; j <= 15 ; j++){
			    		obj = processedRow.opt("Healthcare Provider Primary Taxonomy Switch_" + j);
			    		if(
			    				obj != null	&& (StringUtils.isNotBlank((obj.toString())) && "Y".equalsIgnoreCase(obj.toString()))
			    		){
			    			json_obj.put("Taxonomy",processedRow.opt("Healthcare Provider Taxonomy Code_" + j));
			    			break;
			    		}	
			    	}
		    	
		    	
	    	}   /** Finding Practice Taxonomy END **/
	    	
			return json_obj;
	}
	 
	 private JSONObject filterCommonData(JSONObject processedRow) {
			// TODO Auto-generated method stub
	    	JSONObject json_obj=new JSONObject();
	    	
	    	json_obj.put("NPI", processedRow.opt("NPI"));
	    	json_obj.put("TaxId", processedRow.opt("Employer Identification Number (EIN)"));
	    	
	    	json_obj.put("BillingAddress1", processedRow.opt("Provider First Line Business Practice Location Address"));
	    	json_obj.put("BillingAddress2", processedRow.opt("Provider Second Line Business Practice Location Address"));
	    	json_obj.put("BillingAddressCity", processedRow.opt("Provider Business Practice Location Address City Name"));
	    	json_obj.put("BillingAddressState", processedRow.opt("Provider Business Practice Location Address State Name"));
	    	json_obj.put("BillingAddressZip", processedRow.opt("Provider Business Practice Location Address Postal Code"));
	    	json_obj.put("BillingAddressPhone1", processedRow.opt("Provider Business Practice Location Address Telephone Number"));
	    	json_obj.put("BillingAddressFax", processedRow.opt("Provider Business Practice Location Address Fax Number"));
	    	
	    	json_obj.put("MailingAddress1", processedRow.opt("Provider First Line Business Mailing Address"));
	    	json_obj.put("MailingAddress2", processedRow.opt("Provider Second Line Business Mailing Address"));
	    	json_obj.put("MailingAddressCity", processedRow.opt("Provider Business Mailing Address City Name"));
	    	json_obj.put("MailingAddressState", processedRow.opt("Provider Business Mailing Address State Name"));
	    	json_obj.put("MailingAddressZip", processedRow.opt("Provider Business Mailing Address Postal Code"));
	    	json_obj.put("MailingAddressPhone1", processedRow.opt("Provider Business Mailing Address Telephone Number"));
	    	json_obj.put("MailingAddressFax", processedRow.opt("Provider Business Mailing Address Fax Number"));
	    	
	    	json_obj.put("EnumerationDate", processedRow.opt("Provider Enumeration Date"));
	    	json_obj.put("LastUpdateDate", processedRow.opt("Last Update Date"));
	    	
	    	json_obj.put("AuthorizedTitle", processedRow.opt("Authorized Official Title or Position"));
	    	
	    	String AuthorizedName = processedRow.opt("Authorized Official First Name")!=null?processedRow.opt("Authorized Official First Name").toString() + " ":"";
	    	AuthorizedName += processedRow.opt("Authorized Official Middle Name")!=null?processedRow.opt("Authorized Official Middle Name").toString() + " ":"";
	    	AuthorizedName += processedRow.opt("Authorized Official Last Name")!=null?processedRow.opt("Authorized Official Last Name").toString():"";
	    	json_obj.put("AuthorizedName", StringUtils.isNotBlank(AuthorizedName)?"Dr. " + AuthorizedName:"");
	    	json_obj.put("AuthorizedPhone", processedRow.opt("Authorized Official Telephone Number"));
	    	
	    	json_obj.put("EntityTypeCode", processedRow.opt("Entity Type Code"));
	    	
	    	Object obj = processedRow.opt("NPI Deactivation Reason Code");
	    	if(obj!=null){
	    		String[] reasonCodes = {"DT","DB","FR","OT"};
	    		if(ArrayUtils.contains(reasonCodes, obj.toString())){
	    			json_obj.put("NPIDeactivationReasonCode", processedRow.opt("NPI Deactivation Reason Code"));
	    			json_obj.put("NPIDeactivationDate", processedRow.opt("NPI Deactivation Date"));
	    		}
	    	}
	    	
	    	return json_obj;
	}
 
 
}
