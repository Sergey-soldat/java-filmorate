CREATE TABLE IF NOT EXISTS RATING (
                                      RATING_ID INTEGER NOT NULL AUTO_INCREMENT,
                                      NAME VARCHAR_IGNORECASE NOT NULL,
                                      CONSTRAINT RATING_RATING_ID_PK PRIMARY KEY (RATING_ID)
);

CREATE TABLE IF NOT EXISTS FILMS (
                                     FILM_ID INTEGER NOT NULL AUTO_INCREMENT,
                                     NAME VARCHAR_IGNORECASE NOT NULL,
                                     DESCRIPTION VARCHAR_IGNORECASE(200) NOT NULL,
                                     RELEASE_DATE DATE NOT NULL,
                                     DURATION INTEGER NOT NULL,
                                     RATING_ID INTEGER NOT NULL,
                                     CONSTRAINT FILMS_FILM_ID_PK PRIMARY KEY (FILM_ID),
                                     CONSTRAINT FILMS_RATING_ID_FK FOREIGN KEY (RATING_ID) REFERENCES RATING(RATING_ID)
);


CREATE TABLE IF NOT EXISTS GENRES (
                                      GENRE_ID INTEGER NOT NULL AUTO_INCREMENT,
                                      NAME VARCHAR_IGNORECASE NOT NULL,
                                      CONSTRAINT GENRES_PK PRIMARY KEY (GENRE_ID)
);

CREATE TABLE IF NOT EXISTS FILM_GENRE (
                                          FILM_ID INTEGER NOT NULL,
                                          GENRE_ID INTEGER NOT NULL,
                                          CONSTRAINT FILM_GENRE_FILM_ID_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(FILM_ID),
                                          CONSTRAINT FILM_GENRE_GENRE_ID_FK FOREIGN KEY (GENRE_ID) REFERENCES GENRES(GENRE_ID)
);

CREATE TABLE IF NOT EXISTS USERS (
                                     USER_ID INTEGER NOT NULL AUTO_INCREMENT,
                                     EMAIL VARCHAR_IGNORECASE NOT NULL,
                                     LOGIN VARCHAR_IGNORECASE NOT NULL,
                                     NAME VARCHAR_IGNORECASE,
                                     BIRTHDAY DATE,
                                     CONSTRAINT USERS_USER_ID_PK PRIMARY KEY (USER_ID)
);


CREATE TABLE IF NOT EXISTS LIKES (
                                     FILM_ID INTEGER NOT NULL,
                                     USER_ID INTEGER NOT NULL,
                                     CONSTRAINT LIKES_FILM_ID_USER_ID_PK PRIMARY KEY (FILM_ID,USER_ID) ,
                                     CONSTRAINT LIKES_FILM_ID_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(FILM_ID),
                                     CONSTRAINT LIKES_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID)

);

CREATE TABLE IF NOT EXISTS FRIENDS (
                                       USER_ID INTEGER NOT NULL,
                                       FRIEND_ID INTEGER NOT NULL,
                                       FRIENDS_STATUS BOOLEAN DEFAULT false NOT NULL,
                                       CONSTRAINT FRIENDS_USER_ID_FRIEND_ID_PK PRIMARY KEY (USER_ID,FRIEND_ID),
                                       CONSTRAINT FRIENDS_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID),
                                       CONSTRAINT FRIENDS_FRIEND_ID_FK FOREIGN KEY (FRIEND_ID) REFERENCES USERS(USER_ID)
);
