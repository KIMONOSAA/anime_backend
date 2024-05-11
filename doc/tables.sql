-- auto-generated definition
create table _user
(
    id          uuid         not null
        primary key,
    create_time timestamp(6) not null,
    email       varchar(255),
    is_delete   integer,
    is_enabled  boolean      not null,
    password    varchar(255),
    role        varchar(255)
        constraint _user_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[])),
    update_time timestamp(6),
    username    varchar(255)
);

alter table _user
    owner to postgres;

-- auto-generated definition
create table comment
(
    comment_id  bigserial
        primary key,
    context     varchar(255),
    create_time timestamp(6) not null,
    created_at  timestamp(6),
    is_delete   integer default 0,
    update_time timestamp(6),
    parent_id   bigint
        constraint fkde3rfu96lep00br5ov0mdieyt
            references comment,
    user_id     uuid
        constraint fkoo5phijy22unidgkw0sipcs74
            references _user,
    video_id    uuid
        constraint fkbsuh08kv1lyh8v6ivufrollr6
            references video,
    url_id      bigint
        constraint fka70qtxm025yrlmildp3cr2scu
            references video_url
);

alter table comment
    owner to postgres;

-- auto-generated definition
create table post
(
    id             bigserial
        primary key,
    age            integer,
    contact        varchar(255),
    content        varchar(255),
    create_time    timestamp(6) not null,
    gender         integer,
    is_delete      integer,
    job            varchar(255),
    photo          varchar(255),
    place          varchar(255),
    review_message varchar(255),
    review_status  integer,
    thumb_num      integer,
    title          varchar(255),
    update_time    timestamp(6),
    user_id        uuid,
    view_num       integer
);

alter table post
    owner to postgres;

-- auto-generated definition
create table scoring
(
    id          bigserial
        primary key,
    comment     varchar(255),
    create_time timestamp(6)     not null,
    is_delete   integer,
    score       double precision not null,
    update_time timestamp(6),
    user_id     uuid
        constraint fkkj6vy67lsk1p2dm1t6mf3uy31
            references _user,
    video_id    uuid
        constraint fkjpc7h99cxxj027cdbgp2uhvfw
            references video
);

alter table scoring
    owner to postgres;

-- auto-generated definition
create table token
(
    id      bigserial
        primary key,
    expired boolean not null,
    revoked boolean not null,
    token   varchar(255)
        constraint uk_pddrhgwxnms2aceeku9s2ewy5
            unique,
    type    varchar(255)
        constraint token_type_check
            check ((type)::text = 'BEARER'::text),
    user_id uuid
        constraint fkiblu4cjwvyntq3ugo31klp1c6
            references _user
);

alter table token
    owner to postgres;

-- auto-generated definition
create table user_file
(
    file_id     bigserial
        primary key,
    avatar      oid,
    create_time timestamp(6) not null,
    is_delete   integer,
    update_time timestamp(6),
    user_id     uuid
        constraint uk_polh11imju6b0w34orybum365
            unique
        constraint fk2ynd7gwiel8vudxy2iy1cq006
            references _user
);

alter table user_file
    owner to postgres;

-- auto-generated definition
create table user_info
(
    id          bigserial
        primary key,
    create_time timestamp(6) not null,
    date        date,
    description varchar(255),
    is_delete   integer,
    name        varchar(255),
    sex         varchar(255),
    update_time timestamp(6),
    user_id     uuid
        constraint uk_hixwjgx0ynne0cq4tqvoawoda
            unique
        constraint fkqcx2oa67st4b10dp7roxg7myq
            references _user
);

alter table user_info
    owner to postgres;

-- auto-generated definition
create table video
(
    video_id                  uuid             not null
        primary key,
    video_alias               varchar(255),
    video_anime_company       varchar(255),
    anime_type                varchar(255),
    video_comic_book_author   varchar(255),
    create_time               timestamp(6)     not null,
    video_date                varchar(255),
    video_description         varchar(255),
    video_director_name       varchar(255),
    hot_top                   double precision not null,
    is_delete                 integer,
    video_name                varchar(255),
    video_num_ber_of_sections integer,
    video_photo               oid,
    total_score               double precision,
    video_type                varchar(255),
    update_time               timestamp(6),
    videouv                   bigint,
    video_voice_actor         varchar(255)
);

alter table video
    owner to postgres;

-- auto-generated definition
create table video_url
(
    id             bigserial
        primary key,
    create_time    date not null,
    is_delete      integer,
    update_time    date,
    url            varchar(1024),
    video_video_id uuid
        constraint fkhbltmxv034cs0kl6u39vxyn92
            references video,
    section        integer
);

alter table video_url
    owner to postgres;

