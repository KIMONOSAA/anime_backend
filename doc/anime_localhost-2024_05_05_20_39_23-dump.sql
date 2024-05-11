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

SET default_tablespace = '';

SET default_table_access_method = heap;

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
-- Data for Name: video; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video (video_id, video_alias, video_anime_company, anime_type, video_comic_book_author, create_time, video_date, video_description, video_director_name, hot_top, is_delete, video_name, video_num_ber_of_sections, video_photo, total_score, video_type, update_time, videouv, video_voice_actor) FROM stdin;
4791ede4-1399-40a6-ba1b-92c65553c8eb	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:44:48.531	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38437	\N	[阿萨达]	2024-05-05 15:44:48.531	\N	[阿萨达]
2178e5f6-f2c6-4713-a21b-19447ab2f0e0	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:06.597	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38438	\N	[阿萨达]	2024-05-05 15:45:06.597	\N	[阿萨德]
58c7d967-54b1-4c2c-a87c-621bf6596047	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:25.03	2023	阿萨德	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38439	\N	[阿萨达]	2024-05-05 15:45:25.03	\N	[阿萨达]
c15cf49a-f99d-4083-87b5-18f23c106a55	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:36.764	2023	阿萨达	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38440	\N	[阿萨德]	2024-05-05 15:45:36.764	\N	[阿萨德]
a89f8a69-a428-495d-b9e0-9a469e5ee07b	葬送的芙莉莲	MADHOUSE	日漫番剧	山田钟人	2024-05-05 15:45:53.828	2023	阿萨德	斋藤圭一郎	0	\N	葬送的芙莉莲	28	38441	\N	[阿萨德]	2024-05-05 15:45:53.828	\N	[阿萨德]
\.


--
-- Name: video video_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video
    ADD CONSTRAINT video_pkey PRIMARY KEY (video_id);


--
-- PostgreSQL database dump complete
--

