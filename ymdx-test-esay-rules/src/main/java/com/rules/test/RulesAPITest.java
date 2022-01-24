package com.rules.test;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.FileReader;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/1/19 11:16
 * @Version 1.0
 */
public class RulesAPITest {
    public static void main(String[] args) throws Exception {
        //定义一个条件，并封装成fact事实,并添加到facts中
        Facts facts1 = new Facts();
        facts1.put("rain", true);

        // 定义规则，可多种方式，如：注解，链式编程，表达式，yml配置
        /*Rule weatherRule = new RuleBuilder()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when(facts -> facts.get("rain").equals(true))
                .then(facts -> System.out.println("It rains, take an umbrella!"))
                .build();*/
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        String ruleYml = "F:\\Yaosq\\project\\idea_demo\\ymdx-yaoshuangqi-demo\\ymdx-test-esay-rules\\src\\main\\resources\\weather-rule.yml";
        Rule weatherRule = ruleFactory.createRule(new FileReader(ruleYml));
        Rules rules = new Rules();
        //将规则注入到rules中
        rules.register(weatherRule);

        // 定义规则引擎，并注入规则
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts1);
    }
}
