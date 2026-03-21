# Historical & Genealogical Database: Registry of Apoikia/Andros

📋 System Overview

**Project:** A normalized, relational database and web application to store and visualize historical, geographical, and genealogical data from the Registry of Males in Apoikia/Andros (1845-1925), including older historical references (like the Ottoman Census of 1670).

**Tech Stack (Backend):** Java Spring Boot, PostgreSQL, Spring Data JPA, Spring Security, Spring Batch, Caffeine Cache, Springdoc OpenAPI.
**Tech Stack (Frontend):** React.js (SPA), React Router, Context API/Zustand, standard CSS (Component-scoped), OpenStreetMap API.
**Deployment:** Dockerized on a Linux VM behind an Nginx reverse proxy (with HTTPS), managed via CI/CD.

**Goal:** Transform complex, denormalized Excel/CSV records into a structured relational network capturing family lineages, historical wealth, socio-economic standing, document sources, and social events, while providing a fast, modern, and highly interactive user interface.

---

## 🎨 1. User Interface & Experience (UI/UX)

### Color Theme
The application features a localized, thematic color palette:
* **Primary Color:** Bright cyan (representing the Aegean beach water).
* **Accent/Action Color:** Orange-yellow (representing the dry bushes of the Aegean Islands).

### Landing Page Layout
The home page features a horizontal split background:
* **Top Half (Image Background):** * Transparent hero navigation.
    * Top-Left: Cyan AndrosDB logo.
    * Top-Right: Orange-yellow "Admin" login button.
    * Center: Logo + "Explore the Families of Andros over the Years".
    * Search Bar: Centered, white input field with an orange-yellow search button.
* **Bottom Half (Cyan Background):**
    * **Top Section:** "Explore" – Browse People, Events, Surnames, Churches, Places, and Sources via a structural table map.
    * **Bottom Section:** "Visualize" – "See how Families moved in Real Time" with a large orange-yellow action button.

### Search / Explore Page Layout
* **Top Section:** Transparent hero navigation, "Search Results" text, and the main search bar.
* **Bottom Section (Cyan Background):**
    * **Left Column (Filters):** Dynamic filtering options based on the active entity tab.
    * **Right Column (Results):**
        * Total record count.
        * 7 Navigation Tabs (People, Events, Surname, Surname Events, Churches, Places, Sources).
        * Data table with sortable columns.
        * **Pagination:** 30 records per page, dynamic ellipses (`< 1 2 3 ... 9 >`), and visually disabled arrows at boundaries.

### Visualize Page Layout
* **Header:** Standard transparent hero navigation.
* **Main Area:** Solid cyan background holding a large, fixed interactive OpenStreetMap of Andros.
* **Bottom Controls (Timeline Slider):** A horizontal slider representing the database's available years (e.g., 1670 - 1925).
* **Interactivity:** Dragging the slider dynamically updates the map to show population counts in specific villages/churches for that exact year.

---

## 💻 2. Frontend Architecture (React SPA)

* **Framework & Routing:** React.js configured as a Single-Page Application using **React Router** for seamless, instant page transitions without browser reloads.
* **State Management:** Utilizes React Context API (or Zustand) to manage complex cross-component state, specifically for map filters, timeline slider positions, and search result pagination.
* **Styling:** Component-scoped CSS to maintain modular design without global conflicts.
* **Data Fetching & Mapping:**
    * Asynchronous communication with the Spring Boot REST API.
    * **Visualize View:** Consumes a highly optimized, pre-aggregated JSON payload containing the entire timeline data, allowing the map slider to update instantly without network lag.

---

## 🏗️ 3. Backend Architecture & API Design

The backend acts as a robust RESTful API providing dynamic pagination and high-speed data delivery.

* **Search & Explore API:** Utilizes Spring Data JPA `Pageable` and Criteria API (Specifications) to handle complex, dynamic queries (e.g., `/api/v1/persons?keyword=Polemis&page=0&size=30`), returning flattened DTOs to avoid N+1 query problems and infinite recursion.
* **Visualization API & Caching:** The heavy timeline aggregation (calculating populations per year/village) is computed by a `VisualizationService` and cached in RAM using **Caffeine Cache**.
    * A `CacheWarmer` triggers on application startup to pre-build the dataset.
    * `@CacheEvict` is used on Admin update methods to instantly flush and rebuild the cache if historical data is modified.
* **API Documentation:** Integrated with **Springdoc OpenAPI (Swagger)** for auto-generated, interactive API documentation.
* **Testing:** Automated testing suite utilizing **JUnit 5**, **Mockito**, and **Testcontainers** for disposable PostgreSQL integration tests.

---

## ☕ 4. Spring Boot JPA Mapping (Domain Model)

### Core Enums
* `UserRole`: ADMIN, EDITOR.
* `OriginCategory`: HIGHER, MIDDLE, LOWER, MONK, UNKNOWN.
* `EventType`: MARRIAGE, BIRTH, BAPTISM, DEATH, CENSUS.
* `EventRole`: GROOM, BRIDE, PRIEST, GODFATHER, WITNESS, FATHER, MOTHER.
* `SurnameHistoryType`: FIRST_APPEARANCE_ANDROS, FIRST_APPEARANCE_APOIKIA, CENSUS_1670, OTHER_DOCUMENT.
* `Gender`: MALE, FEMALE.

