import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;

//------------------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                                          -
//Classe que representa a funcionalidade de um jogo de xadrez,                 -
//estabelecendo todas as regras e jogadas realizas durante o funciona-         -
//mento do jogo                                                                -
//------------------------------------------------------------------------------

public class Jogo {
//-----------------------------------Atributos--------------------------------------------------
    private Tabuleiro tabuleiro; //referencia para um tabuleiro
    private Jogador jogadores[]; //vetor para armazenar os dois jogadores
    private ArrayList<Peca> pecasBrancas = new ArrayList<Peca>(); //ArrayList para armazenar as 16 pecas brancas do jogo de xadrez
    private ArrayList<Peca> pecasPretas = new ArrayList<Peca>(); //ArrayList para armazenar as 16 pecas pretas do jogo de xadrez
    private int vezDeQuem; //Variavel que determina de quem é a vez 0 para o jogador 1 e 1 para o jogador 2;
    private int estadoJogo; //variavel que determina o estado do jogo. Inicio (-1), xeque (0), xeque-mate (1) 
//----------------------------------------------------------------------------------------------

//-------------------------------------Metodo Construtor----------------------------------------
    //Metodo construtor adiciona dois jogadores no jogo, inicializa o tabuleiro, distribui as pecas entre os jogadores, determina qual dos jogadores inicia o jogo e inicaliza o atributo estadoJogo com -1 (inicio do jogo)
	public Jogo(Jogador p1, Jogador p2, int vez) throws Exception{
            this.tabuleiro = new Tabuleiro(); //instancia um objeto do tipo tabuleiro
            this.jogadores = new Jogador[2]; //instancia um vetor do tipo Jogador com 2 posicoes
            jogadores[0] = p1; //inicializa o jogador 1 com o valor fornecido como parametro
            jogadores[1] = p2; //inicializa o jogador 2 com o valor fornecido como parametro
            this.vezDeQuem = 0; //jogador 1 (indice 0) sempre inicia um jogo
            this.estadoJogo = -1; //indica inicio de jogo
            this.inicializaPecas(); //inicializa as 32 pecas
            this.distribuiPecas(); //distribui as pecas entre os jogadores
            this.organizaTabuleiro(); //organiza a specas dentro do tabueiro
    }
    /*metodo construtor que retoma um jogo ja salvo, recebe como parametro o nome do arquivo no qual
    as informações do jogo foram gravadas para retomar a partida*/
    public Jogo(String nomeArquivo) throws IOException{
        this.jogadores = new Jogador[2]; //instancia um vetor do tipo Jogador de 2 posicoes      
        this.lerArquivo(nomeArquivo); //faz a leitura do arquivo fornecido como parametro
    }
//----------------------------------------------------------------------------------------------

//------------------------------------Metodos gets e sets---------------------------------------
    //verifica o estado do jogo
	public int getEstadoJogo() {
		return estadoJogo;
	}

	//modifica o estado do jogo
	public void setEstadoJogo(int estadoJogo) {
		this.estadoJogo = estadoJogo;
	}

	//retorna de quem e a vez
	public int getVezDeQuem() {
		return vezDeQuem;
	}
	//alterna a rodada
	public void setVezDeQuem(int vezDeQuem) {
		this.vezDeQuem = 1 - vezDeQuem;
    }
//------------------------------------------------------------------------------------------------

//---------------------------------------Metodos de Instancia--------------------------------------
    //metodo para inicializar as 32 pecas do jogo
    private void inicializaPecas() throws Exception {
        //instancia as pecas brancas e as armazena no ArrayList pecasBrancas
        pecasBrancas.add(new Torre('B'));
        pecasBrancas.add(new Cavalo('B'));
        pecasBrancas.add(new Bispo('B'));
        pecasBrancas.add(new Rainha('B'));
        pecasBrancas.add(new Rei('B'));
        pecasBrancas.add(new Bispo('B'));
        pecasBrancas.add(new Cavalo('B'));
        pecasBrancas.add(new Torre('B'));
        
        for(int i = 0; i < 8; i++)
            pecasBrancas.add(new Peao('B'));

        //instancia as pecas pretas e as armazena no ArrayList pecasPretas 
        for(int i = 0; i < 8; i++)
            pecasPretas.add(new Peao('P'));
        pecasPretas.add(new Torre('P'));
        pecasPretas.add(new Cavalo('P'));
        pecasPretas.add(new Bispo('P'));
        pecasPretas.add(new Rainha('P'));
        pecasPretas.add(new Rei('P'));
        pecasPretas.add(new Bispo('P'));
        pecasPretas.add(new Cavalo('P'));
        pecasPretas.add(new Torre('P'));

    }

