import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrieTest {

    Trie minhaTrie;

    @Before
    public void setUp() {
        minhaTrie = new Trie();
        minhaTrie.adicionaPalavra("goiaba");
    }

    @Test
    public void testePalavraExistente() {
        assertTrue("Deveria ter encontrado a apalavra que foi adicionada",
                minhaTrie.temPalavra("goiaba"));
    }

    @Test
    public void testePalavraNaoExistente() {
        assertFalse("Nao deveria ter uma palavra que nunca foi adicionada!",
                minhaTrie.temPalavra("abacate"));
    }

    @Test
    public void testePalavraNaoExistenteQueEhPrefixo() {
        assertFalse("Nao deveria ter uma palavra que nunca foi adicionada!",
                minhaTrie.temPalavra("goi"));
    }
}
