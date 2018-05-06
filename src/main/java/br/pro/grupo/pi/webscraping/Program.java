package br.pro.grupo.pi.webscraping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {
    
    public static void main(String[] args) throws IOException {
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
                
                //criando uma UBS
                Ubs ubs = new Ubs(codCNES, nome);
                
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
               
        
    }
        
}
