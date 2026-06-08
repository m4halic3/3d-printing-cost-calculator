package com.sistema3d;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainController {

    // Componentes injetados do FXML
    @FXML private HBox raizJanela; // Mapeado para estilizar o fundo da tela inteira se necessário
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

    // Componentes adicionais mapeados para estilização dinâmica
    @FXML private Button btnCalcular; 
    @FXML private GridPane painelResultados;

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

        // Aplica o visual moderno direto via Java
        aplicarEstiloVisual();

        // Seleções iniciais padrão
        comboImpressora.getSelectionModel().selectFirst();
        comboMaterial.getSelectionModel().selectFirst();
        handleImpressoraSelecionada();
    }

    private void aplicarEstiloVisual() {
        // Paleta base moderna e arredondada para os inputs (ComboBox e TextField)
        String estiloInputs = "-fx-background-color: #FFFFFF; -fx-background-radius: 6; -fx-border-color: #CBD5E1; -fx-border-radius: 6; -fx-padding: 5 10 5 10;";
        
        comboImpressora.setStyle(estiloInputs);
        comboMaterial.setStyle(estiloInputs);
        txtNomeArquivo.setStyle(estiloInputs);
        txtMaterialGramas.setStyle(estiloInputs);
        txtTempoHoras.setStyle(estiloInputs);

        // Se o seu container de resultados (GridPane) tiver uma ID correspondente no FXML, estiliza o fundo dele
        if (painelResultados != null) {
            painelResultados.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #E2E8F0; -fx-border-radius: 8; -fx-padding: 15;");
        }

        // Estilização do Botão de Calcular (Azul Moderno com efeito Hover reativo)
        if (btnCalcular != null) {
            String estiloBotaoNormal = "-fx-background-color: #2563EB; -fx-background-radius: 6; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 8;";
            String estiloBotaoHover = "-fx-background-color: #1D4ED8; -fx-background-radius: 6; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 8;";
            
            btnCalcular.setStyle(estiloBotaoNormal);
            btnCalcular.setOnMouseEntered(e -> btnCalcular.setStyle(estiloBotaoHover));
            btnCalcular.setOnMouseExited(e -> btnCalcular.setStyle(estiloBotaoNormal));
        }

        // Destaca as cores de Custo e de Venda com pesos visuais
        lblCustoTotal.setStyle("-fx-font-weight: bold; -fx-text-fill: #DC2626; -fx-font-size: 14px;");
        lblPrecoVenda.setStyle("-fx-font-weight: bold; -fx-text-fill: #16A34A; -fx-font-size: 15px;");
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

    private void mostrarAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}