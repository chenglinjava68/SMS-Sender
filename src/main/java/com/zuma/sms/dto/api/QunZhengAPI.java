package com.zuma.sms.dto.api;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * author:ZhengXing
 * datetime:2017/12/5 0005 13:33
 * 群正接口
 */
public interface QunZhengAPI {

	@Data
	@Builder
	class Request {
		private String flag;//操作命令
		private String loginName;//用户id
		private String password;//密码
		private String p;//手机号
		private String c;//短信消息
	}

	@Data
	@Builder
	class Response {
		String code;//返回码
		String id;//本次请求id
	}

	@Data
	@XmlRootElement(name = "result")
	class AsyncResponse {
		//    @XmlElement(name = "response")
		private Integer response;//本次返回的状态报告条数

		//    @XmlElement(name = "sms",type = QunZhengSendSmsAsyncResponseChild.class)
		private List<AsyncResponseChild> sms;//每条报告实体类

		private AsyncResponseChild uniqueSms;//特例，用来给service方法循环调用

	}

	@XmlRootElement(name = "sms")
	@Data
	class AsyncResponseChild {
		//    @XmlElement(name = "phone")
		private String phone;//手机号
		//    @XmlElement(name = "pno")
		private String pno;//流水号
		//    @XmlElement(name = "state")
		private String state;//状态码
	}

	@Data
	@XmlRootElement(name = "result")
	class SmsUpResponse {
		private Integer response;//大于0：此消息所对应的状态报告条数

		private List<SmsUpResponseChild> sms;

		private SmsUpResponseChild uniqueSms;//特例属性，方便service循环调用
	}

	@XmlRootElement(name = "sms")
	@Data
	class SmsUpResponseChild {
		private String phone;//手机号
		private String content;//消息内容
		private String recvdate;//接收日期
		private String serviceNo;//扩展号
	}
}
