package IgorDouradoDellEstagio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.text.*;
import java.util.*;

public class App {
    private static List<Medicamento> medicamentos;// Instanciei como private para encapsular os objetos
    private static List<Medicamento> Selecionados;// Servirá para ordenar pelo valor da bolsa sem prejudicar a ordem da
                                                  

    private static Scanner teclado;

    public static void main(String[] args) {
        carregaCSV();//Primeiro lê
        menu();//Após isso chama o menu

        System.exit(0);
    }

    public static void menu() {
        while (true) {
            teclado = new Scanner(System.in, "cp850");// Utilizei-me do cp850 para caso o nome tenha acento que o
                                                      // console tenha problema em printar, essa configuração irá
                                                      // normatizar
            System.out.println("---------------------------------- MENU ----------------------------------");// Linha
                                                                                                             // para
                                                                                                             // criar a
                                                                                                             // divisória
                                                                                                             // para o
                                                                                                             // usuário
                                                                                                             // se
                                                                                                             // situar
            System.out.println("[1] Para buscar as informações de um medicamento (que tenha sido comercializado em 2020) ");
            System.out.println("[2] Para fazer a busca de um produto e seus registros pelo código de barras referente");
            System.out.println("[3] Para visualizar a porcetagem e gráfico da classificação geral baseada na classificação de 'Pis/Cofins' de cada produto ");
            System.out.println("[0]- : Para finalizar a sessão do programa ");
            System.out.println("Digite a opção desejada: ");
            int opcao = verificaNumerico();// Esse método irá garantir que o usuário não digite nenhuma letra ou
                                            // caractere especial

            teclado.nextLine();//Consome a primeira linha
            switch (opcao) {
                case 1:
                    System.out.println("Informe o nome do medicamento que esteja buscando ou apenas parte dele: ");
                    String busca = teclado.nextLine();// Chamei esse método em toda interação com o usuário que venha a
                                                      // requerer apenas números
                    questao1(busca.toUpperCase());// Criei os métodos das questões fora do Main para não ficar muito
                                                  // extenso e melhor distribuído
                    espera(720);// Delay proposital para maior fluidez e para o usuário não se "perder", isso se
                                // repetirá no prosseguir do código
                    break;
                case 2:
                    System.out.println("Digite o codigo: ");
                    Long diferenca = teclado.nextLong();
                    questao2(diferenca);
                    break;
                case 3:
                    questao3();
                    break;
                case 0:
                    System.out.println("Obrigado por usar o programa, volte sempre!");// Mensagem para quando o usuário
                                                                                      // sair do programa :)
                    System.exit(0);// Comando para fechar a execuçao
                    break;
                default:
                    System.out.println("*Informe um número referente apenas a uma das 4 opções do menu abaixo* ");// Caso
                                                                                                                  // nenhuma
                                                                                                                  // das
                                                                                                                  // 4
                                                                                                                  // opções
                                                                                                                  // seja
                                                                                                                  // escolhida
                                                                                                                  // pelo
                                                                                                                  // o
                                                                                                                  // usuário,                                                                                                                  // ele
                                                                                                                  // chama
                                                                                                                  // novamente
                                                                                                                  // o
                                                                                                                  // menu
                    break;
            }
            espera(680);// Mais um delay constante, só que mais rápido
        }
    }

