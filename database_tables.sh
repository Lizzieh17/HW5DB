mysql <<EOFMYSQL
use lal013;
show tables;

DROP TABLE IF EXISTS Building;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Assignment;

CREATE TABLE Building(
    buildingID INT PRIMARY KEY, 
    name CHAR(40) NOT NULL, 
    address CHAR(60) NOT NULL, 
    hasAC BOOLEAN NOT NULL,
    hasDining BOOLEAN NOT NULL
);

CREATE TABLE Room(
    roomID INT PRIMARY KEY,
    buildingID INT NOT NULL,
    numBeds INT NOT NULL,
    hasPrivateBaths BOOLEAN NOT NULL,
    hasKitchen BOOLEAN NOT NULL,
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID) ON DELETE CASCADE
);

CREATE TABLE Student(
    studentID INT PRIMARY KEY,
    name CHAR(40) NOT NULL,
    wantsAC BOOLEAN NOT NULL,
    wantsDining BOOLEAN NOT NULL,
    wantsKitchen BOOLEAN NOT NULL,
    wantsPrivateBath BOOLEAN NOT NULL
);

CREATE TABLE Assignment(
    studentID INT PRIMARY KEY,
    buildingID INT NOT NULL,
    roomID INT NOT NULL,
    FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE,
    FOREIGN KEY (buildingID) REFERENCES Building(buildingID) ON DELETE CASCADE,
    FOREIGN KEY (roomID) REFERENCES Room(roomID) ON DELETE CASCADE,
    UNIQUE(studentID, roomID)
);

INSERT INTO
    Building (buildingID, name, address, hasAC, hasDining)
VALUES
    (1, 'Maple Hall', '100 University Ave', TRUE, TRUE),
    (2, 'Oak Tower', '200 College Blvd', FALSE, TRUE),
    (3, 'Pine Residence', '150 Campus Drive', TRUE, FALSE);

INSERT INTO Room (roomID, buildingID, numBeds, hasPrivateBaths, hasKitchen)
VALUES  
    -- Maple Hall (AC + Dining)
    (101, 1, 2, FALSE, TRUE),   -- Shared bath, kitchen
    (102, 1, 1, TRUE, FALSE),   -- Private bath, no kitchen
    (103, 1, 3, FALSE, TRUE),    -- Triple room, shared bath

    -- Oak Tower (No AC, has Dining)
    (201, 2, 2, FALSE, FALSE),  -- Shared bath, no kitchen
    (202, 2, 1, TRUE, TRUE),    -- Private bath, kitchen (premium)
    (203, 2, 4, FALSE, FALSE),  -- Quad room, shared bath

    -- Pine Residence (AC, no Dining)
    (301, 3, 1, TRUE, TRUE),    -- Single, private bath, kitchen
    (302, 3, 2, FALSE, TRUE);   -- Shared bath, kitchen

INSERT INTO Student (studentID, name, wantsAC, wantsDining, wantsKitchen, wantsPrivateBath)
VALUES
    (1001, 'Alice Johnson', TRUE, TRUE, FALSE, TRUE),   -- Prefers AC + dining + private bath
    (1002, 'Bob Smith', FALSE, TRUE, TRUE, FALSE),       -- No AC, wants dining + kitchen
    (1003, 'Charlie Brown', TRUE, FALSE, TRUE, TRUE),    -- AC + kitchen + private bath
    (1004, 'Diana Lee', TRUE, TRUE, TRUE, TRUE),         -- Wants all amenities (premium)
    (1005, 'Ethan Davis', FALSE, FALSE, FALSE, FALSE);   -- Budget option (no preferences)
 
INSERT INTO Assignment ()
VALUES
    -- Alice (wants AC + dining + private bath) → Maple Hall 102 (private bath)
    (1001, 1, 102),

    -- Bob (no AC, wants dining + kitchen) → Oak Tower 202 (kitchen + dining)
    (1002, 2, 202),

    -- Charlie (AC + kitchen + private bath) → Pine Residence 301 (best match)
    (1003, 3, 301),

    -- Diana (wants everything) → Maple Hall 102 (but already taken, so next best)
    -- (Assuming we assign her to another premium room)
    (1004, 3, 301),  -- Conflict! (Room 301 already taken by Charlie)

    -- Ethan (no preferences) → Cheapest available (Oak Tower 201)
    (1005, 2, 201);

EOFMYSQL