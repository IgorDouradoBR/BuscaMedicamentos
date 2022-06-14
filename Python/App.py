import csv
from medicamento import Medicamento
from operator import attrgetter

class App :
    medicamentos = []
    file = open('TA_PRECO_MEDICAMENTO.csv', 'r')
    next(file)
    reader = csv.reader(file, delimiter=';')
    for linha in reader:
        nome = ""
        produto = ""
        apresentacao = ""
        pisCofins = ""
        em2020 = True
        ean1 = 0
        registro = 0
        valorPF0 = 0.0
        __pmc0 = 0.0
        fator = 0
        if (linha != None and len(linha) > 0) :
            i = 0
            for i in range(len(linha) - 1) :
                nome += f" {linha[i]} "
                next = linha[i + 1]
                if (next[1].isnumeric()) :
                    break
                fator += 1
                i += 1 
                
            if (len(linha) > 36) :
                produto = linha[8]
                apresentacao = linha[9]
                pisCofins = linha[37]
                try :
                    registro = linha[4].replace(",",".")
                except :
                    registro = -1
                try :
                    ean1 = linha[5].replace(",",".")
                except :
                    ean1 = -1
                if (len(linha[23]) <= 0) :
                    __pmc0 = -1
                else :
                    __pmc0 = linha[23].replace(",",".")
                try :
                    valorPF0 = linha[13].replace(",",".")
                except  :
                    valorPF0 = -1
                if (linha[38]=="Sim") :
                    em2020 = True
                else :
                    em2020 = False
        
        med = Medicamento(nome, produto, apresentacao, registro, ean1, pisCofins, valorPF0, __pmc0, em2020)
        medicamentos.append(med)
    while(True):
        print("-"*40, "MENU", "-"*40)
        print("[1] Para buscar as informações de um medicamento (que tenha sido comercializado em 2020). ")
        print("[2] Para fazer a busca de um produto e seus registros pelo código de barras referente.")
        print("[3] Para visualizar a porcetagem e gráfico da classificação geral baseada na classificação de 'Pis/Cofins' de cada produto. ")
        print("[0] Para sair do programa.")
        opcao= input("Digite a opcao: ")
        if opcao=="1":
            print("Podes digitar parte do nome do produto ou mesmo parte de uma de suas substâncias ativas.")
            busca= input("Digite a busca: ")
            busca = busca.upper()
            selecionados = []
            for i in range(len(medicamentos)):
                if (busca in medicamentos[i].nome or busca in medicamentos[i].produto) and (medicamentos[i].em2020==True):
                    selecionados.append(medicamentos[i])
            for i in range(len(selecionados)):        
                print(f'---------------- {i + 1} º medicamento encontrado---------------------')
                print(f'Nome: {selecionados[i].nome}.')
                print(f'Produto: {selecionados[i].produto}.')
                print(f'Apresentação: {selecionados[i].apresentacao}. ')
                print(f'Valor PF sem impostos: {selecionados[i].pfSemImp}. ')
            if len(selecionados)==0:
                print("Não foram encontrados registro com a busca de ", busca)
        if opcao=="2":
            entrada= input("Digite o codigo de barras de forma exata: ")
            maior= 0
            menor=0
            prod=[]
            for i in range(len(medicamentos)):
                if entrada== medicamentos[i].ean1:
                    encontrado= medicamentos[i]
                    for j in range(len(medicamentos)):   
                        if(medicamentos[j].produto == encontrado.produto):
                            prod.append(medicamentos[j]) 

                    maior = float(prod[0].pmc0)
                    menor = float(prod[0].pmc0)
                    for x in range(len(prod)):
                        prod[x].pmc0= float(prod[x].pmc0)
                        if prod[x].pmc0>maior:
                            maior= prod[x].pmc0
                        if prod[x].pmc0<menor:
                            menor= prod[x].pmc0

                    print(f'{len(prod)} registros foram encontrado para o produto {prod[0].produto}')
                    print(f'Maior valor: {maior} R$')
                    print(f'Menor valor: {menor} R$')
                    print(f'A diferença entre eles é de: {(maior-menor):,.2f} R$')
                    break
        if(opcao=="3"):
            negativa=0
            positiva=0
            neutra=0
            for i in range(len(medicamentos)):
                if medicamentos[i].em2020==True:
                    if medicamentos[i].pisCofins == "Negativa":
                        negativa += 1
                    elif medicamentos[i].pisCofins == "Positiva":
                        positiva += 1
                    elif medicamentos[i].pisCofins == "Neutra":    
                        neutra += 1
            total= negativa+positiva+neutra
            print("Classificação    Percentual    Gráfico")
            print(f'Negativa:        {((negativa/total)*100):,.2f} %       {"*"*int((negativa/total)*100)}')
            print(f'Neutra:          {((neutra/total)*100):,.2f} %         {"*"*int((neutra/total)*100)}')
            print(f'Positiva:        {((positiva/total)*100):,.2f} %       {"*"*int((positiva/total)*100)}')
            print(f'Total:           {((negativa+positiva+neutra)/total)*100}% ')
        if(opcao=="0"):
            break
    
    
    print("Obrigado por usar o sistema, volte sempre!")
    exit()



            

