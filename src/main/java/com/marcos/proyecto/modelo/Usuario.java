package com.marcos.proyecto.modelo;

public class Usuario {
    private Long idUs;
    private String nomUs;
    private String emailUs;
    private String passwordUs;
    private String rolUs;
    private String estadoUs;

    public Usuario() {}

    public Usuario(Long idUs, String nomUs, String emailUs, String passwordUs, String rolUs, String estadoUs) {
        this.idUs = idUs;
        this.nomUs = nomUs;
        this.emailUs = emailUs;
        this.passwordUs = passwordUs;
        this.rolUs = rolUs;
        this.estadoUs = estadoUs;
    }

    public Long getIdUs() {
        return idUs;
    }

    public void setIdUs(Long idUs) {
        this.idUs = idUs;
    }

    public String getNomUs() {
        return nomUs;
    }

    public void setNomUs(String nomUs) {
        this.nomUs = nomUs;
    }

    public String getEmailUs() {
        return emailUs;
    }

    public void setEmailUs(String emailUs) {
        this.emailUs = emailUs;
    }

    public String getPasswordUs() {
        return passwordUs;
    }

    public void setPasswordUs(String passwordUs) {
        this.passwordUs = passwordUs;
    }

    public String getRolUs() {
        return rolUs;
    }

    public void setRolUs(String rolUs) {
        this.rolUs = rolUs;
    }

    public String getEstadoUs() {
        return estadoUs;
    }

    public void setEstadoUs(String estadoUs) {
        this.estadoUs = estadoUs;
    }

    
}