--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.15 (Homebrew)

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
-- Name: userrole; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.userrole AS ENUM (
    'ADMIN',
    'MINE_ADMIN',
    'USER'
);


ALTER TYPE public.userrole OWNER TO postgres;

--
-- Name: generate_production_variance(numeric, numeric); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.generate_production_variance(base_target numeric, variance_percent numeric) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN base_target * (1 + (random() * variance_percent - variance_percent/2));
END;
$$;


ALTER FUNCTION public.generate_production_variance(base_target numeric, variance_percent numeric) OWNER TO postgres;

--
-- Name: generate_production_variance(numeric, integer, numeric); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.generate_production_variance(base_target numeric, month integer, variance_percent numeric DEFAULT 0.05) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN base_target * (
        CASE
            WHEN month IN (1, 2, 12) THEN 0.95
            WHEN month = 7 THEN 0.90
            WHEN month IN (5, 6, 8, 9) THEN 1.05
            ELSE 1.0
        END
    ) * (1 + (random() * variance_percent - variance_percent/2));
END;
$$;


ALTER FUNCTION public.generate_production_variance(base_target numeric, month integer, variance_percent numeric) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: environmental_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.environmental_data (
    id bigint NOT NULL,
    date_recorded date,
    level double precision NOT NULL,
    pollutant_type character varying(255),
    mine_id bigint,
    station_id bigint,
    created_at timestamp(6) without time zone,
    measured_value double precision NOT NULL,
    measurement_date timestamp(6) without time zone NOT NULL,
    notes character varying(255),
    updated_at timestamp(6) without time zone,
    pollutant_id bigint NOT NULL
);


ALTER TABLE public.environmental_data OWNER TO postgres;

--
-- Name: environmental_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.environmental_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.environmental_data_id_seq OWNER TO postgres;

--
-- Name: environmental_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.environmental_data_id_seq OWNED BY public.environmental_data.id;


--
-- Name: mine_minerals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mine_minerals (
    mine_id bigint NOT NULL,
    mineral_id bigint NOT NULL
);


ALTER TABLE public.mine_minerals OWNER TO postgres;

--
-- Name: mine_minerals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mine_minerals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mine_minerals_id_seq OWNER TO postgres;

