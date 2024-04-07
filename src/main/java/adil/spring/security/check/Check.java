package adil.spring.security.check;

import lombok.Builder;

@Builder
public class Check {
    private final int numberGene;
    private final int numberUser;
    public boolean ch(){
        return this.numberGene==this.numberUser;
    }
}
