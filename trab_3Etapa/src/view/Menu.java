package view;

import java.util.*;


import youtube.*;
import dados.*;

public abstract class Menu {


    public static void clear(){
        System.out.println("\033c");
    }

    public static void menu1(Dados dados, Scanner entrada){
        clear();
        System.out.println("Bem-Vindo ao Youtube. Faça seu cadastro\n");
        System.out.println("Insira um nome");
        String nome = entrada.nextLine();
        System.out.println(" ");
        System.out.println("Insira uma senha");
        String senha = entrada.nextLine();
        System.out.println(" ");
        Usuario user = new Usuario(senha, nome);
        dados.setUsuario(user);
        clear();
        menu2(dados, entrada);
    }

    public static void menu2(Dados dados, Scanner entrada){
        System.out.println("Olá "+dados.getUsuario().getNomeUsuario()+", o que deseja?\n");
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - sair\n";
        opçoes += "  1 - Listar canais\n";
        opçoes += "  2 - Canais Inscritos\n";
        opçoes += "  3 - histórico\n";
        opçoes += "  4 - Editar usuario\n";
        opçoes += "  5 - Criar canal\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        System.out.println(" ");//Pular uma linha
        switch (valor) {
            case 0:
                clear();
                System.out.println("Obrigado por usar o Youtube!!");
                break;
            case 1:
                clear();
                menu3_Canais(dados, entrada);
                break;
            case 2:
                clear();
                menu3_Inscricoes(dados, entrada);
                break;

            case 3:
                clear();
                dados.getUsuario().setVideoHistorico(0, dados.getCanal(0).getVideo(0));
                dados.getUsuario().setQtdHistorico(1);
                menu5_Historico(dados.getUsuario(), dados, entrada);
                break;   
            default:
                clear();
                System.out.println("Opção inválida\n");
                menu2(dados, entrada);//Recursividade, simulando loop de repetição
                break;
        }
    }

