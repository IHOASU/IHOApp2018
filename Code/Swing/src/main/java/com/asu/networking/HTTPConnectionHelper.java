package main.java.com.asu.networking;

import main.java.com.asu.bl.AuthenticationServer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPConnectionHelper {
	
private final String USER_AGENT = "Mozilla/5.0";

public String sendGet(String section) throws Exception {

	String url = HTTPConstants.url + section ;

	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	// optional default is GET
	con.setRequestMethod("GET");

	//add request header
//			con.setRequestProperty("User-Agent", USER_AGENT);

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	
	System.out.println(response.toString());
	return response.toString();
}



// HTTP POST request
	public JSONObject post(String section, JSONObject object)  throws Exception {

		String url = HTTPConstants.url + section;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
//				con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json ");

		if(!section.equals(HTTPConstants.authSection)) {
            con.setRequestProperty("authorization", AuthenticationServer.getAccessToken());
        }
		String urlParameters = object.toString();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());


		JSONObject jsonObject = new JSONObject(response.toString());
		jsonObject.put("httpCode", responseCode);

		return jsonObject;
	}
	
	public void delete(String section)  throws Exception {
		String url = HTTPConstants.url + section ;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("DELETE");
		con.setRequestProperty("authorization", AuthenticationServer.getAccessToken());

		//add request header
//				con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'DELETE' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		System.out.println(response.toString());
	}
	
	
	public void put(String lectureId,JSONObject object)  throws Exception {
		String url = HTTPConstants.url + "lectureimages/" + lectureId;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("PUT");
//				con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json ");
		con.setRequestProperty("authorization", AuthenticationServer.getAccessToken());

		String urlParameters = object.toString();

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Put parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}

}
