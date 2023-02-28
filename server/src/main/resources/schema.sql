create table if not exists USERS
(
    USER_ID BIGINT generated by default as identity not null,
    NAME    VARCHAR(255) NOT NULL,
    EMAIL   VARCHAR(200) NOT NULL,
    constraint UQ_USER_EMAIL UNIQUE (EMAIL),
    constraint USERS_PK
        primary key (USER_ID)
);

create table if not exists REQUESTS
(
    ID           BIGINT generated by default as identity not null,
    DESCRIPTION  CHARACTER VARYING,
    REQUESTOR_ID BIGINT not null,
    CREATED  TIMESTAMP WITHOUT TIME ZONE not null,
    constraint REQUESTS_PK
        primary key (ID),
    constraint REQUESTS_USERS_USER_ID_FK
        foreign key (REQUESTOR_ID) references USERS
            on update cascade on delete cascade
);

create table if not exists ITEMS
(
    ITEM_ID     BIGINT generated by default as identity not null,
    NAME        CHARACTER VARYING(255) not null,
    DESCRIPTION CHARACTER VARYING(500) not null,
    AVAILABLE   BOOLEAN                not null,
    OWNER_ID    BIGINT                 not null,
    REQUEST_ID  BIGINT,
    constraint ITEMS_PK
        primary key (ITEM_ID),
    constraint ITEMS_REQUESTS_ID_FK
        foreign key (REQUEST_ID) references REQUESTS,
    constraint ITEMS_USERS_USER_ID_FK
        foreign key (OWNER_ID) references USERS
            on update cascade on delete cascade
);

create table if not exists BOOKINGS
(
    ID BIGINT generated by default as identity     not null,
    ITEM_ID    BIGINT                              not null,
    OWNER_ID   BIGINT                              not null,
    START_TIME  TIMESTAMP WITHOUT TIME ZONE        not null,
    END_TIME  TIMESTAMP WITHOUT TIME ZONE          not null,
    STATUS     CHARACTER VARYING                   not null,
    USER_ID    BIGINT                              not null,
    constraint BOOKINGS_PK
        primary key (ID),
    constraint BOOKINGS_ITEMS_ITEM_ID_FK
        foreign key (ITEM_ID) references ITEMS
            on update cascade on delete cascade,
    constraint BOOKINGS_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
            on update cascade on delete cascade,
    constraint BOOKINGS_USERS_USER_ID_FK_2
        foreign key (OWNER_ID) references USERS
            on update cascade on delete cascade
);

create table if not exists COMMENTS
(
    ID        BIGINT generated by default as identity not null,
    TEXT      CHARACTER VARYING not null,
    ITEM_ID   BIGINT            not null,
    AUTHOR_ID BIGINT            not null,
    CREATED  TIMESTAMP WITHOUT TIME ZONE        not null,
    constraint COMMENTS_PK
        primary key (ID),
    constraint COMMENTS_ITEMS_ITEM_ID_FK
        foreign key (ITEM_ID) references ITEMS
            on update cascade on delete cascade,
    constraint COMMENTS_USERS_USER_ID_FK
        foreign key (AUTHOR_ID) references USERS
            on update cascade on delete cascade
);