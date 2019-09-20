package cn.org.lilu;

import cn.org.lilu.states.Events;
import cn.org.lilu.states.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * @Auther: Java成魔之路
 * @Date: 2019/9/20
 * @Description:
 */
@SpringBootApplication
public class StateMachineApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class);
    }

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }
}
