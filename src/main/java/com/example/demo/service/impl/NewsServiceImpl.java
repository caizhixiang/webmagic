package com.example.demo.service.impl;

import com.example.demo.service.NewsService;
import com.example.demo.utils.Htmlutil;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Override
    public void getNews() {
        String url = "http://finance.eastmoney.com/";
        HtmlPage page = Htmlutil.getHtmlPage(url);
        log.info("{}",page.asText());
    }
}
