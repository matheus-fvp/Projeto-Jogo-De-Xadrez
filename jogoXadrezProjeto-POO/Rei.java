import java.io.Serializable;

//------------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                          -
//Classe Rei que representa uma peca rei e seu comportamento no jogo de xadrez -
//------------------------------------------------------------------------------

public class Rei extends Peca implements Serializable {

//-------------------------------------Metodo Construtor---------------------------------------------
    //recebe como parametro um char que representa a cor (B ou P) da peca além de passar uma exceção caso a cor seja inválida (diferente de B ou P). Tambem define que um rei nao pode pular 
    public Rei(char cor) throws Exception {
        super(cor, false); //inicializa os atributos presentes na classe mãe Peca
    }
//--------------------------------------------------------------------------------------------------- 

//-------------------------------------Metodos de Instância-------------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico da classe Rei (R ou r)
    @Override
    public char desenho() {

        if(super.getCor() == 'B') 
            return 'R'; //pecas brancas seram representadas por caracteres maiusculos
        return 'r'; //pecas pretas seram representadas por caracteres minusculos

    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe, faz a checagem do movimento de acordo com a movimentação especifica de uma peca rei dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        //verifica se as coordenadas de destino correspondem aos visinhos na horizontal
        if(linhaDestino == linhaOrigem && ((colunaDestino == colunaOrigem + 1) || (colunaDestino == colunaOrigem - 1))) {
            return true;
        }
        //verifica se as coordenadas de destino correspondem aos visinhos na vertical
        else if(colunaDestino == colunaOrigem && ((linhaDestino == linhaOrigem + 1) || (linhaDestino == linhaOrigem - 1))) {
            return true;
        }
        //Verifica se as coordenadas de destino correspondem aos vizinhos da diagonal 
        else if(colunaDestino == colunaOrigem - 1 && ((linhaDestino == linhaOrigem + 1) || (linhaDestino == linhaOrigem - 1))) {
            return true;
        }
        //verifica se as coordenandas de destino correspondem aos vizinhos da diagonal
        else if(colunaDestino == colunaOrigem + 1 && ((linhaDestino == linhaOrigem + 1) || (linhaDestino == linhaOrigem - 1))) {
            return true;
        }
        else {
            return false; //movimento invalido
        }

    }
//----------------------------------------------------------------------------------------------------

}
