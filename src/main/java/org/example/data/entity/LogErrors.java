package org.example.data.entity;
import javax.persistence.*;
@Entity
@Table(name = "logerrors")
public class LogErrors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_file")
    private String name_file;
    @Column(name = "line")
    private String line;
    @Column(name = "error_text")
    private String error_text;

    public void setName_file(String name_file) {
        this.name_file = name_file;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setError_text(String error_text) {
        this.error_text = error_text;
    }
}