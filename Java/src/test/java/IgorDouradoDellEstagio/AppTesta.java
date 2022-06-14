package IgorDouradoDellEstagio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AppTesta {

    @Test
    public void testaQuestao1True() {
        App.carregaCSV();// Aqui também estou testando se a leitura do CSV correta e é necessário esse
                         // método aqui para o funcionamente dos testes que envolvem a leitura e as listas que derivam dela
        assertEquals(1, App.questao1("MINoxi".toUpperCase()));// Verifica se acha algum medicamento correspondente e mantém esse padrão de devolver 1 caso ache, também testo digitando parte dele e também o Upper aqui
    }

    @Test
    public void testaQuestao1False() {
        App.carregaCSV();
        assertEquals(-1, App.questao1("inexistente"));// Verifica se não acha e mantém esse padrão de devolver -1 caso não ache
    }

    @Test
    public void testaQuestao2True() {
        App.carregaCSV();
        Long aux= Long.parseLong("7898100244409");
        assertEquals(1, App.questao2(aux));//Possui esse codigo de barras, então retornará 1
    }

    @Test
    public void testaQuestao2False() {
        App.carregaCSV();
        Long aux= Long.parseLong("5555555555555");
        assertEquals(-1, App.questao2(aux));//Não tem essa entrada, então retornará como -1
    }

    @Test
    public void testaQuestao3() {//Se chegar ao final e retornar 1, significa que está funcionando
        App.carregaCSV();
        assertEquals(1, App.questao3());
    }

    


    @Test
    public void testaRemoverDeAcento() {
        assertEquals("acentuada", App.removeAcentos("àçêñtúádã"));// Troca as letras acentuadas por suas letras
                                                                  // correspondentes sem acentuação, elas serão
                                                                  // colocadas em maiusculas no metodo de mascara
    }

    
    @Test
    public void testaFormata() {
        assertEquals("902,38", App.formata(902.37813));// Formata para um padrão monetário e arredonda
    }

    // O verificadorNumerico depende de um imput do teclado do usuário para
    // funcionar, por isso não foi testado, mas ele
    // foi implicítamente testado junto com outros metodos acima.

}
