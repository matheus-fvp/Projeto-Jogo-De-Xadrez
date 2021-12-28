import java.io.Serializable;
import java.util.ArrayList;

//--------------------------------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                                              -
//Classe Tabuleiro responsavel por representar um tabuleiro de jogo de xadrez com exatamente 64    -
//posicoes                                                                                         -
//--------------------------------------------------------------------------------------------------

public class Tabuleiro implements Serializable {

//------------------------------------------Atributos------------------------------------------------
    private ArrayList<Posicao> tabuleiro = new ArrayList<Posicao>(); //cria uma lista de posicoes
//---------------------------------------------------------------------------------------------------

//------------------------------------Metodo Construtor----------------------------------------------
    public Tabuleiro() throws Exception {
        Posicao posicao; //cria uma referencia a um objeto da classe posicao
        //Inicializa todas as 64 posicoes do tabuleiro com as suas respectivas linhas, colunas e cor. Alem de nao estar ocupada (ou seja as pecas das posiçoes sãp nulas)
		for(int i = 8; i > 0; i--) {
			for(char j = 'a'; j <= 'h'; j++) {
				if(i%2 == 0) {
					if(j%2 == 0) {
						posicao = new Posicao('P', i, j, null); //linha e coluna par a cor da posicao é Preta P
					}
					else {
						posicao = new Posicao('B', i, j, null); //linha par e coluna impar a cor da posicao é Branca B
					}
				}
				else {
					if(j%2 == 0) {
						posicao = new Posicao('B', i, j, null); //linha impar e coluna par a cor da posicao é Branca
					}
					else {
						posicao = new Posicao('P', i, j, null); //linha impar e coluna impar a cor é Preta 
					}
				}
				tabuleiro.add(posicao); //adiciona uma posicao no tabuleiro
			}
		}
    }
//---------------------------------------------------------------------------------------------------

//---------------------------------------Metodos de Instancia----------------------------------------
    //Metodo utilizado para encontar o indice de uma determinada posicao no tabuleiro usando como base a sua linha e coluna, retorna o valor do indice da posicao procurada caso ela seja encontrada ou -1 caso contrario. Lanca uma excecao caso a linha ou coluna fornecida seja invalida
	private int buscaIndicePosicao(int linhaProcurada, char colunaProcurada) throws JogoExcecoes{
		if(linhaProcurada < 1 || linhaProcurada > 8)
			throw new JogoExcecoes("Linha inválida"); //lanca uma excecao cas a linha esteja fora do intervalo [1,8] 
		if(colunaProcurada < 'a' || colunaProcurada > 'h') {
			throw new JogoExcecoes("Coluna inválida"); //lanca a excecao caso a linha esteja fora do intervalo [a,h]
		}
		int i;
		for(i = 0; i < tabuleiro.size(); i++) {
			if(tabuleiro.get(i).getLinha() == linhaProcurada && tabuleiro.get(i).getColuna() == colunaProcurada) {
					return i;
			}
		}
		return -1; //nunca vai acontecer, pois as exceçoes lancadas anteriormente garantem que as posições devem existir no tabuleiro
    }
    
