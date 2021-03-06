package com.zuma.sms.api.socket.handler.chain;

import com.zuma.sms.dto.api.cmpp.CMPPConnectAPI;
import com.zuma.sms.dto.api.cmpp.CMPPSubmitAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * author:ZhengXing
 * datetime:2017/12/15 0015 14:38
 * 发送短信响应  处理器
 */
@Slf4j
@Component
public class CMPPSubmitHandler extends AbstractCustomChannelHandler{
	@Override
	public boolean handler(HandleObject handleObject)  throws Exception{
		if(!(handleObject.getMsg() instanceof CMPPSubmitAPI.Response))
			return nextHandler(handleObject);

		CMPPSubmitAPI.Response response = (CMPPSubmitAPI.Response) handleObject.getMsg();
		log.info("[CMPP发送短信响应]通道:{},消息:{}", response,handleObject.getChannel().getName());

		// TODO 处理该回调

		return true;
	}
}