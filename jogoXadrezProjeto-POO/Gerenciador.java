import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Nome: Matheus Fernando Vieira Pinto
 * Classe gerenciador resposavel por dar inicio a um jogo de xadrez
 * obtem informacoes dos jogadores e inicia o jogo
 */
public class Gerenciador {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Jogo xadrez;
        int opcao = 0;
            do{
                Jogo.limpaTela();
                System.out.println("---------------------------------------");
                System.out.println("----------------XADREZ-----------------");
                System.out.println("---------------------------------------");
                System.out.println("Escolha uma das opções");
                System.out.println("1 - Iniciar um novo jogo.");
                System.out.println("2 - Retomar um jogo salvo");
                try {
                    opcao = Integer.parseInt(teclado.nextLine());
                    switch(opcao) {
                        case 1:
                            String nome1, nome2;
                            Jogador j1, j2;
                            System.out.println("Informe o nome do jogador que optou pelas peças brancas: ");
                            nome1 = teclado.nextLine();
                            System.out.println("Informe o nome do jogador que optou pelas peças pretas: ");
                            nome2 = teclado.nextLine();
                            j1 = new Jogador(nome1);
                            j2 = new Jogador(nome2);
                            xadrez = new Jogo(j1, j2, 0);
                            System.out.println("Vamos Começar o jogo! :)");
                            xadrez.rodada();
                            break;
                        case 2:
                            boolean aux = true;
                            String nomeArquivo;
                            do {
                                try {
                                    Jogo.limpaTela();
                                    System.out.println("Informe o nome do arquivo no qual o jogo está salvo: ");
                                    nomeArquivo = teclado.nextLine();
                                    System.out.println(nomeArquivo);
                                    xadrez = new Jogo(nomeArquivo);
                                    aux = false;
                                    xadrez.rodada();
                                }catch(IOException e) {
                                    System.out.printf("Erro ao abrir o arquivo!");
                                    teclado.nextLine();
                                }
                            }while(aux);
                            break;
                        default:
                            System.out.printf("Opção Inválida! Tente novamente.");
                            teclado.nextLine();
                            break;
                    }
                }catch(NumberFormatException e) {
                    System.out.printf("Entrada Inválida! Digite um inteiro.");
                    teclado.nextLine();
                }catch(Exception e) {
                    System.out.println("Um erro aconteceu! Tente reiniciar o jogo");
                    System.exit(0);
                } 
            }while(opcao != 1 && opcao != 2);
            teclado.close();
    }
}
