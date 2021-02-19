package com.example.demo.service.impl;

import com.example.demo.commons.utils.Htmlutil;
import com.example.demo.dao.NewsDao;
import com.example.demo.dto.NewsDto;
import com.example.demo.service.NewsService;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    Map<String, String> financeMap = Maps.newHashMap();
    String[] financeStr = {"产经资讯", "产业透视", "商业资讯", "商业观察", "行业研究"};

    {
        financeMap.put("fi", "http://finance.eastmoney.com/a/ccjxw.html");
        financeMap.put("产业透视", "http://finance.eastmoney.com/a/ccyts.html");
        financeMap.put("商业资讯", "http://biz.eastmoney.com/a/csyzx.html");
        financeMap.put("商业观察", "http://finance.eastmoney.com/news/csygc.html");
        financeMap.put("行业研究", "http://stock.eastmoney.com/news/chyyj.html");

    }

    @Override
    public void getNewsUrl() {
        String url = "http://finance.eastmoney.com/";
        HtmlPage page = Htmlutil.getHtmlPage(url);
        for (int i = 0; i < financeStr.length; i++) {
            financeMap.put(financeStr[i], page.getAnchorByText(financeStr[i]).getHrefAttribute());
        }
    }

    @Override
    public void getInformation(String informationName) throws IOException {
        HtmlPage page = Htmlutil.getHtmlPage(financeMap.get(informationName));

        while (true) {
            parseNewsInPage(page);
            //这个div里有当前页码，和下一页的链接
            Object pagerNoDiv = page.getFirstByXPath("//div[@id='pagerNoDiv']");
            //下一页
            DomElement previousElementSibling = ((HtmlDivision) pagerNoDiv).getLastChild().getPreviousElementSibling();
            String textContent1 = previousElementSibling.getTextContent();
            if (!"下一页".equals(textContent1)) {
                break;
            }
            page = (HtmlPage) ((HtmlAnchor) previousElementSibling).openLinkInNewWindow();

        }


    }

    //解析新闻列表
    private void parseNewsInPage(HtmlPage page) {
        //        log.info("{}", page.asXml());
        //当前页码
        Object span = page.getFirstByXPath("//div[@id='pagerNoDiv']/span");
        if (span != null) {
            String pageNo = ((HtmlSpan) span).getTextContent();
            log.info("当前页码为：{}", pageNo);
        }
        //新闻
        List<Object> objectList = page.getByXPath("//ul[@id='newsListContent']/li/div[contains(@class,'text')]");

        if (CollectionUtils.isNotEmpty(objectList)) {
            List<NewsDto> newsDtoList = Lists.newArrayList();
            objectList.stream().forEach(object -> {
                HtmlDivision htmlDivision = (HtmlDivision) object;
                DomNodeList<DomNode> childNodes = htmlDivision.getChildNodes();

                if (CollectionUtils.isNotEmpty(childNodes)) {
                    NewsDto newsDto = new NewsDto();
                    childNodes.stream().forEach(childNode -> {
                        if (childNode instanceof DomText) {
                            return;
                        }
                        String aClass = ((HtmlParagraph) childNode).getAttribute("class");
                        String textContent = childNode.getTextContent();
                        if (StringUtils.isBlank(textContent)) {
                            return;
                        }
                        textContent = textContent.trim();
                        if ("title".equals(aClass)) {
                            newsDto.setTitle(textContent);
                            newsDto.setNewsUrl(((HtmlParagraph) childNode).getElementsByTagName("a").get(0).getAttribute("href"));
                        } else if ("info".equals(aClass)) {
                            newsDto.setInfo(textContent);
                        } else if ("time".equals(aClass)) {
                            try {
                                Date date = DateUtils.parseDate("2020年".concat(textContent), "yyyy年MM月dd日 hh:mm");
                                newsDto.setPublicationTime(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        newsDto.setCreateTime(new Date());
                    });
                    newsDtoList.add(newsDto);
                }

            });
            log.info("{}", newsDtoList);
            newsDao.saveAll(newsDtoList);
        }
    }


}
