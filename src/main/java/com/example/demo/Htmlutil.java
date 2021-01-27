package com.example.demo;

import com.example.demo.dto.CapitalInflowRankingDto;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.assertj.core.util.Lists;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Htmlutil {
    public static void main(String[] args) throws Exception {
        List<CapitalInflowRankingDto> result = Lists.newArrayList();

        String url = "http://data.eastmoney.com/bkzj/hy.html";
        // TODO Auto-generated method stub
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

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
                } else if ("a".equals(nodeName)) {
                    String href = ((HtmlAnchor) domNode).getAttribute("href");
                    try {
                        field.set(capitalInflowRankingDto, href);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
//                    String textContent = domNode.getTextContent();
//                    System.out.print(textContent + "  ");
                }

            }
            result.add(capitalInflowRankingDto);

        });


    }
}
