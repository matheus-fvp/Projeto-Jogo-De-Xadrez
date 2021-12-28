import java.io.Serializable;
import java.util.ArrayList;

//-------------------------------------------------------------------
//Nome: Matheus Fernando Vieira Pinto                               -
//Classe jogardor que representa um determinado jogador dentro de   -
//um jogo de xadrez                                                 -
//-------------------------------------------------------------------

public class Jogador implements Serializable{
//-------------------------------------------Atributos--------------------------------------------
    private String nome; //atributo que armazena o nome do jogador
    private ArrayList<Peca> pecas; //atributo que armazena todas as pecas de um dado jogador
    private int  pecasRestantes; //atributo que armazena o quantidade de pecas que o jogador possui
//------------------------------------------------------------------------------------------------ 

//-----------------------------------------Metodo Construtor--------------------------------------
    public Jogador(String nome) {
        this.nome = nome; //inicializa o atributo nome com a variavel nome fornecida como parametro
        this.pecasRestantes = 16; //todo jogador possui 16 pecas quando criados
    }
//------------------------------------------------------------------------------------------------

//--------------------------------------Metodos gets e sets---------------------------------------
    //retorna o nome do jogador
    public String getNome() {
        return this.nome;
    }
    //modifica a quantidade de pecas que o jogador ainda possui
    public void setPecasRestantes(int qtdRestante) {
        this.pecasRestantes = qtdRestante;
    }
    //retorna a quantidade de pecas que o jogador ainda possui
    public int getPecasRestantes() {
        for(Peca p: this.pecas) {
            if(p.getCapturada())
                this.pecasRestantes = this.pecasRestantes - 1;
        }
        return this.pecasRestantes;
    }
    //metodo para inicializar as pecas do jogador
    public void setPecas(ArrayList<Peca> pecas) {
        this.pecas = pecas;
    }

//--------------------------------------------------------------------------------------------------

//--------------------------------------Metodos de instancia-----------------------------------------
    //metodo para imprimir as pecas ja capturadas do jogador
   public void imprimePecasCapturadas() {
       for(Peca p: this.pecas) {
           if(p.getCapturada()) {
            System.out.printf("|%c|", p.desenho());
           }
       }
       System.out.println("");
       System.out.println("");
   }
   //metodo que retorna o conjunto de todas as pecas ativas de um jogador
   public ArrayList<Peca> retornaPecasAtivas() throws Exception {
       ArrayList<Peca> ativas = new ArrayList<Peca>();
       for(Peca p: this.pecas) {
           if(!p.getCapturada()) {
               ativas.add(p);
           }
       }
       if(ativas.isEmpty())
            throw new Exception("O jogador atual não possui mais peças");
       return ativas;
   }
   //metodo que obbtem a peca rei de um determinado jogador
   public Peca procuraRei() throws Exception {
        for(Peca p: this.pecas) {
            if(p.desenho() == 'R' || p.desenho() == 'r')
                return p;
        }
        throw new Exception("O jogador atual não possui Rei! :[");
   }

//---------------------------------------------------------------------------------------------------

}
