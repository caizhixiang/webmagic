package com.example.demo.service.impl;


import com.example.demo.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NewsServiceImplTest {

    @Autowired
    private NewsService newsService;

    @Test
    public void getNews() {
        newsService.getNewsUrl();
    }

    @org.junit.Test
    public void getInformation() throws IOException {
        newsService.getInformation("行业研究");
//        newsService.getInformation("产业透视");
//        newsService.getInformation("商业资讯");
    }
}