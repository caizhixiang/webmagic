package com.example.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\caizx\\Desktop\\chromedriver.exe");
        //新建一个WebDriver 的对象，但是new 的是chrome的驱动
        WebDriver driver =new ChromeDriver();
        //打开指定的网站
        driver.get("http://www.baidu.com");
        System.out.println(driver.getTitle());

        /**
         * dr.quit()和dr.close()都可以退出浏览器,简单的说一下两者的区别：第一个close，
         * 如果打开了多个页面是关不干净的，它只关闭当前的一个页面。第二个quit，
         * 是退出了所有Webdriver所有的窗口，退的非常干净，所以推荐使用quit最为一个case退出的方法。
         */
        driver.quit();//退出浏览器
    }
}
