alter table events
add column organizerEmail VARCHAR(255),
add column ticketPrice DOUBLE PRECISION NOT NULL DEFAULT 0