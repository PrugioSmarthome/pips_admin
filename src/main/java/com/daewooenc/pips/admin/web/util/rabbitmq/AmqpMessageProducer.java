package com.daewooenc.pips.admin.web.util.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * AMQP Listener로 받은 에러메시지를 다시 AMQP Topic으로 담아 보내는 Producer for 웹 사용자 제어결과 알림
 * @author : dokim
 * @version :
 * @see <pre>
 * == Modification Information ==<br/>
 *
 *         Date        :       User          :     Description      <br/>
 * ---------------------------------------------------------------  <br/>
 *       2019-10-08      :       dokim        :                       <br/>
 *
 * </pre>
 * @since : 2019-10-08
 **/
@Component
public class AmqpMessageProducer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // TODO 설정파일에서 정보 불러오도록 처리
    private static String rabbitmqIp;

    @Value("${rabbitmq.host}")
    public void setRabbitmqIp(String ip) {
        this.rabbitmqIp = ip;
    }

    private static int rabbitmqPort;

    @Value("${rabbitmq.port}")
    public void setRabbitmqPort(int port) {
        this.rabbitmqPort = port;
    }

    @Value("${rabbitmq.username}")
    private String userName;

    @Value("${rabbitmq.password}")
    private String pwd;

    @Value("${rabbitmq.noti.queue}")
    private String queueName;

    public void sendMessage(String routingKey, String message) {
        logger.debug("AmqpMessageProducer sendMessage: {}", message);

        CachingConnectionFactory cf = null;
        try {
            cf = new CachingConnectionFactory(rabbitmqIp, rabbitmqPort);
            cf.setUsername("webAdmin");
            cf.setPassword("webAdmin1!");

            //메시지 보내기
            RabbitTemplate template = new RabbitTemplate(cf);
            template.setExchange("amq.topic");
            template.setQueue("ADMIN-Noti-Subscribe");
            template.setEncoding("UTF-8");
            template.convertAndSend(routingKey, message);
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cf != null) {
                try {
                    cf.destroy();
                }catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}