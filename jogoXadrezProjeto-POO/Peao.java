import java.io.Serializable;

//------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                    -
//Classe Peao responsavel por representar uma peca peao dentro de um     -
//jogo de xadrez de acordo com as regras do proprio jogo                 -
//------------------------------------------------------------------------

public class Peao extends Peca implements Serializable{

//----------------------------------------Atributos------------------------------------------------
    private boolean primeiroMovimento; //atributo para verificar o primeiro movimento do peao
//-------------------------------------------------------------------------------------------------

//-------------------------------------Metodo Construtor---------------------------------------------
    /*recebe como parametro um char que representa a cor (B ou P) da peca além de verificar
     e repassa uma exceção caso a cor seja inválida (diferente de B ou P). 
     Tambem inicializa o atributo primeiroMovimento*/
    public Peao(char cor) throws Exception {
        super(cor, false); //inicializa os atributos presentes na classe mãe Peca
        this.primeiroMovimento = true; //todo peao quando é criado possui o primeiro movimento valido
    }
//--------------------------------------------------------------------------------------------------- 


//-------------------------------------Metodos de Instância-------------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico da classe Peao (P ou p)
    @Override
    public char desenho() {

        if(super.getCor() == 'B') 
            return 'P'; //pecas brancas seram representadas por caracteres maiusculos
        return 'p'; //pecas pretas seram representadas por caracteres minusculos

    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe, faz a checagem do movimento de acordo com a movimentação especifica de uma peca peao dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        if(super.getCor() == 'P') { //as pecas pretas comecao na parte superior do tabuleiro
            //checa os possiveis movimentos na vertical
            if(colunaDestino == colunaOrigem && linhaDestino == linhaOrigem - 1) {
                    return true;
            }
            if(colunaDestino == colunaOrigem && linhaDestino == linhaOrigem - 2) {
                if(primeiroMovimento) //verifica se é o primeiro movimento do peao
                    return true;
                else
                    return false;
            }
            //checa os possiveis movimentos na diagonal
            if(linhaDestino == linhaOrigem - 1 && (colunaDestino == colunaOrigem - 1 || colunaDestino == colunaOrigem + 1)) {
                return true;
            }
        }
        else if(super.getCor() == 'B') {//as pecas brancas comecao na posicao inferior do tabuleiro
            if(colunaDestino == colunaOrigem && linhaDestino == linhaOrigem + 1) {
                return true;
            }
            if(colunaDestino == colunaOrigem && linhaDestino == linhaOrigem + 2) {
                if(primeiroMovimento) //verifica se é o primeiro movimento do peao
                    return true;
                else
                    return false;
            }
            if(linhaDestino == linhaOrigem + 1 && (colunaDestino == colunaOrigem - 1 || colunaDestino == colunaOrigem + 1)) {
                return true;
            }     	
        }
        return false; //movimentos invalidos
        
    }
    //metodo que garante o primeiro movimento do peao
    public void movido() {
        this.primeiroMovimento = false;
    }
//----------------------------------------------------------------------------------------------------

//-----------------------------------Metodos gets e sets----------------------------------------------
    //retorna verdadeiro caso seja o primeiro movimento do peao e falso caso contrario
    public boolean getPrimeiroMovimento() {
        return this.primeiroMovimento;
    }
//----------------------------------------------------------------------------------------------------

}
