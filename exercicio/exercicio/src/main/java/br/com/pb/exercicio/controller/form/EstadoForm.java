package br.com.pb.exercicio.controller.form;

import br.com.pb.exercicio.modelo.Estado;
import br.com.pb.exercicio.modelo.Regioes;
import br.com.pb.exercicio.repository.EstadoRepository;
import com.sun.istack.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class EstadoForm {

    @NotNull
    private String nome;
    @NotNull
    private Regioes regiao;
    @NotNull
    private Long populacao;
    @NotNull
    private String capital;
    @NotNull
    private Long area;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Regioes getRegiao() {
        return regiao;
    }

    public void setRegiao(Regioes regiao) {
        this.regiao = regiao;
    }

    public Long getPopulacao() {
        return populacao;
    }

    public void setPopulacao(Long populacao) {
        this.populacao = populacao;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Estado converter() {
        return new Estado(nome, regiao, populacao, capital, area);
    }
}

