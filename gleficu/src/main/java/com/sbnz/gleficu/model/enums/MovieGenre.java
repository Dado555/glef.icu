package com.sbnz.gleficu.model.enums;

// https://help.imdb.com/article/contribution/titles/genres/GZDRMS6R742JRGAG?ref_=helpms_helpart_inline#

public enum MovieGenre {
    ACTION(1), ADULT(2), ADVENTURE(3), ANIMATION(4), BIOGRAPHY(5) , COMEDY(6) , CRIME(7),
    DOCUMENTARY(8) , DRAMA(9), FAMILY(10) , FANTASY(11), FILM_NOIR(12) , GAME_SHOW(13),
    HISTORY(14) , HORROR(15) , MUSICAL(16), MUSIC(17) , MYSTERY(18) , NEWS(19) ,
    REALITY_TV(20) , ROMANCE(21) , SCI_FI(22), SHORT(23), SPORT(24) ,
    TALK_SHOW(25) , THRILLER(26) , WAR(27) , WESTERN(28);

    public final int fId;

    private MovieGenre(int num) {
        this.fId = num;
    }

    public static MovieGenre forInt(int num) {
        return values()[num-1];
    }
}
