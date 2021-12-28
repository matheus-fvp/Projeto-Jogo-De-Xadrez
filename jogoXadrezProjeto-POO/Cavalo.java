import java.io.Serializable;

//---------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                 -
//Classe Cavalo que representa um cavalo dentro de um jogo de xadrez  -
//de acordo com as regras do proprio jogo                             -
//---------------------------------------------------------------------

public class Cavalo extends Peca implements Serializable {
    
//-------------------------Metodo Construtor-----------------------------------------------------
    //metodo construtor que inicializa a cor da peca cavalo de acordo com o parametro do tipo caractere fornecido. Tambem repassa a excecao caso o parametro fornecido seja invalido (diferente de B ou P)
    public Cavalo(char cor) throws Exception {
        super(cor, true); //chama o construtor da superclasse peca para inicializar os atributos herdados
    }
//-----------------------------------------------------------------------------------------------

//---------------------------------Metodos de Instancia------------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico de um Cavalo (C ou c)
    @Override
    public char desenho() {
        if(super.getCor() == 'B') 
            return 'C'; //pecas brancas seram representadas por caracteres maiusculos
        return 'c'; //pecas pretas seram representadas por caracteres minusculos
    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe, faz a checagem do movimento de acordo com a movimentação especifica de uma peca cavalo dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        if(linhaDestino == linhaOrigem - 2 && ((colunaDestino == colunaOrigem + 1) || (colunaDestino == colunaOrigem - 1))) {
            return true;
        }
        else if(linhaDestino == linhaOrigem + 2 && ((colunaDestino == colunaOrigem + 1) || (colunaDestino == colunaOrigem - 1))) {
            return true;
        }
        else if(colunaDestino == colunaOrigem - 2 && ((linhaDestino == linhaOrigem + 1) || (linhaDestino == linhaOrigem - 1))) {
            return true;
        }
        else if(colunaDestino == colunaOrigem + 2 && ((linhaDestino == linhaOrigem + 1) || (linhaDestino == linhaOrigem - 1))) {
            return true;
        }
        else {
            return false;
        }
        
    }
//-----------------------------------------------------------------------------------------------
}
