package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class MeipaiProccessor  implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");

    @Override
    public void process(Page page) {
        System.setProperty("webdriver.chrome.driver","C:\\workspace\\webmagic\\src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://data.eastmoney.com/bkzj/hy.html");
        //防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //找到登录按钮，点击
        driver.findElement(By.id("filter_stat")).findElements(By.tagName("li")).get(1).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("dialogLWrap")).findElement(By.id("dialogLPlatform")).findElement(By.tagName("a")).click();
        driver.findElement(By.id("userId")).sendKeys("账号");
        driver.findElement(By.id("passwd")).sendKeys("密码");
        driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//p[@class='oauth_formbtn']/a[@node-type='submit']")).click();

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        List<SpiderListener> spiderListeners = new ArrayList<>();
        SpiderListener spiderListener = new SpiderListener() {
            @Override
            public void onSuccess(Request request) {
                System.out.println("sucsess");
            }

            @Override
            public void onError(Request request) {

            }
        };
        spiderListeners.add(spiderListener);
        Spider.create(new MeipaiProccessor())
                .setSpiderListeners(spiderListeners)
                .addUrl("http://data.eastmoney.com/bkzj/hy.html")
                .thread(5)
                .start();
    }
}
