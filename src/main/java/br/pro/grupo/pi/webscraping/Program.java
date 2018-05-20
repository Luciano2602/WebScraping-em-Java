package br.pro.grupo.pi.webscraping;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.sun.webkit.WebPageClient;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;

public class Program {
    
    public static void main(String[] args) throws IOException {
        
        JBrowserDriver driver = new JBrowserDriver();

        // carrega a página principal
        String url = "http://cnes2.datasus.gov.br/Listar_Mantidas.asp?VCnpj=46392130000380";
        driver.get(url);

        System.out.println(" --------  URL  -----------");
        System.out.println(driver.toString());
        // decobre os links dos mantidos
        List<WebElement> links = driver.findElementsByCssSelector("a");
        for (WebElement link : links) {
            // carrega a página de uma mantida
            url = link.getAttribute("href");
            driver.get(url);
            
            System.out.println("----    HREF    ----");
            System.out.println(link.getText());
            
            // descobre os botões de módulos
            List<WebElement> modulos = driver.findElementsByCssSelector("a");
            for (WebElement modulo : modulos) {
                url = modulo.getAttribute("href");
                System.out.println(modulo.getAttribute("href"));
                // se for o botão Profissionais
                if (url.contains("Profissional")) {
                    // carreaga a página de profissionais
                    driver.get(url);
                    System.out.println(driver.toString());
                    boolean fim = false;
                    do {
                        // mostra/usa os dados
                        WebElement dados = driver.findElementByCssSelector("table#example");
                        System.out.println(dados.getText());

                        // carrega os dados de paginação
                        WebElement infoElem = driver.findElementById("example_info");
                        String info = infoElem.getText();

                        // verifica se fim
                        if (info.matches("Mostrando de (\\d+) até (\\d+) de (\\2) registros")) {
                            fim = true;
                        } else {
                            // "clica" no botão next
                            WebElement next = driver.findElementByCssSelector("span.next");
                            next.click();
                        }
                    } while (!fim);

                    // não tirar esse break
                    break;
                }
            }

            // tirar esse break para processar todos os mantidos
            break;
        }

        driver.quit();
        
        
        
        
        /*
        System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog","fatal");       
        
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage page = webClient.getPage("http://cnes2.datasus.gov.br/Listar_Mantidas.asp?VCnpj=46392130000380");
        System.out.println(page.asText() + "-" );
        
        System.out.println("===================================================");
        
        List<HtmlAnchor> ancors = page.getAnchors();
        
        for (HtmlAnchor linkMantidas : ancors) {
            
            String linkText = linkMantidas.asText();
            
            if (linkText.startsWith("PREFEITURA")) {
                continue;
            }
            
            System.out.println("Clicando em => " + linkText);
            
            
            HtmlPage pageMantida = linkMantidas.click();
            
            System.out.println("Paginas Matidas => " + pageMantida.asText());
            
            List<HtmlAnchor> botoesMantida = pageMantida.getAnchors();
            System.out.println("Modulos => " + botoesMantida.size());
            
            System.out.println("");
            System.out.println("");
            System.out.println("");
            //break;
            for (HtmlAnchor botao : botoesMantida) {
                
                System.out.println(" botao Mantidas => " + botao.asText());
                HtmlAnchor linkProfissionais = pageMantida.getAnchorByHref("Mod_Profissional");
                System.out.println("Link Profissionais => " + linkProfissionais);
                
                HtmlPage pageProfissionais = linkProfissionais.click();
                System.out.println("Pagina Prof => " + pageProfissionais.asText());
                List<DomElement> trs = pageProfissionais.getElementsByName("tr");
                
                for (DomElement tr : trs) {
                    
                    System.out.println("tr Prof => " + tr.asText());
                }
            }
            
        }
        
        
        System.out.println("ACABOU");
        
        
        
        
        
        
        
        
        
        
        //as linhas abaixos instancia a class para trabalharmos com JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        
        //criando uma instancia do banco
        MongoClient mongo = new MongoClient("localhost",27017);
        //Passando o DB
        DB db = mongo.getDB("Luciano");
        
        //a linha abaixo pega a collection
        DBCollection cnes = db.getCollection("CNES");
        
        //site onde vamos estrair as informacoes        
        String site = "http://cnes2.datasus.gov.br/Listar_Mantidas.asp?VCnpj=46392130000380";
        //pegando o html da página
        Document pagina = Jsoup.connect(site).userAgent("Mozila/5.0").get();
        
        //pegando o elemento da tag
        Elements itens = pagina.select("tr");
          
        //a partir da linha 16 que consigo pegar os itens
        int i = 0;
        
        //percorrendo o objeto obitido atravez da tag
        for(Element item : itens){
            i++;
            if(i>16){
                //pegando o cod do CNES
                String codCNES = item.select("font").text().substring(0, 7);            
                //pegando o nome da UBS
                String nome = item.select("a").text();
                
                String razao = "";
                String codUnidade = item.select("a").attr("abs:href");;
                //criando uma UBS
                Ubs ubs = new Ubs(codCNES, nome, razao, codUnidade);
                
                //unidade podemos pegar o cód que está dentro do href e passar no parametro pela url 
                String urlFuncionario = "http://cnes2.datasus.gov.br/Mod_Profissional.asp?VCo_Unidade=" + codUnidade;
                pagina = Jsoup.connect(urlFuncionario).userAgent("Mozila/5.0").get();
                Elements func = pagina.select("tr");
                for(Element funcionario : func){
                    //pegar o html na url
                }
                //Panssando os para a class responsavel por transformar em JSON
                mapper.writeValueAsString(ubs);
                String json = mapper.writeValueAsString(ubs);
                System.out.println(json);
            
                //persistindo no banco 
                DBObject obj = (DBObject) JSON.parse(json);
                cnes.insert(obj);
            }
            
        
        }
        
        System.out.println("<<<<<<<<<<<<<<<<Acabou>>>>>>>>>>>>>>>>");
        
            
            
            
            */
        
        
        
        
        
        
       
        

        
    }
    
    
}