    //metodo utilizado para distrbuir as pecas entre os jogadores
    private void distribuiPecas() {
        //jogador de indice 0 sempre ficara com as pecas brancas
        jogadores[0].setPecas(this.pecasBrancas);
        //jogador de indic 1 sempre ficara com as pecas pretas
        jogadores[1].setPecas(this.pecasPretas);
    }

    //metodo para organizar a specas dentro do tabuleiro
    private  void organizaTabuleiro() throws Exception {
        int aux = 0; //variavel que auxilia o acesso as pecas do jogo dentro do ArrayList
        //organiza as pecas brancas no tabuleiro
		for(int i = 1; i <= 2; i++) {
			for(char j = 'a'; j <= 'h'; j++) {
				this.tabuleiro.inserePeca(i, j, this.pecasBrancas.get(aux));
				aux++;
			}
		}
        aux = 0;
        //organiza as pecas pretas no tabuleiro
		for(int i = 7; i <= 8; i++) {
			for(char j = 'a'; j <= 'h'; j++) {
				this.tabuleiro.inserePeca(i, j, this.pecasPretas.get(aux));
				aux++;
			}
		}
	}

    
    /*metodo para escolher uma peca que sera movimentada, recebe como parametro a linha e coluna da posicao do tabuleiro onde a peca a ser movimentada esta localizada. retorna a peca a ser movida 
    caso ela exista e pertenca do jogador*/
    private Peca escolheOrigem(int linhaOrigem, char colunaOrigem) throws Exception {

        Peca escolhida; //referencia para uma determinada peca 
        escolhida = this.tabuleiro.quemEstaPosicao(linhaOrigem, colunaOrigem); //obtem a peca localizada no posicao com coordenadas linhaOrigem e colunaOrigem fornecidas como parametro
        if(escolhida == null) //verifica se não existe peca para movimentar
            throw new JogoExcecoes("Não há peça na posição escolhida"); //gera uma excecao 
        //verifica se a peca escolhida pertence ao outro jogador
        if((escolhida.getCor() == 'B' && vezDeQuem == 1) || (escolhida.getCor() == 'P' && vezDeQuem == 0))
            throw new JogoExcecoes("Essa peça não é sua"); //gera uma excecao indicando que a peca nao é do jogador
        return escolhida; //retorna a peca escolhida caso ela seja do jogador

    }
    //metodo para realizar uma jogada
    private void fazJogada(int linOrig, char colOrig, int linDes, char colDes, Peca origem) throws Exception {
        Peao p = new Peao(origem.getCor());
        //verifica se o movimento da peca dentro do tabuleiro é valido
        if(!this.tabuleiro.fazMovimento(linOrig, colOrig, linDes, colDes, origem)) {
            throw new JogoExcecoes("Movimento Inválido"); //gera uma excecao indicando movimento invalido
        }
        //atualiza o primeiro movimento do peao
        if(origem.desenho() == p.desenho()) {
            p = (Peao) origem;
            if(p.getPrimeiroMovimento())
                p.movido();
        }
    }
    //metodo que realiza a comunicacao com os jogadores
    public void rodada() {
        Scanner teclado = new Scanner(System.in);
        Peca origem, removida; //referencia para as pecas de origem e destino
        int linDes, linOrig; //variaveis para armazenar as linhas de destino e origem
        char colDes, colOrig; //variaveis para armazenar as colunas de destino e origem
        int sair; //armazena a vontade do jogador em sair ou não do jogo.
        //o jogo acaba quando um dos jogadores estiver em xeque
        while(estadoJogo != 1) {
            try {
                Jogo.limpaTela(); //limpa a tela
                this.tabuleiro.imprimeTabuleiro(); //imprime o tabuleiro de acordo com a configuração da rodada
                //verifica se o jogador da rodada atual está em xeque
                if(testaXeque()) {
                    System.out.println("Check!"); //informa o jogador caso ele esteja em xeque
                }
                System.out.printf("Pecas Brancas Capturadas: ");
                this.jogadores[0].imprimePecasCapturadas(); //imprime as pecas brancas capturadas do jogador 1
                System.out.printf("Pecas Pretas Capturadas: ");
                this.jogadores[1].imprimePecasCapturadas(); //imprime as pecas pretas capturadas do jogador 2
                //informa de que é a rodada
                if(vezDeQuem == 1) {
                    System.out.println("Rodada do jogador " + this.jogadores[vezDeQuem].getNome() + " movimente as peças pretas");
                }else {
                    System.out.println("Rodada do jogador " + this.jogadores[vezDeQuem].getNome() + " movimente as peças brancas");
                }
                System.out.println("Coordenadas de origem (Linha Coluna): ");
                linOrig = teclado.nextInt(); //jogador fornece a linha de origem
                colOrig = teclado.next().charAt(0); //usuario fornece a coluna de origem
                origem = this.escolheOrigem(linOrig, colOrig); //obtem ou nao a peca que esta armazenada na posicao correspondente a linha e coluna de origem fornecida pelo usuario.
                System.out.println("Coordenadas de destino (Linha Coluna): ");
                linDes = teclado.nextInt(); //obtem a linha de destino
                colDes = teclado.next().charAt(0); //obtem a coluna de destino
                removida = this.tabuleiro.quemEstaPosicao(linDes, colDes); //obtem a peca da posicao de destino
                this.fazJogada(linOrig, colOrig, linDes, colDes, origem); //realiza a jogada
                //verifica se o movimento realizado pelo jogador não o colocu em xeque
                if(!this.testaXeque()) {
                    this.setVezDeQuem(vezDeQuem); //muda de jogador
                }else {
                    //o jogador tentou se colocar em xeque, desfaz o movimento. O rei precisa ser salvo
                    this.tabuleiro.desfazMovimento(linDes, colDes, linOrig, colOrig, origem, removida);
                    //gera uma excecao indicando ao jogador que ele tentou se colocar em xeque
                    throw new JogoExcecoes("Você não se pode colocar em xeque");
                }
                //fornece a opção do jogador encerrar a partida.
                testaXequeMate(); //verifica se existe um xequeMate
                do {
                    Jogo.limpaTela(); //limpa a tela
                    this.tabuleiro.imprimeTabuleiro(); //imprime o tabuleiro
                    //se o xeque-mate for detectadoo estado do jogo passa a ser 1
                    if(estadoJogo == 1) {
                        this.vezDeQuem = 1 - vezDeQuem; //vencedor da partida
                        throw new JogoExcecoes("Xeque-Mate!"); //lanca uma exceçao indicando o xeque-Mate e consequentemente o fim do jogo
                    }
                    //oferece a opção de sair e gravar o jogo em um arquivo
                    System.out.println("Para encerrar o jogo e salvá-lo digite 1, mas se deseja continuar digite 0");
                    try {
                        sair = teclado.nextInt(); 
                        
                    }catch(InputMismatchException e) { //trata a exceção caso o usuario não tenha fornecido um inteiro como entrada
                        System.out.printf("Entrada inválida!");
                        sair = 2; //flag para o laço continuar, desse modo o sistema pode solicitar do jogador outra tentativa de entrada
                        teclado.nextLine(); //continua apos um enter do jogador
                        teclado.nextLine();
                    }
                }while(sair != 1 && sair != 0); //sai do laço caso a opçao inserida pelo jogador seja 0 ou 1
                //Verifica se o jogador optou por sair do jogo
                if(sair == 1) {
                    //laço para garantir que o jogador insira um nome de arquivo valido
                    while(true) {
                        try {
                            teclado.nextLine(); 
                            System.out.println("Informe o nome do arquivo no qual o jogo será gravado");
                            this.gravarArquivo(teclado.nextLine()); //obtem o nome do arquivo do usuario no qual o jogo será gravado
                            System.exit(0); //encerra o jogo
                        }catch(IOException e) { //trata a exceção caso o arquivo não exista
                            System.out.println("Erro ao abrir o arquivo, tente novamente!");
                            teclado.nextLine();
                            teclado.nextLine();
                        }
                    }
                }
            }catch(JogoExcecoes e) { //verica se existe alguma excecao tratavel
                System.out.printf(e.getMessage());
                teclado.nextLine();
                teclado.nextLine();
            }catch(RuntimeException e) { //excecao que pode ocorrer em tempo de execucao
                System.out.printf("Entrada inválida");
                teclado.nextLine();
                teclado.nextLine();
            }catch(Exception e) {
                System.out.printf(e.getMessage());
                teclado.nextLine();
                teclado.nextLine();
            }
    }
    //informa que é o vencedor do jogo em caso de xeque mate
    System.out.println("O vencedor do jogo é o jogador " + this.jogadores[vezDeQuem].getNome());
}
    //metodo que realiza o teste de um xeque
    private boolean testaXeque() throws Exception {
        Peca reiAlvo; //referencia para o rei ameacado
        ArrayList<Peca> pecasInimigas; //armazena todas as pecas inimigas ainda ativas em jogo
        int linhaReiAlvo; //armazena a linha onde o rei alvo esta localizado
        int linhaInimiga; //armazena a linha de uma determinada peca inimiga
        char colunaReiAlvo; //armazena a coluna em que o rei alvo esta localizado
        char colunaInimiga; //armazena a coluna de uma determinada peca inimiga
        reiAlvo = this.jogadores[vezDeQuem].procuraRei(); //procura o rei alvo entre as pecas do jogador atual
        pecasInimigas = this.jogadores[1-vezDeQuem].retornaPecasAtivas(); //obtem uma lista de todas as pecas ativas do adversario
        linhaReiAlvo = this.tabuleiro.linhaPeca(reiAlvo); //verifica no tabuleiro qual linha pertence ao rei ameacado
        colunaReiAlvo = this.tabuleiro.colunaPeca(reiAlvo); //verifica no tabuleiro qual coluna pertence ao rei ameacado
        //Analisa o movimento de todas as pecas inimigas, identificando assim se alguma delas coloca o rei do jogador atual em xeque
        for(Peca p: pecasInimigas) {
            linhaInimiga = this.tabuleiro.linhaPeca(p); //obtem a linha da peca inimiga
            colunaInimiga = this.tabuleiro.colunaPeca(p); //obtem a coluna da peca inimiga
            //verifica se o movimento da peca inimiga e valido
            if(this.tabuleiro.fazMovimento(linhaInimiga, colunaInimiga, linhaReiAlvo, colunaReiAlvo, p)) {
                this.tabuleiro.desfazMovimento(linhaReiAlvo, colunaReiAlvo, linhaInimiga, colunaInimiga, p, reiAlvo);
                return true;
            }
        }
        return false;
    }

