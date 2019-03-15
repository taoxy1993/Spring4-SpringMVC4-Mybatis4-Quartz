package com.dfs.websocket;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手（handshake）接口
 * Created by taoxy1988 on 2019/3/10
 */
@Component
public class HttpSessionIdHandshakeInterceptor extends HttpSessionHandshakeInterceptor  {


	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
								   ServerHttpResponse response, WebSocketHandler wsHandler,
								   Map<String, Object> attributes) throws Exception {
		//解決The extension [x-webkit-deflate-frame] is not supported問題
		if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
		}
		//檢查session的值是否存在
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			String accountId = (String) session.getAttribute(Constants.SKEY_ACCOUNT_ID);
			//把session和accountId存放起來
			attributes.put(Constants.SESSIONID, session.getId());
			attributes.put(Constants.SKEY_ACCOUNT_ID, accountId);
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}


	@Override
	public void afterHandshake(ServerHttpRequest request,
							   ServerHttpResponse response, WebSocketHandler wsHandler,
							   Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}


}