# 🖨️ Calculadora de Custos para Impressão 3D

Este projeto é uma aplicação de desktop desenvolvida em **Java** utilizando **JavaFX** e gerenciada pelo **Maven**. O objetivo do sistema é calcular de forma automatizada os custos envolvidos no processo de impressão 3D de arquivos STL, além de sugerir um preço de venda final baseado em margens de lucro.

Projeto desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)** do curso de Tecnologia em Sistemas para Internet (TSI).

---

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **JavaFX 17** (Interface Gráfica)
* **Maven** (Gerenciador de Dependências)
* **VS Code** (Ambiente de Desenvolvimento)

---

## 📐 Conceitos de POO Aplicados

O projeto foi estruturado seguindo rigorosamente os pilares da Orientação a Objetos:

* **Encapsulamento:** Classes de domínio (`Impressora3D`, `MaterialImpressao`, `ProjetoImpressao`) possuem atributos privados acessados exclusivamente por métodos seletores (*getters*), garantindo a consistência dos dados.
* **Separação de Responsabilidades (MVC):** * As regras de cálculo matemático estão isoladas na classe `CalculadoraCusto`.
  * A renderização visual é gerenciada pelo arquivo FXML (`main-view.fxml`).
  * A mediação entre os dados e a tela é feita pelo `MainController`.
* **Sobrescrita de Métodos (@Override):** Utilizada no método `toString()` para permitir a exibição amigável dos objetos dentro dos componentes visuais do JavaFX (`ComboBox`).

---

## 🚀 Como Executar o Projeto

Certifique-se de ter o **Maven** e o **JDK 17** instalados em sua máquina.

1. Clone o repositório ou navegue até a pasta do projeto:
   ```bash
   cd calculadora-impressao
   ``` 

2. Compile o projeto para baixar as dependências do JavaFX:
   ```bash
    mvn clean compile
   ``` 


3. Execute a aplicação:
   ```bash
   mvn javafx:run
   ``` 
