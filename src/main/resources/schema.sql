CREATE SCHEMA VIDEOTHEQUE_SCHEMA AUTHORIZATION SA;

use VIDEOTHEQUE_SCHEMA;

CREATE TABLE actor (
    id   INTEGER   PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    bio VARCHAR(255)
);

CREATE TABLE category (
    id   INTEGER   PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE film (
    id   INTEGER   PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50),
    "year" INTEGER,
    description VARCHAR(255),
    release_date DATE,
    duration INTEGER,
    actor_id INT,
    category_id INT,
    FOREIGN KEY (actor_id) REFERENCES actor(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE film_actor (
	film_id INT,
	actor_id INT,
	primary key (film_id, actor_id),
    foreign key (film_id) references film(id),
    foreign key (actor_id) references actor(id)
);