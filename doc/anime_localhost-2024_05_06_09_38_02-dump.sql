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
    id uuid NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    email character varying(255),
    is_delete integer,
    is_enabled boolean NOT NULL,
    password character varying(255),
    role character varying(255),
    update_time timestamp(6) without time zone,
    username character varying(255),
    CONSTRAINT _user_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public._user OWNER TO postgres;

--
-- Name: comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comment (
    comment_id bigint NOT NULL,
    context character varying(255),
    create_time timestamp(6) without time zone NOT NULL,
    created_at timestamp(6) without time zone,
    is_delete integer DEFAULT 0,
    update_time timestamp(6) without time zone,
    parent_id bigint,
    user_id uuid,
    video_id uuid,
    url_id bigint
);


ALTER TABLE public.comment OWNER TO postgres;

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
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id bigint NOT NULL,
    age integer,
    contact character varying(255),
    content character varying(255),
    create_time timestamp(6) without time zone NOT NULL,
    gender integer,
    is_delete integer,
    job character varying(255),
    photo character varying(255),
    place character varying(255),
    review_message character varying(255),
    review_status integer,
    thumb_num integer,
    title character varying(255),
    update_time timestamp(6) without time zone,
    user_id uuid,
    view_num integer
);


ALTER TABLE public.post OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_id_seq OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.post_id_seq OWNED BY public.post.id;


--
-- Name: scoring; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scoring (
    id bigint NOT NULL,
    comment character varying(255),
    create_time timestamp(6) without time zone NOT NULL,
    is_delete integer,
    score double precision NOT NULL,
    update_time timestamp(6) without time zone,
    user_id uuid,
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
    id bigint NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    type character varying(255),
    user_id uuid,
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
    avatar oid,
    create_time timestamp(6) without time zone NOT NULL,
    is_delete integer,
    update_time timestamp(6) without time zone,
    user_id uuid
);


ALTER TABLE public.user_file OWNER TO postgres;

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
-- Name: user_info; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_info (
    id bigint NOT NULL,
    create_time timestamp(6) without time zone NOT NULL,
    date date,
    description character varying(255),
    is_delete integer,
    name character varying(255),
    sex character varying(255),
    update_time timestamp(6) without time zone,
    user_id uuid
);


ALTER TABLE public.user_info OWNER TO postgres;

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
    video_id uuid NOT NULL,
    video_alias character varying(255),
    video_anime_company character varying(255),
    anime_type character varying(255),
    video_comic_book_author character varying(255),
    create_time timestamp(6) without time zone NOT NULL,
    video_date character varying(255),
    video_description character varying(255),
    video_director_name character varying(255),
    hot_top double precision NOT NULL,
    is_delete integer,
    video_name character varying(255),
    video_num_ber_of_sections integer,
    video_photo oid,
    total_score double precision,
    video_type character varying(255),
    update_time timestamp(6) without time zone,
    videouv bigint,
    video_voice_actor character varying(255)
);


ALTER TABLE public.video OWNER TO postgres;

--
-- Name: video_url; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.video_url (
    id bigint NOT NULL,
    create_time date NOT NULL,
    is_delete integer,
    update_time date,
    url character varying(255),
    video_video_id uuid,
    section integer
);


ALTER TABLE public.video_url OWNER TO postgres;

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
-- Name: comment comment_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comment ALTER COLUMN comment_id SET DEFAULT nextval('public.comment_comment_id_seq'::regclass);


--
-- Name: post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post ALTER COLUMN id SET DEFAULT nextval('public.post_id_seq'::regclass);


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
-- Data for Name: _user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public._user (id, create_time, email, is_delete, is_enabled, password, role, update_time, username) FROM stdin;
4396383f-f2da-4d7a-b5b9-8fc12ebe70db	2024-05-05 10:24:50.963	garntoleana@gmail.com	\N	t	$2a$10$UT8vywkf9ogfAozH8pS4EuVUQGyMgPzmh6L2y9txLZDIbTj99YdTW	USER	2024-05-05 11:04:33.04	kimo
\.


