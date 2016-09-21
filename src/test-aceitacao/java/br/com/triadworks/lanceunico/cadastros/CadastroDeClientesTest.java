package br.com.triadworks.lanceunico.cadastros;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertTrue;

public class CadastroDeClientesTest {
	
	private static WebDriver driver;
    private static DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    private static String marionetteDriverLocation = "geckodriver";

	@BeforeClass
	public static void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", marionetteDriverLocation);
        capabilities.setCapability("marionette", true);
		driver = new MarionetteDriver(capabilities);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		driver.close();
	}
	
	@Before
	public void abrePagina(){
        driver.get("http://localhost:8080/lance-unico/pages/clientes/novo.xhtml");
	}

	@Test
	public void deveAdicionarNovoCliente() {
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys("Davi");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("davi@gmail.com");
		
		WebElement botao = driver.findElement(By.id("btn-salvar"));
		botao.click();
	
		String html = driver.getPageSource();
		assertTrue(html.contains("Davi"));
		assertTrue(html.contains("davi@gmail.com"));
	}
	
	@Test
	public void naoDeveAdicionarNovoClienteSemEmail(){
		WebElement nome = driver.findElement(By.name("nome"));
		nome.sendKeys("Steve Rogers");
		
		WebElement botao = driver.findElement(By.id("btn-salvar"));
		botao.click();
		
		String html = driver.getPageSource();
		assertTrue(html.contains("Email: campo é obrigatório"));
	}

	@Test
	public void naoDeveAdicionarNovoClienteSemEmailENome(){
		WebElement botao = driver.findElement(By.id("btn-salvar"));
		botao.click();
		
		String html = driver.getPageSource();
		assertTrue(html.contains("Email: campo é obrigatório"));
		assertTrue(html.contains("Nome: campo é obrigatório "));
	}
}
