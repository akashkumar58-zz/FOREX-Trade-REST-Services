drop table users if exists;
drop view mov_avg if exists;
drop table HIST_REUTERS if exists;
drop table orders if exists;

create table users
(
	id int(11) not null AUTO_INCREMENT,
	username varchar(100) default null,
	password varchar(14) default null,
	PRIMARY KEY (id)
);

create table orders
(
	order_id int(12) AUTO_INCREMENT,
	order_type varchar(10),
	username varchar(100),
	currency_pair varchar(10),
	order_side varchar(10),
	price decimal(10,2),
	lot_size int(15),
	amount decimal(10,2),
	order_status boolean,
	insert_time timestamp,
	constraint oType check (order_type  = 'MARKET' OR order_type = 'LIMIT'),
	constraint sType check (order_side  = 'BUY' OR order_side  = 'SELL'),
	PRIMARY KEY(order_id),
	FOREIGN KEY (username) REFERENCES public.users(username)
);

create table hist_reuters
(
	CURRENCY_PAIR VARCHAR(10),
	PRICE DECIMAL(10,2),
	LOT_SIZE INT(15),
	INSERT_TIME TIMESTAMP
);

CREATE OR REPLACE VIEW MOV_AVG AS SELECT CURRENCY_PAIR, AVG(PRICE)MOVING_AVG FROM HIST_REUTERS 
WHERE INSERT_TIME > DATEADD(DD, -10, CURRENT_TIMESTAMP() ) GROUP BY CURRENCY_PAIR;