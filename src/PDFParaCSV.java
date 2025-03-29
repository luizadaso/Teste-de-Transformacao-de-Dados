import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;

public class PDFParaCSV {
    public static void main(String[] args) {
        String caminhoPdf = "downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String caminhoCsv = "Rol_de_Procedimentos.csv";
        String caminhoZip = "Teste_Ana_Luiza.zip";

        try {
            PDDocument documento = Loader.loadPDF(new File(caminhoPdf));
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true); // Mantém a ordem dos textos
            String texto = stripper.getText(documento);
            documento.close();

            List<String[]> dadosTabela = processarTextoParaTabela(texto);
            salvarEmCSV(dadosTabela, caminhoCsv);
            compactarParaZip(caminhoCsv, caminhoZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> processarTextoParaTabela(String texto) {
        List<String[]> dadosTabela = new ArrayList<>();
        String[] linhas = texto.split("\n");
        
        for (String linha : linhas) {
            String[] colunas = linha.split(" {2,}"); // Divide por múltiplos espaços
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