### JPA Associations
* **Location:** `@ManyToOne` (parentLocation). `@OneToMany` (Churches, Events).
* **Church:** `@ManyToOne` (Location).
* **Source:** `@OneToMany` (Events, SurnameHistory).
* **Surname:** `@OneToMany` (SurnameHistory, Persons).
* **Person:** `@ManyToOne` (Surname, Father), `@OneToMany` (EventParticipation).
* **Event:** `@ManyToOne` (Location, Church, Source), `@OneToMany` (EventParticipation).

---

## 🚀 5. ETL & Data Import Strategy

A 4-step pipeline efficiently transforms flat Excel/CSV files into the normalized PostgreSQL schema:
1.  **Staging Database:** Bulk-load raw CSV data into a flat `staging_import` table.
2.  **Dictionary Initialization:** Run `SELECT DISTINCT` queries to populate `LOCATION`, `CHURCH`, `SOURCE`, and `SURNAME` to establish Primary Keys.
3.  **In-Memory Caching (Java):** Load core dictionaries into memory (`HashMap<String, Long>`) on Spring Boot startup.
4.  **Batch Processing:** Spring Batch reads the staging table, resolves Foreign Keys in O(1) time via the HashMaps, and persists `Person`, `Event`, and `EventParticipation` records using JDBC batch inserts.

---

## 💾 6. DevOps & Deployment

* **Hosting:** Linux Virtual Machine (VM) with Boot SSDs to ensure fast I/O during database JOIN operations.
* **Containerization:** The application is built using a Monorepo architecture and orchestrated via Docker Compose (`compose.yaml`).
* **Web Server & Security:** Nginx acts as a reverse proxy, secured with HTTPS via **Let's Encrypt / Certbot**.
* **CI/CD Pipeline:** Automated build and deployment pipeline (e.g., GitHub Actions) to run tests and push updated Docker images to the VM.
* **Automated Backups:** Scheduled cron jobs run `pg_dump` to create encrypted backups of the historical data, shipped to secure off-site storage.

---

## 🗄️ 7. PostgreSQL Schema

The schema utilizes `GENERATED ALWAYS AS IDENTITY` for efficient primary key management, audit columns (`created_at`, `updated_at`), and strategic indexing for high-performance searching.

```sql
-- 1. Locations
CREATE TABLE LOCATION (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_location_id INT,  
    type VARCHAR(50),        
    latitude DECIMAL(9,6),   
    longitude DECIMAL(9,6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_location_id) REFERENCES LOCATION(id)
);
CREATE INDEX idx_location_parent ON LOCATION(parent_location_id);

-- 2. Churches
CREATE TABLE CHURCH (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location_id INT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES LOCATION(id)
);
CREATE INDEX idx_church_location ON CHURCH(location_id);

-- 3. Surnames
CREATE TABLE SURNAME (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latin_name VARCHAR(255),
    origin_category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_surname_name ON SURNAME(name);

-- 4. Sources
CREATE TABLE SOURCE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    author VARCHAR(255),
    archive_name VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Surname History
CREATE TABLE SURNAME_HISTORY (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    surname_id INT NOT NULL,
    year INT,
    event_type VARCHAR(50),
    location_id INT,
    church_id INT,
    source_id INT,
    registry_number VARCHAR(255),
    ancestor_name VARCHAR(255),
    assets_text TEXT,
    assets_data JSONB,
    source_detail VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (surname_id) REFERENCES SURNAME(id),
    FOREIGN KEY (location_id) REFERENCES LOCATION(id),
    FOREIGN KEY (church_id) REFERENCES CHURCH(id),
    FOREIGN KEY (source_id) REFERENCES SOURCE(id)
);
CREATE INDEX idx_sh_surname ON SURNAME_HISTORY(surname_id);
CREATE INDEX idx_sh_year ON SURNAME_HISTORY(year);

-- 6. Persons
CREATE TABLE PERSON (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    surname_id INT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    father_name VARCHAR(255),
    father_id INT,
    gender VARCHAR(10),
    birth_year INT,
    death_year INT,
    occupation VARCHAR(255),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (surname_id) REFERENCES SURNAME(id),
    FOREIGN KEY (father_id) REFERENCES PERSON(id)
);
CREATE INDEX idx_person_surname ON PERSON(surname_id);
CREATE INDEX idx_person_father ON PERSON(father_id);
CREATE INDEX idx_person_birth ON PERSON(birth_year);

-- 7. Events
CREATE TABLE EVENT (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_type VARCHAR(50) NOT NULL,
    event_date DATE,
    year_only INT,
    location_id INT,
    church_id INT,
    source_id INT,
    source_detail VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES LOCATION(id),
    FOREIGN KEY (church_id) REFERENCES CHURCH(id),
    FOREIGN KEY (source_id) REFERENCES SOURCE(id)
);
CREATE INDEX idx_event_type ON EVENT(event_type);
CREATE INDEX idx_event_year ON EVENT(year_only);

-- 8. Event Participation
CREATE TABLE EVENT_PARTICIPATION (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    event_id INT NOT NULL,
    person_id INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES EVENT(id),
    FOREIGN KEY (person_id) REFERENCES PERSON(id)
);
CREATE INDEX idx_ep_event ON EVENT_PARTICIPATION(event_id);
CREATE INDEX idx_ep_person ON EVENT_PARTICIPATION(person_id);

-- 9. Application Users
CREATE TABLE APP_USER (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);