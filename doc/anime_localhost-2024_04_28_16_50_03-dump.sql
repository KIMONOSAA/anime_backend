--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: _user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public._user (
    is_enabled boolean NOT NULL,
    id bigint NOT NULL,
    email character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255),
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    create_time timestamp(6) without time zone,
    is_delete integer,
    update_time timestamp(6) without time zone,
    CONSTRAINT _user_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public._user OWNER TO postgres;

--
-- Name: COLUMN _user."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public._user."createTime" IS '创建时间';


--
-- Name: COLUMN _user."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public._user."updateTime" IS '更新时间';


--
-- Name: COLUMN _user."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public._user."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: _user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public._user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public._user_id_seq OWNER TO postgres;

--
-- Name: _user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public._user_id_seq OWNED BY public._user.id;


--
-- Name: _user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public._user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public._user_seq OWNER TO postgres;

--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    comment_id bigint NOT NULL,
    context character varying(255),
    created_at timestamp(6) without time zone,
    parent_id bigint,
    user_id bigint,
    video_id uuid,
    url_id bigint,
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    create_time timestamp(6) without time zone,
    is_delete integer,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.comment OWNER TO postgres;

--
-- Name: COLUMN comment."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment."createTime" IS '创建时间';


--
-- Name: COLUMN comment."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment."updateTime" IS '更新时间';


--
-- Name: COLUMN comment."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.comment."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: comment_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comment_comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comment_comment_id_seq OWNER TO postgres;

--
-- Name: comment_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comment_comment_id_seq OWNED BY public.comment.comment_id;


--
-- Name: scoring; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scoring (
    id bigint NOT NULL,
    comment character varying(255),
    create_time timestamp(6) without time zone,
    is_delete integer,
    score double precision,
    update_time timestamp(6) without time zone,
    user_id bigint,
    video_id uuid
);


ALTER TABLE public.scoring OWNER TO postgres;

--
-- Name: scoring_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.scoring_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.scoring_id_seq OWNER TO postgres;

--
-- Name: scoring_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.scoring_id_seq OWNED BY public.scoring.id;


--
-- Name: token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.token (
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    id bigint NOT NULL,
    user_id bigint,
    token character varying(255),
    type character varying(255),
    CONSTRAINT token_type_check CHECK (((type)::text = 'BEARER'::text))
);


ALTER TABLE public.token OWNER TO postgres;

--
-- Name: token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.token_id_seq OWNER TO postgres;

--
-- Name: token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.token_id_seq OWNED BY public.token.id;


