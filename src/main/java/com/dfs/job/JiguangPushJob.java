package com.dfs.job;

import com.dfs.jpush.JiguangPush;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author taoxy 2019/2/28
 */
@Component
public class JiguangPushJob {
    private static final String message = "茕茕白兔，东走西顾，衣不如新，人不如故";

    @Scheduled(cron = "* 0/5 * * * ?")
    public void run() {
        synchronized (JiguangPushJob.class) {
            JiguangPush jiguangPush = new JiguangPush();
            //jiguangPush.jiguangPush("1993","www.taoxy.online","0701");
        }

    }
}
