package IgorDouradoDellEstagio;


public class Medicamento implements Comparable<Medicamento>{
    
    
    //Para ocupar menos memória e tornar o programa mais fluído, captei apenas os dados do CSV que variam entre os Medicamentos e podem ser úteis
    private String nome, produto, apresentacao, pisCofins = "";//Instanciei como private para encapsular os objetos
    private boolean em2020 = true;
    private long ean1, registro;
    private double pfSemImp, pmc0; //Por se tratar de uma moeda que pode ter representação decimal importante para a análise, coloquei como tipo "double"
    public Medicamento(String nome, String produto, String apresentacao, long registro, long ean1,
            String pisCofins, double pfSemImp, double pmc0) {//Instancio o construtor
        this.nome = nome;
        this.produto = produto;
        this.apresentacao = apresentacao;
        this.registro = registro;
        this.ean1 = ean1;
        this.pisCofins = pisCofins;
        this.pfSemImp = pfSemImp;
        this.pmc0 = pmc0;
    }

    @Override 
    public int compareTo(Medicamento ordenador) {//Para exercer o comparador entre os Medicamentos baseado no valor de pagamento da bolsa, fiz para ficar na ordem decrescente, dos maiores valores de bolsa até os menores
        if (this.pfSemImp > ordenador.getpfSemImp()) { 
          return -1; 
          } 
          if (this.pfSemImp < ordenador.getpfSemImp()) { 
          return 1; 
          } 
          return 0; 
         }


    //Abaixo os Gets e os Sets para cada atributo do objeto
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isEm2020() {
        return em2020;
    }

    public void setEm2020(boolean em2020) {
        this.em2020 = em2020;
    }

    public double getPmc0() {
        return pmc0;
    }

    public void setPmc0(double pmc0) {
        this.pmc0 = pmc0;
    }

    public String getproduto() {
        return produto;
    }

    public void setproduto(String produto) {
        this.produto = produto;
    }

    public String getapresentacao() {
        return apresentacao;
    }

    public void setapresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public long getregistro() {
        return registro;
    }

    public void setregistro(long registro) {
        this.registro = registro;
    }

    public long getean1() {
        return ean1;
    }
    public void setean1(long ean1) {
        this.ean1 = ean1;
    }
    public String getpisCofins() {
        return pisCofins;
    }
    public void setpisCofins(String pisCofins) {
        this.pisCofins = pisCofins;
    }
    public double getpfSemImp() {
        return pfSemImp;
    }
    public void setpfSemImp(double pfSemImp) {
        this.pfSemImp = pfSemImp;
    }
    
}
