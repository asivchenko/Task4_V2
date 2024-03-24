package org.example.data.inter;

import java.util.List;

public interface DatabaseWriter <T1,T2>{
    void writeErrorToDateBase (List<T1> data1);
    void writeToDateBase (List<T2> data2);
    void setDatabaseConnection (String url, String username, String password);
}
