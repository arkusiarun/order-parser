package com.parser.orderparser.dto;

import com.parser.orderparser.util.CommonUtils;
import lombok.Data;

/**
 * @author Arun Singh
 * Resultant POJO.
 * File Data of any Format when parsed this POJO is Populated.
 */

@Data
public class Output {

    private Integer id;
    private long orderId;
    private double amount;
    private String comment;
    private String filename;
    private Integer line;
    private String result;

    @Override
    public String toString() {
        return CommonUtils.getJson(this);
    }
}
