package model;

import org.jspecify.annotations.NullMarked;

import java.time.LocalDate;
import java.util.ArrayList;

@NullMarked

public class Udflugt {
    private String navn;
    private LocalDate dato;
    private double pris;

    //LinkAttributter
    private Konference konference;
    private final ArrayList<Ledsager> ledsagere = new ArrayList<>();
}