    //metodo para imprimir um tabuleiro
	public void imprimeTabuleiro() throws JogoExcecoes{
		System.out.println("   a  b  c  d  e  f  g  h");
		int i;
		Posicao p;
		for(i = 8; i >= 1; i--) {
			System.out.printf("%d ", i);
			for(char j = 'a'; j <= 'h'; j++) {
				p = this.tabuleiro.get(this.buscaIndicePosicao(i, j));
				if(p.getEstaOcupada()) {
					System.out.printf("|%c|", p.getPeca().desenho());
				}
				else {
					System.out.printf("|*|");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	/*metodo que sera utilizado como interface para a realizacao de um movimento de uma peca dentro
	do tabuleiro. Recebe como parametro as coordenadas da posicao de origem, as coordenadas da 
	posicao de destino e a peca a ser movida retorna verdadeiro em caso de movimento realizado com
	sucesso e false caso contrario*/
	public boolean fazMovimento(int linO, char colO, int linD, char colD, Peca p) throws Exception {
		//verifica se a peca nao pode pular
		Peca removida;
		Peao peao = new Peao(p.getCor());
		//verifica se o movimento da peca a ser movimentada é valido
		if(p.checaMovimento(linO, colO, linD, colD)) {
			if(!p.getPodePular()) { //verifica se a peca nao pode pular
				if(this.haPecasCaminho(linO, colO, linD, colD)) //testa se existem pecas no caminho
					return false; //movimento invalido caso existam pecas no caminho
			}
			//Não existem pecas no caminho, faz o movimento e retorna o formato da peca eliminada.
			removida = movePeca(linO, colO, linD, colD, p);
			//verifica se existia uma peca na posicao de destino
			if(removida != null) {
				//a cor da peca na posicao de destino é a mesma da peca a ser movida 
				if(removida.getCor() == p.getCor()) {
					//desfaz o movimento realizado
					this.desfazMovimento(linD, colD, linO, colO, p, removida);
					return false; //retorna false indicando que o movimento e invalido, ou seja, existe uma peca da mesma cor na posicao de destino
				}
				//verifica se a peca que capturou a peca removida é um peao
				if(p.desenho() == peao.desenho()) {
					//se o movimento foi na vertical ele e invalido
					if(colO == colD) {
						//desfaz o movimento realizado
						this.desfazMovimento(linD, colD, linO, colO, p, removida);
						return false; //retorna falso, já que o peao nao come na vertical
					} 

				}
				//a peca removida agora esta capturada
				removida.setCapturada(true);
			}
			//caso em que nao existe peca na posicao de destino
			else {
				//verifica se a peca atacante e um peao
				if(p.desenho() == peao.desenho()) {
					//verifica se o movimento foi na diagonal
					if(colO != colD) {
						//desfaz o movimento caso o peao tenha se movido na diagonal
						this.desfazMovimento(linD, colD, linO, colO, p, removida);
						return false; //retorna false indicando que um peao nao pode se mover na diagonal quando nao existem pecas inimigas la
					} 
				}
			}
			return true; //movimento realizado com sucesso
		}else {
			return false; //movimento incorreto, o movimento da peca e invalido
		}
	}

	//metodo que desfaz um determinado movimento
	public void desfazMovimento(int linhaO, char colunaO, int linhaD, char colunaD, Peca movida, Peca removida) throws Exception {
		//move a peca atacante da posicao atacada para a posicao de origem
		this.movePeca(linhaO, colunaO, linhaD, colunaD, movida);
		//insere a peca removida na posicao atacada
		this.inserePeca(linhaO, colunaO, removida);
		//caso em que existia uma peca na posicao atacada
		if(removida != null)
			//o status de nao capturada é devolvida para a peca atacada.
			removida.setCapturada(false);
	}
	//metodo para mover uma determinada peca de posicao
	private Peca movePeca(int linO, char colO, int linD, char colD, Peca p) throws Exception {

		int origem; //armazena o indice da posicao referente a origem do movimento
		int destino; //armazena o indice da posicao referente ao destino do movimento
		Peca removida; //armazena a peca a ser removida da posicao de destino caso ela esteja ocupada
		origem = this.buscaIndicePosicao(linO, colO);
		destino = this.buscaIndicePosicao(linD, colD);
		removida = this.tabuleiro.get(destino).getPeca();
		this.tabuleiro.get(destino).setPeca(p); //adiciona a peca movida na posicao de destino
		this.tabuleiro.get(origem).setPeca(null); //a posicao de origem agora esta vazia
		return removida; //retorna o formato da peca removida
	
	}

	//metodo que identifica se existem pecas no caminho durante o movimento
	private boolean haPecasCaminho(int linhaOrigem, char colunaOrigem, int linhaDestino, char colunaDestino) throws Exception{

		int indicePosicaoInicial = this.buscaIndicePosicao(linhaOrigem, colunaOrigem);
		int indicePosicaoFinal   = this.buscaIndicePosicao(linhaDestino, colunaDestino);
		if(linhaDestino - linhaOrigem > 0) {
			if(colunaDestino == colunaOrigem) {
				for(int i = indicePosicaoInicial-8; i > indicePosicaoFinal; i = i - 8) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
			else if(colunaDestino > colunaOrigem) {
				for(int i = indicePosicaoInicial-7; i > indicePosicaoFinal; i = i - 7) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
			else if(colunaDestino < colunaOrigem) {
				for(int i = indicePosicaoInicial-9; i > indicePosicaoFinal; i = i - 9) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
		}
		else if(linhaDestino - linhaOrigem < 0) {
			if(colunaDestino == colunaOrigem) {
				for(int i = indicePosicaoInicial+8; i < indicePosicaoFinal; i = i + 8) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
			else if(colunaDestino > colunaOrigem) {
				for(int i = indicePosicaoInicial + 9; i < indicePosicaoFinal; i = i + 9) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
			else if(colunaDestino < colunaOrigem) {
				for(int i = indicePosicaoInicial + 7; i < indicePosicaoFinal; i = i + 7) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
		}
		else if(linhaDestino == linhaOrigem) {
			if(colunaDestino < colunaOrigem) {
				for(int i = indicePosicaoInicial - 1; i > indicePosicaoFinal; i--) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
			else if(colunaDestino > colunaOrigem) {
				for(int i = indicePosicaoInicial + 1; i < indicePosicaoFinal; i++) 
					if(this.tabuleiro.get(i).getEstaOcupada())
						return true;
				return false;
			}
		}
		return false;
	}
	//metodo para inserir uma determinada peca em uma posicao do tabuleiro, recebe como parametro a linha e coluna da posicao onde a peca sera inserida
	public void inserePeca(int linha, char coluna, Peca p) throws Exception {
		int indicePosicao = this.buscaIndicePosicao(linha, coluna); //busca o indice da possicao no tabuleiro
		this.tabuleiro.get(indicePosicao).setPeca(p); //insere a peca no tabuleiro
	}

	//metodo que retorna a peca presente em uma determinada posicao do tabuleiro
	public Peca quemEstaPosicao(int linha, char coluna) throws Exception{
		int indice;
		indice = this.buscaIndicePosicao(linha, coluna);
		return this.tabuleiro.get(indice).getPeca(); 
	}
	//retorna a linha na qual esta localizada uma determinada peca
	public int linhaPeca(Peca p) throws Exception {
		if(p == null) 
			throw new Exception("Peça inválida");
		for(Posicao pos: this.tabuleiro) {
			if(pos.getPeca() != null && pos.getPeca().equals(p)) {
				return pos.getLinha();
			}
		}
		return -1;
	}
	//retorna a coluna na qual uma determinada peca está localizada
	public char colunaPeca(Peca p) throws Exception {
		if(p == null) 
			throw new Exception("Peça inválida");
		for(Posicao pos: this.tabuleiro) {
			if(pos.getPeca() != null && pos.getPeca().equals(p)) {
				return pos.getColuna();
			}
		}
		return '*';
	}


//---------------------------------------------------------------------------------------------------
}
