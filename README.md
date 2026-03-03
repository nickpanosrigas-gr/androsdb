Historical & Genealogical Database: Registry of Apoikia/Andros
📋 System Overview

Project: A normalized, relational database and web application to store and visualize historical, geographical, and genealogical data from the Registry of Males in Apoikia/Andros (1845-1925), including older historical references (like the Ottoman Census of 1670).

Tech Stack (Backend): Java Spring Boot, PostgreSQL, Spring Data JPA, Spring Security, Spring Batch.

Tech Stack (Frontend): React.js (Single-Page Application), standard CSS (Component-scoped), OpenStreetMap API.

Deployment: Dockerized on a Linux VM behind an existing Nginx reverse proxy.

Goal: Transform complex, denormalized Excel/CSV records into a structured relational network capturing family lineages, historical wealth/assets, socio-economic standing, document sources, and social events, while providing a fast, modern, and highly interactive user interface.
🎨 1. User Interface & Experience (UI/UX)
Color Theme

The application features a localized, thematic color palette:

    Primary Color: Bright cyan (representing the Aegean beach water).

    Accent/Action Color: Orange-yellow (representing the dry bushes of the Aegean Islands).

Landing Page Layout

The home page features a horizontal split background:

    Top Half (Image Background):

        Features a transparent hero navigation section.

        Top-Left: Cyan AndrosDB logo (clickable to return home).

        Top-Right: Orange-yellow "Admin" button for database editors to log in.

        Center: The AndrosDB logo, accompanied by the text "Explore the Families of Andros over the Years".

        Search Bar: Centered below the text. The input field is white, and the search button is orange-yellow.

    Bottom Half (Cyan Background): Split into two sections:

        Top Section (2/3 height): Titled "Explore" with a sub-heading "Browse People, Events, Surname, Surname Events, Churches, Places and Sources". Features a structural table mapping out these 7 core entities.

        Bottom Section (1/3 height): Titled "Visualize" with a sub-heading "See how Families moved in Real Time", accompanied by a large orange-yellow "Visualize" action button.

Search / Explore Page Layout

    Top Section (1/3 height - Image Background):

        Uses the same transparent hero navigation (Cyan logo left, Orange-Yellow Admin button right).

        Center text: "Search Results" and "Enter a keyword to narrow down the results below".

        Features the exact same white/orange-yellow search bar.

    Bottom Section (2/3 height - Cyan Background): Split vertically into two columns:

        Left Column (Filters): Displays a title "Filters", followed by dynamic filtering options that change depending on which entity tab is currently active.

        Right Column (Results & Pagination):

            Top row displays the total record count (e.g., "8567 Results").

            Directly below is a row of 7 Navigation Tabs: People, Events, Surname, Surname Events, Churches, Places, Sources.

            The main area renders the data table for the active tab. Column headers are clickable to allow for ordered sorting.

            Pagination:

                Results are limited to 30 records per page.

                Displayed at the very bottom of the results table.

                The current active page is highlighted and kept as close to the middle as possible using dynamic ellipses (e.g., if on page 1: < 1 2 3 4 5 ... > or if on page 5: < ... 3 4 5 6 7 ... >).

                Directional arrows (<, >) are highlighted when active and visually disabled when navigating further is impossible (e.g., < is disabled on page 1).

Visualize Page Layout

    Header: Uses the exact same transparent hero navigation as the other pages (Cyan logo left, Orange-Yellow Admin button right).

    Main Area (Map):

        The background of the entire page is solid cyan.

        A large, fixed interactive map of Andros powered by OpenStreetMap fills almost the entire screen.

    Bottom Controls (Timeline Slider):

        Positioned at the bottom of the screen is a horizontal timeline slider.

        The endpoints of the slider represent the very first and very last years available in the database.

        The currently selected year is displayed dynamically right above the slider thumb.

    Interactivity: As the user drags the slider to a specific year, the map instantly updates to show the number of people living in each village or church for that specific year.

💻 2. Frontend Architecture (React SPA)

    Framework: React.js configured as a Single-Page Application (SPA) serving a single index.html file to ensure instant, fluid page transitions without browser reloads.

    Styling: Component-scoped CSS files (e.g., SearchBar.css, FilterSidebar.css) to maintain organized, modular design without global CSS conflicts.

    Data Fetching & Mapping:

        The frontend communicates asynchronously with the Spring Boot backend using fetch() (or Axios).

        Visualize View: The map page consumes a specialized Data Transfer Object (DTO) from the API. This DTO pre-packages the data, providing an array of years, and for each year, a list of villages/churches with their respective latitude, longitude, and total population count. This ensures the slider can update the map smoothly without lagging.

    API Integration: The backend exposes RESTful API endpoints (e.g., /api/persons, /api/visualize). The React app consumes this data strictly in JSON format and dynamically renders the UI state.

    Cross-Origin Resource Sharing (CORS): Spring Boot will be configured to allow HTTP requests from the React frontend port during development.

