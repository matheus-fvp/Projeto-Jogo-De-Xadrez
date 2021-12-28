import java.io.Serializable;

//----------------------------------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                                                -
//Classe Rainha responsavel por representar uma rainha dentro de um jogo de xadrez de acordo com     -
//as suas regras.                                                                                    -
//----------------------------------------------------------------------------------------------------

public class Rainha extends Peca implements Serializable {
//--------------------------------------Metodo Construtor---------------------------------------------
    //inicializa a cor de uma peca rainha de acordo com o parametro fornecido, alem de repassar a excecao caso o char fornecido seja diferente de B ou P. Tambem define a rainha como uma peca que nao pode pular
    public Rainha(char cor) throws Exception {
        super(cor, false); //chama o construtor da classe mae Peca
    }
//----------------------------------------------------------------------------------------------------

//-----------------------------------Metodos de Instancia---------------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico de uma Rainha (D ou d)
    @Override
    public char desenho() {
        if(super.getCor() == 'B') 
            return 'D'; //pecas brancas seram representadas por caracteres maiusculos
        return 'd'; //pecas pretas seram representadas por caracteres minusculos
    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe Peca, faz a checagem do movimento de acordo com a movimentação especifica de uma peca rainha dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        //Movimento valido para qualquer posicao na vertical ou horizontal
    	if(linhaDestino == linhaOrigem || colunaDestino == colunaOrigem) {
            return true;
        }
    	//Movimentos validos em qualquer posicao na diagonal
    	if(Math.abs(linhaDestino - linhaOrigem) == Math.abs(colunaDestino - colunaOrigem)) {
            return true;
        }
    	return false; //movimentos invalidos
    }
//----------------------------------------------------------------------------------------------------

}
