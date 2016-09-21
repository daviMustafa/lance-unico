package br.com.triadworks.lanceunico.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PesquisaNoGoogle {
	
	/**
	 * Teste utilizando versões anteriores à 47.0 do firefox
	 * @param args
	 */
	public static void main(String[] args) {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();

        String marionetteDriverLocation = "/home/davi.oliveira/workspaceEstudo/lance-unico/geckodriver";
        System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
        capabilities.setCapability("marionette", true);
		WebDriver driver = new MarionetteDriver(capabilities);
		
		// acessa o site do google
		driver.get("http://www.google.com.br/");
		
		// digita no campo com nome "q" do google
		WebElement campoDeTexto = driver.findElement(By.name("q"));
		campoDeTexto.sendKeys("triadworks");
		
		// submete o form
		campoDeTexto.submit();
		driver.close();
	}
}