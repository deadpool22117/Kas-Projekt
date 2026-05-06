package model;

import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;

@NullMarked
public class Firma {
    private String firmaNavn;
    private String firmaTlf;
    

    public Firma(String firmaNavn, String firmaTlf) {
        this.firmaNavn = firmaNavn;
        this.firmaTlf = firmaTlf;
    }

}