    public static void menu3_Canais(Dados dados, Scanner entrada){
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar\n\n";
            opçoes +="Canais disponíveis:\n\n";
            for(int i = 1; i<= dados.getQtdCanais(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+dados.getCanal(i-1).getNomeCanal()+"\n");
            }
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= dados.getQtdCanais()){
            clear();
            menu4(dados, entrada, dados.getCanal(valor-1));

        }else{
            System.out.println("Opção inválida");
            clear();
            menu3_Canais(dados, entrada);
        }
    }
    
    public static void menu3_Inscricoes(Dados dados, Scanner entrada){
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar\n\n";
        if(dados.getUsuario().getQtdInscricoes() == 0){
            opçoes+= "Você não está inscrito em nenhum canal!\n";
        }else{
            opçoes +="Canais disponíveis:\n\n";
            for(int i = 1; i<= dados.getUsuario().getQtdInscricoes(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+dados.getUsuario().getInscricao(i-1).getNomeCanal()+"\n");
            }
        }
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= dados.getUsuario().getQtdInscricoes() && dados.getUsuario().getQtdInscricoes() != 0){
            clear();
            menu4(dados, entrada, dados.getUsuario().getInscricao(valor-1));

        }else{
            System.out.println("Opção inválida");
            clear();
            menu3_Inscricoes(dados, entrada);
        }


    }

    public static void menu4(Dados dados, Scanner entrada, Canal canal){
        System.out.println("Este é o canal: "+canal.getNomeCanal());
        String opçoes = new String("Escolha uma opção:\n\n");
        opçoes += "  0 - voltar para pagina principal\n";
        if(dados.getUsuario().inscricoesToString().contains(canal.getNomeCanal())){
            //está inscrito
            opçoes += "  1 - Cancelar inscrição\n";
        }else{
            opçoes += "  1 - Inscrever-se\n";
        }
        opçoes += "  2 - Listar videos\n";
        opçoes += "  3 - Listar enquetes\n";
        opçoes += "  4 - Editar canal\n";
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                menu2(dados, entrada);
                break;
            case 1:
                if(dados.getUsuario().inscricoesToString().contains(canal.getNomeCanal())){
                //está inscrito
                    dados.getUsuario().CancelarInscrição(canal);
                    canal.setQtdInscritos(canal.getQtdInscritos()-1);
                    clear();
                     menu4(dados, entrada, canal);
                }else{
                    dados.getUsuario().inscreverSe(canal);
                    canal.setQtdInscritos(canal.getQtdInscritos()+1);
                    clear();
                    menu4(dados, entrada, canal);

                }
                break;
            case 2:
                clear();
                menu5_Videos(canal, dados, entrada);
                break;
            case 3:
                clear();
                menu5_Enquete(canal, dados, entrada);
                break;
            default:
                clear();
                System.out.println("opção inválida");
                menu4(dados, entrada, canal);
                break;
        }
            
    }

    public static void menu5_Videos(Canal canal, Dados dados, Scanner entrada){//Printa Videos Canal
        String opçoes = new String("Escolha um opção\n\n");
        opçoes +="  0 - voltar ao canal\n\n";
        opçoes+="Videos de "+canal.getNomeCanal()+"\n\n";       
        for(int i = 1; i<= canal.getQtdVideos(); i++){
            String s = String.valueOf(i);//Transformo j em string
            opçoes += new String("  "+s+" - "+canal.getVideo(i-1).getTitulo()+"\n");
        }System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu4(dados, entrada,canal);
        }else if(valor >= 1 && valor <= canal.getQtdVideos()){
            clear();
            menu6(canal.getVideo(valor-1), dados, entrada);

        }else{
            System.out.println("Opção inválida");
            clear();
            menu5_Videos(canal, dados, entrada);
        }
    }
    
    public static void menu5_Historico(Usuario user, Dados dados, Scanner entrada){//Printa Histórico
        String opçoes = new String("Escolha um opção\n\n");
        opçoes +="  0 - voltar para pagina principal\n\n";
        opçoes+="Histórico de "+user.getNomeUsuario()+":\n\n";
        if(user.getQtdHistorico() == 0 ){
            opçoes +="Você não possui vídeos em seu histórico.\n";
        }else{
            for(int i = 1; i <= user.getQtdHistorico(); i++){
                String s = String.valueOf(i);//Transformo j em string
                opçoes += new String("  "+s+" - "+user.getVideoHistorico(i-1).getTitulo()+"\n");//do canal tal?(autor)
            }
        }     
        System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu2(dados, entrada);
        }else if(valor >= 1 && valor <= user.getQtdHistorico() && user.getQtdHistorico()!=0){
            clear();
            //Menu 6
        }else{
            System.out.println("Opção inválida");
            clear();
            menu5_Historico(user, dados, entrada);
        }
    }
    
    public static void menu5_Enquete(Canal canal, Dados dados, Scanner entrada){
        String opçoes = new String("Escolha um opção\n\n");
        opçoes +="  0 - voltar ao canal\n\n";
        opçoes+="Enquetes de "+canal.getNomeCanal()+"\n\n";       
        for(int i = 1; i<= canal.getQtdEnquetes(); i++){
            String s = String.valueOf(i);//Transformo j em string
            opçoes += new String("  "+s+" - "+canal.getEnquete(i-1).getPergunta()+"\n");
        }System.out.println(opçoes);
        int valor = entrada.nextInt();
        if(valor == 0){
            clear();
            menu4(dados, entrada,canal);
        }else if(valor >= 1 && valor <= canal.getQtdEnquetes()){
            clear();
            //Menu8

        }else{
            System.out.println("Opção inválida");
            clear();
            menu5_Enquete(canal, dados, entrada);
        }

    }

    public static void menu6(Video video, Dados dados, Scanner entrada){
        if(video.getIsPausado()){
            System.out.println("O video está pausado\n");
        }else{
            System.out.println("O video "+video.getTitulo()+" está passando\n");
        }
        switch (video.getVelocidade()) {
            case 2:
                System.out.println("Velocidade atual: 2x\n");
                break;
        
            default:
                System.out.println("Velocidade atual: 1x\n");
                break;
        }
        System.out.println("Quantidade Gostei: "+video.getQtdGostei()+"\n");
        System.out.println("Quantidade Não Gostei: "+video.getQtdNaoGostei()+"\n");
        String opcoes = new String("Escolha uma opção\n\n");
        opcoes += "  0 - voltar canal\n";
        opcoes += "  1 - voltar página principal\n";
        if(!video.getStatusGostei() && !video.getStatusNãoGostei()){
            opcoes += "  2 - Gostei\n"; //GOSTEI = TRUE // NAO GOSTEI = FALSE
            opcoes += "  3 - Não gostei\n";//GOSTEI = FALSE // NAO GOSTEI = TRUE
        }else if(video.getStatusGostei() == true && video.getStatusNãoGostei() == false){
            opcoes += "  3 - Não gostei\n";
        }else{
            opcoes += "  2 - Gostei\n";}
        opcoes += "  4 - Ler comentários\n";
        if(video.getIsPausado()){
             opcoes += "  5 - Reproduzir\n";
        }else{
            opcoes += "  5 - pausar video\n";
        }
        switch (video.getVelocidade()){
            case 2:
                opcoes += "  6 - Reduzir velocidade\n";
                break;
        
            default:
                opcoes += "  6 - aumentar velocidade\n";
                break;
        }
        System.out.println(opcoes);
        int valor = entrada.nextInt();
        switch (valor) {
            case 0:
                clear();
                menu4(dados, entrada, video.getAutor());
                break;

            case 1:
                clear();
                menu2(dados, entrada);
                break;
            case 2:
            if(!video.getStatusGostei() && !video.getStatusNãoGostei()){
                video.setStatusGostei(true);
                video.setQtdGostei(video.getQtdGostei()+1);//adiciona 1 na quantidade de gostei
                video.setStatusNãoGostei(false);
                clear();
                menu6(video, dados, entrada);
                break;
            }else if(video.getStatusNãoGostei() && !video.getStatusGostei()){
                video.setStatusGostei(true);
                video.setQtdGostei(video.getQtdGostei()+1);//adiciona 1 na quantidade de gostei
                video.setStatusNãoGostei(false);
                video.setQtdNaoGostei(video.getQtdNaoGostei()-1);
                clear();
                menu6(video, dados, entrada);
                break;
            }else{
                clear();
                System.out.println("Opção inválida");
                menu6(video, dados, entrada);
                break;
            }
            case 3:
            if(!video.getStatusGostei() && !video.getStatusNãoGostei()){
                video.setStatusNãoGostei(true);
                video.setQtdNaoGostei(video.getQtdNaoGostei()+1);
                video.setStatusGostei(false);
                clear();
                menu6(video, dados, entrada);
                break;
            }else if(video.getStatusGostei() && !video.getStatusNãoGostei()){
                video.setStatusNãoGostei(true);
                video.setQtdNaoGostei(video.getQtdNaoGostei()+1);//adiciona 1 na quantidade de gostei
                video.setStatusGostei(false);
                video.setQtdGostei(video.getQtdGostei()-1);
                clear();
                menu6(video, dados, entrada);
                break;

            }else{
                clear();
                System.out.println("Opção inválida");
                menu6(video, dados, entrada);
                break;
            }
        
            default:
                clear();
                System.out.println("Opção inválida");
                menu6(video, dados, entrada);
                break;
        }

        
    }
}
/*
 * console 6:_console para Video_
        6.0 voltar ao canal _Retorna console 4_
        6.1 voltar Home page _Pode ser uma boa ideia poder voltar diretamente pra Home_
        6.2 Like/deslike
        6.3 Fazer um comentario _Adiciona ao Array de comentarios_//_console de criação 9_
        6.4 Ler comentarios _PRINTA o toString dos comentários_ 
            6.5 Acessar comentario _Chama  o console 7_
        6.6 pausar video/despausar
        6.7 aumentar velocidade
        
        6.9 editar video _Pede senha do canal para habilitar edição_//_Chama console 13_
*/