🏗️ 3. Backend Architecture & ERD

The database separates core identities (Surnames, Persons) from their historical footprint (Surname History, Events), geography (Locations, Churches), bibliography (Sources), and system access (Users).
Relationships Summary:

    Locations & Churches: Hierarchical locations (Island -> Village). Churches belong to a Location. Composition is used over inheritance.

    Sources: A centralized bibliography table. Events and Historical Records reference a Source ID along with specific details.

    Lineage: Persons reference themselves (Father -> Child).

    History Log: Surnames have many Historical Records (1670 Census, 1st Appearance). These records can link to a specific Church, include a Registry Number (Αριθμός Μητρώου), and cite a Source.

    Social Network: Many-to-Many between Persons and Events, mapped by EventParticipation.

    Security: An isolated APP_USER table manages system access and roles (e.g., ADMIN).

☕ 4. Spring Boot JPA Mapping (Domain Model)
Core Enums

To maintain strict data integrity in the Java layer:

    UserRole: ADMIN (Δικαιώματα διαχειριστή συστήματος).

    OriginCategory: HIGHER, MIDDLE, LOWER, MONK, UNKNOWN (Καταγωγή).

    EventType: MARRIAGE, BIRTH, BAPTISM, DEATH, CENSUS.

    EventRole: GROOM, BRIDE, PRIEST, GODFATHER, WITNESS, FATHER, MOTHER.

    SurnameHistoryType: FIRST_APPEARANCE_ANDROS, FIRST_APPEARANCE_APOIKIA, CENSUS_1670, OTHER_DOCUMENT.

    Gender: MALE, FEMALE.

JPA Associations

    AppUser: Independent entity used by Spring Security for authentication.

    Location: @ManyToOne to itself (parentLocation). @OneToMany to Church, Event, and SurnameHistory.

    Church: @ManyToOne to Location.

    Source: @OneToMany to Event and SurnameHistory.

    Surname: @OneToMany to SurnameHistory and Person.

    SurnameHistory: @ManyToOne to Surname, Location, Church, and Source.

    Person: @ManyToOne to Surname, @ManyToOne to itself (father), @OneToMany to EventParticipation.

    Event: @ManyToOne to Location, Church, and Source. @OneToMany to EventParticipation.

    EventParticipation: @ManyToOne to Event and @ManyToOne to Person.

🚀 5. ETL & Data Import Strategy

Transforming the flat Excel/CSV files into this normalized PostgreSQL database efficiently requires a 4-step pipeline to avoid JPA overhead (N+1 select issues):

    Staging Database: Bulk-load the raw CSV data into a flat staging_import PostgreSQL table (COPY command or fast bulk insert).

    Dictionary Initialization: Run SQL INSERT INTO ... SELECT DISTINCT queries on the staging table to populate LOCATION, CHURCH, SOURCE, and SURNAME first. This establishes Primary Keys.

    In-Memory Caching (Java): On Spring Boot startup, load all Locations, Churches, Sources, and Surnames into memory (HashMap<String, Long>).

    Batch Processing: Spring Batch reads staging_import row-by-row. It resolves foreign keys via the HashMaps (O(1) lookup), creates Person and Event objects, and persists the network into EventParticipation using JDBC batch inserts.

💾 6. Deployment & Hardware Considerations

    Hosting Environment: The application will be deployed on a Linux Virtual Machine (VM).

    Web Server / Reverse Proxy: The Linux VM already runs an instance of Nginx, which will act as a reverse proxy. It will handle incoming web traffic and route requests appropriately to the backend API or frontend container.

    Containerization: The application (Spring Boot, React, PostgreSQL) is built using a Monorepo architecture and will be orchestrated via Docker Compose (compose.yaml).

    Storage: Should be hosted on a Boot SSD. Even though the data size is small (estimated 15 MB - 50 MB), SSDs are critical for fast I/O during the complex Multi-Table JOIN operations required by the normalized network structure and for the heavy write-transactions during the initial ETL import process.

🗄️ 7. PostgreSQL Schema
SQL

