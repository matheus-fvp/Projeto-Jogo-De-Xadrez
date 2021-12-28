import java.io.Serializable;

//------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                    -
//Classe Torre responsavel por representar uma peca torre dentro de um   -
//jogo de xadrez de acordo com as regras do proprio jogo                 -
//------------------------------------------------------------------------

public class Torre extends Peca implements Serializable{

//-----------------------------------Metodo Construtor----------------------------------------
    //inicializa o atributo cor da classe de acordo com o valor fornecido como parametro (char). Tambem faz o tratamento de exceção caso a cor inserida seja invalida (diferente de P ou B)
    public Torre(char cor) throws Exception {
        super(cor, false);//chama o construtor da superclasse para inicializar os atributos herdados
    }
//--------------------------------------------------------------------------------------------

//-----------------------------------Metodos de instancia-------------------------------------
    //sobrecarga do metodo abstrato desenho da classe mãe Peca, retorna o formato especifico de uma Torre (T ou t)
    @Override
    public char desenho() {
        if(super.getCor() == 'B') 
            return 'T'; //pecas brancas seram representadas por caracteres maiusculos
        return 't'; //pecas pretas seram representadas por caracteres minusculos
    }

    //sobrecarga do metodo abstrato checaMovimento da classe mãe Peca, faz a checagem do movimento de acordo com a movimentação especifica de uma peca Torre dentro do jogo de xadrez, retorna verdadeiro caso o movimento seja valido ou falso caso contrario
    @Override
    public boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) {

        if(linhaOrigem == linhaDestino && colunaOrigem == colunaDestino)
            return false; //movimento invalido caso a peca permaneca na mesma posicao

        //Movimento valido para todas as posicoes na vertical ou horizontal
        if(linhaDestino == linhaOrigem || colunaDestino == colunaOrigem) {
            return true;
        }
        else {
            return false; //movimentos invalidos
        }

    }
//--------------------------------------------------------------------------------------------

}
