package com.lunz;

import lombok.Builder;
import lombok.Data;

/**
 * @author Liuruixia
 * @Description:
 * @date 2020/03/12
 */
@Data
@Builder
public class Test {
    private int id;
    private String name;
    private  int age;
}
