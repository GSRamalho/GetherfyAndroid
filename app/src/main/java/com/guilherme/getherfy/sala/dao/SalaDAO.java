package com.guilherme.getherfy.sala.dao;

import com.guilherme.getherfy.sala.model.Sala;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalaDAO {

    public List<Sala> lista() { List<Sala> salas = new ArrayList<>(Arrays.asList(

                new Sala(1, "Conselho Jedi", 20, true, true, 3.5, true, "1o. Andar", true )));
        return salas;
    }

}