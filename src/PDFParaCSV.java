import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;

public class PDFParaCSV {
    public static void main(String[] args) {
        // Caminho relativo para o arquivo PDF
        String caminhoPdf = "downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String caminhoCsv = "Rol_de_Procedimentos.csv";
        String caminhoZip = "Teste_seu_nome.zip";

        try {
            // Extrair texto do PDF
            PDDocument documento = Loader.loadPDF(new File(caminhoPdf));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String texto = pdfStripper.getText(documento);
            documento.close();

            // Processar texto para extrair dados da tabela
            List<String[]> dadosTabela = extrairDadosTabela(texto);

            // Salvar dados em CSV
            salvarEmCSV(dadosTabela, caminhoCsv);

            // Substituir abreviações
            substituirAbreviacoes(caminhoCsv);

            // Compactar CSV em ZIP
            compactarParaZip(caminhoCsv, caminhoZip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> extrairDadosTabela(String texto) {
        List<String[]> dadosTabela = new ArrayList<>();
        String[] linhas = texto.split("\n");
        for (String linha : linhas) {
            String[] colunas = linha.split("\\s+");
            dadosTabela.add(colunas);
        }
        return dadosTabela;
    }

    private static void salvarEmCSV(List<String[]> dadosTabela, String caminhoCsv) throws IOException {
        try (BufferedWriter escritor = Files.newBufferedWriter(Paths.get(caminhoCsv))) {
            for (String[] linha : dadosTabela) {
                escritor.write(String.join(",", linha));
                escritor.newLine();
            }
        }
    }

    private static void substituirAbreviacoes(String caminhoCsv) throws IOException {
        Path caminho = Paths.get(caminhoCsv);
        List<String> linhas = Files.readAllLines(caminho);
        List<String> linhasAtualizadas = new ArrayList<>();
        for (String linha : linhas) {
            linha = linha.replace("OD", "Descrição Completa OD");
            linha = linha.replace("AMB", "Descrição Completa AMB");
            linhasAtualizadas.add(linha);
        }
        Files.write(caminho, linhasAtualizadas);
    }

    private static void compactarParaZip(String caminhoCsv, String caminhoZip) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(caminhoZip);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            File arquivoParaZip = new File(caminhoCsv);
            try (FileInputStream fis = new FileInputStream(arquivoParaZip)) {
                ZipEntry zipEntry = new ZipEntry(arquivoParaZip.getName());
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int comprimento;
                while ((comprimento = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, comprimento);
                }
            }
        }
    }
}