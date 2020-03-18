package com.lunz.cep;

/**
 * @author Liuruixia
 * @Description: 用户登录失败检测
 * @date 2020/03/13
 */
public class LoginFailCEPJob {
    //1.读取时间数据，创建简单事件流
    //keyby（）
    //2.定义匹配模式
    //Pattern<String, ?> pattern = Pattern.<String>begin("start").where(
    //        new SimpleCondition<Event>() {
    //            @Override
    //            public boolean filter(Event event) {
    //                return event.getId() == 42;
    //            }
    //        }
    //).next("middle")
    //3.在事件流上应用模式，得到一个pattern stream
    //从pattern stream应用select  function，检出匹配事件序列
    //4.打印输出
}
