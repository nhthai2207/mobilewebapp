package com.mobileweb.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Base makes sure that before any test empty database is available.
 */

public class APIClient {

	private static final String BASE_URL = Constants.BASE_URL;

	public static String get(String uri) {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(BASE_URL + uri);
			ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			String output = response.getEntity(String.class);
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String post(String uri, Object data) {
		String output = "";
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(BASE_URL + uri);
			// webResource.p
			ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, data);
			output = response.getEntity(String.class);
			System.out.println("OUTPUT: " + output);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String delete(String uri) {
		String output = "";
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(BASE_URL + uri);
			// webResource.p
			ClientResponse response = webResource.accept("application/json").type("application/json").delete(ClientResponse.class);
			output = response.getEntity(String.class);
			System.out.println("OUTPUT: " + output);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
