package br.pro.grupo.pi.webscraping;

public class Ubs {
    
    private String codCNES;
    private String nomeUbs;
    private String razaoSocial;
    private String codUnidade;

    public Ubs(String codCNES, String nomeUbs, String razaoSocial, String codUnidade) {
        this.codCNES = codCNES;
        this.nomeUbs = nomeUbs;
        this.razaoSocial = razaoSocial;
        this.codUnidade = codUnidade;
    }

    
        
    public String getCodCNES() {
        return codCNES;
    }

    public void setCodCNES(String codCNES) {
        this.codCNES = codCNES;
    }

    public String getNomeUbs() {
        return nomeUbs;
    }

    public void setNomeUbs(String nomeUbs) {
        this.nomeUbs = nomeUbs;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCodUnidade() {
        return codUnidade;
    }

    public void setCodUnidade(String codUnidade) {
        this.codUnidade = codUnidade;
    }
    
    
    
}
