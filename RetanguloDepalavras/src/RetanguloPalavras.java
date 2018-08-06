import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RetanguloPalavras {

    /**
     * Todas as palavras validas
     */
    private List<String> palavrasValidas;

    /**
     * Na posicao j, armazena uma lista com todas
     * as palavras validas de tamanho j
     */
    private List<List<String>> palavrasPorTamanho;

    /**
     * Na posicao j, armazena uma trie com todas
     * as palavras validas de tamanho j
     */
    private List<Trie> triePorTamanho;

    public RetanguloPalavras(List<String> palavrasValidas) {
        this.palavrasValidas = palavrasValidas;

        this.palavrasPorTamanho = new ArrayList<>();
        this.triePorTamanho = new ArrayList<>();

        // separa as palavras por tamanho
        for (String palavra : palavrasValidas) {
            int tamanho = palavra.length();
            /* expande os arrays, se necessario,
               ate conter a posicao desejada */
            while (this.palavrasPorTamanho.size() <= tamanho) {
                this.palavrasPorTamanho.add(null);
                this.triePorTamanho.add(null);
            }
            List<String> lista = this.palavrasPorTamanho.get(tamanho);
            Trie trie = this.triePorTamanho.get(tamanho);
            if (lista == null) {  // lazy instantiation
                lista = new ArrayList<>();
                trie = new Trie();
                this.palavrasPorTamanho.set(tamanho, lista);
                this.triePorTamanho.set(tamanho, trie);
            }
            trie.adicionaPalavra(palavra);
            lista.add(palavra);
        }
    }

    private boolean eh_prefixo(String prefixo, int tamanho_alvo) {
//        if (tamanho_alvo >= this.palavrasPorTamanho.size()) {
//            return false;
//        }
//        List<String> lista = this.palavrasPorTamanho.get(tamanho_alvo);
//        if (lista == null) {
//            return false;
//        }
//
//        for (String palavra : lista) {
//            if (palavra.startsWith(prefixo)) {
//                return true;
//            }
//        }
//        return false;

        if (tamanho_alvo >= this.triePorTamanho.size()) {
            return false;
        }
        Trie trie = this.triePorTamanho.get(tamanho_alvo);
        if (trie == null) {
            return false;
        }

        return trie.temPrefixo(prefixo);
    }

    private boolean backtrack(
            List<String> estado, int tamanho) {

        // verifica se eh estado final (solucao)
        if (estado.size() == tamanho) {
            return true;
        }

        List<String> palavras = this.palavrasPorTamanho.get(tamanho);

        for_candidatas:
        for (String candidata : palavras) {

            // verifica se pode ser incluida (se os prefixos existem)
            for (int coluna_idx = 0; coluna_idx < tamanho; coluna_idx++) {
                StringBuffer sb = new StringBuffer();
                for (String linha : estado) {
                    sb.append(linha.substring(
                            coluna_idx, coluna_idx + 1));
                }
                sb.append(candidata.substring(
                        coluna_idx, coluna_idx + 1));
                String prefixo = sb.toString();

                if (!eh_prefixo(prefixo, tamanho)) {
                    continue for_candidatas;  // essa candidata nao serve (poda!)
                }
            }

            // candidata ok!
            estado.add(candidata);
            if (backtrack(estado, tamanho)) {
                return true;  // repassando para cima o fato de ter
                              // encontrado um estado final
            }
            estado.remove(estado.size() - 1);
        }

        return false;
    }

    public List<String> encontraRetangulo(int tamanho) {
        if (tamanho >= this.palavrasPorTamanho.size()) {
            return null;
        }
        if (this.palavrasPorTamanho.get(tamanho) == null) {
            return null;
        }
        List<String> estado = new ArrayList<>();
        return backtrack(estado, tamanho) ? estado : null;
    }

    public static List<String> lerPalavras(String filePath) {
        List<String> resultado = new ArrayList<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            resultado.add(scanner.next());
        }
        scanner.close();

        return resultado;
    }

    public static void main(String args[]) {
        List<String> listaPalavras = lerPalavras(
                "words_alpha.txt");

        int k = 6;

        RetanguloPalavras solver = new RetanguloPalavras(listaPalavras);

        System.out.println("Comecei!");
        long start = System.currentTimeMillis();
        List<String> resultado = solver.encontraRetangulo(k);
        long elapsed = System.currentTimeMillis() - start;

        if (resultado != null) {
            for (String palavra : resultado) {
                System.out.println(palavra);
            }
        } else {
            System.out.println("Nao encontrei");
        }
        System.out.println("Elapsed time = " + elapsed);
    }
}
