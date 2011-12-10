package ch.inf.usi.ubicomp.vlc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VLCController
{
	/**
	 * Sends an HTTP GET request to a url
	 *
	 * @param endpoint - The URL of the server. (Example: " http://www.yahoo.com/search")
	 * @param requestParameters - all the request parameters (Example: "param1=val1&param2=val2"). Note: This method will add the question mark (?) to the request - DO NOT add it yourself
	 * @return - The response from the end point
	 * 
	 * 
	 */
	
	public ParseVLCStatus parseVLC;
	
	public VLCController(){
		parseVLC = new ParseVLCStatus();
		
	}
	
	public static String sendGetRequest(String endpoint, String requestParameters){
		String result = null;
		if (endpoint.startsWith("http://"))
		{
			// Send a GET request to the servlet
			try
			{

				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length () > 0)
				{
					urlStr += "?" + requestParameters;
				}
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection ();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
				//System.out.println(result);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String playCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=pl_play");
	}
	
	public String pauseCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=pl_pause");
	}
	
	public String stopCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=pl_stop");
	}
	
	public String nextCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=pl_next");
	}
	
	public String prevCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=pl_previous");
	}
	
	public String fullScreenCommand(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=fullscreen");
	}
	
	public String increaseVolumeCommand(){
		return increaseVolumeCommand(20);
	}
	
	public String decreaseVolumeCommand(){
		return decreaseVolumeCommand(20);
	}
	
	public String increaseVolumeCommand(int delta){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=volume&val=%2B"+ delta);
	}
	
	public String decreaseVolumeCommand(int delta){
		return sendGetRequest("http://localhost:8080/requests/status.xml", "command=volume&val=-" + delta);
	}
	
	public static String getStatus(){
		return sendGetRequest("http://localhost:8080/requests/status.xml", null);
	}
	
	public String increaseSeekCommand(){
		//check the position
		int actualPosition = parseVLC.getPosition();
		int newPosition = actualPosition;
		if(actualPosition <= 90){
			newPosition = actualPosition+10;
			return sendGetRequest("http://localhost:8080/requests/status.xml", "command=seek&val="+newPosition+"%25");
		}else{
			return "not send";
		}
				
	}
	
	public String decreaseSeekCommand(){
		//check the position
		int actualPosition = parseVLC.getPosition();
		int newPosition = actualPosition;
		if(actualPosition >=10){
			newPosition = actualPosition-10;
			return sendGetRequest("http://localhost:8080/requests/status.xml", "command=seek&val="+newPosition+"%25");
		}else{
			return "not send";
		}			
			
	}

	public void playOrPauseCommand() {
		if(parseVLC.isPlaying()) {
			pauseCommand();
		} else {
			playCommand();
		}
	}
	
	
}