package es.upm.dit.gsi.slaa.ratype;

public interface SocialNetworkRAProvider {

	void connectTwitter(String connectionID, String consumerKey, String consumerSecret, String accessToken, String tokenSecret);
	
	void sendTweet(String connectionID, String tweet);
	
	void disconnectTwitter(String connectionID);
}
