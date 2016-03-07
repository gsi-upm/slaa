package es.upm.dit.gsi.slaa.ra;

import es.upm.dit.gsi.slaa.ratype.SocialNetworkRAProvider;

public class SocialNetworkRAProviderImpl implements SocialNetworkRAProvider {

	TwitterConnection tc;
	
	public SocialNetworkRAProviderImpl() {
		tc = new TwitterConnection();
	}

	public void connectTwitter(String connectionID, String consumerKey,
			String consumerSecret, String accessToken, String tokenSecret) {
		tc.connectTwitter(connectionID, consumerKey, consumerSecret, accessToken, tokenSecret);
	}

	public void sendTweet(String connectionID, String tweet) {
		tc.sendTweet(connectionID, tweet);
	}

	public void disconnectTwitter(String connectionID) {
		tc.disconnectTwitter(connectionID);
	}
}
