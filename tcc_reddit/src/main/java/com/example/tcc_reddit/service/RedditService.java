package com.example.tcc_reddit.service;


import com.example.tcc_reddit.DTOs.reddit.baseStructure.RedditListingDTO;
import com.example.tcc_reddit.DTOs.reddit.subReddit.SubRedditDTO;
import com.example.tcc_reddit.controller.reddit.BaseReddit;
import com.example.tcc_reddit.controller.reddit.RedditApiException;
import com.example.tcc_reddit.credentials.Credentials;
import com.example.tcc_reddit.model.SubReddit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class RedditService extends BaseReddit {
    //todo usar blocos try catch
    //todo não iniciar stream quando não houver coordenadas.
    protected final SubRedditService subRedditService;
    protected final SubRedditPostService subRedditPostService;
    protected final CategoriaService categoriaService;
    @Autowired
    public RedditService(SubRedditPostService subRedditPostService,
                         Credentials credentials,
                         SubRedditService subRedditService,
                         CategoriaService categoriaService)
    {
        super(credentials);
        this.subRedditPostService = subRedditPostService;
        this.subRedditService = subRedditService;
        this.categoriaService = categoriaService;
    }

    public void streamSubreddits(List<String> subredditsName, int intervalo, int limite, String sort, int peso) throws RedditApiException{
        for (String subredditName : subredditsName) {
            this.fetchAndSaveSubReddit(subredditName);
        }
        List<SubReddit> subReddits = this.subRedditService.getAllSubReddits();
        if (!this.categoriaService.categoriaExists()){
            System.out.println("Não Existe categoria no banco de dados!!!");
            System.out.println("Adicionando categorias iniciais pré-definidas");
            this.categoriaService.storeCategoriasIniciais();
            System.out.println("Categorias iniciais salvas com sucesso.\n");
        }
        for (SubReddit subReddit : subReddits) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread foi interrompida, saindo do loop.");
                return;
            }
            this.streamSubredditPosts(subReddit, intervalo, limite, sort, peso);
        }
        System.out.println("\n\n*****Terminou o FETCH*****\n\n");
    }

    public void fetchAndSaveSubReddit(String subredditName) throws RedditApiException{
        SubRedditDTO subRedditDTO = this.subRedditService.findSubReddit(subredditName);
        Optional<SubReddit> subReddit = this.subRedditService.getByid(subRedditDTO.getData().getId());
        if(subReddit.isEmpty()){
            SubReddit newSubReddit = this.subRedditService.store(subRedditDTO.getData().getId(), subRedditDTO.getData().getDisplay_name(), subRedditDTO.getData().getTitle(), subRedditDTO.getData().getDescription());
            this.subRedditService.save(newSubReddit);
        }
    }

    private void streamSubredditPosts(SubReddit subReddit, int intervalo, int limite, String sort, int peso) throws RedditApiException {
        boolean aindaTemPost = true;
        int totalLidos = 0;
        System.out.println("\n---inicio do stream para o subreddit  : " + subReddit.getSubRedditName() + "---\n");
        while (aindaTemPost) {
            Map<String, Object> retorno = this.fetchAndSavePosts(subReddit, sort, limite, intervalo, peso);
            aindaTemPost = (boolean) retorno.get("continuar");
            totalLidos += (int) retorno.get("qtdPostsLidos");
            if((int) retorno.get("qtdPostsLidos") != 0){
                System.out.println("Total de posts lidos nessa seção -> " + totalLidos);
            }
        }
        if(totalLidos == 0){
            System.out.println("Não houve stream para esse subreddit");
        }
        System.out.println("\n---Fim do stream para o subreddit     : " + subReddit.getSubRedditName() + "---\n");
    }

    private Map<String, Object> fetchAndSavePosts(SubReddit subReddit, String sort, int limite, int intervalo, int peso)  throws RedditApiException{
        Map<String, Object> retorno = new HashMap<>();
        try {
            Map<String, Object> resultado;
            if(!subReddit.isAcabou_after()){
                resultado = this.subRedditPostService.fetchPosts(subReddit.getSubRedditName(), subReddit.getAfter(), null, limite, sort);
            }else{
                resultado = this.subRedditPostService.fetchPosts(subReddit.getSubRedditName(), null, subReddit.getBefore(), limite, sort);
            }

            RedditListingDTO posts = (RedditListingDTO) resultado.get("redditListinfDto");

            Map<String, Object> resultadoSalvos = this.subRedditPostService.savePosts(posts, peso);
            String postId = (String) resultadoSalvos.get("lastPostId");
            int totalSalvos = (int) resultadoSalvos.get("totalSalvo");
            if (postId == null && !subReddit.isAcabou_after()){
                subReddit.setAcabou_after(true);
                retorno.put("qtdPostsLidos", 0);
                retorno.put("continuar", true);
                return retorno;
            } else if (postId == null) {
                retorno.put("qtdPostsLidos", 0);
                retorno.put("continuar", false);
                return retorno;
            }

            if(!subReddit.isAcabou_after()){
                subReddit.setAfter("t3_" + postId);
            }else{
                subReddit.setBefore("t3_" + postId);
            }

            if(subReddit.getBefore() == null){
                subReddit.setBefore("t3_" + postId);
            }

            this.subRedditService.save(subReddit);

            String requests_remaing = (String) resultado.get("requests_remaing");
            String requests_used = (String) resultado.get("requests_used");
            String requests_reset = (String) resultado.get("requests_reset");

            System.out.println("\n--------------------------------------------");
            System.out.println("API: requests remaing            -> " + requests_remaing);
            System.out.println("API: requests used               -> " + requests_used);
            System.out.println("API: requests to reset           -> " + requests_reset);
            System.out.println("Nome do Subreddit                -> " + subReddit.getSubRedditName());
            MemoryService.logMemoryUsage();

            Thread.sleep(intervalo * 1000L);
            retorno.put("continuar", true);
            retorno.put("qtdPostsLidos", totalSalvos);
            return retorno;
        } catch (RedditApiException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RedditApiException("Streaming process interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        retorno.put("continuar", false);
        return retorno;
    }

}

