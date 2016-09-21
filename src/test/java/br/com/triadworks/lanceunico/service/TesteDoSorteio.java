package br.com.triadworks.lanceunico.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import br.com.triadworks.lanceunico.builder.CriadorDePromocao;
import br.com.triadworks.lanceunico.modelo.Cliente;
import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.service.Sorteio;

public class TesteDoSorteio {

    private Sorteio sorteio;
    private Cliente rafael;
    private Cliente rommel;
    private Cliente handerson;

    @Before
    public void setUp() {
        this.sorteio = new Sorteio();
        this.rafael = new Cliente("Rafael");
        this.rommel = new Cliente("Rommel");
        this.handerson = new Cliente("Handerson");
    }

    @Test
    public void deveSortearLancesEmOrdemCrescente() {

        // passo 1: cenário

        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(handerson, 250).comLance(rafael, 300)
                .comLance(rommel, 500).cria();

        // passo 2: executar a ação
        sorteio.sorteia(promocao);

        // passo 3: validar o resultado
        double maiorEsperado = 500.00;
        double menorEsperado = 250.00;

        assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
        assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);
    }

    @Test
    public void deveSortearLancesEmOrdemDescrescente() {

        // passo 1: cenário

        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(handerson, 400).comLance(rafael, 300)
                .comLance(rommel, 250).cria();

        // passo 2: executar a ação
        sorteio.sorteia(promocao);

        // passo 3: validar o resultado
        double maiorEsperado = 400.00;
        double menorEsperado = 250.00;

        assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
        assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);
    }

    @Test
    public void deveSortearQuandoHouverApenasUmLance() {

        // passo 1: cenário

        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(rommel, 600).cria();

        // passo 2: executar a ação
        sorteio.sorteia(promocao);

        // passo 3: validar o resultado

        assertEquals(600.00, sorteio.getMaiorDeTodos(), 0.0001);
        assertEquals(600.00, sorteio.getMenorDeTodos(), 0.0001);
    }

    @Test
    public void deveSortearEmOrdemAleatoria() {

        // passo 1: cenário

        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(handerson, 1400).comLance(rafael, 300)
                .comLance(rommel, 580).cria();

        // passo 2: executar a ação
        sorteio.sorteia(promocao);

        // passo 3: validar o resultado
        double maiorEsperado = 1400.00;
        double menorEsperado = 300.00;

        assertEquals(maiorEsperado, sorteio.getMaiorDeTodos(), 0.0001);
        assertEquals(menorEsperado, sorteio.getMenorDeTodos(), 0.0001);
    }

    @Test
    public void deveSortearOsTresMenoresLances() {
        // passo 1: cenário
        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(handerson, 1400.00).comLance(rafael, 300)
                .comLance(rommel, 1850.00).comLance(rafael, 300.00)
                .comLance(rommel, 1550.00).cria();
        // passo 2: ação
        sorteio.sorteia(promocao);

        // passo 3: validação
        List<Lance> menores = sorteio.getMenores();

        assertEquals(3, menores.size(), 0.0001);
        assertEquals(300.00, menores.get(0).getValor(), 0.0001);
        assertEquals(300.00, menores.get(1).getValor(), 0.0001);
        assertEquals(1400, menores.get(2).getValor(), 0.0001);
    }

    @Test
    public void deveSortearTodosOsLancesQuandoHouverMenosDe3Lances() {
        // passo 1: cenário

        Promocao promocao = new CriadorDePromocao().para("xbox")
                .comLance(rafael, 300).comLance(rommel, 1550).cria();

        // passo 2: ação
        sorteio.sorteia(promocao);

        // passo 3: validação
        List<Lance> menores = sorteio.getMenores();

        assertEquals(2, menores.size(), 0.0001);
        assertEquals(300.00, menores.get(0).getValor(), 0.0001);
        assertEquals(1550.00, menores.get(1).getValor(), 0.0001);
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveSortearQuandoNaoHouverLances() {
        // passo 1: cenário
        Promocao promocao = new CriadorDePromocao().para("xbox").cria();
        // passo 2: ação
        sorteio.sorteia(promocao);

        // passo 3: validação
        // List<Lance> menores = sorteio.getTresMenores();
        // assertEquals(0, menores.size(), 0.0001);
    }

}