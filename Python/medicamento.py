class Medicamento :
    # Para ocupar menos mem?ria e tornar o programa mais flu?do, captei apenas os dados do CSV que variam entre os Medicamentos e podem ser ?teis
    nome = None
    produto = None
    apresentacao = None
    pisCofins = ""
    # Instanciei como private para encapsular os objetos
    em2020 = True
    ean1 = 0
    registro = 0
    pfSemImp = 0.0
    pmc0 = 0.0
    # Por se tratar de uma moeda que pode ter representa??o decimal importante para a an?lise, coloquei como tipo "double"
    def __init__(self, nome,  produto,  apresentacao,  registro,  ean1,  pisCofins,  pfSemImp,  pmc0,  em2020) :
        # Instancio o construtor
        self.nome = nome
        self.produto = produto
        self.apresentacao = apresentacao
        self.registro = registro
        self.ean1 = ean1
        self.pisCofins = pisCofins
        self.pfSemImp = pfSemImp
        self.pmc0 = pmc0
        self.em2020 = em2020
    # Abaixo os Gets e os Sets para cada atributo do objeto
    def  getNome(self) :
        return self.nome
    def setNome(self, nome) :
        self.nome = nome
    def  isEm2020(self) :
        return self.em2020
    def setEm2020(self, em2020) :
        self.em2020 = em2020
    def  getPmc0(self) :
        return self.pmc0
    def setPmc0(self, pmc0) :
        self.pmc0 = pmc0
    def  getproduto(self) :
        return self.produto
    def setproduto(self, produto) :
        self.produto = produto
    def  getapresentacao(self) :
        return self.apresentacao
    def setapresentacao(self, apresentacao) :
        self.apresentacao = apresentacao
    def  getregistro(self) :
        return self.registro
    def setregistro(self, registro) :
        self.registro = registro
    def  getean1(self) :
        return self.ean1
    def setean1(self, ean1) :
        self.ean1 = ean1
    def  getpisCofins(self) :
        return self.pisCofins
    def setpisCofins(self, pisCofins) :
        self.pisCofins = pisCofins
    def  getpfSemImp(self) :
        return self.pfSemImp
    def setpfSemImp(self, pfSemImp) :
        self.pfSemImp = pfSemImp