-- 1. Locations (Hierarchical geographical data)
CREATE TABLE LOCATION (
id INT PRIMARY KEY,
name VARCHAR(255),
parent_location_id INT,  -- Self-referencing FK (e.g., Apoikia belongs to Andros)
type VARCHAR(50),        -- 'ISLAND', 'VILLAGE', 'PARISH', etc.
latitude DECIMAL(9,6),   -- GPS Coordinates
longitude DECIMAL(9,6),
FOREIGN KEY (parent_location_id) REFERENCES LOCATION(id)
);

-- 2. Churches (Specific religious landmarks within a location)
CREATE TABLE CHURCH (
id INT PRIMARY KEY,
name VARCHAR(255),       -- e.g., "Agios Nikolaos"
location_id INT,
notes TEXT,
FOREIGN KEY (location_id) REFERENCES LOCATION(id)
);

-- 3. Surnames (The core family identity)
CREATE TABLE SURNAME (
id INT PRIMARY KEY,
name VARCHAR(255),
latin_name VARCHAR(255),           
origin_category VARCHAR(100) -- e.g., 'χαμηλότερη', 'υψηλότερη'
);

-- 4. Sources (Bibliography and Documents)
CREATE TABLE SOURCE (
id INT PRIMARY KEY,
title VARCHAR(500) NOT NULL,    -- e.g., "Μητρώο Αρρένων Αποικίων"
author VARCHAR(255),            -- e.g., "Δημήτριος Πολέμης"
archive_name VARCHAR(255),
notes TEXT
);

-- 5. Surname History (Timeline logbook: 1670 census, 1st appearances)
CREATE TABLE SURNAME_HISTORY (
id INT PRIMARY KEY,
surname_id INT,
year INT,
event_type VARCHAR(50),
location_id INT,
church_id INT,                      -- Nullable: Specific church
source_id INT,                      -- Link to Source Document
registry_number VARCHAR(255),       -- Αριθμός Μητρώου (e.g., '136_1670_6')
ancestor_name VARCHAR(255),
assets_text TEXT,
assets_data JSONB,
source_detail VARCHAR(255),         -- Specific page/entry (e.g., "σελ. 94")
notes TEXT,
FOREIGN KEY (surname_id) REFERENCES SURNAME(id),
FOREIGN KEY (location_id) REFERENCES LOCATION(id),
FOREIGN KEY (church_id) REFERENCES CHURCH(id),
FOREIGN KEY (source_id) REFERENCES SOURCE(id)
);

-- 6. Persons (Individuals)
CREATE TABLE PERSON (
id INT PRIMARY KEY,
surname_id INT,
first_name VARCHAR(255),
father_name VARCHAR(255),
father_id INT,           -- Self-referencing FK for direct lineage
gender VARCHAR(10),      -- 'MALE', 'FEMALE'
birth_year INT,
death_year INT,
occupation VARCHAR(255),
comments TEXT,
FOREIGN KEY (surname_id) REFERENCES SURNAME(id),
FOREIGN KEY (father_id) REFERENCES PERSON(id)
);

-- 7. Events (Marriages, Baptisms, Births)
CREATE TABLE EVENT (
id INT PRIMARY KEY,
event_type VARCHAR(50),
event_date DATE,
year_only INT,
location_id INT,
church_id INT,           -- Nullable
source_id INT,           -- Link to Source Document
source_detail VARCHAR(255), -- Specific act number (e.g., "Γάμος 4942")
FOREIGN KEY (location_id) REFERENCES LOCATION(id),
FOREIGN KEY (church_id) REFERENCES CHURCH(id),
FOREIGN KEY (source_id) REFERENCES SOURCE(id)
);

-- 8. Event Participation (The Junction Table linking People to Events)
CREATE TABLE EVENT_PARTICIPATION (
id INT PRIMARY KEY,
event_id INT,
person_id INT,
role VARCHAR(50),        -- 'GROOM', 'BRIDE', 'PRIEST', 'GODFATHER'
FOREIGN KEY (event_id) REFERENCES EVENT(id),
FOREIGN KEY (person_id) REFERENCES PERSON(id)
);

-- 9. Application Users (Διαχειριστές, Ερευνητές, κλπ.)
CREATE TABLE APP_USER (
id INT PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,  -- Εδώ θα αποθηκεύεται το Hashed Password (π.χ. BCrypt)
role VARCHAR(50) NOT NULL,       -- π.χ. 'ADMIN'
email VARCHAR(255) UNIQUE,
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
