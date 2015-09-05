import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import javax.net.ssl.HttpsURLConnection;
//import org.json.simple.JSONObject;

class RequestGetSender implements Runnable {
	HttpConnection con = null;
	RequestGetSender(HttpConnection httpCon){
		con = httpCon;
	}
	public void run (){
		try {
			long before = System.currentTimeMillis();
			con.sendGet();
			long after = System.currentTimeMillis();
			System.out.println(" total time in call "+(after-before));
		}catch(Exception e){}}

}
class RequestPostSender implements Runnable {
        HttpConnection con = null;
        RequestPostSender(HttpConnection httpCon){
                con = httpCon;
        }
        public void run (){
		try{
                        long before = System.currentTimeMillis();
			con.sendPost();
                        long after = System.currentTimeMillis();
                        System.out.println(" total time in call "+(after-before));
		}catch(Exception e){}}

}
public class HttpConnection {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		HttpConnection http = new HttpConnection();
		Thread[] get_Thread = new Thread[100];
		Thread[] post_Thread = new Thread[100];
		RequestGetSender getSend = new RequestGetSender(http);
		RequestPostSender postSend = new RequestPostSender(http);
	
		for(int i=0; i<100; i++){
			get_Thread[i] = new Thread(getSend);
			post_Thread[i] = new Thread(postSend);
		}	
		
		for(int i=0; i<100; i++){
		      	get_Thread[i].start();
			post_Thread[i].start();
		}
			//http.sendGet();
        	        //http.sendPost();
	}

// HTTP GET request
	public void sendGet() throws Exception {

		String url = "http://surya-interview.appspot.com/message";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("X-Surya-Email-Id", "lalit0509@gmail.com");

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

		//print result
		System.out.println(response.toString());

		}
	// HTTP POST request
	public void sendPost() throws Exception {

		String url = "http://surya-interview.appspot.com/message";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");

		/*JSONObject obj = new JSONObject();
        	obj.put("emailId", "lalit0509@gmail.com");
	        obj.put("uuid", "fa674442-c513-4b1f-8dce-47f70307143c");*/

		String data = "{\"emailId\":\"lalit0509@gmail.com\",\"uuid\":\"fa674442-c513-4b1f-8dce-47f70307143c\"}";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		wr.writeBytes(obj.toJSONString());
		wr.writeBytes(data);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
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