--
-- Data for Name: comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comment (comment_id, context, create_time, created_at, is_delete, update_time, parent_id, user_id, video_id, url_id) FROM stdin;
2	这法师打发我说的啊实打实大	2024-05-05 22:58:21.476	2024-05-05 22:58:21.476151	0	2024-05-05 22:58:21.476	\N	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
1	这法师打发我说的阿萨达	2024-05-05 22:54:10.124	2024-05-05 22:54:10.097136	0	2024-05-05 22:54:10.124	\N	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
3	你是sb吧卧槽	2024-05-05 23:44:55.517	2024-05-05 23:44:55.51704	0	2024-05-05 23:44:55.517	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
4	我擦无情啊你	2024-05-06 07:52:31.57	2024-05-06 07:52:31.568669	0	2024-05-06 07:52:31.57	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
5	你好我是sb	2024-05-06 07:54:21.34	2024-05-06 07:54:21.340943	0	2024-05-06 07:54:21.34	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
6	天呐我TM真是个人才	2024-05-06 07:57:01.81	2024-05-06 07:57:01.805675	0	2024-05-06 07:57:01.81	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
7	天呐我TM真是个哈哈	2024-05-06 07:57:08.178	2024-05-06 07:57:08.178773	0	2024-05-06 07:57:08.178	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
8	我不知道你在说啥	2024-05-06 07:57:20.468	2024-05-06 07:57:20.467863	0	2024-05-06 07:57:20.468	2	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
9	红红火火恍恍惚惚	2024-05-06 08:10:09.428	2024-05-06 08:10:09.427097	0	2024-05-06 08:10:09.428	1	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
10	红红火火恍恍惚惚哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈	2024-05-06 08:16:22.641	2024-05-06 08:16:22.641825	0	2024-05-06 08:16:22.641	1	4396383f-f2da-4d7a-b5b9-8fc12ebe70db	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
\.


--
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.post (id, age, contact, content, create_time, gender, is_delete, job, photo, place, review_message, review_status, thumb_num, title, update_time, user_id, view_num) FROM stdin;
\.


--
-- Data for Name: scoring; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scoring (id, comment, create_time, is_delete, score, update_time, user_id, video_id) FROM stdin;
\.


--
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.token (id, expired, revoked, token, type, user_id) FROM stdin;
1	f	f	eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXJudG9sZWFuYUBnbWFpbC5jb20iLCJpYXQiOjE3MTQ4Nzg0MTUsImV4cCI6MTcxNDk2NDgxNX0.AN4iEqQvTT4tQD21p1IDfkuNHgcRqLgug4FdG7NHzZk	BEARER	4396383f-f2da-4d7a-b5b9-8fc12ebe70db
\.


--
-- Data for Name: user_file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_file (file_id, avatar, create_time, is_delete, update_time, user_id) FROM stdin;
1	38428	2024-05-05 13:05:31.204	\N	2024-05-05 13:05:31.204	4396383f-f2da-4d7a-b5b9-8fc12ebe70db
\.


--
-- Data for Name: user_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_info (id, create_time, date, description, is_delete, name, sex, update_time, user_id) FROM stdin;
1	2024-05-05 12:44:41.001	2024-05-06	啊实打实	\N	kimofuka	女	2024-05-05 13:03:50.835	4396383f-f2da-4d7a-b5b9-8fc12ebe70db
\.