--
-- Name: user_file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_file (
    file_id bigint NOT NULL,
    user_id bigint,
    avatar oid,
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    create_time timestamp(6) without time zone,
    is_delete integer,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.user_file OWNER TO postgres;

--
-- Name: COLUMN user_file."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_file."createTime" IS '创建时间';


--
-- Name: COLUMN user_file."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_file."updateTime" IS '更新时间';


--
-- Name: COLUMN user_file."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_file."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: user_file_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_file_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_file_file_id_seq OWNER TO postgres;

--
-- Name: user_file_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_file_file_id_seq OWNED BY public.user_file.file_id;


--
-- Name: user_file_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_file_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_file_seq OWNER TO postgres;

--
-- Name: user_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_info (
    date date,
    id bigint NOT NULL,
    user_id bigint,
    description character varying(255),
    name character varying(255),
    sex character varying(255),
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    create_time timestamp(6) without time zone,
    is_delete integer,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.user_info OWNER TO postgres;

--
-- Name: COLUMN user_info."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_info."createTime" IS '创建时间';


--
-- Name: COLUMN user_info."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_info."updateTime" IS '更新时间';


--
-- Name: COLUMN user_info."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.user_info."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: user_info_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_info_id_seq OWNER TO postgres;

--
-- Name: user_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_info_id_seq OWNED BY public.user_info.id;


--
-- Name: video; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.video (
    "video-num_ber_of_sections" integer,
    video_id uuid NOT NULL,
    "anime-type" character varying(255),
    "video-alias" character varying(255),
    "video-anime_company" character varying(255),
    "video-comic_book_author" character varying(255),
    "video-date" character varying(255),
    "video-description" character varying(255),
    "video-director_name" character varying(255),
    "video-name" character varying(255),
    "video-photo" oid,
    "video-type" character varying(255),
    "video-video_data" oid,
    "video-voice_actor" character varying(255),
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    videouv bigint DEFAULT 0,
    is_delete integer,
    score_num double precision,
    hot_top double precision,
    total_score double precision,
    create_time timestamp(6) without time zone,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.video OWNER TO postgres;

--
-- Name: COLUMN video."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video."createTime" IS '创建时间';


--
-- Name: COLUMN video."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video."updateTime" IS '更新时间';


--
-- Name: COLUMN video."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: COLUMN video.videouv; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video.videouv IS '统计次数';


--
-- Name: video_url; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.video_url (
    id bigint NOT NULL,
    video_video_id uuid,
    url character varying(255),
    video_url_id bigint NOT NULL,
    video_url character varying(255),
    "createTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updateTime" timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "isDelete" smallint DEFAULT 0 NOT NULL,
    create_time timestamp(6) without time zone,
    is_delete integer,
    update_time timestamp(6) without time zone
);


ALTER TABLE public.video_url OWNER TO postgres;

--
-- Name: COLUMN video_url."createTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video_url."createTime" IS '创建时间';


--
-- Name: COLUMN video_url."updateTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video_url."updateTime" IS '更新时间';


--
-- Name: COLUMN video_url."isDelete"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN public.video_url."isDelete" IS '是否删除0-未删,1-已删';


--
-- Name: video_url_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.video_url_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.video_url_id_seq OWNER TO postgres;

--
-- Name: video_url_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.video_url_id_seq OWNED BY public.video_url.id;


--
-- Name: video_url_video_url_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.video_url_video_url_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.video_url_video_url_id_seq OWNER TO postgres;

--
-- Name: video_url_video_url_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.video_url_video_url_id_seq OWNED BY public.video_url.video_url_id;


--
-- Name: _user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public._user ALTER COLUMN id SET DEFAULT nextval('public._user_id_seq'::regclass);


--
-- Name: comment comment_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment ALTER COLUMN comment_id SET DEFAULT nextval('public.comment_comment_id_seq'::regclass);


--
-- Name: scoring id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scoring ALTER COLUMN id SET DEFAULT nextval('public.scoring_id_seq'::regclass);


--
-- Name: token id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token ALTER COLUMN id SET DEFAULT nextval('public.token_id_seq'::regclass);


--
-- Name: user_file file_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file ALTER COLUMN file_id SET DEFAULT nextval('public.user_file_file_id_seq'::regclass);


--
-- Name: user_info id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info ALTER COLUMN id SET DEFAULT nextval('public.user_info_id_seq'::regclass);


--
-- Name: video_url id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url ALTER COLUMN id SET DEFAULT nextval('public.video_url_id_seq'::regclass);


--
-- Name: video_url video_url_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url ALTER COLUMN video_url_id SET DEFAULT nextval('public.video_url_video_url_id_seq'::regclass);


--
-- Data for Name: _user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public._user (is_enabled, id, email, password, role, username, "createTime", "updateTime", "isDelete", create_time, is_delete, update_time) FROM stdin;
f	4	3248034755@qq.com	$2a$10$bRXSifW0AlHqeN5JdfhB4uTE5Zcs1Mhhvxeb/Wpr/m61sPiQald/q	USER	kkkkkkk	2024-04-22 01:19:43.649404	2024-04-22 01:19:43.771295	0	\N	\N	\N
t	5	garntoleana@gmail.com	$2a$10$7FAmmXQY1Q5irrNwN6FzL.8PposYLig7gv9gAnCGMZ90WP6bgzqga	USER	kimo	2024-04-26 09:44:30.421532	2024-04-26 09:44:30.421532	0	\N	\N	\N
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comment (comment_id, context, created_at, parent_id, user_id, video_id, url_id, "createTime", "updateTime", "isDelete", create_time, is_delete, update_time) FROM stdin;
\.


--
-- Data for Name: scoring; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scoring (id, comment, create_time, is_delete, score, update_time, user_id, video_id) FROM stdin;
1	这个动漫非常的好看，值得花时间去看看！	\N	\N	4	\N	5	7c52cda3-547c-4661-ba03-d92442173c23
\.


--
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.token (expired, revoked, id, user_id, token, type) FROM stdin;
t	t	31	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQyOTA5MzAsImV4cCI6MTcxNDM3NzMzMH0.Qjs51roD6ObVj59kcd5TlH9FFxRrzCr44Z-dqJ2ROHg	BEARER
t	t	32	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQyOTA5ODAsImV4cCI6MTcxNDM3NzM4MH0.4YA2NWKijPxeDYx1zg5jSpc-EZiCk6YmO58h4wci43E	BEARER
t	t	33	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQyOTEwMTMsImV4cCI6MTcxNDM3NzQxM30.-pRcMm1MOZbdF9VaCJ-39zDZ_X4w1ZxDGI6XYbuc_fs	BEARER
f	f	34	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQyOTE2NTcsImV4cCI6MTcxNDM3ODA1N30.cRxUgEVodOC1wOB6SpZjc5hepADpRGP3lmzNVnethaw	BEARER
t	t	28	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQwOTYzNDIsImV4cCI6MTcxNDE4Mjc0Mn0.r6tWLpydj7pzW15WJT5xrm4pjgCoJQI0d40-8oB5ZeU	BEARER
t	t	29	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQwOTY2OTQsImV4cCI6MTcxNDE4MzA5NH0.y6o6bpXGNslv7FasBqiTmw7wfixAFPjs5YqdCQg0I0U	BEARER
t	t	30	5	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQxOTc1MjUsImV4cCI6MTcxNDI4MzkyNX0.IpRPwcCWAUdJ5vFhcI8GeiurNCyDactWRxQocCqwVK0	BEARER
\.


--
-- Data for Name: user_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_file (file_id, user_id, avatar, "createTime", "updateTime", "isDelete", create_time, is_delete, update_time) FROM stdin;
2	5	30281	2024-04-26 09:58:37.168324	2024-04-26 09:58:37.168324	0	\N	\N	\N
\.


--
-- Data for Name: user_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_info (date, id, user_id, description, name, sex, "createTime", "updateTime", "isDelete", create_time, is_delete, update_time) FROM stdin;
2024-04-27	2	5	hahaha	kimos	女	2024-04-26 09:58:54.860942	2024-04-26 09:58:54.860942	0	\N	\N	\N
\.


--
-- Data for Name: video; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video ("video_num_ber_of_sections", video_id, "anime_type", "video_alias", "video_anime_company", "video_comic_book_author", "video_date", "video_description", "video_director_name", "video_name", "video_photo", "video_type", "video_video_data", "video_voice_actor", "createTime", "updateTime", "isDelete", videouv, is_delete, score_num, hot_top, total_score, create_time, update_time) FROM stdin;
24	8b0b9a29-193d-4cba-9410-518b11211ddd	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。\r\n	长沼范裕	药屋少女的呢喃第一季	30589	[治愈, 推理]	\N	[悠木碧, 大塚刚央, 种﨑敦美, 小西克幸]	2024-04-26 11:16:32.899512	2024-04-26 11:16:32.899512	0	0	\N	\N	0	\N	\N	\N
12	974796b8-bdc5-49c2-b99c-3d3560c73d5c	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30613	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.408447	2024-04-26 11:36:10.408447	0	0	\N	\N	0	\N	\N	\N
12	53f3f4de-29fe-4a22-9537-68ed512a808e	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30634	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:14.750719	2024-04-26 11:36:14.750719	0	0	\N	\N	0	\N	\N	\N
12	6d3790d8-9381-41de-ac86-81517516927c	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30649	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:17.934754	2024-04-26 11:36:17.934754	0	0	\N	\N	0	\N	\N	\N
12	9f98d009-fb62-459b-ad10-e3dfaf4a82c6	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30636	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:15.18883	2024-04-26 11:36:15.18883	0	0	\N	\N	0	\N	\N	\N
12	16b6c041-2e9a-474b-8e38-baaf2ab356b8	日漫剧场	为美好的世界献上祝福	Studio DEEN	暁なつめ	2024	热爱游戏的家里蹲少年·佐藤和真的人生，因交通事故而轻易闭幕……本该是这样，但当他醒来之时，眼前有一位自称是女神的美少女。“喂，我有点好事要告诉你。要去异世界吗？只带一样你喜欢的东西没问题喔。”“那，我就带着你好了。”由此开始，在异世界转生的和真的魔王讨伐大冒险开始了……虽然是这么想的，但他却为了获得衣食住行而开始劳动。想要平稳度日的和真，却由于女神引起的各种问题，而终于被魔王军盯上了	昼熊	为美好的世界献上祝福第三季	30585	[搞笑, 异世界]	\N	[福岛润, 雨宫天, 高桥李依, 茅野爱衣, 原纱友里]	2024-04-26 10:35:30.338486	2024-04-26 10:35:30.338486	0	0	\N	\N	0	\N	\N	\N
12	769cbdac-7537-4e69-8443-63aa348178e4	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30653	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.588511	2024-04-26 11:36:16.588511	0	0	\N	\N	0	\N	\N	\N
12	c2200171-2218-4d4e-a969-e1dc5b3c8e42	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30633	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:14.552407	2024-04-26 11:36:14.552407	0	0	\N	\N	0	\N	\N	\N
12	de5f5e64-ed39-4a5d-a29d-176b7b7b6971	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30626	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.045089	2024-04-26 11:36:13.045089	0	0	\N	\N	0	\N	\N	\N
12	ef18d5ef-4880-47b6-9870-90a9fc2230da	日漫番剧	想要成为影之实力者	Nexus	逢泽大介	2023	少年希德憧憬着以路人身份隐藏自身力量，不为人知地介入故事，展现实力的“影之实力者”。转生到异世界后，企图充分享受这种设定的希德，为了击溃妄想中的敌人“黑暗教团”而暗中大显身手。然而，这个教团似乎真的存在。同时，因为他顺势收为部下的少女们“会错意”了，希德在一无所知的情况下成了真正的“影之实力者”，一行人建立起来的“暗影庭园”将歼灭这世上的黑暗	加藤还一	想要成为影之实力者第二季	30584	[战斗, 后宫, 异世界, 装逼]	\N	[山下誠一郎, 日高里菜, 早見沙織, 伊藤静]	2024-04-26 11:13:01.128523	2024-04-26 11:13:01.128523	0	0	\N	\N	0	\N	\N	\N
12	a962df43-4266-4b5c-a2c5-b8923d243c48	日漫番剧	欢迎来到实力至上主义的教室	Lerche	衣笠彰梧	2024	东京都高度育成高等学校、それは进学率・就职率100％を夸り、毎月10万円の金銭に相当するポイントが支给される梦のような学校。しかし、その内実は一部の成绩优秀者のみが好待遇を受けられる実力至上主义の学校であった。3学期を迎え、DクラスからCクラスに昇格した綾小路たちは、林间学校へと向かう。そこで実施されるのは「混合合宿」と呼ばれる全学年合同で行われる特別试験。	仁昌寺义人	欢迎来到实力至上主义的教室第三季	30588	[校园, 推理, 后宫]	\N	[鬼头明里, 千叶翔也, 久保ユリカ, 东山奈央]	2024-04-26 11:04:18.400362	2024-04-26 11:04:18.400362	0	0	\N	\N	0	\N	\N	\N
12	46b8bef0-d44d-4432-a283-d2611e1abba9	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	测试	kimo	为美好的世界献上祝福第三季	30659	[kimo]	\N	[kimo]	2024-04-28 14:12:01.296605	2024-04-28 14:12:01.296605	0	\N	\N	\N	0	\N	\N	\N
12	4388f68d-a01a-477c-9ff9-31470bddd537	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	ces	kimo	为美好的世界献上祝福第三季	30660	[kimo]	\N	[kimo]	2024-04-28 14:15:03.715735	2024-04-28 14:15:03.715735	0	\N	\N	\N	0	\N	\N	\N
12	1475e1b8-97ae-4c8a-a8da-fa3615c52de5	asdas	asd	asouli	kimo	2024	asdasd	kimo	asda	30661	[asdas]	\N	[asdas]	2024-04-28 14:15:52.854444	2024-04-28 14:15:52.854444	0	\N	\N	\N	0	\N	\N	\N
12	34e4d967-dad5-43c6-be62-b7c9f543988d	日漫番剧	愚蠢天使与恶魔共舞	童园娱乐	东泽良	2024	天界からの攻势に抗うために、 魔界を鼓舞するカリスマとなりうる人间をスカウトしに 人间界へとやってきた悪魔「阿久津雅虎」。 彼は正体を隠し転入した学校で、可怜な美少女「天音リリー」と出会う。 早速リリーを勧诱する阿久津だったが、リリーの正体は悪魔の天敌「天使」で…！？ 魔界を守るためにリリーを堕天させようとする阿久津と、 とある目的のために阿久津を更生させようとするリリー。 絶対にアイツを堕天/更生おとしてやる…！ 天使と悪魔の ｢オトしあい｣バトル、开幕―！	川崎逸朗	愚蠢天使与恶魔共舞	30592	[恋爱, 搞笑, 校园]	\N	[内田雄马, 佐仓綾音, 梅田修一朗, 土岐隼一]	2024-04-26 11:20:35.374515	2024-04-26 11:20:35.374515	0	0	\N	\N	0	\N	\N	\N
12	c69d7450-ed5a-4da6-a373-1ab42229456f	日漫番剧	gf	qwdfq	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30593	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:07.911899	2024-04-26 11:36:07.911899	0	0	\N	\N	0	\N	\N	\N
12	0addff98-5c39-4e13-9cc2-d03bf88dcc75	日漫番剧	vbgvf	qwd	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30594	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.100168	2024-04-26 11:36:08.100168	0	0	\N	\N	0	\N	\N	\N
24	7c52cda3-547c-4661-ba03-d92442173c23	日漫番剧	葬送的芙莉莲	MADHOUSE	山田钟人（原作）、阿部司（作画）	2024	故事背景设定于存在着魔法与奇幻生物的虚构世界，讲述勇者一行人打倒魔王之后，勇者一行人中精灵魔法使芙莉莲的故事。\r\n	斋藤圭一郎	葬送的芙莉莲	30596	[奇幻,冒险,异世界]	21988	[种崎敦美,市之濑加那,小林千晃,清都亚理沙,中村悠一,川井田夏海,冈本信彦,东地宏树,上田燿司]	2024-04-22 01:24:42.14079	2024-04-22 01:24:42.186038	0	100	\N	\N	0	\N	\N	\N
12	c8395c2a-2e9a-4686-82f4-f7cc095282ca	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30628	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.399147	2024-04-26 11:36:13.399147	0	0	\N	\N	0	\N	\N	\N
12	0a05ede3-c41d-43b3-a501-77e09f57e5e5	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30635	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:14.968813	2024-04-26 11:36:14.968813	0	0	\N	\N	0	\N	\N	\N
12	45b10a01-3c40-4c4a-8574-74edcf3c2d3f	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	asd	kimo	为美好的世界献上祝福第三季	30662	[asdas]	\N	[adsad]	2024-04-28 14:17:24.097903	2024-04-28 14:17:24.097903	0	\N	\N	\N	0	\N	\N	\N
12	abd897ee-a038-4b87-bf69-c59ffc53a46b	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30598	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:09.532535	2024-04-26 11:36:09.532535	0	0	\N	\N	0	\N	\N	\N
12	cc36c1c0-55c6-43fa-bcd5-027fe7baa8d0	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30599	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:11.696233	2024-04-26 11:36:11.696233	0	0	\N	\N	0	\N	\N	\N
12	efea0ac7-de7a-4459-9436-75ab36eb9917	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30600	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.973306	2024-04-26 11:36:13.973306	0	0	\N	\N	0	\N	\N	\N
12	55b9b59e-ae58-4af6-aa03-8f1c90cde2c7	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30601	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.392858	2024-04-26 11:36:16.392858	0	0	\N	\N	0	\N	\N	\N
12	7fe633e1-9b26-42ec-b0fe-54c9ac5b6084	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30632	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:14.3673	2024-04-26 11:36:14.3673	0	0	\N	\N	0	\N	\N	\N
12	38fa6a93-f626-4028-ac04-be7309952d36	日漫番剧	公主大人“拷问”的时间到了	PINE JAM	春原罗宾逊	2024	国王军と魔王军が衝突をはじめ、几年月。 王女にして、国王军第三骑士団“骑士団长”である姫は、魔王军によって囚われの身となっていた。 ｢姫様“拷问”の时间です｣ 监禁された姫を待ち受けていたのは、身闷えるような“拷问”の数々……。 ほわほわの焼きたてトースト！ 汤気がたちのぼる深夜のラーメン！ 爱くるしい动物たちと游ぶ时间！ 美味しい食事＆楽しい游びを容赦なく突きつけられた姫は、 “拷问”に打ち胜ち王国の秘密を守り抜くことができるのか！？ 谁もが笑颜になれる世界一やさしい “拷问”ファンタジーアニメ开幕！	金森阳子	公主大人“拷问”的时间到了	30597	[搞笑, 异世界]	\N	[白石晴香, 小林亲弘, 伊藤静]	2024-04-26 10:59:04.922233	2024-04-26 10:59:04.922233	0	0	\N	\N	0	\N	\N	\N
25	180775af-d8f8-402f-9523-a058c20a7956	日漫番剧	从零开始的异世界生活	WHITE FOX	长月达平	2020	我一定会拯救你。菜月昴打倒了魔女教大罪司教「怠惰」担当的贝特鲁吉乌斯·罗曼尼康帝，并与爱蜜莉雅重逢。两人在经历苦涩的诀别后终于和解，但那却是新的波澜的开端。超乎想像、走投无路的危机，以及无情袭来的	川村贤一,土屋浩幸,小泽一浩,美甘义人,武市直子,渡边政治	 RE：从零开始的异世界生活 第二季	30590	[异世界, 恋爱, 奇幻]	\N	[坂本真绫, 石田彰, 子安武人, 中村悠一]	2024-04-26 11:08:16.895376	2024-04-26 11:08:16.895376	0	0	\N	\N	0	\N	\N	\N
12	ae7b88f2-fc37-441f-ba37-6d50fe6c3e9f	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30604	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:09.144358	2024-04-26 11:36:09.144358	0	0	\N	\N	0	\N	\N	\N
12	3dcdfd7c-c2b2-4d72-bfbf-52ca703e164b	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30619	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:11.528791	2024-04-26 11:36:11.528791	0	0	\N	\N	0	\N	\N	\N
12	6ca09252-d0b5-40e8-927f-9e7bfab5119e	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30638	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:15.614569	2024-04-26 11:36:15.614569	0	0	\N	\N	0	\N	\N	\N
12	e37878b7-75e0-4265-8d8f-70edb0f943a0	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30640	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.006415	2024-04-26 11:36:16.006415	0	0	\N	\N	0	\N	\N	\N
12	9ea62b4d-9aef-4342-9805-db9533734242	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30647	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:17.557853	2024-04-26 11:36:17.557853	0	0	\N	\N	0	\N	\N	\N
12	8d91c99e-fa32-4f98-9c49-e069f20b86de	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30620	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:09.72915	2024-04-26 11:36:09.72915	0	0	\N	\N	0	\N	\N	\N
12	6ddcfd1d-e73a-407b-9305-c364a49610d3	日漫剧场	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30655	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:18.872091	2024-04-26 11:36:18.872091	0	0	\N	\N	0	\N	\N	\N
12	71856c19-7e19-4cc3-a2df-21e72f9b22f7	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	asdasda	kimo	为美好的世界献上祝福第三季	30663	[asdasd]	\N	[asdas]	2024-04-28 14:20:41.316357	2024-04-28 14:20:41.316357	0	\N	\N	\N	0	\N	\N	\N
12	549c1391-f276-484c-aa87-6f29d970595f	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	asdasda	kimo	为美好的世界献上祝福第三季	30664	[]	\N	[]	2024-04-28 14:22:42.093563	2024-04-28 14:22:42.093563	0	\N	\N	\N	0	\N	\N	\N
12	33f27cdd-e49b-476f-a407-51345dc100ee	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	12	kimo	为美好的世界献上祝福第三季	30665	[请问请问]	\N	[我去]	2024-04-28 14:26:54.681879	2024-04-28 14:26:54.681879	0	\N	\N	\N	0	\N	\N	\N
12	d089f1ab-6045-4aab-a484-f8794481e250	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	请问请问	kimo	为美好的世界献上祝福第三季	30666	[我去恶趣味]	\N	[请问]	2024-04-28 14:30:21.921336	2024-04-28 14:30:21.921336	0	\N	\N	\N	0	\N	\N	\N
12	8eeb6ac3-df3b-4689-a0e9-b52af0a1970f	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	sadasd	kimo	为美好的世界献上祝福第三季	30667	[阿萨达]	\N	[阿达撒]	2024-04-28 14:30:53.52049	2024-04-28 14:30:53.52049	0	\N	\N	\N	0	\N	\N	\N
12	3ab67621-9e9d-44cb-89d2-cb82e085dbc6	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	请问	kimo	为美好的世界献上祝福第三季	30668	[请问]	\N	[请问]	2024-04-28 14:35:59.849352	2024-04-28 14:35:59.849352	0	\N	\N	\N	0	\N	\N	\N
12	d3cf1606-0edd-42c2-a6bf-ae03c7f60f36	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	请问	kimo	为美好的世界献上祝福第三季	30669	[驱蚊器]	\N	[驱蚊器]	2024-04-28 14:37:35.773605	2024-04-28 14:37:35.773605	0	\N	\N	\N	0	\N	\N	\N
12	221182cf-0a1e-4a7e-b117-2ff9b9b9673c	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨达	kimo	为美好的世界献上祝福第三季	30670	[啊撒大声地]	\N	[啊实打实]	2024-04-28 14:39:03.856002	2024-04-28 14:39:03.856002	0	\N	\N	\N	0	\N	\N	\N
12	e20baec4-6668-430c-98e6-430a404b20c1	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30646	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:17.380343	2024-04-26 11:36:17.380343	0	0	\N	\N	0	\N	\N	\N
12	59755411-341e-4218-a25a-f866f3e6e412	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30629	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.607872	2024-04-26 11:36:13.607872	0	0	\N	\N	0	\N	\N	\N
12	93ca894e-4dc1-4612-983b-d3c9be95cf63	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30621	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:12.075881	2024-04-26 11:36:12.075881	0	0	\N	\N	0	\N	\N	\N
12	67b3c860-62cb-43b0-8dde-15cf74379d57	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30641	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.200655	2024-04-26 11:36:16.200655	0	0	\N	\N	0	\N	\N	\N
12	c1c9af07-e9fe-4269-bb51-cf8a076cddd4	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30614	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.595534	2024-04-26 11:36:10.595534	0	0	\N	\N	0	\N	\N	\N
12	05afa801-2ae7-4651-a072-ff8c1b54ba06	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30623	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:12.449293	2024-04-26 11:36:12.449293	0	0	\N	\N	0	\N	\N	\N
12	34606162-b52a-49db-8254-8c06617a886d	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30651	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:18.320598	2024-04-26 11:36:18.320598	0	0	\N	\N	0	\N	\N	\N
12	cdba43a5-0745-4570-8724-2fe6da2081ff	日漫番剧	偶像大师	Polygong pictures	加藤阳一	2024	2023年3月19日晚，万代南梦宫公布了动画《偶像大师：闪耀色彩》的TV动画化项目。计划于2024年春季在电视上播出。此外，在电视播出之前，将从2023年10月27日（星期五）开始进行剧场上映。	万久	偶像大师 闪耀色彩	30591	[偶像]	\N	[关根瞳, 近藤玲奈, 峯田茉优]	2024-04-26 10:44:43.543113	2024-04-26 10:44:43.543113	0	0	\N	\N	0	\N	\N	\N
12	3deb4ed1-2673-4f3e-a8e4-25b25c403ad7	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30617	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:11.150365	2024-04-26 11:36:11.150365	0	0	\N	\N	0	\N	\N	\N
12	7c89e6f9-e641-4100-ae4b-c9eae6a37454	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30650	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:18.130425	2024-04-26 11:36:18.130425	0	0	\N	\N	0	\N	\N	\N
26	1674fa68-60e7-44fd-84ef-383635921150	日漫番剧	月光下的异世界之旅	C2C	あずみ圭	2024	曾经因为父母的关系被召唤到异世界的男高中生深澄真，本应作为勇者与魔族战斗。 但是，他被作为召唤主的女神骂道“太丑了”，勇者的称号立即被剥夺。 他被扔到最边远的荒野。 被打上勇者失格烙印的真，在荒野中徘徊，与成为自己随从的上位龙种“蜃”巴，再加上“灾害的黑蜘蛛”澪，开始了异世界重建之旅。	石平信司	月光下的异世界之旅第二季	30587	[异世界, 后宫, 战斗]	\N	[花江夏树, 佐仓綾音, 鬼头明里, 早见沙织, 辻亲八]	2024-04-26 10:56:03.728987	2024-04-26 10:56:03.728987	0	0	\N	\N	0	\N	\N	\N
12	c9bff1ac-c6b5-4770-b73e-5bc8f26110e1	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30627	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.222181	2024-04-26 11:36:13.222181	0	0	\N	\N	0	\N	\N	\N
12	5c3455f7-fd82-467a-baeb-2ee49a03aaaa	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30622	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:12.251544	2024-04-26 11:36:12.251544	0	0	\N	\N	0	\N	\N	\N
12	fcfb5f9b-333e-49af-8c72-f37ea5e8c5a5	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30652	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:18.507436	2024-04-26 11:36:18.507436	0	0	\N	\N	0	\N	\N	\N
12	042e93e4-1e06-417e-ac51-306fa43c201f	日漫番剧	我独自升级	A-1 Pictures	Chugong	2024	自从连接异次元与当前世界的通道“门”突然出现后已过去十余年，世界上出现了被称为“猎人”的，觉醒了非凡力量的人们。猎人在门内攻略地下城以获取回报以维持生计，但在强者如云的猎人中，水篠旬作为被称作人类最弱武器的低级猎人生活着。	木村畅	我独自升级	30583	[后宫, 战斗, 冒险]	\N	[坂泰斗, 中村源太, 三川华月, 上田丽奈, 平川大辅]	2024-04-26 11:10:49.336095	2024-04-26 11:10:49.336095	0	0	\N	\N	0	\N	\N	\N
12	a1119170-3fb6-4574-9929-fcabac8e4b06	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30625	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:12.848309	2024-04-26 11:36:12.848309	0	0	\N	\N	0	\N	\N	\N
12	9ca6a765-85b7-4330-8411-3d7d04937392	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30671	[阿萨德]	\N	[阿萨德]	2024-04-28 14:39:55.719376	2024-04-28 14:39:55.719376	0	\N	\N	\N	0	\N	\N	\N
12	952380e0-c67c-4f82-bcde-d0741268df13	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	驱蚊器	kimo	为美好的世界献上祝福第三季	30672	[请问]	\N	[请问]	2024-04-28 14:49:55.590227	2024-04-28 14:49:55.590227	0	\N	\N	\N	0	\N	\N	\N
12	ae845326-5314-4d6a-8b28-f9a3fe7ba871	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨达	kimo	为美好的世界献上祝福第三季	30673	[阿萨德]	\N	[阿萨德]	2024-04-28 14:51:12.400613	2024-04-28 14:51:12.400613	0	\N	\N	\N	0	\N	\N	\N
12	1bc643f4-2ea6-4199-b3be-7162a35e87a0	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨达	kimo	为美好的世界献上祝福第三季	30674	[阿萨德]	\N	[阿萨德]	2024-04-28 14:51:52.943993	2024-04-28 14:51:52.943993	0	\N	\N	\N	0	\N	\N	\N
12	bc68fec0-037f-4870-a0e5-0435ab577b1f	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	驱蚊器	kimo	为美好的世界献上祝福第三季	30675	[请问]	\N	[1231]	2024-04-28 14:56:12.499658	2024-04-28 14:56:12.499658	0	\N	\N	\N	0	\N	\N	\N
12	9a70baef-9578-426c-bbbe-8d13a4ae67e0	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30676	[啊实打实]	\N	[阿萨达]	2024-04-28 14:58:19.956383	2024-04-28 14:58:19.956383	0	\N	\N	\N	0	\N	\N	\N
12	9d7d93a7-5582-4755-98b9-1cda3c06ddd4	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	我去恶趣味	kimo	为美好的世界献上祝福第三季	30677	[]	\N	[请问]	2024-04-28 15:03:42.431835	2024-04-28 15:03:42.431835	0	\N	\N	\N	0	\N	\N	\N
12	ad96c91a-150b-4085-9b6f-e69e30e141c7	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	王企鹅	kimo	为美好的世界献上祝福第三季	30678	[阿萨德]	\N	[阿萨德]	2024-04-28 15:06:37.270395	2024-04-28 15:06:37.270395	0	\N	\N	\N	0	\N	\N	\N
12	0c3d50fe-89bb-47b4-a73d-ab633d5aa11e	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	请问	kimo	为美好的世界献上祝福第三季	30679	[阿萨德]	\N	[阿萨德]	2024-04-28 15:08:09.742449	2024-04-28 15:08:09.742449	0	\N	\N	\N	0	\N	\N	\N
12	7867e4ab-b9de-47cf-9379-fbb5970a49df	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	爱我的	kimo	为美好的世界献上祝福第三季	30680	[请问]	\N	[请问]	2024-04-28 15:08:47.670231	2024-04-28 15:08:47.670231	0	\N	\N	\N	0	\N	\N	\N
12	d6216355-e957-4897-aefa-d0f731be97a8	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30681	[阿萨德]	\N	[阿萨德]	2024-04-28 15:09:29.822583	2024-04-28 15:09:29.822583	0	\N	\N	\N	0	\N	\N	\N
12	5da71f24-e20c-467c-ade3-97ac8a875edb	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30682	[阿萨德]	\N	[阿萨德]	2024-04-28 15:14:55.966026	2024-04-28 15:14:55.966026	0	\N	\N	\N	0	\N	\N	\N
12	daf50cb9-b8af-4890-b0da-fb38fd341b71	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30602	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.623282	2024-04-26 11:36:08.623282	0	0	\N	\N	0	\N	\N	\N
12	cc68d63b-9a8a-48d9-8806-2e2b37f88e84	日漫番剧	ddfd	2	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30607	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:07.751722	2024-04-26 11:36:07.751722	0	0	\N	\N	0	\N	\N	\N
12	834dc0cc-7b09-40cd-aadb-2ce2a49a5106	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30611	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.064251	2024-04-26 11:36:10.064251	0	0	\N	\N	0	\N	\N	\N
12	87e3bc25-a34a-4e66-9668-e90016a41916	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30648	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:17.768787	2024-04-26 11:36:17.768787	0	0	\N	\N	0	\N	\N	\N
12	9560be89-85b4-4b19-9e3f-b80f455b0392	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	驱蚊器	kimo	为美好的世界献上祝福第三季	30683	[请问请问]	\N	[起始位]	2024-04-28 15:17:20.734065	2024-04-28 15:17:20.734065	0	\N	\N	\N	0	\N	\N	\N
12	863d6d9c-5184-43a3-a44f-3a48a545014c	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30610	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:09.898853	2024-04-26 11:36:09.898853	0	0	\N	\N	0	\N	\N	\N
12	ef28e806-0153-4049-b4b2-5b97d27b0ade	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30684	[阿萨德]	\N	[阿萨德]	2024-04-28 15:25:57.190762	2024-04-28 15:25:57.190762	0	\N	\N	\N	0	\N	\N	\N
12	6f41c1a0-b1ca-4d39-95af-bc6aa5f9b439	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨达	kimo	为美好的世界献上祝福第三季	30685	[1阿萨德]	\N	[啊实打实]	2024-04-28 15:26:38.234793	2024-04-28 15:26:38.234793	0	\N	\N	\N	0	\N	\N	\N
12	321b7da0-386e-4b77-8ed0-c1656e596d97	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30686	[阿萨德]	\N	[阿萨德]	2024-04-28 15:27:31.547254	2024-04-28 15:27:31.547254	0	\N	\N	\N	0	\N	\N	\N
12	a46ceb2c-8c75-4cf3-8a25-93db2eb8dc9d	日漫番剧	为美好的世界献上祝福	asouli	kimo	2024	阿萨德	kimo	为美好的世界献上祝福第三季	30687	[]	\N	[]	2024-04-28 15:28:57.173439	2024-04-28 15:28:57.173439	0	\N	\N	\N	0	\N	\N	\N
12	55f5df10-19ac-46b6-b25f-fce4ed039965	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30630	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:13.815371	2024-04-26 11:36:13.815371	0	0	\N	\N	0	\N	\N	\N
12	60c7460b-a214-441c-b4a3-7d00760ffbbd	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30642	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:14.172181	2024-04-26 11:36:14.172181	0	0	\N	\N	0	\N	\N	\N
12	cd55060a-6aef-4659-98f1-9ad5f221d85c	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30645	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:17.174812	2024-04-26 11:36:17.174812	0	0	\N	\N	0	\N	\N	\N
12	ab811624-6e46-4d90-b00c-40be8d7e4d6f	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30656	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:19.065805	2024-04-26 11:36:19.065805	0	0	\N	\N	0	\N	\N	\N
12	15a460ee-e035-40cc-a880-3c92035dbd5c	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30654	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:18.699818	2024-04-26 11:36:18.699818	0	0	\N	\N	0	\N	\N	\N
12	3d29ac91-1d12-4c17-9ab8-317d7790f2bb	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30608	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.273095	2024-04-26 11:36:08.273095	0	0	\N	\N	0	\N	\N	\N
12	7079f72c-4b26-4489-8ce4-fa056fd0a5ec	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30603	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.975424	2024-04-26 11:36:08.975424	0	0	\N	\N	0	\N	\N	\N
12	4ff40a1d-7d13-4862-943e-8c96f09db8c2	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30616	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.94853	2024-04-26 11:36:10.94853	0	0	\N	\N	0	\N	\N	\N
12	e6231a6b-9d9d-46d3-b3dd-06a7fbafbaa2	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30618	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:11.330699	2024-04-26 11:36:11.330699	0	0	\N	\N	0	\N	\N	\N
26	2d28e02f-0042-40be-8c34-61580a4fff50	日漫番剧	迷宫饭	TRIGGER	九井谅子	2023	《迷宫饭》是由九井谅子创作的漫画作品，于漫画志《Harta》volume11号（2014年2月）开始连载，是作者初次的长篇连载作品。该作曾在2016年度这本漫画真厉害！上获得男榜第1位。	宫岛善博	迷宫饭	30586	[异世界, 冒险]	\N	[熊谷健太郎, 千本木彩花, 泊明日菜, 中博史]	2024-04-26 10:48:14.140051	2024-04-26 10:48:14.140051	0	0	\N	\N	0	\N	\N	\N
12	5659e834-af56-440a-a239-74602c2320f9	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30615	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.780555	2024-04-26 11:36:10.780555	0	0	\N	\N	0	\N	\N	\N
12	b2b31213-1886-44da-93fb-b5ed00728a1d	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30643	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.786565	2024-04-26 11:36:16.786565	0	0	\N	\N	0	\N	\N	\N
12	c4387600-9f30-45d2-8600-96f3ab695dae	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30644	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:16.97937	2024-04-26 11:36:16.97937	0	0	\N	\N	0	\N	\N	\N
12	958f8ef6-3c84-4a1c-b5d7-1f05c1a08c2c	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30612	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:10.224294	2024-04-26 11:36:10.224294	0	0	\N	\N	0	\N	\N	\N
12	d8371fc5-ea65-4571-ae0e-adc73a587a21	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30639	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:15.818128	2024-04-26 11:36:15.818128	0	0	\N	\N	0	\N	\N	\N
12	0d36dfac-cfdd-4119-80a3-e0f60018556e	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30609	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.447338	2024-04-26 11:36:08.447338	0	0	\N	\N	0	\N	\N	\N
12	cca3580d-457e-4c28-95cb-1c46600007bb	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30606	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:08.825226	2024-04-26 11:36:08.825226	0	0	\N	\N	0	\N	\N	\N
12	59fbd9a3-590e-4b1b-bd49-9a0e2458e1e0	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30605	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:09.347558	2024-04-26 11:36:09.347558	0	0	\N	\N	0	\N	\N	\N
12	a66fcae2-e009-4a5d-bafc-675821c21d27	日漫番剧	qwdqwd	TOHO1	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30595	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:07.556356	2024-04-26 11:36:07.556356	0	0	\N	\N	0	\N	\N	\N
12	5c72cc71-611b-4e60-b70c-c82d911e4129	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30624	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:12.670788	2024-04-26 11:36:12.670788	0	0	\N	\N	0	\N	\N	\N
12	85ce9950-8520-4a77-a521-3cdbbd6127f6	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30631	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:11.88941	2024-04-26 11:36:11.88941	0	0	\N	\N	0	\N	\N	\N
12	02eaffe4-41aa-4d61-95de-e2bfd81ee7b6	日漫番剧	药屋少女的呢喃	TOHO	日向夏	2024	大陆的中央有个大国，在那个国家的后宫之中有一位少女，其名为“猫猫”。她以前在花街做药师，现在在后宫做事。某天，她得知皇子们皆早夭，而现在的两个皇子都因为生病而逐渐衰弱。于是，在好奇心的驱使下，她开始调查事情的真相，就好像在说世上不可能存在诅咒。	长沼范裕	药屋少女的呢喃第一季	30637	[治愈,  推理]	\N	[悠木碧,  大塚刚央,  种﨑敦美,  小西克幸]	2024-04-26 11:36:15.405494	2024-04-26 11:36:15.405494	0	0	\N	\N	0	\N	\N	\N
\.


--
-- Data for Name: video_url; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video_url (id, video_video_id, url, video_url_id, video_url, "createTime", "updateTime", "isDelete", create_time, is_delete, update_time) FROM stdin;
1	7c52cda3-547c-4661-ba03-d92442173c23	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/04/02/17120364407276292.mp4	1	0	2024-04-22 01:25:07.387196	2024-04-22 01:25:07.40867	0	\N	\N	\N
2	7c52cda3-547c-4661-ba03-d92442173c23	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/04/26/17140974284974348.mp4	2	\N	2024-04-26 10:10:57.869924	2024-04-26 10:10:57.869924	0	\N	\N	\N
3	2d28e02f-0042-40be-8c34-61580a4fff50	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/04/26/17140974284974348.mp4	1	\N	2024-05-26 15:47:43	2024-04-26 15:47:49	0	\N	\N	\N
\.


--
-- Name: _user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public._user_id_seq', 5, true);


--
-- Name: _user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public._user_seq', 1, false);


--
-- Name: comment_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comment_comment_id_seq', 12, true);


--
-- Name: scoring_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scoring_id_seq', 1, true);


--
-- Name: token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.token_id_seq', 34, true);


--
-- Name: user_file_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_file_file_id_seq', 2, true);


--
-- Name: user_file_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_file_seq', 351, true);


--
-- Name: user_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_info_id_seq', 2, true);


--
-- Name: video_url_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.video_url_id_seq', 2, true);


--
-- Name: video_url_video_url_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.video_url_video_url_id_seq', 2, true);


--
-- Name: _user _user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public._user
    ADD CONSTRAINT _user_pkey PRIMARY KEY (id);


--
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (comment_id);


--
-- Name: scoring scoring_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scoring
    ADD CONSTRAINT scoring_pkey PRIMARY KEY (id);


--
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- Name: token token_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_token_key UNIQUE (token);


--
-- Name: user_file user_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file
    ADD CONSTRAINT user_file_pkey PRIMARY KEY (file_id);


--
-- Name: user_file user_file_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file
    ADD CONSTRAINT user_file_user_id_key UNIQUE (user_id);


--
-- Name: user_info user_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);


--
-- Name: user_info user_info_user_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_user_id_key UNIQUE (user_id);


--
-- Name: video video_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video
    ADD CONSTRAINT video_pkey PRIMARY KEY (video_id);


--
-- Name: video_url video_url_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url
    ADD CONSTRAINT video_url_pkey PRIMARY KEY (id);


--
-- Name: user_file fk2ynd7gwiel8vudxy2iy1cq006; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file
    ADD CONSTRAINT fk2ynd7gwiel8vudxy2iy1cq006 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: comment fka70qtxm025yrlmildp3cr2scu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fka70qtxm025yrlmildp3cr2scu FOREIGN KEY (url_id) REFERENCES public.video_url(id);


--
-- Name: comment fkbsuh08kv1lyh8v6ivufrollr6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkbsuh08kv1lyh8v6ivufrollr6 FOREIGN KEY (video_id) REFERENCES public.video(video_id);


--
-- Name: comment fkde3rfu96lep00br5ov0mdieyt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkde3rfu96lep00br5ov0mdieyt FOREIGN KEY (parent_id) REFERENCES public.comment(comment_id);


--
-- Name: video_url fkhbltmxv034cs0kl6u39vxyn92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url
    ADD CONSTRAINT fkhbltmxv034cs0kl6u39vxyn92 FOREIGN KEY (video_video_id) REFERENCES public.video(video_id);


--
-- Name: token fkiblu4cjwvyntq3ugo31klp1c6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkiblu4cjwvyntq3ugo31klp1c6 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: scoring fkjpc7h99cxxj027cdbgp2uhvfw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scoring
    ADD CONSTRAINT fkjpc7h99cxxj027cdbgp2uhvfw FOREIGN KEY (video_id) REFERENCES public.video(video_id);


--
-- Name: scoring fkkj6vy67lsk1p2dm1t6mf3uy31; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scoring
    ADD CONSTRAINT fkkj6vy67lsk1p2dm1t6mf3uy31 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: comment fkoo5phijy22unidgkw0sipcs74; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkoo5phijy22unidgkw0sipcs74 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: user_info fkqcx2oa67st4b10dp7roxg7myq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT fkqcx2oa67st4b10dp7roxg7myq FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- PostgreSQL database dump complete
--