    public static void carregaCSV() {
        String currDir = Paths.get("").toAbsolutePath().toString();
        try (BufferedReader ler = new BufferedReader(
                new InputStreamReader(new FileInputStream(currDir + "\\" + "TA_PRECO_MEDICAMENTO.csv"), "utf-8"));) {

            medicamentos = new ArrayList<>();// //optei por ArrayList ao invés de LinkedList por tá fazendo mais
                                             // operações
                                             // de get e add, mas nenhuma de remove, e no ArrayList tem complexidade
                                             // apenas O(1) ao contrário do LinkedList                        
            ler.readLine();// Garante que ele pule a primeira linha que é o cabeçalho do CSV
            String novaLinha;
            while ((novaLinha = ler.readLine()) != null) {// Enquanto houver linha, ele estará lendo
                String linha = novaLinha;
                String nome = "";
                String produto = "";
                String apresentacao = "";
                String pisCofins = "";
                long registro = 0;
                long ean1 = 0;
                double valorPF0 = 0;
                double pmc0 = 0;
                boolean test2020 = true;
                String[] dados;
                if (linha != null && linha.length() > 0) {
                    if (linha.charAt(0) == '\"' && linha.length() > 0) {//Caso seja composto o campo das substâncias
                        String aspas[] = linha.split("\"");//Splita
                        nome = aspas[1].replaceAll(";", " + ");//Une os nomes compostos
                        String garante = "";
                        for (int i = 2; i < aspas.length; i++) {
                            garante += aspas[i];//Normatiza
                        }
                        dados = garante.split(";");//Para não haver diferença no vetor
                    } else {
                        dados = linha.split(";");
                        nome = dados[0];
                    }
                    if (dados.length > 36) {//Minha forma de garantir que a linha seja válida
                        //Abaixo fiz a atribuição de cada atributo de acordo com o correspondente no csv
                        produto = dados[8];
                        apresentacao = dados[9];
                        pisCofins = dados[37];
                        //Try Catchs para onde ocorriam exceções, para não perder o input.
                        try {
                            registro = Long.parseLong(dados[4].replaceAll(",", "."));
                        } catch (NumberFormatException semNum) {
                            registro = -1;
                        }
                        try {
                            ean1 = Long.parseLong(dados[5].replaceAll(",", "."));
                        } catch (NumberFormatException semNum) {
                            ean1 = -1;
                        }
                        if (dados[23].length() <= 0) {
                            pmc0 = -1;
                        } else {
                            pmc0 = Double.parseDouble(dados[23].replaceAll(",", "."));
                        }
                        try {
                            valorPF0 = Double.parseDouble(dados[13].replaceAll(",", "."));
                        } catch (NumberFormatException invalido) {
                            valorPF0 = -1;
                        }
                        if (dados[38].equals("Sim")) {//Testa se foi comercializado em 2020
                            test2020 = true;
                        } else {
                            test2020 = false;
                        }
                    }

                }

                medicamentos.add(new Medicamento(nome, produto, apresentacao, registro, ean1, pisCofins, valorPF0, pmc0,
                        test2020));//Adiciona a lista cada entrada

                
            }
           

        } catch (IOException x) {
            System.err.format("Ocorreu o seguinte erro na leitura: %s%n", x);// Caso ele não consiga ler o CSV
        }
    }

    public static int verificaNumerico() {
        while (true) {
            try {
                int trata = teclado.nextInt();// Pega o que foi digitado pelo usuário
                return trata;// Em caso de não haver letras ou caracteres especiais contidos no que foi
                             // digitado e apenas números, ele retorna
            } catch (InputMismatchException e1) {
                teclado.next();// Controla o Scanner
                System.out.println(
                        "Ops... você digitou caracteres. Precisamos que tente novamente digitando apenas números a seguir: ");
                espera(310);// achei mais fluido com 310, para o usuário conseguir ler a mensagem
                System.out.print("=>");// Aponta para onde deve ser digitado
            }
        }
    }

    

