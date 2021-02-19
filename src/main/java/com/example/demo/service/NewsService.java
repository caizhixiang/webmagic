package com.example.demo.service;

import java.io.IOException;

/**
 * 新闻
 */
public interface NewsService {
    /**
     * @Des 获取财经新闻的url
     * @Author caizhixiang
     * @Date 2021/2/8 11:10
     * @Param []
     * @Return void
     */
    void getNewsUrl();

    /**
     * @Des 获取指定资讯的信息
     * @Author caizhixiang
     * @Date 2021/2/8 11:11
     * @Param [informationName]
     * @Return void
     */
    void getInformation(String informationName) throws IOException;
}