    //metodo para testar o xeque mate de um jogo de xadrez. Retorna true se o jogador estiver em xeque mate e 0 caso contrario
    public boolean testaXequeMate() throws Exception {
        if(!testaXeque()) //se nao estiver em xeque dificilmente estará em xeque-mate
            return false;
        Peca removida;
        ArrayList<Peca> pecasAliadas = this.jogadores[vezDeQuem].retornaPecasAtivas(); //obtem todas as pecas aliadas do jogador atual
        int linhaAliada; //vaiavel para armazenar a linha do tabuleiro onde uma peca aliada esta
        char colunaAliada; //variavel para armazenar a coluna do tabuleiro onde uma peca aliada esta
        int i;
        char j;
        boolean xequeMate = true; //considera xeque-mate verdadeiro no inicio
        //percorre todas as pecas aliadas e testa o seu movimento em relacao as outras posições do tabuleiro
        for(Peca p: pecasAliadas) {
            linhaAliada = this.tabuleiro.linhaPeca(p); 
            colunaAliada = this.tabuleiro.colunaPeca(p);
            for(i = 1; i <= 8; i++) {
                for(j = 'a'; j <= 'h'; j++) {
                        //obtem a peca removida da posicao de destino
                        removida = this.tabuleiro.quemEstaPosicao(i, j);
                        //verifica se o movimento da peca aliada e valido
                        if(this.tabuleiro.fazMovimento(linhaAliada, colunaAliada, i, j, p)) {
                            if(!testaXeque()) { //verifica se o movimento da peca tirou o rei do xeque
                                xequeMate = false; //nao e um xeque mate, pois pelo menos uma peca salvou o rei
                            }
                            //desfaz o movimento caso movimento para nao baguncar o tabuleiro
                            this.tabuleiro.desfazMovimento(i, j, linhaAliada, colunaAliada, p, removida);
                        }
                }
            }
        }
        if(xequeMate)
            this.estadoJogo = 1; //caso de xeque-mate o status do jogo vai para 1
        return xequeMate; //retorna true ou false
    }

