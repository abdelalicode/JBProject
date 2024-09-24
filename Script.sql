CREATE DATABASE baticuisine;


CREATE TABLE Client (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        address VARCHAR(255),
                        phoneNumber VARCHAR(15),
                        isProfessional BOOLEAN DEFAULT FALSE
);

CREATE TYPE ProjectStatus AS ENUM ('Ongoing', 'Completed', 'Canceled');

CREATE TABLE Project (
                         id SERIAL PRIMARY KEY,
                         projectName VARCHAR(255) NOT NULL,
                         surfaceArea NUMERIC(5, 2) DEFAULT NULL,
                         profitMargin NUMERIC(5, 2) DEFAULT NULL,
                         totalCost NUMERIC(10, 2) DEFAULT 0.00,
                         projectStatus ProjectStatus DEFAULT 'Ongoing' NOT NULL,
                         clientID INT REFERENCES Client(id)
);

CREATE TABLE Component (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           componentType VARCHAR(255) NOT NULL,
                           tvaRate NUMERIC(5, 2) DEFAULT NULL,
                           projectID INT REFERENCES Project(id)
);

CREATE TABLE Material (
                          quantity NUMERIC(10, 2) NOT NULL,
                          unitCost NUMERIC(10, 2) NOT NULL,
                          transportCost NUMERIC(10, 2) NOT NULL,
                          qualityCoefficient NUMERIC(5, 2) NOT NULL
) INHERITS (Component);

CREATE TABLE Labor (
                       laborType VARCHAR(100) NOT NULL,
                       hourlyRate NUMERIC(10, 2) NOT NULL,
                       WorkHours NUMERIC(5, 2) NOT NULL,
                       WorkerProductivity NUMERIC(5, 2) NOT NULL
) INHERITS (Component);


CREATE TABLE Devis (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       estimatedAmount NUMERIC(10, 2) NOT NULL,
                       issueDate DATE NOT NULL,
                       validityDate DATE NOT NULL,
                       accepted BOOLEAN DEFAULT FALSE,
                       projectID INT REFERENCES Project(id)
);
