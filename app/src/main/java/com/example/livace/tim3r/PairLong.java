package com.example.livace.tim3r;

/**
 * Created by User on 22.01.2018.
 */

public class PairLong {
    private Long first;
    private Long second;

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long Difference(){
        return this.second - this.first;
    }
}
