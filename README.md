# Conversor PDF para CSV

## Descrição
Este projeto converte dados de um arquivo PDF para um formato CSV, garantindo que cada informação seja corretamente organizada em colunas. Ele também compacta o arquivo CSV gerado em um arquivo ZIP para facilitar o compartilhamento e armazenamento.

## Como Usar
### 1. Compilar o código
```sh
javac PDFParaCSV.java
```

### 2. Executar o programa
```sh
java PDFParaCSV
```

Isso gerará dois arquivos na pasta do projeto:
- `Rol_de_Procedimentos.csv` → Arquivo CSV com os dados extraídos
- `Rol_de_Procedimentos.zip` → Arquivo compactado contendo o CSV

## Funcionalidades
- Extração de texto de um arquivo PDF utilizando a biblioteca **Apache PDFBox**.
- Organização dos dados extraídos em colunas específicas.
- Salvamento dos dados formatados em um arquivo **CSV**.
- Compactação do arquivo CSV em um **ZIP**.

## Requisitos
- Java 8 ou superior
- Biblioteca Apache PDFBox

## Dependências
Este projeto usa a biblioteca **Apache PDFBox**. Para utilizá-la, adicione o arquivo JAR ao seu classpath ou utilize um gerenciador de dependências como Maven ou Gradle.

## Autora

Para mais informações, sinta-se à vontade para entrar em contato:

<div align="left">
  <img src="https://github.com/user-attachments/assets/57cac2a3-49b1-4a0a-aef3-e968523971eb" width="15%" alt="autora" />
</div>

- [Github](https://github.com/luizadaso)
- [Linkedin](https://www.linkedin.com/in/luizadaso)
