package es.upm.dit.gsi.slaa.ra;

import java.util.Hashtable;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterConnection {
	
	Hashtable<String, Twitter> connections = new Hashtable<String, Twitter>();
	
    public void connectTwitter(String connectionID, String consumerKey, String consumerSecret, String accessToken, String tokenSecret){
    	Twitter twitter = new TwitterFactory().getInstance();
        // Twitter Consumer key & Consumer Secret
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        // Twitter Access token & Access token Secret
        twitter.setOAuthAccessToken(new AccessToken(accessToken, tokenSecret));
        connections.put(connectionID, twitter);
    }
	
    public void sendTweet(String connectionID, String tweet){
    	Twitter twitter = connections.get(connectionID);
    	try {
			Status status = twitter.updateStatus(tweet);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    public void disconnectTwitter(String connectionID){
    	connections.remove(connectionID);
    }

}
