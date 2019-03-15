package com.dfs.websocket;

import com.dfs.utils.RedisSingletonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * stomp連接處理類
 * Created by taoxy1988 on 2019/3/10
 */
@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);
	private static final RedisSingletonUtil redisUtil = new RedisSingletonUtil();

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		// ignore non-STOMP messages like heartbeat messages
		if (sha.getCommand() == null) {
			return;
		}
		//這裏的sessionId和accountId對應HttpSessionIdHandshakeInterceptor攔截器的存放key
		String sessionId = sha.getSessionAttributes().get(Constants.SESSIONID).toString();
		String accountId = sha.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();
		//判斷客戶端的連接狀態
		switch (sha.getCommand()) {
			case CONNECT:
				connect(sessionId, accountId);
				break;
			case CONNECTED:
				break;
			case DISCONNECT:
				disconnect(sessionId, accountId, sha);
				break;
			default:
				break;
		}
	}

	//連接成功
	private void connect(String sessionId, String accountId) {
		logger.debug(" STOMP Connect [sessionId: " + sessionId + "]");
		//存放至ehcache
		String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
		//若在多個瀏覽器登錄，直接覆蓋保存
		redisUtil.setString(cacheName + accountId, sessionId);
	}

	//斷開連接
	private void disconnect(String sessionId, String accountId, StompHeaderAccessor sha) {
		logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
		sha.getSessionAttributes().remove(Constants.SESSIONID);
		sha.getSessionAttributes().remove(Constants.SKEY_ACCOUNT_ID);
		//ehcache移除
		String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
		if (redisUtil.get(cacheName + accountId) != null) {
			redisUtil.remove(cacheName + accountId);
		}

	}

}