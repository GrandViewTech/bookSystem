package com.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.abmware.esb.data.DataHolder;
import com.abmware.esb.data.HttpMethodType;
import com.abmware.esb.data.ResponseDataHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.v2tech.domain.Book;

public class TestAbmMiddleware {
	List<Object> providers = new ArrayList<Object>();	
	@Test
	public void testSendPostRequestToRemoteApp() {
		try {
			providers.add( new JacksonJaxbJsonProvider() );
			String url = "http://localhost:9091/simple/portalWebService/dispatchRequestToRemoteApplication/token/test";
			//String url = "http://localhost/v2chat-1.0/ws/rest/chatService/getContacts/token/test/user/"+URLEncoder.encode("test@abc.com");
			WebClient client = WebClient.create(url, providers);
			DataHolder dataHolder = new DataHolder();
			
			dataHolder.setContentType("application/json");
			dataHolder.setEndPointMethod(HttpMethodType.GET.value());
			dataHolder.setEndPointUrl("http://localhost/v2-booksysN-1.0/ws/rest/bookService/search/genericCriteria/Maths/startFrom/1/maxResults/10/token/test");
			dataHolder.setOrgId("org1001");
			Book book = new Book();
			book.setISBN("isbn_toBeDeleted");
			book.setBookTitle("to be deleted");
			book.setAuthors("jais");
			//dataHolder.setDataToPost(book);
			ObjectMapper mapper = new ObjectMapper();
			String str = mapper.writeValueAsString(dataHolder);
			Response res = client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(str);
			String obj = (String) res.getEntity();
			
			ResponseDataHolder responseDataHolder = mapper.readValue(obj, ResponseDataHolder.class);
			System.out.println(responseDataHolder.getRequestId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
