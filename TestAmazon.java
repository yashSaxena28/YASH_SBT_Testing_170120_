package org.deloite.amazon;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestAmazon 
{

	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\geckodriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");
		WebElement search,grocery,kitchen,apparel,mobile;
		
		search = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
		search.sendKeys("twf wheat flour");
		search.sendKeys(Keys.ENTER);
		grocery=driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/span[4]/div[1]/div[2]/div/span/div/div/div/div/span/a/div/img"));
		grocery.click();
		List<String> curentTab = new ArrayList<String>();
		curentTab.add(driver.getWindowHandle());
		Set<String> allTabs = driver.getWindowHandles();
		outer1:
		for(String newTab : allTabs)
		{
			for(String tab : curentTab)
			{
			if(newTab.equalsIgnoreCase(tab))
			{
				continue outer1;
			}
			driver.switchTo().window(newTab);
			}
		}
		driver.findElement(By.xpath("//*[@id='add-to-cart-button']")).click();
		Thread.sleep(1000);
		search = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
		search.sendKeys("chef knife set");
		search.sendKeys(Keys.ENTER);
		kitchen = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/span[4]/div[1]/div[2]/div/span/div/div/div[2]/h2/a/span"));
		kitchen.click();
		curentTab.add(driver.getWindowHandle());
		allTabs = driver.getWindowHandles();
		outer2:
		for(String newTab : allTabs)
		{
			for(String tab : curentTab)
			{
			if(newTab.equalsIgnoreCase(tab))
			{
				continue outer2;
			}
			driver.switchTo().window(newTab);
			}
		}
		driver.findElement(By.xpath("//*[@id='add-to-cart-button']")).click();
		Thread.sleep(1000);
		search = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
		search.sendKeys("Puma Shoes");
		search.sendKeys(Keys.ENTER);
		apparel=driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/span[4]/div[1]/div[2]/div/span/div/div/div[2]/div[1]/div/div/span/a/div/img"));
		apparel.click();
		curentTab.add(driver.getWindowHandle());
		allTabs = driver.getWindowHandles();
		outer3:
		for(String newTab : allTabs)
		{
			for(String tab : curentTab)
			{
			if(newTab.equalsIgnoreCase(tab))
			{
				continue outer3;
			}
			driver.switchTo().window(newTab);
			}
		}
		Select droplist = new Select(driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[4]/div[1]/div[2]/div[2]/div/div/div[1]/div[20]/div[1]/form/div/span[1]/span/select")));
		droplist.selectByVisibleText("7 UK");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='add-to-cart-button']")).click();
		Thread.sleep(1000);
		search = driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
		search.sendKeys("Samsung A30");
		search.sendKeys(Keys.ENTER);
		
		mobile=driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/span[4]/div[1]/div[2]/div/span/div/div/div[2]/div[1]/div/div/span/a/div/img"));
		mobile.click();
		curentTab.add(driver.getWindowHandle());
		allTabs = driver.getWindowHandles();
		outer4:
		for(String newTab : allTabs)
		{
			for(String tab : curentTab)
			{
			if(newTab.equalsIgnoreCase(tab))
			{
				continue outer4;
			}
			driver.switchTo().window(newTab);
			}
		}
		driver.findElement(By.xpath("//*[@id='add-to-cart-button']")).click();
		Thread.sleep(2000);
//		driver.findElement(By.xpath("//*[@id='nav-cart']")).clear();
		
		
		driver.switchTo().window(curentTab.get(0));
		WebElement cart=driver.findElement(By.id("nav-cart"));
		cart.click();
		 java.util.List<WebElement> allitems = driver.findElements(By.className("sc-product-title"));
         int RowCount = allitems.size();
         java.util.List<WebElement> allprice = driver.findElements(By.className("sc-product-price"));
         ArrayList<String> products = new ArrayList<String>();
         ArrayList<String> price =  new ArrayList<String>();
        for(int i=0;i<RowCount;i++)
        {
        	WebElement w1=allitems.get(i);
        	WebElement w2=allprice.get(i);
        	products.add(w1.getText());
        	price.add(w2.getText());
        	System.out.println(w1.getText());
        	System.out.println(w2.getText());
        }
        WebElement total = driver.findElement(By.className("sc-price"));
        System.out.println(total.getText());
		products.add("total");
		price.add(total.getText());
        
        //***************adding to excel sheet*******
		
		 XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("products");
	         for(int i=0;i<RowCount+1;i++)
	         {
	        	 Row row = sheet.createRow(i+1);
	        	 for(int j=1;j<=2;j++)
	        	 {
	        		 Cell cell = row.createCell(j);
	        		 if(j==1)
	        		 cell.setCellValue(products.get(i));
	        		 if(j==2)
	        			 cell.setCellValue(price.get(i));
	        	 }
	         }
	         
	        try (FileOutputStream outputStream = new FileOutputStream("D://products.xlsx")) {
	            workbook.write(outputStream);
	        }
	        catch(Exception e)
	        {}
	}

		
	}


