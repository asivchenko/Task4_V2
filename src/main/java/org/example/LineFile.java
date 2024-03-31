package org.example;

import java.sql.Timestamp;

public class LineFile {

    private String login;
    private String fio;
    private String date_string;
    private Timestamp access_stamp;
    private String app ;
    private String namefile;
    private String texterror;
    private String line ;

    public LineFile(String login,String fio, String date_string,
                     String app, String nameFile,String line,String texterror
                    ) {
        this.login = login;
        this.fio = fio;
        this.date_string = date_string;
        this.app =app;
        this.namefile = nameFile;
        this.line=line;
        this.texterror=texterror;
    }

    public void setTextError(String textError) {
        if (this.texterror ==null)
            this.texterror = textError;
        else
            this.texterror = this.texterror +"; " +textError;
    }

    public String getLine() {
        return line;
    }

    public String getNameFile() {
        return namefile;
    }

    public String getTextError() {
        return this.texterror;
    }

    public String getLogin() {
        return login;
    }
    public String getFio()
    {
        return fio;
    }
    public String getApp() {
        return app;
    }

    public Timestamp getAccess_stamp() {
        return access_stamp;
    }

    public String getDate_string() {

        return date_string;
    }

    //сеттеры
    public void setAccess_stamp(Timestamp access_stamp) {
        this.access_stamp = access_stamp;
    }



    public void setFio(String fio) {
        this.fio = fio;
    }



    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "\nСтрока файла {" +
                " файл='" +getNameFile() + '\'' +
                ", строка файла ='" + getLine() + '\'' +
                ", Error=" + getTextError() +
                '}';
    }

}
