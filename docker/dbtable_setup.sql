-- 1) Wallet Table
CREATE TABLE wallet (
    wallet_id            SERIAL PRIMARY KEY,
    wallet_name          VARCHAR(100) NOT NULL,
    wallet_amount        NUMERIC(12,2) DEFAULT 0,
    wallet_spent         NUMERIC(12,2) DEFAULT 0,
    wallet_balance       NUMERIC(12,2) DEFAULT 0,
    wallet_currency      VARCHAR(10) DEFAULT 'INR',
    country              VARCHAR(50) DEFAULT 'India',
    purpose              VARCHAR(255),
    usage_type           VARCHAR(20) CHECK (usage_type IN ('Payments','Planning','Saving')),
    category             VARCHAR(20) CHECK (category IN ('Family','Friends','Colleagues','Others')),
    wallet_max_limit     NUMERIC(12,2),
    daily_usage_limit    NUMERIC(12,2),
    per_person_limit     NUMERIC(12,2),
    per_transaction_limit NUMERIC(12,2),
    created_by           VARCHAR(50),
    created_timestamp    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_by      VARCHAR(50),
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2) Member Profile Table
CREATE TABLE member_profile (
    member_id       SERIAL PRIMARY KEY,
    member_name     VARCHAR(100) NOT NULL,
    relation        VARCHAR(50),
    mobile          VARCHAR(15) UNIQUE NOT NULL,
    email           VARCHAR(100),
    avatar_url      TEXT
);

-- 3) Wallet-Partner-Relation Table
CREATE TABLE wallet_partner_relation (
    id              SERIAL PRIMARY KEY,
    wallet_id       INT NOT NULL REFERENCES wallet(wallet_id) ON DELETE CASCADE,
    member_id       INT NOT NULL REFERENCES member_profile(member_id) ON DELETE CASCADE,
    is_admin        BOOLEAN DEFAULT FALSE,
    role            VARCHAR(20) CHECK (role IN ('Admin','Member','Viewer')),
    amount_added    NUMERIC(12,2) DEFAULT 0,
    amount_spent    NUMERIC(12,2) DEFAULT 0,
    joined_by       VARCHAR(20) CHECK (joined_by IN ('Link','QR','Code')),
    nick_name       VARCHAR(50),
    relation_color  VARCHAR(20) CHECK (relation_color IN ('Green','Orange','Red'))
);

-- 4) Transaction Table
CREATE TABLE transaction (
    transaction_id      SERIAL PRIMARY KEY,
    wallet_id           INT NOT NULL REFERENCES wallet(wallet_id) ON DELETE CASCADE,
    payer_id            INT NOT NULL REFERENCES member_profile(member_id),
    receiver_id         INT, -- optional user or external
    receiver_type       VARCHAR(50), 
    receiver_name       VARCHAR(100),
    receiver_account    VARCHAR(100), -- optional / encrypted storage recommended
    included_members    JSONB, -- stores array of member IDs
    amount              NUMERIC(12,2) NOT NULL,
    created_timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_by     VARCHAR(50),
    is_cash_transaction BOOLEAN DEFAULT FALSE,
    category            VARCHAR(50),
    status              VARCHAR(20) CHECK (status IN ('Pending','Completed','Failed','Cancelled')),
    description         TEXT,
    transaction_type    VARCHAR(20) CHECK (transaction_type IN ('Add','Pay')),
    metadata            JSONB,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5) Roles Table (reference if needed)
CREATE TABLE roles (
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(20) UNIQUE NOT NULL CHECK (role_name IN ('Admin','Member','Viewer'))
);


INSERT INTO Roles (role_id, role_name) VALUES
(1, 'Admin'),
(2, 'Viewer'),
(3, 'Member');