    //metodo para gravar todos os atributos do jogo em um arquivo cujo nome é fornecido como parametro
    public void gravarArquivo(String nomeArquivo) throws IOException {
    
			ObjectOutputStream objArq = new ObjectOutputStream(Files.newOutputStream(Paths.get(nomeArquivo)));
			objArq.writeObject(this.tabuleiro);
			objArq.writeObject(this.jogadores[0]);
            objArq.writeObject(this.jogadores[1]);
            objArq.writeObject(this.pecasBrancas);
			objArq.writeObject(this.pecasPretas);
			objArq.writeInt(this.estadoJogo);
			objArq.writeInt(this.vezDeQuem);
			objArq.close();
		
    }
    //metodo para ler os dados armazenados em um arquivo cujo nome e fornecido como parametro e desse modo retoma o jogo salvo
    private void lerArquivo(String nomeArquivo) throws IOException {
        try {
			ObjectInputStream lidos = new ObjectInputStream(Files.newInputStream(Paths.get(nomeArquivo)));
			this.tabuleiro = (Tabuleiro) lidos.readObject();
			this.jogadores[0] = (Jogador) lidos.readObject();
            this.jogadores[1] = (Jogador) lidos.readObject();
            this.pecasBrancas = (ArrayList<Peca>) lidos.readObject();
            this.pecasPretas = (ArrayList<Peca>) lidos.readObject();
			this.estadoJogo = (int) lidos.readInt();
			this.vezDeQuem = (int) lidos.readInt();
            lidos.close();
            jogadores[0].setPecas(this.pecasBrancas);
            jogadores[1].setPecas(this.pecasPretas);
			
		}catch(ClassNotFoundException e) {
			e.getMessage();
		}
    }
    //metodo para limpar a tela
    public static void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

//-------------------------------------------------------------------------------------------------
}
