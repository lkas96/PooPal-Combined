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
  
SELECT * FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2" ORDER BY timestamp DESC limit 1;
  
SELECT * FROM records 
WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2" 
ORDER BY timestamp ASC;

SELECT pooWhere, COUNT(pooWhere) as total FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"  GROUP BY pooWhere;

SELECT s.pooWhere, COALESCE(r.total, 0) as total
FROM (SELECT 'home' AS pooWhere 
      UNION ALL 
      SELECT 'outside') AS s
LEFT JOIN (
    SELECT pooWhere, COUNT(*) as total
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY pooWhere
) AS r
ON s.pooWhere = r.pooWhere
ORDER BY 
  CASE 
    WHEN s.pooWhere = 'home' THEN 1
    WHEN s.pooWhere = 'outside' THEN 2
  END;

SELECT pooType, COUNT(pooWhere) as total FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"  GROUP BY pooType;


SELECT s.pooColor, COALESCE(r.total, 0) as total 
FROM (SELECT 'Brown' AS pooColor 
      UNION ALL SELECT 'Bright Brown'
      UNION ALL SELECT 'Yellowish'
      UNION ALL SELECT 'White Clay Colored'
      UNION ALL SELECT 'Green'
      UNION ALL SELECT 'Bright Red'
      UNION ALL SELECT 'Reddish'
      UNION ALL SELECT 'Black or Dark Brown') AS s
LEFT JOIN (
    SELECT pooColor, COUNT(*) as total 
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY pooColor
) AS r 
ON s.pooColor = r.pooColor 
ORDER BY 
  CASE 
    WHEN s.pooColor = 'Brown' THEN 1 
    WHEN s.pooColor = 'Bright Brown' THEN 2 
    WHEN s.pooColor = 'Yellowish' THEN 3 
    WHEN s.pooColor = 'White Clay Colored' THEN 4 
    WHEN s.pooColor = 'Green' THEN 5 
    WHEN s.pooColor = 'Bright Red' THEN 6 
    WHEN s.pooColor = 'Reddish' THEN 7 
    WHEN s.pooColor = 'Black or Dark Brown' THEN 8 
  END;
  
  
SELECT painBefore, COUNT(pooWhere) as total FROM records WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"  GROUP BY painBefore;

SELECT s.painBefore, COALESCE(r.total, 0) AS total 
FROM (
    SELECT 'Unbearable' AS painBefore 
    UNION ALL SELECT 'Severe' 
    UNION ALL SELECT 'Moderate' 
    UNION ALL SELECT 'Mild' 
    UNION ALL SELECT 'None'
) AS s 
LEFT JOIN (
    SELECT painBefore, COUNT(*) AS total 
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY painBefore
) AS r 
ON s.painBefore = r.painBefore 
ORDER BY 
    CASE 
        WHEN s.painBefore = 'Unbearable' THEN 1 
        WHEN s.painBefore = 'Severe' THEN 2 
        WHEN s.painBefore = 'Moderate' THEN 3 
        WHEN s.painBefore = 'Mild' THEN 4 
        WHEN s.painBefore = 'None' THEN 5 
    END;



SELECT s.urgent, COALESCE(r.total, 0) AS total 
FROM (
    SELECT 'no' AS urgent 
    UNION ALL SELECT 'yes'
) AS s 
LEFT JOIN (
    SELECT urgent, COUNT(*) AS total 
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY urgent
) AS r 
ON s.urgent = r.urgent 
ORDER BY 
    CASE 
        WHEN s.urgent = 'yes' THEN 1 
        WHEN s.urgent = 'no' THEN 2 
    END;


SELECT s.laxative, COALESCE(r.total, 0) AS total 
FROM (
    SELECT 'no' AS laxative 
    UNION ALL SELECT 'yes'
) AS s 
LEFT JOIN (
    SELECT laxative, COUNT(*) AS total 
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY laxative
) AS r 
ON s.laxative = r.laxative 
ORDER BY 
    CASE 
        WHEN s.laxative = 'yes' THEN 1 
        WHEN s.laxative = 'no' THEN 2 
    END;


SELECT s.bleeding, COALESCE(r.total, 0) AS total 
FROM (
    SELECT 'no' AS bleeding 
    UNION ALL SELECT 'yes'
) AS s 
LEFT JOIN (
    SELECT bleeding, COUNT(*) AS total 
    FROM records 
    WHERE userId = "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY bleeding
) AS r 
ON s.bleeding = r.bleeding 
ORDER BY 
    CASE 
        WHEN s.bleeding = 'yes' THEN 1 
        WHEN s.bleeding = 'no' THEN 2 
    END;

SELECT s.satisfactionLevel, COALESCE(r.total, 0) AS total 
FROM (
    SELECT 'good' AS satisfactionLevel 
    UNION ALL SELECT 'mid' 
    UNION ALL SELECT 'bad'
) AS s 
LEFT JOIN (
    SELECT satisfactionLevel, COUNT(*) AS total 
    FROM records 
    WHERE userId =  "vkYekvVLXzPVA1dv0eiQR7qS7Vs2"
    GROUP BY satisfactionLevel
) AS r 
ON s.satisfactionLevel = r.satisfactionLevel 
ORDER BY 
    CASE 
        WHEN s.satisfactionLevel = 'good' THEN 1 
        WHEN s.satisfactionLevel = 'mid' THEN 2 
        WHEN s.satisfactionLevel = 'bad' THEN 3 
    END;
