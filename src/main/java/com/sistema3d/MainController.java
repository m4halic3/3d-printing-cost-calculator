package com.sistema3d;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML private ComboBox<Impressora3D> comboImpressora;
    @FXML private ComboBox<MaterialImpressao> comboMaterial;
    @FXML private TextField txtNomeArquivo;
    @FXML private TextField txtMaterialGramas;
    @FXML private TextField txtTempoHoras;
    @FXML private CheckBox chkFalha;
    @FXML private CheckBox chkMargem;
    
    @FXML private ImageView imgImpressora;
    @FXML private Label lblDescricao;
    
    @FXML private Label lblCustoMaterial;
    @FXML private Label lblCustoMaquina;
    @FXML private Label lblCustoEnergia;
    @FXML private Label lblCustoMaoObra;
    @FXML private Label lblCustoManutencao;
    @FXML private Label lblCustoTotal;
    @FXML private Label lblPrecoVenda;

    @FXML
    public void initialize() {
        // Impressoras Hardcoded com a extensão corrigida para .jpeg
        comboImpressora.getItems().addAll(
            new Impressora3D("Ender 3", 1500.00, 350, "ender3.jpeg", "Impressora de entrada, ótima para filamentos básicos."),
            new Impressora3D("Creality K1", 3500.00, 500, "creality_k1.jpeg", "Alta velocidade de impressão com estrutura fechada CoreXY."),
            new Impressora3D("Bambu Lab A1", 4200.00, 400, "bambu_a1.jpeg", "Excelente qualidade de impressão e calibração automática robusta.")
        );

        // Materiais Hardcoded do enunciado
        comboMaterial.getItems().addAll(
            new MaterialImpressao("PLA baixa densidade", "Baixa", 0.08),
            new MaterialImpressao("PLA média densidade", "Média", 0.12),
            new MaterialImpressao("PLA alta densidade", "Alta", 0.18)
        );

        // Seleções iniciais padrão
        comboImpressora.getSelectionModel().selectFirst();
        comboMaterial.getSelectionModel().selectFirst();
        handleImpressoraSelecionada();
    }

    @FXML
    private void handleImpressoraSelecionada() {
        Impressora3D selecionada = comboImpressora.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            lblDescricao.setText(selecionada.getDescricao() + " (" + selecionada.getPotencia() + "W)");
            try {
                String caminhoImg = "/com/sistema3d/images/" + selecionada.getNomeImagem();
                URL recurso = getClass().getResource(caminhoImg);
                
                if (recurso != null) {
                    // Método robusto que funciona perfeitamente com module-info.java
                    imgImpressora.setImage(new Image(recurso.toExternalForm()));
                } else {
                    System.out.println("Aviso: Imagem não encontrada no caminho: " + caminhoImg);
                    imgImpressora.setImage(null);
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
                imgImpressora.setImage(null);
            }
        }
    }

    @FXML
    private void handleCalcular() {
        try {
            Impressora3D imp = comboImpressora.getSelectionModel().getSelectedItem();
            MaterialImpressao mat = comboMaterial.getSelectionModel().getSelectedItem();
            
            // Validação básica se os campos estão preenchidos antes do parse
            if (txtMaterialGramas.getText().isBlank() || txtTempoHoras.getText().isBlank()) {
                mostrarAlertaErro("Campos vazios", "Por favor, preencha os campos de peso e tempo.");
                return;
            }

            double gramas = Double.parseDouble(txtMaterialGramas.getText().trim());
            double horas = Double.parseDouble(txtTempoHoras.getText().trim());
            String nome = txtNomeArquivo.getText();

            ProjetoImpressao projeto = new ProjetoImpressao(
                nome, imp, mat, gramas, horas, chkFalha.isSelected(), chkMargem.isSelected()
            );

            ResumoCusto resumo = CalculadoraCusto.calcular(projeto);

            // Atualiza os valores na tela formatados como moeda local (R$)
            lblCustoMaterial.setText(String.format("R$ %.2f", resumo.material));
            lblCustoMaquina.setText(String.format("R$ %.2f", resumo.maquina));
            lblCustoEnergia.setText(String.format("R$ %.2f", resumo.energia));
            lblCustoMaoObra.setText(String.format("R$ %.2f", resumo.maoObra));
            lblCustoManutencao.setText(String.format("R$ %.2f", resumo.manutencao));
            lblCustoTotal.setText(String.format("R$ %.2f", resumo.total));
            lblPrecoVenda.setText(String.format("R$ %.2f", resumo.venda));

        } catch (NumberFormatException e) {
            mostrarAlertaErro("Entrada Inválida", "Por favor, insira valores numéricos válidos (use ponto para decimais).");
        }
    }

    // Método auxiliar para manter o código limpo e reutilizar diálogos de erro
    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}