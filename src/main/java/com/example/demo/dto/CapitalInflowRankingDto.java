package com.example.demo.dto;

import lombok.Data;

/**
 * 资金净流入排行
 */
@Data
public class CapitalInflowRankingDto {
    /*序号*/
    private String _totalnumber;
    /*名称*/
    private String name_bk;
    /*相关*/
    private String xg_bk;
    /*今日涨跌幅*/
    private String zdf;

    /*今日主力净流入净额  亿元*/
    private String zlje;

    /*今日主力净流入净占比*/
    private String zljzb;

    /*超大单净流入净额*/
    private String cddje;

    /*超大单净占比*/
    private String cddjzb;

    /*大单净流入净额*/
    private String ddje;

    /*大单净流入净占比*/
    private String ddjzb;

    /*中单净流入净额*/
    private String zdje;

    /*中单净流入净占比*/
    private String zdjzb;

    /*小单净流入净额*/
    private String xdje;

    /*小单净流入净占比*/
    private String xdjzb;

    /*今日主力净流入最大股*/
    private String zdname;
}
