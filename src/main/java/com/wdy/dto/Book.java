package com.wdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wgch
 * @Description
 * @date 2019/4/11 15:51
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    public String id;
    public String name;
    public String author;
    public String year;
    public String price;
}
