package com.xxcx.pay.mq;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author YaoShuangQi
 * @create 2021/1/8 17:31
 * @version 1.0.0
 * @Description RabbitMq消息发送，消息监听实现类
 */
@Slf4j
@Component
public class MRabbitMqTemplate {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * @author YaoShuangQi
	 * @create 2021/2/4 17:25
	 * @version 1.0.0
	 * @Description 根据routingKey来发送消息
	 */
	public void send(String msg, String routingKey, String exchange) {
		String messageId = IdUtil.fastSimpleUUID();
		//消息id实例
		CorrelationData correlationData = new CorrelationData(messageId);
		log.info("==>> 支付消息发送到exchange:[{}], routeKey:[{}], msg:[{}] messageId:{}", exchange, routingKey, msg, messageId);
		rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
	}

	/**
	 * @author YaoShuangQi
	 * @create 2021/1/13 15:22
	 * @version 1.0.0
	 * @Description 发送延迟消息， 需要开启延迟插件
	 */
	public void send(String msg, Long delay, String routingKey, String exchange) {
		log.info("发送rabbitMQ延迟插件实现延时消息:delay={}, msg={}", delay, msg);
		rabbitTemplate.convertAndSend(exchange, routingKey, msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(Integer.valueOf(delay.toString()) );//毫秒
				return message;
			}
		});
	}

	/**
	 * @author YaoShuangQi
	 * @create 2021/1/13 15:22
	 * @version 1.0.0
	 * @Description 发送延迟消息， 暂时采用死信队列完成
	 */
	public void send(Object msg, String routingKey, Long delay) {
		log.info("发送rabbitMQ基于死信队列实现延时消息:delay={}, msg={}", delay, msg);
		rabbitTemplate.convertAndSend(routingKey, msg, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				//设置延迟时间
				message.getMessageProperties().setExpiration(delay.toString());//毫秒
				return message;
			}
		});
	}


	//确认消息是否正确发送到交换机回调（内部做区分处理，使用ack布尔判断）
	final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {

			log.info("==> 发送交换机时触发回调，消息id="+correlationData+",是否ack确认："+ack);
			if(!ack){
				//可以进行日志记录、异常处理、补偿处理等
				log.error("==>> 未发送到交换机，做后置处理，异常原因："+cause);
			}else {
				//更新数据库，可靠性投递机制
				log.info("==>> 成功投递消息，进入业务处理模块...");
			}
		}
	};

	//消息由交换机发送到指定队列，消息的失败回调处理触发
	final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
									String exchange, String routingKey) {
			log.error("==>消息从交换机发送到指定队列失败，触发回调函数做补偿机制处理");
			log.info("return exchange: " + exchange + ", routingKey: "
					+ routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
		}
	};



}
