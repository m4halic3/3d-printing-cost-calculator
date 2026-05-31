package com.sistema3d;

public class CalculadoraCusto {
    private static final double VALOR_KWH = 0.85; 
    private static final double VALOR_HORA_MAO_OBRA = 4.00; 
    private static final double TAXA_MANUTENCAO_FIXA = 2.50; 

    public static ResumoCusto calcular(ProjetoImpressao projeto) {
        Impressora3D imp = projeto.getImpressora();
        MaterialImpressao mat = projeto.getMaterial();

        // Custo da Máquina por Hora (2 anos, 365 dias, 8 horas/dia)
        double horasTotaisUso = 2 * 365 * 8; 
        double custoMaquinaPorHora = imp.getPreco() / horasTotaisUso;
        double custoMaquina = custoMaquinaPorHora * projeto.getTempoHoras();

        // Custo do Material (com acréscimo se houver taxa de falha)
        double pesoUtilizado = projeto.getPesoGramas();
        if (projeto.isConsiderarFalha()) {
            pesoUtilizado *= 1.10;
        }
        double custoMaterial = pesoUtilizado * mat.getCustoPorGrama();

        // Custo de Energia
        double energiaKwh = ((double) imp.getPotencia() / 1000) * projeto.getTempoHoras();
        double custoEnergia = energiaKwh * VALOR_KWH;

        // Custo de Mão de Obra
        double custoMaoObra = projeto.getTempoHoras() * VALOR_HORA_MAO_OBRA;

        // Custo Total
        double custoTotal = custoMaterial + custoMaquina + custoEnergia + custoMaoObra + TAXA_MANUTENCAO_FIXA;

        // Preço de Venda Sugerido (com margem de lucro de 30%)
        double valorVenda = custoTotal;
        if (projeto.isAplicarMargem()) {
            valorVenda = custoTotal * 1.30;
        }

        return new ResumoCusto(custoMaterial, custoMaquina, custoEnergia, custoMaoObra, TAXA_MANUTENCAO_FIXA, custoTotal, valorVenda);
    }
}