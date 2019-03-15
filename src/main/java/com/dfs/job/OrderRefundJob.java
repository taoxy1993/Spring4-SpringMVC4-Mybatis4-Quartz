package com.dfs.job;


import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author taoxy 2019/2/28
 */
@Component
public class OrderRefundJob {

    private static final Logger logger = Logger.getLogger(OrderRefundJob.class);

    //@Autowired
    //private SenseAgroRefundService senseAgroRefundService;


    @Scheduled(cron = "* 0/15 *  * * ?")
    public void refund() {
        // 微信支付宝执行批量退款操作
        //senseAgroRefundService.refundAll();
    }
}
