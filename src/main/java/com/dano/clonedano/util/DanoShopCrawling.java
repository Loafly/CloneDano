package com.dano.clonedano.util;

import com.dano.clonedano.model.Product;
import com.dano.clonedano.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DanoShopCrawling {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "chromedriver.exe"; // 드라이버 경로

    public static void DanoShopCrawlingFunc(ProductRepository productRepository){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        WebDriver driver = new ChromeDriver();

        DanoCategory[] danoCategories = DanoCategory.values();

        for (DanoCategory danoCategory : danoCategories){

            String krCategory = danoCategory.getKrCategory();
            int categoryNumber = danoCategory.getCategoryNumber();

            String strUrl = "https://danoshop.net/category?cate_no=" + categoryNumber;

            //webDriver를 해당 url로 이동한다.
            driver.get(strUrl);

            //브라우저 이동시 생기는 로드시간을 기다린다.
            //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollTo({top:document.body.scrollHeight, left:0, behavior:'smooth'})");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> webElementList = driver.findElements(By.className("prd-list"));

            for (WebElement webElement : webElementList){
                String strTitle = null;
                String strPrice = null;
                String strImageUrl = null;
                boolean isDano = false;
                boolean isFree = false;
                boolean isThrifty = false;
                boolean isBest = false;
                boolean isNew = false;

                strTitle = webElement.findElement(By.className("prd-name")).getText();

                strPrice = webElement.findElement(By.className("price")).getText();

                strImageUrl = webElement.findElement(By.className("thumbnail")).findElement(By.tagName("img")).getAttribute("src");

                if (krCategory.contains("다노")){
                    isDano = true;
                } else if (krCategory.contains("무료")){
                    isFree = true;
                } else if (krCategory.contains("알뜰")){
                    isThrifty = true;
                } else if (krCategory.contains("인기")){
                    isBest = true;
                } else if (krCategory.contains("신상품")){
                    isNew = true;
                }

                System.out.println("strTitle = " + strTitle);
                System.out.println("strPrice = " + strPrice);
                System.out.println("strImageUrl = " + strImageUrl);

                Product product = productRepository.findByTitle(strTitle);

                if (product == null){
                    product = Product.builder()
                            .title(strTitle)
                            .imageUrl(strImageUrl)
                            .price(strPrice)
                            .isDano(isDano)
                            .isFree(isFree)
                            .isThrifty(isThrifty)
                            .isBest(isBest)
                            .isNew(isNew)
                            .build();

                    productRepository.save(product);
                } else{
                    product.setImageUrl(strImageUrl);
                    if (krCategory.contains("다노")){
                        product.setDano(isDano);
                    } else if (krCategory.contains("무료")){
                        product.setFree(isFree);
                    } else if (krCategory.contains("알뜰")){
                        product.setThrifty(isThrifty);
                    } else if (krCategory.contains("인기")){
                        product.setBest(isBest);
                    } else if (krCategory.contains("신상품")){
                        product.setNew(isNew);
                    }
                    productRepository.save(product);
                }
            }
        }
        // 크롤링이 끝났을 경우 driver 종료
        try {
            // 드라이버 연결 종료
            driver.close();
            // 프로세스 종료
            driver.quit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }




}
