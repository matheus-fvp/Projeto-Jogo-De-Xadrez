import java.io.Serializable;

//------------------------------------------------------------------
//Nome: Matheus Fernando vieira Pinto                              -
//Classe Posicao responsavel por representar uma posicao em um     -
//tabuleiro                                                        -
//------------------------------------------------------------------

public class Posicao implements Serializable{

//----------------------------------------Atributos--------------------------------------------------
    private char coluna; //representa a coluna de uma posicao
    private int  linha;  //representa a linha  de uma posicao
    private char cor;    //representa a cor de uma posicao
    private Peca peca;  //aramazena a peca que ira ocupar a posicao
    private boolean estaOcupada; //armazena o status da posicao se ela esta ocupada ou nao
//---------------------------------------------------------------------------------------------------

//------------------------------------Metodo Construtor----------------------------------------------
    /*inicializa os atributos de uma determinada posicao com os valores
    fornecidos como parametro. Gera uma excecao caso a cor fornecida 
    seja diferente de B ou P, se a linha e colunas fornecidas estiverem
    fora dos limites de um tabuleiro de xadrez*/
    public Posicao(char cor, int linha, char coluna, Peca p) throws Exception{

        //lança uma excecao caso a cor fornecida seja invalida
        if(cor != 'B' && cor != 'P')
            throw new Exception("Cor da posicão inválida");

        //lança uma excecao caso a linha nao esteja no intervalo [1, 8]
        if(linha < 1 || linha > 8)
            throw new JogoExcecoes("Linha fornecida não está no intervalo [1, 8] considerado válido");

        //lanca uma excecao caso a coluna nao esteja no intervalo [a, h]
        if(coluna < 'a' || coluna > 'h')
            throw new JogoExcecoes("Coluna fornecida não está no intervalo [a, h] considerado válido");
        
        this.cor = cor; //inicializa o atributo cor com o valor fornecido como parametro
        this.linha = linha; //inicializa o atributo linha com o valor fornecido como parametro
        this.coluna = coluna; //inicializa o atributo coluna com o valor fornecido como parametro
        this.setPeca(p);; //adiciona uma peça a posicao
    }
//---------------------------------------------------------------------------------------------------

//------------------------------------Metodos gets e sets--------------------------------------------
    //retorna a cor de uma posicao
    public char getCor() {
        return this.cor;
    }
    //retorna a coluna na qual a posicao esta ocupada
    public char getColuna() {
        return this.coluna;
    }
    //retorna a linha na qual a posicao esta localizada
    public int getLinha() {
        return this.linha;
    }
    //retorna o status da posicao verdadeiro para ocupada e falso caso contrario
    public boolean getEstaOcupada() {
        return this.estaOcupada;
    }
    //modifica o status da posicao, indicando se ela esta ocupada ou nao
    public void setEstaOcupada(boolean capturada) {
        this.estaOcupada = capturada;
    }
    //insere uma peca na posicao
    public void setPeca(Peca p) {
        this.peca = p;
        if(p == null)
            this.setEstaOcupada(false);
        else
            this.setEstaOcupada(true);
    }
    //retorna uma referencia para um objeto do tipo peca que ocupa uma determinada posicao ou o valor null caso ela esteja vazia.
    public Peca getPeca() {
        return this.peca;
    }
//---------------------------------------------------------------------------------------------------

}
