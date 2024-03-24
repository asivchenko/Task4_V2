package org.example;

public class LineFile {

    private String line;
    private String NameFile;
    private String TextError;

    public LineFile(String line, String nameFile) {
        this.line = line;
        NameFile = nameFile;
    }

    public void setTextError(String textError) {
       // System.out.println ("ОШИБКА texterror=" +textError);
        TextError = textError;
    }

    public String getLine() {
        return line;
    }

    public String getNameFile() {
        return NameFile;
    }

    public String getTextError() {
        return TextError;
    }
    @Override
    public String toString() {
        return "\nСтрока из файла {" +
                " файлn='" +getNameFile() + '\'' +
                ", строка файла ='" + getLine() + '\'' +
                ", Error=" + getTextError() +
                '}';
    }

}
