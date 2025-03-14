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

drop table records;

create table records (
	id bigint AUTO_INCREMENT, 
    userId varchar(32), 
    pooWhere varchar(16),
    pooType varchar(6), 
    pooColor varchar(32),
    painBefore varchar(16), 
    painDuring varchar(16), 
    painAfter varchar(16), 
    urgent varchar(4),
    laxative varchar(4),
    bleeding varchar(4),
    notes varchar(128),
    timestamp datetime DEFAULT CURRENT_TIMESTAMP,
    satisfactionLevel varchar(8),
    primary key (id)
);

create table toilets (
	id bigint AUTO_INCREMENT,
    name varchar(128),
    district varchar(32), 
    type varchar(32),
    rating varchar(15),
    primary key (id)
);

select * from toilets;
drop table toilets;

select * from records;
SELECT @@global.time_zone, @@session.time_zone;

create table placeIds (
	toiletId bigint, 
    placeId varchar(1048), 
    primary key (toiletId), 
    foreign key (toiletId) references toilets(id)
);
select * from placeIds;
drop table placeIds;

select * from records;

select * from records;
SELECT urgent, COUNT(*) AS total FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2" GROUP BY urgent order BY urgent asc;

SELECT satisfactionLevel, COUNT(*) as total FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2" GROUP BY satisfactionLevel ORDER BY satisfactionLevel DESC;
SELECT  satisfactionLevel , COUNT(*) as total
FROM records 
WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
GROUP BY satisfactionLevel 
ORDER BY 
  CASE 
    WHEN satisfactionLevel = 'good' THEN 1
    WHEN satisfactionLevel = 'mid' THEN 2
    WHEN satisfactionLevel = 'bad' THEN 3
  END;
  
  SELECT s.satisfactionLevel, COALESCE(r.total, 0) as total
FROM (SELECT 'good' AS satisfactionLevel 
      UNION ALL 
      SELECT 'mid' 
      UNION ALL 
      SELECT 'bad') AS s
LEFT JOIN (
    SELECT satisfactionLevel, COUNT(*) as total
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY satisfactionLevel
) AS r
ON s.satisfactionLevel = r.satisfactionLevel
ORDER BY 
  CASE 
    WHEN s.satisfactionLevel = 'good' THEN 1
    WHEN s.satisfactionLevel = 'mid' THEN 2
    WHEN s.satisfactionLevel = 'bad' THEN 3
  END;
  
  SELECT * FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2" ORDER BY timestamp DESC limit 1
  
  