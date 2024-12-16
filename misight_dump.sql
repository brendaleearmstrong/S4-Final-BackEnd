-- PostgreSQL database dump
-- Adjusted for dependency order and proper restoration.

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Create custom ENUM type
CREATE TYPE public.userrole AS ENUM (
    'ADMIN',
    'MINE_ADMIN',
    'USER'
);

ALTER TYPE public.userrole OWNER TO postgres;

-- Create sequences before tables
CREATE SEQUENCE public.environmental_data_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.mine_minerals_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.minerals_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.mines_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.monitoring_stations_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.pollutants_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.privileges_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.provinces_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.safety_data_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.user_privileges_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE public.users_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

-- Assign sequence ownership to corresponding tables later.

-- Create tables
CREATE TABLE public.environmental_data (
    id bigint NOT NULL DEFAULT nextval('public.environmental_data_id_seq'),
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

CREATE TABLE public.mine_minerals (
    mine_id bigint NOT NULL,
    mineral_id bigint NOT NULL
);

CREATE TABLE public.minerals (
    id bigint NOT NULL DEFAULT nextval('public.minerals_id_seq'),
    name character varying(255)
);

CREATE TABLE public.mines (
    id bigint NOT NULL DEFAULT nextval('public.mines_id_seq'),
    company character varying(255),
    location character varying(255),
    name character varying(255),
    province_id bigint
);

CREATE TABLE public.monitoring_stations (
    id bigint NOT NULL DEFAULT nextval('public.monitoring_stations_id_seq'),
    location character varying(255),
    name character varying(255),
    province_id bigint NOT NULL,
    station_name character varying(255)
);

CREATE TABLE public.pollutants (
    id bigint NOT NULL DEFAULT nextval('public.pollutants_id_seq'),
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

CREATE TABLE public.privileges (
    id bigint NOT NULL DEFAULT nextval('public.privileges_id_seq'),
    name character varying(255) NOT NULL
);

CREATE TABLE public.provinces (
    id bigint NOT NULL DEFAULT nextval('public.provinces_id_seq'),
    abbreviation character varying(255),
    name character varying(255)
);

CREATE TABLE public.safety_data (
    id bigint NOT NULL DEFAULT nextval('public.safety_data_id_seq'),
    date_recorded date NOT NULL,
    lost_time_incidents integer,
    near_misses integer,
    safety_level character varying(255),
    mine_id bigint,
    date date,
    description character varying(255),
    CONSTRAINT safety_data_safety_level_check CHECK (
        ((safety_level)::text = ANY (
            (ARRAY['GOOD', 'FAIR', 'NEEDS_IMPROVEMENT', 'EXCELLENT', 'CRITICAL'])::text[]
        ))
    )
);

CREATE TABLE public.user_privileges (
    id integer NOT NULL DEFAULT nextval('public.user_privileges_id_seq'),
    privilege_id integer NOT NULL,
    user_id integer NOT NULL
);

CREATE TABLE public.users (
    id bigint NOT NULL DEFAULT nextval('public.users_id_seq'),
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    role public.userrole NOT NULL
);

-- Add functions
CREATE FUNCTION public.generate_production_variance(base_target numeric, variance_percent numeric) RETURNS numeric
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN base_target * (1 + (random() * variance_percent - variance_percent / 2));
END;
$$;

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
    ) * (1 + (random() * variance_percent - variance_percent / 2));
END;
$$;

-- Finally, insert data using COPY commands
COPY public.environmental_data (id, date_recorded, level, pollutant_type, mine_id, station_id, created_at, measured_value, measurement_date, notes, updated_at, pollutant_id) FROM stdin;
1	2024-11-10	1	Total Dust	2	3	2024-11-13 16:35:42.708871	3.8	2024-11-10 00:00:00	Quarterly check	2024-11-13 16:35:42.708871	1
2	2024-11-10	1.5	PM10	4	4	2024-11-13 16:35:42.708871	18	2024-11-10 00:00:00	Increased mining activity	2024-11-13 16:35:42.708871	11
-- Add all the remaining COPY data here.
\.

