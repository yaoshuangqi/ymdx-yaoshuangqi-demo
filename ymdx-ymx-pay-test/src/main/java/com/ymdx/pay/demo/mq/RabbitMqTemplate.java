package com.ymdx.pay.demo.mq;

import com.ymdx.pay.demo.constant.PayType;
import com.ymdx.pay.demo.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.ymdx.pay.demo.mq.MqConfig.*;


/**
 * @author YaoShuangQi
 * @create 2021/1/8 17:31
 * @version 1.0.0
 * @Description RabbitMq消息发送，消息监听实现类
 */
@Slf4j
@Component
public class RabbitMqTemplate implements InitializingBean {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * @author YaoShuangQi
	 * @create 2021/1/13 15:22
	 * @version 1.0.0
	 * @Description 发送普通消息
	 */
	public void send(String msg, String routeKey) {
		//发送消息到rabbitmq 用于同步确认消息正常发送到交换机
		String messageId = SnowFlakeUtils.getWorkerId();//IdWorker.getTimeId();
		//消息头设置
		CorrelationData correlationData = new CorrelationData(messageId);
		log.info("发送数据到交换机={}  routeKey={}   msg={} ", WITHHOLD_PAY_EXCHANGE, WITHHOLD_PAY_QUEUE, msg);
		rabbitTemplate.convertAndSend(WITHHOLD_PAY_EXCHANGE, routeKey, msg, correlationData);
		/*rabbitTemplate.convertAndSend(WITHHOLD_PAY_EXCHANGE, routeKey, msg, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setHeader("payType", payType);
				return message;
			}
		}, correlationData);*/

		//同步确认交换机正常收到消息
		try {
			CorrelationData.Confirm confirm = correlationData.getFuture().get(1, TimeUnit.SECONDS);
			System.out.println("数据发送完成 confirm : "+confirm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author YaoShuangQi
	 * @create 2021/1/13 15:22
	 * @version 1.0.0
	 * @Description 发送延迟消息， 需要开启延迟插件
	 */
	public void send(String msg, long delay) {
		log.info("发送rabbitMQ延时消息:msg={},delay={}", msg, delay);
		rabbitTemplate.convertAndSend(PAY_NOTIFY_EXCHANGE_NAME, PAY_NOTIFY_QUEUE_NAME, msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay((int) delay);
				return message;
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//目前只做日志打印
		rabbitTemplate.setConfirmCallback(confirmCallback);
		rabbitTemplate.setReturnCallback(returnCallback);
	}




	//确认消息是否正确发送到交换机回调（内部做区分处理，使用ack布尔判断）
	final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {

			log.info("==> 发送交换机时触发回调，消息id="+correlationData+",是否ack确认："+ack);
			if(!ack){
				//可以进行日志记录、异常处理、补偿处理等
				log.info("未发送到交换机，做后置处理，异常原因："+cause);
			}else {
				//更新数据库，可靠性投递机制
				log.info("==> 成功投递消息，进入业务处理模块...");
			}
		}
	};

	//消息由交换机发送到指定队列，消息的失败回调处理触发
	final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
									String exchange, String routingKey) {
			log.info("==>消息从交换机发送到指定队列失败，触发回调函数做补偿机制处理");
			log.info("return exchange: " + exchange + ", routingKey: "
					+ routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
		}
	};
}
