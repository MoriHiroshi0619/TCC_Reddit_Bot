package com.example.tcc_reddit.repository;

import com.example.tcc_reddit.model.Categoria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository <Categoria, Integer> {
    List<Categoria> findByNomeIn(List<String> nomes);

    Optional<Categoria> findFirstByNome(String nome);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO categoria (descricao, nome) VALUES " +
            "('Linguagem de programação', 'c#')," +
            "('Linguagem de programação', 'python')," +
            "('Linguagem de programação', 'rust')," +
            "('Linguagem de programação', 'typescript')," +
            "('Linguagem de programação', 'lisp')," +
            "('Linguagem de programação', 'prolog')," +
            "('Linguagem de programação', 'java')," +
            "('Linguagem de programação', 'c++')," +
            "('Linguagem de programação', 'php')," +
            "('Linguagem de programação', 'javascript')," +
            "('Linguagem de programação', 'swift')," +
            "('Linguagem de programação', 'go')," +
            "('Linguagem de programação', 'kotlin')," +
            "('Linguagem de programação', 'r')," +
            "('Linguagem de programação', 'perl')," +
            "('Linguagem de programação', 'scala')," +
            "('Linguagem de programação', 'dart')," +
            "('Linguagem de programação', 'ruby')," +
            "('framework', 'cakephp')," +
            "('framework', 'react')," +
            "('framework', 'vue')," +
            "('framework', 'unity')," +
            "('framework', 'spring')," +
            "('framework', 'next')," +
            "('framework', 'node')," +
            "('framework', 'laravel')," +
            "('framework', '.net')," +
            "('framework', 'django')," +
            "('framework', 'flask')," +
            "('framework', 'angular')," +
            "('framework', 'svelte')," +
            "('framework', 'ember')," +
            "('framework', 'symfony')," +
            "('framework', 'express')," +
            "('framework', 'flutter')," +
            "('IDE', 'vscode')," +
            "('IDE', 'intellij')," +
            "('IDE', 'phpstorm')," +
            "('IDE', 'pycharm')," +
            "('IDE', 'eclipse')," +
            "('IDE', 'netbeans')," +
            "('IDE', 'rubymine')," +
            "('IDE', 'android studio')," +
            "('IDE', 'xcode')," +
            "('termo de Tecnologia', 'AJAX')," +
            "('termo de Tecnologia', 'gamedev')," +
            "('termo de Tecnologia', 'desktop')," +
            "('termo de Tecnologia', 'clean code')," +
            "('termo de Tecnologia', 'solid')," +
            "('termo de Tecnologia', 'api')," +
            "('termo de Tecnologia', 'microservices')," +
            "('termo de Tecnologia', 'devops')," +
            "('termo de Tecnologia', 'cloud')," +
            "('termo de Tecnologia', 'docker')," +
            "('termo de Tecnologia', 'kubernetes')," +
            "('termo de Tecnologia', 'blockchain')," +
            "('termo de Tecnologia', 'cybersecurity')," +
            "('sistema operacional', 'windows')," +
            "('sistema operacional', 'mac')," +
            "('sistema operacional', 'linux')," +
            "('sistema operacional', 'ubuntu')," +
            "('sistema operacional', 'fedora')," +
            "('sistema operacional', 'debian')," +
            "('sistema operacional', 'manjaro')," +
            "('banco de dados', 'mysql')," +
            "('banco de dados', 'postgres')," +
            "('banco de dados', 'mongoDb')," +
            "('banco de dados', 'oracle')," +
            "('banco de dados', 'sqlite')," +
            "('banco de dados', 'redis')," +
            "('banco de dados', 'cassandra')," +
            "('metodologia ágil', 'scrum')," +
            "('metodologia ágil', 'kanban')", nativeQuery = true)
    void insertCategoriasIniciais();
}
