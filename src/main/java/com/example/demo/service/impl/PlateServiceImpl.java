package com.example.demo.service.impl;

import com.example.demo.dto.CapitalInflowRankingDto;
import com.example.demo.service.PlateService;
import com.example.demo.utils.Htmlutil;
import com.gargoylesoftware.htmlunit.html.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
@Slf4j
public class PlateServiceImpl implements PlateService {
    /**
     * 获取板块资金流排行榜
     */
    @Override
    public void getPlateCapitalFlow() {
        List<CapitalInflowRankingDto> result = Lists.newArrayList();
        String url = "http://data.eastmoney.com/bkzj/hy.html";

        HtmlPage page = Htmlutil.getHtmlPage(url);

        List<Object> dataview = page.getElementById("dataview").getChildNodes().get(1).getLastChild().getByXPath("table/tbody");
        DomNodeList<DomNode> childNodes = ((HtmlTableBody) dataview.get(0)).getChildNodes();
        childNodes.stream().forEach(childNode -> {
            DomNodeList<HtmlElement> td = ((HtmlTableRow) childNode).getElementsByTagName("td");
            CapitalInflowRankingDto capitalInflowRankingDto = new CapitalInflowRankingDto();
            for (int i = 0; i < td.size(); i++) {
                Field[] fields = CapitalInflowRankingDto.class.getDeclaredFields();
                Field field = fields[i];
                field.setAccessible(true);

                DomNode domNode = td.get(i).getFirstChild();
                String nodeName = domNode.getNodeName();
                if ("#text".equals(nodeName)) {
                    try {
                        field.set(capitalInflowRankingDto, domNode.getNodeValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if ("span".equals(nodeName)) {
                    try {
                        field.set(capitalInflowRankingDto, domNode.getFirstChild().getNodeValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if ("a".equals(nodeName)) {
                    String href = ((HtmlAnchor) domNode).getAttribute("href");
                    String textContent = domNode.getTextContent();
                    String value = textContent + "|==|" + href;
                    try {
                        field.set(capitalInflowRankingDto, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
//                    System.out.print(textContent + "  ");
                }

            }
            result.add(capitalInflowRankingDto);

        });
        System.out.println(result);
    }
}
