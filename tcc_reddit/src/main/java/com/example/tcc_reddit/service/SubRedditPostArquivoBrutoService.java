package com.example.tcc_reddit.service;

import com.example.tcc_reddit.model.Categoria;
import com.example.tcc_reddit.model.Municipio;
import com.example.tcc_reddit.model.SubReddit;
import com.example.tcc_reddit.model.SubRedditPostArquivoBruto;
import com.example.tcc_reddit.repository.SubRedditPostArquivoBrutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubRedditPostArquivoBrutoService {
    protected final CategoriaService categoriaService;
    protected final MunicipioService municipioService;
    protected final SubRedditService subRedditService;

    protected final SubRedditPostArquivoBrutoRepository repository;
    protected final SubredditPostCategoriaArquivoBrutoService subredditPostCategoriaArquivoBrutoService;

    public SubRedditPostArquivoBrutoService(CategoriaService categoriaService, MunicipioService municipioService, SubRedditService subRedditService, SubRedditPostArquivoBrutoRepository repository, SubredditPostCategoriaArquivoBrutoService subredditPostCategoriaArquivoBrutoService) {
        this.categoriaService = categoriaService;
        this.municipioService = municipioService;
        this.subRedditService = subRedditService;
        this.subredditPostCategoriaArquivoBrutoService = subredditPostCategoriaArquivoBrutoService;
        this.repository = repository;
    }

    @Transactional
    public void savePost(JsonNode jsonNode, int pesoMinimo) {
        try {
            String postId = jsonNode.has("id") ? jsonNode.get("id").asText() : null;
            String title = jsonNode.has("title") ? jsonNode.get("title").asText() : null;
            String name = jsonNode.has("name") ? jsonNode.get("name").asText() : null;
            String selfText = jsonNode.has("selftext") ? jsonNode.get("selftext").asText() : null;
            String author_id = jsonNode.has("author_fullname") ? jsonNode.get("author_fullname").asText() : null;
            String author = jsonNode.has("author") ? jsonNode.get("author").asText() : null;
            boolean saved = jsonNode.has("saved") && jsonNode.get("saved").isBoolean() && jsonNode.get("saved").asBoolean();
            String subreddit_name_prefixed = jsonNode.has("subreddit_name_prefixed") ? jsonNode.get("subreddit_name_prefixed").asText() : null;
            float upvote_ratio = jsonNode.has("upvote_ratio") ? jsonNode.get("upvote_ratio").floatValue() : 0;
            int ups = jsonNode.has("ups") ? jsonNode.get("ups").intValue() : 0;
            int downs = jsonNode.has("downs") ? jsonNode.get("downs").intValue() : 0;
            int score = jsonNode.has("score") ? jsonNode.get("score").intValue() : 0;

            String created = null;
            if (jsonNode.has("created")) {
                long createdUnixTime = jsonNode.get("created").asLong();
                created = Instant.ofEpochSecond(createdUnixTime)
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("d/M/y H:mm:ss"));
            }

            String created_utc = null;
            if (jsonNode.has("created_utc")) {
                long createdAtUtcUnixTime = jsonNode.get("created_utc").asLong();
                created_utc = Instant.ofEpochSecond(createdAtUtcUnixTime)
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("d/M/y H:mm:ss"));
            }

            int num_comments = jsonNode.has("num_comments") ? jsonNode.get("num_comments").intValue() : 0;
            String url = jsonNode.has("url") ? jsonNode.get("url").asText() : null;
            boolean over_18 = jsonNode.has("over_18") && jsonNode.get("over_18").isBoolean() ? jsonNode.get("over_18").asBoolean() : false;
            String edited_at = null;
            boolean is_edited = jsonNode.has("edited") && jsonNode.get("edited").isBoolean() && jsonNode.get("edited").asBoolean();
            if (is_edited) {
                long editedAtUnixTime = jsonNode.get("edited").asLong();
                edited_at = Instant.ofEpochSecond(editedAtUnixTime)
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("d/M/y H:mm:ss"));
            }

            SubRedditPostArquivoBruto subRedditPostArquivoBruto = new SubRedditPostArquivoBruto();
            subRedditPostArquivoBruto.setPostId(postId);
            subRedditPostArquivoBruto.setName(name);
            subRedditPostArquivoBruto.setSelftext(selfText);
            subRedditPostArquivoBruto.setAuthor_id(author_id);
            subRedditPostArquivoBruto.setAuthor(author);
            subRedditPostArquivoBruto.setSaved(saved);
            subRedditPostArquivoBruto.setSubreddit_name_prefixed(subreddit_name_prefixed);
            subRedditPostArquivoBruto.setTitle(title);
            subRedditPostArquivoBruto.setUpvote_ratio(upvote_ratio);
            subRedditPostArquivoBruto.setUps(ups);
            subRedditPostArquivoBruto.setDowns(downs);
            subRedditPostArquivoBruto.setScore(score);
            subRedditPostArquivoBruto.setCreated(created);
            subRedditPostArquivoBruto.setNum_comments(num_comments);
            subRedditPostArquivoBruto.setUrl(url);
            subRedditPostArquivoBruto.setOver_18(over_18);
            subRedditPostArquivoBruto.setCreated_utc(created_utc);
            subRedditPostArquivoBruto.setEdited_at(edited_at);

            String subRedditName = jsonNode.has("subreddit") ? jsonNode.get("subreddit").asText() : null;

            if (subRedditName != null) {
                Optional<SubReddit> subReddit = this.subRedditService.getBySubRedditName(subRedditName);
                subReddit.ifPresent(subRedditPostArquivoBruto::setSubreddit_id);
            }

            Municipio municipio = this.municipioService.getRandomMunicipio();
            subRedditPostArquivoBruto.setMunicipio_id(municipio);

            this.repository.save(subRedditPostArquivoBruto);

            List<Map<String, Object>> categorias = this.categoriaService.definirCategorias(title, selfText, pesoMinimo);
            categorias.forEach(categoria -> {
                int categoria_id = (int) categoria.get("id_categoria");
                int peso = (int) categoria.get("peso_postagem");
                Optional<Categoria> categoriaEntity = this.categoriaService.getById(categoria_id);
                categoriaEntity.ifPresent(categoriaGet -> this.subredditPostCategoriaArquivoBrutoService.store(categoriaGet, subRedditPostArquivoBruto, peso));
            });

        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        }
    }
}
