package com.wdy.biz.progress.jfinal;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wgch
 * @Description 创建线程
 * @date 2019/4/24 9:49
 */
public class AlarmThreadController extends Thread {
    private static String lastId = "0";
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static class SingletonHolder {
        private static AlarmThreadController instance = new AlarmThreadController();
    }

    public static AlarmThreadController getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
//        super.run();
//        Alarm alarm = Alarm.dao.findFirst("select max(AlarmId) as maxId from alarm");
//        if (alarm != null && alarm.getInt("maxId") != null) {
//            lastId = String.valueOf(alarm.getInt("maxId"));
//        }
//        logger.info("begin ID:" + lastId);
        while (true) {
            try {
                sleep(30000);
//                List<Alarm> list = Alarm.dao.find("select AlarmId  from alarm  where AlarmId>? group by AlarmId desc", lastId);
//                String idsJSON = "";
//                if (list != null && list.size() > 0) {
//                    for (int i = 0; i < list.size(); i++) {
//                        if (i == 0) {
//                            lastId = String.valueOf(list.get(i).getInt("AlarmId"));
//                            idsJSON += list.get(i).getInt("AlarmId");
//                        } else if (i > 0 && i < list.size() - 1) {
//                            idsJSON += "," + list.get(i).getInt("AlarmId");
//                        } else {
//                            idsJSON += "," + list.get(i).getInt("AlarmId");
//                        }
//                    }
//                    //System.out.println(idsJSON);
//                    String type = "success";
//                    String message = "告警日志，新添加了" + list.size() + "条数据，请自行查看。";
//                    WebSocketController.broadcastAll(type, message, idsJSON);
//                }
//                logger.info("last Id:" + lastId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    // 把数据库里的数据，进行处理，转成long 和现在时间进行比较
    public static long getDateLongVal(String str) {
        int one = str.indexOf("AlarmTime:");
        String str1 = str.substring(one + 10, one + 29);
        String st1 = str1.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        return Long.parseLong(st1);
    }

}