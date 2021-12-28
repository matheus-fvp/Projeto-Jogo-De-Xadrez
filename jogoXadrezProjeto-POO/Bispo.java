import java.io.Serializable;

//----------------------------------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                                                -
//Classe Bispo responsavel por representar um bispo dentro do jogo de xadrez de acordo com as regras -
//establecidas pelo proprio jogo                                                                     -
//----------------------------------------------------------------------------------------------------

public class Bispo extends Peca implements Serializable {
//----------------------------------------Metodo Construtor---------------------------------------
    //Inicializa a classe bisbo com o caractere fornecido como parametro (B ou P) que representa a cor da peca e repassa a excecao em caso de entrada invalida (caractere diferente de B ou P)
    public Bispo(char cor) throws Exception {
        super(cor, false); //chama o construtor da classe mae Peca para inicializar os atributos herdados
    }
//------------------------------------------------------------------------------------------------

//---------------------------------Metodos de Instancia----------------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico de um Bispo (B ou b)
    @Override
    public char desenho() {
        if(super.getCor() == 'B') 
            return 'B'; //pecas brancas seram representadas por caracteres maiusculos
        return 'b'; //pecas pretas seram representadas por caracteres minusculos
    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe, faz a checagem do movimento de acordo com a movimentação especifica de uma peca bispo dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        //Movimentos nas diagonais sao validos a partir do ponto de origem da peca
        if(Math.abs(linhaDestino - linhaOrigem) == Math.abs(colunaDestino - colunaOrigem)) {
            return true;
        }
        else {
            return false; //movimentos invalidos
        }
    }
//---------------------------------------------------------------------------------------------------
}