--
-- Name: minerals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.minerals (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.minerals OWNER TO postgres;

--
-- Name: minerals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.minerals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.minerals_id_seq OWNER TO postgres;

--
-- Name: minerals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.minerals_id_seq OWNED BY public.minerals.id;


--
-- Name: mines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mines (
    id bigint NOT NULL,
    company character varying(255),
    location character varying(255),
    name character varying(255),
    province_id bigint
);


ALTER TABLE public.mines OWNER TO postgres;

--
-- Name: mines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mines_id_seq OWNER TO postgres;

--
-- Name: mines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mines_id_seq OWNED BY public.mines.id;


--
-- Name: monitoring_stations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.monitoring_stations (
    id bigint NOT NULL,
    location character varying(255),
    name character varying(255),
    province_id bigint NOT NULL,
    station_name character varying(255)
);


ALTER TABLE public.monitoring_stations OWNER TO postgres;

--
-- Name: monitoring_stations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.monitoring_stations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.monitoring_stations_id_seq OWNER TO postgres;

--
-- Name: monitoring_stations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.monitoring_stations_id_seq OWNED BY public.monitoring_stations.id;


--
-- Name: pollutants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pollutants (
    id bigint NOT NULL,
    measured_value numeric(38,2) NOT NULL,
    measurement character varying(255),
    name character varying(255),
    "timestamp" date,
    type character varying(255),
    station_id bigint,
    benchmark_type character varying(255) NOT NULL,
    benchmark_value double precision NOT NULL,
    category character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    description character varying(1000),
    measurement_frequency character varying(255) NOT NULL,
    unit character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    benchmark character varying(255)
);


ALTER TABLE public.pollutants OWNER TO postgres;

--
-- Name: pollutants_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pollutants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pollutants_id_seq OWNER TO postgres;

--
-- Name: pollutants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pollutants_id_seq OWNED BY public.pollutants.id;


--
-- Name: privileges; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.privileges (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.privileges OWNER TO postgres;

--
-- Name: privileges_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.privileges_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.privileges_id_seq OWNER TO postgres;

--
-- Name: privileges_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.privileges_id_seq OWNED BY public.privileges.id;


--
-- Name: provinces; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.provinces (
    id bigint NOT NULL,
    abbreviation character varying(255),
    name character varying(255)
);


ALTER TABLE public.provinces OWNER TO postgres;

--
-- Name: provinces_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.provinces_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.provinces_id_seq OWNER TO postgres;

--
-- Name: provinces_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.provinces_id_seq OWNED BY public.provinces.id;


--
-- Name: safety_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.safety_data (
    id bigint NOT NULL,
    date_recorded date NOT NULL,
    lost_time_incidents integer,
    near_misses integer,
    safety_level character varying(255),
    mine_id bigint,
    date date,
    description character varying(255),
    CONSTRAINT safety_data_safety_level_check CHECK (((safety_level)::text = ANY ((ARRAY['GOOD'::character varying, 'FAIR'::character varying, 'NEEDS_IMPROVEMENT'::character varying, 'EXCELLENT'::character varying, 'CRITICAL'::character varying])::text[])))
);


ALTER TABLE public.safety_data OWNER TO postgres;

--
-- Name: safety_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.safety_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.safety_data_id_seq OWNER TO postgres;

--
-- Name: safety_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.safety_data_id_seq OWNED BY public.safety_data.id;


--
-- Name: station_pollutants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.station_pollutants (
    station_id bigint NOT NULL,
    pollutant_id bigint NOT NULL
);


ALTER TABLE public.station_pollutants OWNER TO postgres;

--
-- Name: user_privileges; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_privileges (
    id integer NOT NULL,
    privilege_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.user_privileges OWNER TO postgres;

--
-- Name: user_privileges_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_privileges_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_privileges_id_seq OWNER TO postgres;

--
-- Name: user_privileges_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_privileges_id_seq OWNED BY public.user_privileges.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    role character varying(50)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: environmental_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.environmental_data ALTER COLUMN id SET DEFAULT nextval('public.environmental_data_id_seq'::regclass);


--
-- Name: minerals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.minerals ALTER COLUMN id SET DEFAULT nextval('public.minerals_id_seq'::regclass);


--
-- Name: mines id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mines ALTER COLUMN id SET DEFAULT nextval('public.mines_id_seq'::regclass);


--
-- Name: monitoring_stations id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.monitoring_stations ALTER COLUMN id SET DEFAULT nextval('public.monitoring_stations_id_seq'::regclass);


--
-- Name: pollutants id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pollutants ALTER COLUMN id SET DEFAULT nextval('public.pollutants_id_seq'::regclass);


--
-- Name: privileges id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges ALTER COLUMN id SET DEFAULT nextval('public.privileges_id_seq'::regclass);


--
-- Name: provinces id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.provinces ALTER COLUMN id SET DEFAULT nextval('public.provinces_id_seq'::regclass);


--
-- Name: safety_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.safety_data ALTER COLUMN id SET DEFAULT nextval('public.safety_data_id_seq'::regclass);


--
-- Name: user_privileges id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_privileges ALTER COLUMN id SET DEFAULT nextval('public.user_privileges_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: environmental_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.environmental_data (id, date_recorded, level, pollutant_type, mine_id, station_id, created_at, measured_value, measurement_date, notes, updated_at, pollutant_id) FROM stdin;
1	2024-11-10	1	Total Dust	2	3	2024-11-13 16:35:42.708871	3.8	2024-11-10 00:00:00	Quarterly check	2024-11-13 16:35:42.708871	1
2	2024-11-10	1.5	PM10	4	4	2024-11-13 16:35:42.708871	18	2024-11-10 00:00:00	Increased mining activity	2024-11-13 16:35:42.708871	11
3	2024-11-11	0.5	VOC Concentration	5	5	2024-11-13 16:35:42.708871	0.45	2024-11-11 00:00:00	Seasonal baseline	2024-11-13 16:35:42.708871	7
4	2024-11-12	1	Ozone	6	6	2024-11-13 16:35:42.708871	0.08	2024-11-12 00:00:00	Warm season measure	2024-11-13 16:35:42.708871	9
5	2024-11-12	1.5	Sulfur Dioxide	3	1	2024-11-13 16:35:42.708871	0.06	2024-11-12 00:00:00	Nearby industrial emissions	2024-11-13 16:35:42.708871	6
6	2024-11-13	0.5	Ammonia	8	7	2024-11-13 16:35:42.708871	0.05	2024-11-13 00:00:00	Ammonia from processing plant	2024-11-13 16:35:42.708871	8
7	2024-11-13	1	PM2.5	12	4	2024-11-13 16:35:42.708871	22	2024-11-13 00:00:00	Airborne fine particles	2024-11-13 16:35:42.708871	12
8	2024-11-14	1.5	Silica Dust	14	5	2024-11-13 16:35:42.708871	0.28	2024-11-14 00:00:00	Increased silica concentration	2024-11-13 16:35:42.708871	4
9	2024-11-14	1	Settled Dust	15	6	2024-11-13 16:35:42.708871	25.4	2024-11-14 00:00:00	Dust from transport	2024-11-13 16:35:42.708871	14
10	2024-11-15	0.5	Carbon Monoxide	9	2	2024-11-13 16:35:42.708871	3.1	2024-11-15 00:00:00	CO from machinery	2024-11-13 16:35:42.708871	10
11	2024-11-15	1	Total Dust	7	3	2024-11-13 16:35:42.708871	4	2024-11-15 00:00:00	High dust levels	2024-11-13 16:35:42.708871	1
12	2024-11-15	1.5	PM10	10	4	2024-11-13 16:35:42.708871	15	2024-11-15 00:00:00	Seasonal rise in PM10	2024-11-13 16:35:42.708871	2
13	2024-11-16	1	VOC Concentration	11	5	2024-11-13 16:35:42.708871	0.6	2024-11-16 00:00:00	VOC from nearby industry	2024-11-13 16:35:42.708871	7
14	2024-11-16	0.5	Ammonia	13	6	2024-11-13 16:35:42.708871	0.04	2024-11-16 00:00:00	Reduced emissions	2024-11-13 16:35:42.708871	8
15	2024-11-17	1	Ozone	2	1	2024-11-13 16:35:42.708871	0.06	2024-11-17 00:00:00	Low ozone levels	2024-11-13 16:35:42.708871	9
16	2024-11-17	1.5	PM2.5	4	2	2024-11-13 16:35:42.708871	24.5	2024-11-17 00:00:00	High fine particles	2024-11-13 16:35:42.708871	3
17	2024-11-18	1	Nitrogen Dioxide	5	3	2024-11-13 16:35:42.708871	0.7	2024-11-18 00:00:00	NO2 from plant emissions	2024-11-13 16:35:42.708871	5
18	2024-11-18	1.5	Total Dust	6	4	2024-11-13 16:35:42.708871	3.5	2024-11-18 00:00:00	Increased dust	2024-11-13 16:35:42.708871	1
19	2024-11-18	0.5	Sulfur Dioxide	14	5	2024-11-13 16:35:42.708871	0.05	2024-11-18 00:00:00	Reduced SO2 levels	2024-11-13 16:35:42.708871	6
20	2024-11-19	1	Settled Dust	3	6	2024-11-13 16:35:42.708871	20	2024-11-19 00:00:00	Dust control measures	2024-11-13 16:35:42.708871	14
\.


--
-- Data for Name: mine_minerals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mine_minerals (mine_id, mineral_id) FROM stdin;
2	7
3	4
4	5
5	3
7	1
9	4
10	1
11	3
13	4
14	2
15	1
1	3
1	7
6	1
8	1
12	3
3	11
\.


--
-- Data for Name: minerals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.minerals (id, name) FROM stdin;
1	Gold
2	Silver
3	Copper
4	Iron Ore
5	Zinc
6	Lead
7	Nickel
8	Aluminum
9	Lithium
10	Cobalt
11	Manganese
\.


--
-- Data for Name: mines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mines (id, company, location, name, province_id) FROM stdin;
2	Vale Canada Limited	Long Harbour, NL	Long Harbour Processing Plant	1
4	Glencore	Bathurst, NB	Brunswick Mine	1
5	Teck Resources	Pilleys Island, NL	Duck Pond Mine	1
8	Marble Mountain Resources	Baie Verte, NL	Hammerdown Mine	1
9	Labrador Iron Mines Holdings Limited	Sandy Lake, NL	Labrador Iron Mines	1
10	Signal Gold	Baie Verte, NL	Stoger Tight Mine	1
13	Red Moon Resources	Grand Falls-Windsor, NL	Red Moon Potash	1
14	Buchans Minerals	Buchans, NL	Crown Pillar Mine	1
15	Sunnyside Mining Company	Sunnyside, NL	Sunnyside Mine	1
3	Tacora Resources	Wabush, NL	Scully Mine	1
12	Fjordland Exploration	Gullbridge, NL	Gullbridge Mine	1
1	Vale Canada Limited	Labrador City, NL	Voiseys Bay Mine	1
7	Marble Mountain Resources	Baie Verte, NL	Hammerdown Mine	1
11	Fjordland Exploration	Gullbridge, NL	Gullbridge Mine	1
6	Signal Gold	Point Rousse, NL	Pine Cove Mine	1
17	Nordic Mining AS	Sande	Nordic Mines	8
18	Armdiksen Exploration	Witless Bay	Witess Bay Copper	1
\.


--
-- Data for Name: monitoring_stations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.monitoring_stations (id, location, name, province_id, station_name) FROM stdin;
1	Wabush, NL	Wabush Station	1	\N
2	Labrador City, NL	Labrador City Station	1	\N
3	Long Harbour, NL	Long Harbour Station	1	\N
4	Baie Verte, NL	Baie Verte Station	1	\N
5	Goose Bay, NL	Goose Bay Station	1	\N
6	Pilleys Island, NL	Pilleys Island Station	1	\N
7	Steady Brook, NL	Marble Mountain Station	1	\N
\.


--
-- Data for Name: pollutants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pollutants (id, measured_value, measurement, name, "timestamp", type, station_id, benchmark_type, benchmark_value, category, created_at, description, measurement_frequency, unit, updated_at, benchmark) FROM stdin;
1	3.80	Dust Level	Total Dust Deposition	2024-11-06	Total Dust	1	Maximum Monthly	4	Air Quality	2024-11-07 10:06:55.377156	Total settleable dust affecting surrounding communities	Monthly	g/m²/month	2024-11-07 10:06:55.377156	Dust Standard
2	15.30	Particulate Matter	PM10	2024-11-06	Coarse Particulate Matter	1	Daily Limit	50	Air Quality	2024-11-07 10:06:55.377156	Particulate matter with diameter less than 10 µm	Daily	µg/m³	2024-11-07 10:06:55.377156	PM10 Standard
3	28.70	Particulate Matter	PM2.5	2024-11-06	Fine Particulate Matter	1	Daily Limit	25	Air Quality	2024-11-07 10:06:55.377156	Particulate matter with diameter less than 2.5 µm	Daily	µg/m³	2024-11-07 10:06:55.377156	PM2.5 Standard
4	0.10	Silica Concentration	Respirable Crystalline Silica	2024-11-06	Silica	1	Weekly Average	0.05	Air Quality	2024-11-07 10:06:55.377156	Crystalline silica particles respirable in size	Weekly	mg/m³	2024-11-07 10:06:55.377156	Silica Safety Limit
5	0.70	Gas Concentration	Nitrogen Dioxide (NO2)	2024-11-06	Nitrogen Dioxide	2	Hourly Limit	0.53	Air Quality	2024-11-07 10:06:55.377156	Gas emitted from industrial processes affecting respiratory system	Hourly	ppb	2024-11-07 10:06:55.377156	NO2 Standard
6	0.08	Gas Concentration	Sulfur Dioxide (SO2)	2024-11-06	Sulfur Dioxide	2	Hourly Limit	0.075	Air Quality	2024-11-07 10:06:55.377156	Gaseous pollutant mainly from mining activities	Hourly	ppm	2024-11-07 10:06:55.377156	SO2 Standard
7	0.50	VOC Concentration	Total VOCs	2024-11-06	Volatile Organic Compounds	2	Daily Limit	0.6	Air Quality	2024-11-07 10:06:55.377156	Total volatile organic compounds in air	Daily	ppm	2024-11-07 10:06:55.377156	VOC Standard
8	0.02	Ammonia Concentration	Ammonia (NH3)	2024-11-06	Ammonia	3	Daily Limit	0.1	Air Quality	2024-11-07 10:06:55.377156	Ammonia emissions from industrial activities	Daily	ppb	2024-11-07 10:06:55.377156	NH3 Standard
9	0.07	Ozone Concentration	Ground-level Ozone	2024-11-06	Ozone	3	8-hour Average	0.07	Air Quality	2024-11-07 10:06:55.377156	Ground-level ozone affecting air quality and health	8-Hour	ppm	2024-11-07 10:06:55.377156	Ozone Limit
10	3.10	CO Concentration	Carbon Monoxide	2024-11-06	Carbon Monoxide	3	8-hour Average	9	Air Quality	2024-11-07 10:06:55.377156	Toxic gas emitted from vehicles and machinery	8-Hour	ppm	2024-11-07 10:06:55.377156	CO Limit
11	12.00	Dust Level	PM10	2024-11-06	Particulate Matter	4	Daily Limit	50	Air Quality	2024-11-07 10:06:55.377156	Coarse particulate matter	Daily	µg/m³	2024-11-07 10:06:55.377156	PM10 Standard
12	20.00	Dust Level	PM2.5	2024-11-06	Fine Particulate Matter	4	Daily Limit	25	Air Quality	2024-11-07 10:06:55.377156	Fine particulate matter	Daily	µg/m³	2024-11-07 10:06:55.377156	PM2.5 Standard
13	0.30	Dust Concentration	Silica Dust	2024-11-06	Silica	5	Monthly Average	0.1	Air Quality	2024-11-07 10:06:55.377156	Fine crystalline silica dust	Monthly	mg/m³	2024-11-07 10:06:55.377156	Silica Standard
14	25.40	Dust Concentration	Settled Dust	2024-11-06	Total Dust	5	Maximum Daily	30	Air Quality	2024-11-07 10:06:55.377156	Total dust that has settled in the environment	Daily	g/m²	2024-11-07 10:06:55.377156	Dust Deposition Standard
15	0.02	SO2 Concentration	Sulfur Dioxide	2024-11-06	Sulfur Dioxide	6	8-hour Average	0.075	Air Quality	2024-11-07 10:06:55.377156	Gas emitted from combustion processes	8-Hour	ppm	2024-11-07 10:06:55.377156	SO2 Standard
16	2.50	CO Concentration	Carbon Monoxide	2024-11-06	Carbon Monoxide	6	8-hour Average	9	Air Quality	2024-11-07 10:06:55.377156	Emissions from vehicles and machinery	8-Hour	ppm	2024-11-07 10:06:55.377156	CO Standard
\.


--
-- Data for Name: privileges; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.privileges (id, name) FROM stdin;
1	ROLE_ADMIN
2	ROLE_USER
3	ROLE_MINE_ADMIN
4	VIEW_ENVIRONMENTAL_DATA
5	EDIT_ENVIRONMENTAL_DATA
6	VIEW_SAFETY_DATA
7	EDIT_SAFETY_DATA
8	VIEW_PRODUCTION_DATA
9	EDIT_PRODUCTION_DATA
\.


--
-- Data for Name: provinces; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.provinces (id, abbreviation, name) FROM stdin;
1	NL	Newfoundland and Labrador
2	BC	British Columbia
3	ON	Ontario
4	QC	Quebec
5	AB	Alberta
6	SK	Saskatchewan
7	NS	Nova Scotia
8	MB	Manitoba
9	YT	Yukon
10	NT	Northwest Territories
11	NU	Nunavut
\.


--
-- Data for Name: safety_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.safety_data (id, date_recorded, lost_time_incidents, near_misses, safety_level, mine_id, date, description) FROM stdin;
1	2024-10-01	2	3	FAIR	4	\N	\N
9	2024-10-02	1	2	GOOD	3	2024-10-02	Routine maintenance
10	2024-10-05	0	1	EXCELLENT	3	2024-10-05	Safe working conditions
11	2024-10-10	2	4	FAIR	5	2024-10-10	Minor incidents
12	2024-10-12	1	3	GOOD	3	2024-10-12	Heavy machinery incident
13	2024-10-15	0	0	EXCELLENT	5	2024-10-15	No incidents
14	2024-10-18	3	5	FAIR	3	2024-10-18	Dust and gas-related incidents
15	2024-10-20	2	2	FAIR	5	2024-10-20	Dust control issue
16	2024-10-23	1	2	GOOD	3	2024-10-23	Minor incidents resolved
17	2024-10-25	0	1	EXCELLENT	5	2024-10-25	Safe working period
18	2024-10-28	1	3	FAIR	3	2024-10-28	Fall incident resolved
\.


--
-- Data for Name: station_pollutants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.station_pollutants (station_id, pollutant_id) FROM stdin;
1	1
1	2
1	3
1	4
1	5
2	6
2	7
2	8
2	9
2	10
3	11
3	12
3	13
3	14
3	15
4	1
4	2
4	4
4	8
4	14
5	3
5	6
5	7
5	13
5	9
6	5
6	8
6	12
6	10
6	11
7	7
7	9
7	10
7	12
7	15
\.


--
-- Data for Name: user_privileges; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_privileges (id, privilege_id, user_id) FROM stdin;
1	1	1
2	2	1
3	3	1
4	4	1
5	5	1
6	6	1
7	7	1
8	8	1
9	9	1
10	3	2
11	4	2
12	6	2
13	7	2
14	8	2
15	9	2
16	2	3
17	4	3
18	6	3
19	8	3
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, password, username, role) FROM stdin;
5	defaultPassword	brenda	\N
6	password	ba007	\N
1	$2a$10$xP3Zdv0tV0FCJHvmWPo3MONuEKwmBRPXxXwHlhQNo7C2uHHg6dTu2	admin	admin
2	$2a$10$xP3Zdv0tV0FCJHvmWPo3MONuEKwmBRPXxXwHlhQNo7C2uHHg6dTu2	mine_admin	mine_admin
3	$2a$10$xP3Zdv0tV0FCJHvmWPo3MONuEKwmBRPXxXwHlhQNo7C2uHHg6dTu2	user	user
\.


--
-- Name: environmental_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.environmental_data_id_seq', 20, true);


--
-- Name: mine_minerals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mine_minerals_id_seq', 36, true);


--
-- Name: minerals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.minerals_id_seq', 11, true);


--
-- Name: mines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mines_id_seq', 18, true);


--
-- Name: monitoring_stations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.monitoring_stations_id_seq', 7, true);


--
-- Name: pollutants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pollutants_id_seq', 22, true);


--
-- Name: privileges_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.privileges_id_seq', 9, true);


--
-- Name: provinces_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.provinces_id_seq', 11, true);


--
-- Name: safety_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.safety_data_id_seq', 18, true);


--
-- Name: user_privileges_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_privileges_id_seq', 19, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- Name: environmental_data environmental_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.environmental_data
    ADD CONSTRAINT environmental_data_pkey PRIMARY KEY (id);


--
-- Name: mine_minerals mine_minerals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mine_minerals
    ADD CONSTRAINT mine_minerals_pkey PRIMARY KEY (mine_id, mineral_id);


--
-- Name: minerals minerals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.minerals
    ADD CONSTRAINT minerals_pkey PRIMARY KEY (id);


--
-- Name: mines mines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mines
    ADD CONSTRAINT mines_pkey PRIMARY KEY (id);


--
-- Name: monitoring_stations monitoring_stations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.monitoring_stations
    ADD CONSTRAINT monitoring_stations_pkey PRIMARY KEY (id);


--
-- Name: pollutants pollutants_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pollutants
    ADD CONSTRAINT pollutants_pkey PRIMARY KEY (id);


--
-- Name: privileges privileges_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges
    ADD CONSTRAINT privileges_pkey PRIMARY KEY (id);


--
-- Name: provinces provinces_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.provinces
    ADD CONSTRAINT provinces_pkey PRIMARY KEY (id);


--
-- Name: safety_data safety_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.safety_data
    ADD CONSTRAINT safety_data_pkey PRIMARY KEY (id);


--
-- Name: privileges uk_m2tnonbcaquofx1ccy060ejyc; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.privileges
    ADD CONSTRAINT uk_m2tnonbcaquofx1ccy060ejyc UNIQUE (name);


--
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: user_privileges user_privileges_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_privileges
    ADD CONSTRAINT user_privileges_pkey PRIMARY KEY (id, user_id, privilege_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: mine_minerals fk3fwg6isikcw3yjpalgb89u3bu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mine_minerals
    ADD CONSTRAINT fk3fwg6isikcw3yjpalgb89u3bu FOREIGN KEY (mineral_id) REFERENCES public.minerals(id);


--
-- Name: environmental_data fk48lwuue82hfpbiq9yy4ho733; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.environmental_data
    ADD CONSTRAINT fk48lwuue82hfpbiq9yy4ho733 FOREIGN KEY (mine_id) REFERENCES public.mines(id);


--
-- Name: user_privileges fk4pjq82mghfm4ec8innfnfetrm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_privileges
    ADD CONSTRAINT fk4pjq82mghfm4ec8innfnfetrm FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_privileges fk8ht5860cyq8fsdbceqwnq517e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_privileges
    ADD CONSTRAINT fk8ht5860cyq8fsdbceqwnq517e FOREIGN KEY (privilege_id) REFERENCES public.privileges(id);


--
-- Name: environmental_data fk8xjrwxid8r1ikc20be9k2uq5s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.environmental_data
    ADD CONSTRAINT fk8xjrwxid8r1ikc20be9k2uq5s FOREIGN KEY (pollutant_id) REFERENCES public.pollutants(id);


--
-- Name: environmental_data fkc9f0eovywei5omiycl1xeosre; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.environmental_data
    ADD CONSTRAINT fkc9f0eovywei5omiycl1xeosre FOREIGN KEY (station_id) REFERENCES public.monitoring_stations(id);


--
-- Name: pollutants fkcq758a9u5m8jrj4bkby9xkljp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pollutants
    ADD CONSTRAINT fkcq758a9u5m8jrj4bkby9xkljp FOREIGN KEY (station_id) REFERENCES public.monitoring_stations(id);


--
-- Name: mines fkecu55g86m5q5as67vtftud5cj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mines
    ADD CONSTRAINT fkecu55g86m5q5as67vtftud5cj FOREIGN KEY (province_id) REFERENCES public.provinces(id);


--
-- Name: station_pollutants fki9wthajg3ybnr1unla0f55gc5; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.station_pollutants
    ADD CONSTRAINT fki9wthajg3ybnr1unla0f55gc5 FOREIGN KEY (station_id) REFERENCES public.monitoring_stations(id);


--
-- Name: station_pollutants fkkdhthy7mm9mh15r00qsctof26; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.station_pollutants
    ADD CONSTRAINT fkkdhthy7mm9mh15r00qsctof26 FOREIGN KEY (pollutant_id) REFERENCES public.pollutants(id);


--
-- Name: mine_minerals fkkpu0euqkui5n6eac2f9ubx4hp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mine_minerals
    ADD CONSTRAINT fkkpu0euqkui5n6eac2f9ubx4hp FOREIGN KEY (mine_id) REFERENCES public.mines(id);


--
-- Name: monitoring_stations fkmw2ilmp75yyls4ith4j30j1x0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.monitoring_stations
    ADD CONSTRAINT fkmw2ilmp75yyls4ith4j30j1x0 FOREIGN KEY (province_id) REFERENCES public.provinces(id);


--
-- Name: safety_data fkrplm0l123luplg1clfuvyviat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.safety_data
    ADD CONSTRAINT fkrplm0l123luplg1clfuvyviat FOREIGN KEY (mine_id) REFERENCES public.mines(id);


--
-- PostgreSQL database dump complete
--

