package org.example.Interface;

import java.util.List;

public interface DataValidator <T,R> {
    List <R>  validatordata ( List<T> lines);
}
