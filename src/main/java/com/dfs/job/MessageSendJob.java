package com.dfs.job;


import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author taoxy 2019/2/28
 */
@Component
public class MessageSendJob {

    private static final Logger logger = Logger.getLogger(MessageSendJob.class);

    //@Autowired
    //private SenseAgroMessageService senseAgroMessageService;

    @Scheduled(cron = "* 0/5 * * * ?")
    public void message() {
        // 执行批量消息推送操作
        //senseAgroMessageService.messageAll();
    }
}
