package com.example.demo.dto;

import lombok.Data;

/**
 * 资金净流入排行
 */
@Data
public class CapitalInflowRankingDto {
    /*序号*/
    private String num;
    /*名称*/
    private String name;
    /*相关*/
    private String remark;
    /*涨跌幅度*/
    private String range;

    /*净流入  亿元*/
    private String inflow;

    /*净流入最大股*/
    private String example;
}
