import java.io.Serializable;

//----------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto RA: 771047                       -
//Classe abstrata que representa uma determinada peça                  -
//----------------------------------------------------------------------

public abstract class Peca implements Serializable {
    //-------------------------------------Atributos--------------------------------------------------
        private char cor; //atributo que armazena a cor de uma determinada peça
        private boolean capturada; //atributo que armazena o estado de uma determinada peça
        private boolean podePular; //atributo que determina se uma deerminada peca pode pular ou nao
    //------------------------------------------------------------------------------------------------

    //-------------------------------------construtor-------------------------------------------------
        /*inicializa os atributos cor e pode pular com os parametros
         fornecidos. Lança uma excecao caso a cor fornecida se diferente
        de B ou P*/
        public Peca(char cor, boolean pode) throws Exception {
            if(cor == 'B' || cor == 'P') { //verifica se a cor fornecida é valida(B ou P)
                this.cor = cor;
                this.setCapturada(false); //toda peca começa com o status capturada false
                this.podePular = pode; //determina se uma peca pode pular ou não
            }
            else {
                throw new Exception("Cor Inválida"); //dispara uma exceção para cor invalida
            }
        }
    //------------------------------------------------------------------------------------------------

    //-----------------------------metodos acessores e modificadores----------------------------------
        //retorna a cor de uma peça
        public char getCor() {
            return this.cor;
        }
        //modifica o status de uma determinada peça
        public void setCapturada(boolean capturada) {
            this.capturada = capturada;
        }
        //retorna o status atual da peça
        public boolean getCapturada() {
            return this.capturada;
        }
        //retorna verdadeiro se a peca pode pular e false se nao pode
        public boolean getPodePular() {
            return this.podePular;
        }
    //------------------------------------------------------------------------------------------------

    //--------------------------------------metodos abstratos-----------------------------------------
        public abstract char desenho();
        public abstract boolean checaMovimento(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino);
    //------------------------------------------------------------------------------------------------
}