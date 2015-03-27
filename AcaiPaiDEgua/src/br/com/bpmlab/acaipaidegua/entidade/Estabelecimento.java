package br.com.bpmlab.acaipaidegua.entidade;

import java.math.BigDecimal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="estabelecimento")
public class Estabelecimento {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String endereco;
    @DatabaseField
    private String bairro;
    @DatabaseField
    private String telefone;
    @DatabaseField
    private Double latitude;
    @DatabaseField
    private Double longitude;



    public Estabelecimento(){

    }

    public Integer getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
