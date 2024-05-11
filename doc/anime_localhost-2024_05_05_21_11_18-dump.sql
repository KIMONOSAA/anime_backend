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
-- Name: video_url; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.video_url (
    id bigint NOT NULL,
    create_time date NOT NULL,
    is_delete integer,
    update_time date,
    url character varying(255),
    video_video_id uuid
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
-- Name: video_url id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url ALTER COLUMN id SET DEFAULT nextval('public.video_url_id_seq'::regclass);


--
-- Data for Name: video_url; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.video_url (id, create_time, is_delete, update_time, url, video_video_id) FROM stdin;
1	2024-05-06	\N	2024-05-06	http://oss-kimo-video.oss-cn-guangzhou.aliyuncs.com/video/2024/05/05/1714871020239347.mp4	d901d2c5-cbff-44c3-9c1c-ee0395f38914
\.


--
-- Name: video_url_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.video_url_id_seq', 1, true);


--
-- Name: video_url video_url_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url
    ADD CONSTRAINT video_url_pkey PRIMARY KEY (id);


--
-- Name: video_url fkhbltmxv034cs0kl6u39vxyn92; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.video_url
    ADD CONSTRAINT fkhbltmxv034cs0kl6u39vxyn92 FOREIGN KEY (video_video_id) REFERENCES public.video(video_id);


--
-- PostgreSQL database dump complete
--

