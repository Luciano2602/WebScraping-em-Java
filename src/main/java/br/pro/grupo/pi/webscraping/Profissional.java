package br.pro.grupo.pi.webscraping;

public class Profissional {
    private String nome;
    private String codCNES;
    private String codCNES_Master;
    private String dataAtribuicao;
    private String cbo; 
    private String cargaHorariaTotal; 
    private String sus; 
    private String vinculacao; 
    private String tipo; 
    private String situacap; 

    public Profissional(String nome, String codCNES, String codCNES_Master, String dataAtribuicao, String cbo, String cargaHorariaTotal, String sus, String vinculacao, String tipo, String situacap) {
        this.nome = nome;
        this.codCNES = codCNES;
        this.codCNES_Master = codCNES_Master;
        this.dataAtribuicao = dataAtribuicao;
        this.cbo = cbo;
        this.cargaHorariaTotal = cargaHorariaTotal;
        this.sus = sus;
        this.vinculacao = vinculacao;
        this.tipo = tipo;
        this.situacap = situacap;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodCNES() {
        return codCNES;
    }

    public void setCodCNES(String codCNES) {
        this.codCNES = codCNES;
    }

    public String getCodCNES_Master() {
        return codCNES_Master;
    }

    public void setCodCNES_Master(String codCNES_Master) {
        this.codCNES_Master = codCNES_Master;
    }

    public String getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(String dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public String getCargaHorariaTotal() {
        return cargaHorariaTotal;
    }

    public void setCargaHorariaTotal(String cargaHorariaTotal) {
        this.cargaHorariaTotal = cargaHorariaTotal;
    }

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public String getVinculacao() {
        return vinculacao;
    }

    public void setVinculacao(String vinculacao) {
        this.vinculacao = vinculacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSituacap() {
        return situacap;
    }

    public void setSituacap(String situacap) {
        this.situacap = situacap;
    }
    
    
    
}