--
-- Data for Name: video; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video (video_id, video_alias, video_anime_company, anime_type, video_comic_book_author, create_time, video_date, video_description, video_director_name, hot_top, is_delete, video_name, video_num_ber_of_sections, video_photo, total_score, video_type, update_time, videouv, video_voice_actor) FROM stdin;
d901d2c5-cbff-44c3-9c1c-ee0395f38914	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 08:28:10.131	2023	电视动画片《葬送的芙莉莲》改编自山田钟人原作、阿部司作画的同名漫画作品，于2022年9月13日宣布制作消息 [37]。该片由MADHOUSE负责制作，第1部分（第1～16集）于2023年9月29日至12月22日播出，首播剧集是时长为2小时的特别篇《首播2小时特别篇 ～启程之章～》（即第1～4集） [1]；第2部分（第17～28集）于2024年1月5日至3月22日播出 [21]。全28集	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38443	\N	[治愈, 推理]	2024-05-06 09:13:58.19	\N	[种崎敦美, 市之濑加那, 小林千晃, 中村悠一]
5ae2a0ab-0247-4861-aea5-d307d5900f95	asd	as	日漫番剧	as	2024-05-04 11:12:13.705	sad	sad	as	0	\N	sad	0	38425	\N	[治愈, 推理]	2024-05-04 11:12:13.705	\N	[asd]
2178e5f6-f2c6-4713-a21b-19447ab2f0e2	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:06.597	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38438	\N	[治愈, 推理]	2024-05-05 15:45:06.597	\N	阿萨德
58c7d967-54b1-4c2c-a87c-621bf6596044	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:25.03	2023	阿萨德	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38439	\N	[治愈, 推理]	2024-05-05 15:45:25.03	\N	阿萨达
4791ede4-1399-40a6-ba1b-92c65553c8ec	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:44:48.531	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38437	\N	[治愈, 推理]	2024-05-05 15:44:48.531	\N	阿萨达
2178e5f6-f2c6-4713-a21b-19447ab2f0e4	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:06.597	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38438	\N	[治愈, 推理]	2024-05-05 15:45:06.597	\N	阿萨德
c15cf49a-f99d-4083-87b5-18f23c106a57	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:36.764	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38440	\N	[治愈, 推理]	2024-05-05 15:45:36.764	\N	阿萨德
58c7d967-54b1-4c2c-a87c-621bf6596045	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:25.03	2023	阿萨德	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38439	\N	[治愈, 推理]	2024-05-05 15:45:25.03	\N	阿萨达
58c7d967-54b1-4c2c-a87c-621bf6596047	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:25.03	2023	阿萨德	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38439	\N	[治愈, 推理]	2024-05-05 15:45:25.03	\N	阿萨达
0398d345-9a71-4984-9e6c-c8b36be89908	asa	asda	日漫番剧	sadsa	2024-05-04 12:01:40.883	2023	asdasd	asda	0	\N	啊手动阀	0	38426	\N	[治愈, 推理]	2024-05-04 12:01:40.883	\N	[S打赏]
2178e5f6-f2c6-4713-a21b-19447ab2f0e0	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:06.597	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38438	\N	[治愈, 推理]	2024-05-05 15:45:06.597	\N	阿萨德
4791ede4-1399-40a6-ba1b-92c65553c8eb	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:44:48.531	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38437	\N	[治愈, 推理]	2024-05-05 15:44:48.531	\N	阿萨达
2178e5f6-f2c6-4713-a21b-19447ab2f0e3	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:06.597	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38438	\N	[治愈, 推理]	2024-05-05 15:45:06.597	\N	阿萨德
4791ede4-1399-40a6-ba1b-92c65553c8e4	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:44:48.531	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38437	\N	[治愈, 推理]	2024-05-05 15:44:48.531	\N	阿萨达
4791ede4-1399-40a6-ba1b-92c65553c8ed	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:44:48.531	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38437	\N	[治愈, 推理]	2024-05-05 15:44:48.531	\N	阿萨达
c15cf49a-f99d-4083-87b5-18f23c106a56	葬送的芙莉莲	MADHOUSE	日漫剧场	山田钟人	2024-05-05 15:45:36.764	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38440	\N	[治愈, 推理]	2024-05-05 15:45:36.764	\N	阿萨德
c15cf49a-f99d-4083-87b5-18f23c106a55	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:36.764	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38440	\N	[治愈, 推理]	2024-05-05 15:45:36.764	\N	阿萨德
\.


--
-- Data for Name: video_url; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video_url (id, create_time, is_delete, update_time, url, video_video_id, section) FROM stdin;
1	2024-05-06	\N	2024-05-06	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/05/05/1714871020239347.mp4	d901d2c5-cbff-44c3-9c1c-ee0395f38914	1
3	2024-05-06	\N	2024-05-06	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/05/06/17149580096563995.mp4	d901d2c5-cbff-44c3-9c1c-ee0395f38914	8
\.


--
-- Name: comment_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comment_comment_id_seq', 10, true);


--
-- Name: post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.post_id_seq', 1, false);


--
-- Name: scoring_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.scoring_id_seq', 1, false);


--
-- Name: token_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.token_id_seq', 1, true);


--
-- Name: user_file_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_file_file_id_seq', 1, true);


--
-- Name: user_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_info_id_seq', 1, true);


--
-- Name: video_url_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.video_url_id_seq', 3, true);


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
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


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
-- Name: user_info uk_hixwjgx0ynne0cq4tqvoawoda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT uk_hixwjgx0ynne0cq4tqvoawoda UNIQUE (user_id);


--
-- Name: token uk_pddrhgwxnms2aceeku9s2ewy5; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT uk_pddrhgwxnms2aceeku9s2ewy5 UNIQUE (token);


--
-- Name: user_file uk_polh11imju6b0w34orybum365; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file
    ADD CONSTRAINT uk_polh11imju6b0w34orybum365 UNIQUE (user_id);


--
-- Name: user_file user_file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_file
    ADD CONSTRAINT user_file_pkey PRIMARY KEY (file_id);


--
-- Name: user_info user_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);


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

