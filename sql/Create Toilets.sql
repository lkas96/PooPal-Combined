create database poopal;
use poopal;

create table toilets (
	id varchar(8) not null, 
    name varchar(64), 
    district varchar(32), 
    type varchar(32),
    rating int,
    primary key (id)
);

create table records (
	id bigint AUTO_INCREMENT, 
    userId varchar(32), 
    public boolean,
    pooType int, 
    pooColor varchar(16),
    painBefore int, 
    painDuring int, 
    painAfter int, 
    urgent boolean,
    laxative boolean,
    bleeding boolean,
    notes varchar(128),
    timestamp datetime DEFAULT CURRENT_TIMESTAMP,
    primary key (id)
);

drop table records;

-- test data 
INSERT INTO records (userId, public, pooType, pooColor, painBefore, painDuring, painAfter, urgent, laxative, bleeding, notes)
VALUES 
(1, true, 1, 'Brown', 2, 3, 1, false, false, false, 'Normal consistency'),
(2, false, 2, 'Dark Brown', 3, 4, 2, true, false, false, 'Slightly constipated'),
(3, true, 3, 'Green', 1, 2, 1, false, false, false, 'Had a lot of vegetables'),
(4, false, 1, 'Yellow', 2, 3, 2, false, true, false, 'Took a laxative'),
(5, true, 4, 'Black', 3, 5, 4, true, false, true, 'Possible internal bleeding, need to monitor'),
(6, false, 2, 'Brown', 1, 2, 1, false, false, false, 'Felt normal'),
(7, true, 3, 'Red', 2, 3, 2, false, false, true, 'Might be due to beets'),
(8, false, 1, 'Light Brown', 1, 1, 1, false, false, false, 'No issues, regular bowel movement'),
(9, true, 2, 'Dark Green', 2, 2, 2, true, false, false, 'Ate a lot of leafy greens'),
(10, false, 4, 'Gray', 3, 4, 3, true, false, false, 'Possible liver issue, keeping track');


select * from records;