    public static void espera(int ms) {// Método para fazer o Delay e o usuário conseguir ler as mensagens na tela
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int questao1(String busca) {
        Selecionados = new ArrayList<>();//inicia só aqui o array pra ficar zerado
        try {
            for (int i = 0; i < medicamentos.size(); i++) {
                if (((medicamentos.get(i).getNome().contains(busca) || (medicamentos.get(i).getproduto().contains(busca)))&& medicamentos.get(i).isEm2020() == true))//Expressão lógica para garantir que atenda a exigência
                    Selecionados.add(medicamentos.get(i));//Adiciona a lista de selecionados pois passou pela seleção
            }
            for (int i = 0; i < Selecionados.size(); i++) {
                System.out.println("----------------" + (i + 1) + "º medicamento encontrado---------------------");
                System.out.println(
                        "Nome: " + Selecionados.get(i).getNome() + "\nProduto: " + Selecionados.get(i).getproduto()
                                + "\nApresentação: " + Selecionados.get(i).getapresentacao() + "\nValor pf0: "
                                + Selecionados.get(i).getpfSemImp()); //Printa sua informações
                System.out.println("---------------------------------------------------------------------");

            }
            if (Selecionados.size()==0){
                System.out.println("Não foi encontrado nenhum Medicamento pela busca de: " + busca);//Para caso não seja encontrado nada
                return -1;
            }
            return 1;// Retorno para possibilitar o teste na classe de testes
            
        } catch (Exception e) {
            System.out.println("Busca inválida.");// Caso tenha sido
                                                                                                // digitado um ano que
                                                                                                // não contenha
                                                                                                // medicamentos
            return -1;// Retorno para possibilitar o teste na classe de teste
        }

    }

    public static int questao2(long entrada) {
        try {
            double maior, menor = 0;//Inicio eles
            Medicamento encontrado;//Para o codigo que for acha 
            List<Medicamento> prod = new ArrayList<>();//Os que contenham o mesmo nome de produto do encontrado entram aqui
            for (int i = 0; i < medicamentos.size(); i++) {//percorre
                if (entrada == medicamentos.get(i).getean1()) {//Verifica se encontrou
                    encontrado = medicamentos.get(i);//Atribui
                    for (int j = 0; j < medicamentos.size(); j++) {//Percorre novamente para ver quais produtos tem o mesmo nome que o encontrado
                        if (medicamentos.get(j).getproduto().equals(encontrado.getproduto())) {//Verifica o que foi dito acima
                            prod.add(medicamentos.get(j));//Adiciona caso tenha o mesmo nome de produto
                        }
                    }
                    Collections.sort(prod);// Faço a ordenação com o comparador implementado na classe Medicamento

                    menor = prod.get(0).getPmc0();//Pego o primeiro, que é o com valor mais baixo de pmc0
                    maior = prod.get(prod.size() - 1).getPmc0();//Pego o último que depois de ordenado é o maior pmc0
                    System.out.println(prod.size() + " registros do medicamento " + prod.get(0).getproduto()
                            + " foram encontrados\nMaior valor: " + maior + " R$\nMenor valor: " + menor
                            + " R$\nDiferença entre eles foi de " + formata(maior - menor) + " R$.");
                    break;
                }
            }
            if(prod.size()==0){
                System.out.println("Não foi encontrado");//Não encontrou
                return -1;
            }
            return 1;

        } catch (Exception e) {
            System.out.println("Não foi encontrado");//Não encontrou
            return -1;
        }
    }

    public static int questao3() {
        double positiva = 0;
        double negativa = 0;
        double neutra = 0;
        for (Medicamento aux : medicamentos) {
            if (aux.isEm2020()) {//Verifica que foi comercializado em 2020
                //Verifica  PIS/COFINS de cada medicamento
                if (aux.getpisCofins().equals("Positiva"))
                    positiva++;
                else if (aux.getpisCofins().equals("Negativa"))
                    negativa++;
                else if (aux.getpisCofins().equals("Neutra")) {
                    neutra++;
                }
            }
        }
        double total = positiva + negativa + neutra;//Pega o total para fazer a porcetagem.
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println("Classificação    Percentual    Gráfico");//O escopo de como deve ficar, seguindo o enunciado
        System.out.print("Negativa         " + (formata((negativa/total)*100)) + "%        ");//Exibe o referente de cada campo
        stars((int)((negativa/total)*100));//Printa os asterísticos de acordo com o percentual
        System.out.print("Neutra           " + formata((neutra/total)*100) + "%         ");
        stars((int)((neutra/total)*100));
        System.out.print("Positiva         " + formata((positiva/total)*100) + "%        ");
        stars((int)((positiva/total)*100));
        System.out.print("Total            " +(((positiva+negativa+neutra)/total)*100)+ "%\n");
        System.out.println("----------------------------------------------------------------------------------------------------");
        return 1;
    }

    public static void stars(int quant){//Metodo para agilizar o print dos asterísticos
        for(int i=0; i<quant;i++)
            System.out.print("*");
        System.out.print("\n");    
    }

    public static String formata(Double valor) {
        DecimalFormat duasCasas = new DecimalFormat("#,##0.00");
        duasCasas.setRoundingMode(RoundingMode.HALF_EVEN);//Faz o round
        return duasCasas.format(valor);//Retorna com o valor padronizado
        }

    public static String removeAcentos(String string) {
        if (string != null) {// Garante que não entre uma string vazia
            string = Normalizer.normalize(string, Normalizer.Form.NFD);// Normaliza
            string = string.replaceAll("[^\\p{ASCII}]", "");// Troca os caracteres acentuados por seus respectivos
                                                            // caracteres sem acento e de acordo com a tabela e padrão
                                                            // ASCII
        }
        return string;// Retorna a string sem acento
    }

    
}
