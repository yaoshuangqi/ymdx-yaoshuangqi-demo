package com.xxcx.admin.demo;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 集成飞书机器人通知
 */
public class FlyBookNotifier extends AbstractStatusChangeNotifier {

    private static final String DEFAULT_MESSAGE = "#{instance.registration.name} (#{instance.id}) 状态发生转变 #{lastStatus} ➡️ #{instance.statusInfo.status} " +
            "\n" +
            "\n 实例详情：#{instanceEndpoint}";

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private RestTemplate restTemplate;

    private URI webhookUrl;

    private Expression message;

    public FlyBookNotifier(InstanceRepository repository, RestTemplate restTemplate) {
        super(repository);
        this.restTemplate = restTemplate;
        this.message = parser.parseExpression(DEFAULT_MESSAGE, ParserContext.TEMPLATE_EXPRESSION);
    }

    @Override
    protected Mono<Void> doNotify( InstanceEvent event,  Instance instance) {
        if (webhookUrl == null) {
            return Mono.error(new IllegalStateException("'webhookUrl' must not be null."));
        }
        return Mono
                .fromRunnable(() -> restTemplate.postForEntity(webhookUrl, createMessage(event, instance), Void.class));
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected Object createMessage(InstanceEvent event, Instance instance) {
        Map<String, Object> innerJson = new HashMap<>();

        innerJson.put("tag", "text");
        innerJson.put("text", getText(event, instance));

        ArrayList<Object> list = new ArrayList<>();
        list.add(innerJson);
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(list);
        HashMap<Object, Object> thirdMsgJson = new HashMap<>();
        thirdMsgJson.put("title", "👹警告&👼提醒");
        thirdMsgJson.put("content", objects);

        HashMap<Object, Object> twoMsgJson = new HashMap<>();
        twoMsgJson.put("zh_cn", thirdMsgJson);

        HashMap<Object, Object> oneMsgJson = new HashMap<>();
        oneMsgJson.put("post", twoMsgJson);
        Map<String, Object> messageJson = new HashMap<>();
        messageJson.put("msg_type", "post");
        messageJson.put("content", oneMsgJson);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(messageJson, headers);
    }


    protected String getText(InstanceEvent event, Instance instance) {
        Map<String, Object> root = new HashMap<>();
        root.put("event", event);
        root.put("instance", instance);
        root.put("instanceEndpoint", instance.getEndpoints().toString());
        root.put("lastStatus", getLastStatus(event.getInstance()));
        StandardEvaluationContext context = new StandardEvaluationContext(root);
        context.addPropertyAccessor(new MapAccessor());
        return message.getValue(context, String.class);
    }


    public URI getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(URI webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getMessage() {
        return message.getExpressionString();
    }

    public void setMessage(String message) {
        this.message = parser.parseExpression(message, ParserContext.TEMPLATE_EXPRESSION);
    }

}
