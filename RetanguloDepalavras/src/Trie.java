import java.util.HashMap;

public class Trie {

    private TrieNode raiz;

    public Trie() {
        this.raiz = new TrieNode(null);
    }

    public void adicionaPalavra(String palavra) {
        TrieNode noCorrente = this.raiz;
        for (char c : palavra.toCharArray()) {
            TrieNode noFilho = noCorrente.getFilho(c);
            if (noFilho == null) {
                noFilho = noCorrente.addFilho(c);
            }
            noCorrente = noFilho;
        }
        noCorrente.setTerminal(true);
    }

    public boolean temPrefixo(String prefixo) {
        TrieNode no = obterUltimoNoPrefixo(prefixo);
        return no != null;
    }

    private TrieNode obterUltimoNoPrefixo(String prefixo) {
        TrieNode noCorrente = this.raiz;
        for (char c : prefixo.toCharArray()) {
            TrieNode noFilho = noCorrente.getFilho(c);
            if (noFilho == null) {
                return null;
            }
            noCorrente = noFilho;
        }
        return noCorrente;
    }

    public boolean temPalavra(String palavra) {
        TrieNode no = obterUltimoNoPrefixo(palavra);
        return no != null && no.isTerminal();
    }

    private class TrieNode {

        private final Character caracter;
        boolean terminal;
        HashMap<Character, TrieNode> filhos;

        TrieNode(Character caracter) {
            this.caracter = caracter;
            this.terminal = false;
            this.filhos = new HashMap<>();
        }

        public Character getCaracter() {
            return caracter;
        }

        public boolean isTerminal() {
            return terminal;
        }

        public void setTerminal(boolean terminal) {
            this.terminal = terminal;
        }

        public TrieNode getFilho(char caracter) {
            return this.filhos.get(caracter);
        }

        public TrieNode addFilho(char caracter) {
            TrieNode filhoNode = new TrieNode(caracter);
            this.filhos.put(caracter, filhoNode);
            return filhoNode;
        }
    }
}
