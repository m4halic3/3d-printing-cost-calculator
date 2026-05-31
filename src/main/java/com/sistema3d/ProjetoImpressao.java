package com.sistema3d;

public class ProjetoImpressao {
    private String nomeArquivo;
    private Impressora3D impressora;
    private MaterialImpressao material;
    private double pesoGramas;
    private double tempoHoras;
    private boolean considerarFalha;
    private boolean aplicarMargem;

    public ProjetoImpressao(String nomeArquivo, Impressora3D impressora, MaterialImpressao material, 
                            double pesoGramas, double tempoHoras, boolean considerarFalha, boolean aplicarMargem) {
        this.nomeArquivo = nomeArquivo;
        this.impressora = impressora;
        this.material = material;
        this.pesoGramas = pesoGramas;
        this.tempoHoras = tempoHoras;
        this.considerarFalha = considerarFalha;
        this.aplicarMargem = aplicarMargem;
    }

    public String getNomeArquivo() { return nomeArquivo; }
    public Impressora3D getImpressora() { return impressora; }
    public MaterialImpressao getMaterial() { return material; }
    public double getPesoGramas() { return pesoGramas; }
    public double getTempoHoras() { return tempoHoras; }
    public boolean isConsiderarFalha() { return considerarFalha; }
    public boolean isAplicarMargem() { return aplicarMargem; }
}