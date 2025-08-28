-- 1) Group Table
CREATE TABLE group_dtl (
    group_id              BIGSERIAL PRIMARY KEY,
    group_name            VARCHAR(255) NOT NULL,
    group_amount          NUMERIC(12,2) DEFAULT 0,
    group_spent           NUMERIC(12,2) DEFAULT 0,
    group_balance         NUMERIC(12,2) DEFAULT 0,
    group_currency        VARCHAR(20) DEFAULT 'INR',
    country               VARCHAR(100) DEFAULT 'India',
    purpose               VARCHAR(255),
    usage_type            VARCHAR(50), -- Payments | Planning | Saving
    category              VARCHAR(50), -- Family | Friends | Colleagues | Others
    group_max_limit       NUMERIC(12,2),
    daily_usage_limit     NUMERIC(12,2),
    per_person_limit      NUMERIC(12,2),
    per_transaction_limit NUMERIC(12,2),
    created_by            BIGINT,
    created_timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_by       BIGINT,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2) Member Profile Table
CREATE TABLE member_profile (
    member_id   BIGSERIAL PRIMARY KEY,
    member_name VARCHAR(255),
    relation    VARCHAR(100),
    mobile      BIGINT UNIQUE NOT NULL,
    show_only_admin_groups BOOLEAN,
    email       VARCHAR(255),
    avatar_url  TEXT
);


-- 3) Group-Member-Relation Table
CREATE TABLE group_member_relation (
    id             BIGSERIAL PRIMARY KEY,
    group_id       BIGINT NOT NULL REFERENCES "group_dtl"(group_id) ON DELETE CASCADE,
    member_id      BIGINT ,
    is_admin       BOOLEAN DEFAULT FALSE,
    mobile         BIGINT NOT NULL,
    role           VARCHAR(20) CHECK (role IN ('Admin','Member','Viewer')),
    amount_added   NUMERIC(12,2) DEFAULT 0,
    amount_spent   NUMERIC(12,2) DEFAULT 0,
    joined_by      VARCHAR(20) CHECK (joined_by IN ('Link','QR','Code')),
    nick_name      VARCHAR(100),
    relation_color VARCHAR(20) CHECK (relation_color IN ('Green','Orange','Red'))
);


-- 4) Transaction Table
CREATE TABLE transaction (
    transaction_id      BIGSERIAL PRIMARY KEY,
    group_id            BIGINT NOT NULL REFERENCES "group_dtl"(group_id) ON DELETE CASCADE,
    payer_id            BIGINT NOT NULL REFERENCES member_profile(member_id),
    receiver_id         BIGINT,
    receiver_type       VARCHAR(50),
    receiver_name       VARCHAR(255),
    receiver_account    VARCHAR(255),
    included_members    JSONB,
    amount              NUMERIC(12,2) NOT NULL,
    created_timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_by     VARCHAR(100),
    is_cash_transaction BOOLEAN DEFAULT FALSE,
    image_id            BIGSERIAL,
    category            VARCHAR(100),
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


CREATE TABLE transaction_images (
    image_id BIGSERIAL PRIMARY KEY,
    transaction_id BIGINT NOT NULL REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);