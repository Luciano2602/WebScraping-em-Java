package br.pro.grupo.pi.webscraping;

public class Ubs {
    
    private String codCNES;
    private String nomeUbs;

    public Ubs(String codCNES, String nomeUbs) {
        this.codCNES = codCNES;
        this.nomeUbs = nomeUbs;
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
    
    
}
