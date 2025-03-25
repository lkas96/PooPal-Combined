package com.lask.poopal_server.poopal_server.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.lask.poopal_server.poopal_server.models.PooRecord;

@Repository
public class PooRepo {
    
    @Autowired private JdbcTemplate template;
    
    //To save a poo entry/record
    public void savePooEntry(String userId, PooRecord poo) {
        final String SQL_INSERT = "INSERT INTO records (userId, pooWhere, pooType, pooColor, painBefore, painDuring, painAfter, urgent, laxative, bleeding, notes, satisfactionLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int isAdded = template.update(SQL_INSERT, userId, poo.getPooWhere(), poo.getPooType(), poo.getPooColor(), poo.getPainBefore(), poo.getPainDuring(), poo.getPainAfter(), poo.getUrgent(), poo.getLaxative(), poo.getBleeding(), poo.getNotes(), poo.getSatisfactionLevel());

        if (isAdded == 1) {
            System.out.println("Poo entry added successfully");
        } else {
            System.out.println("Poo entry failed to add");
        }
    }

    //To get all poo entries
    @SuppressWarnings({ "deprecation", "unused" })
    public List<PooRecord> getAllPooEntries(String userId) {
        final String SQL_SELECT_ALL = "SELECT * FROM records where userId = ? ORDER BY timestamp DESC";

        return template.query(SQL_SELECT_ALL, new Object[]{userId},(rs, rowNum) -> {
            PooRecord p = new PooRecord();
            p.setId(rs.getInt("id"));
            p.setPooWhere(rs.getString("pooWhere"));
            p.setPooType(rs.getString("pooType"));
            p.setPooColor(rs.getString("pooColor"));
            p.setPainBefore(rs.getString("painBefore"));
            p.setPainDuring(rs.getString("painDuring"));
            p.setPainAfter(rs.getString("painAfter"));
            p.setUrgent(rs.getString("urgent"));
            p.setLaxative(rs.getString("laxative"));
            p.setBleeding(rs.getString("bleeding"));
            p.setNotes(rs.getString("notes"));
            //convert to localdatetime
            Timestamp timestamp = rs.getTimestamp("timestamp");
            p.setTimestamp(timestamp != null ? timestamp.toLocalDateTime() : null);
            p.setSatisfactionLevel(rs.getString("satisfactionLevel"));

            return p;
    });

    }

    //To get a single poo entry
    @SuppressWarnings("deprecation")
    public PooRecord getPooEntry(String userId, int id) {
        final String SQL_SELECT_A_POO = "SELECT * FROM records where userId = ? and id = ?";

        return template.query(SQL_SELECT_A_POO, new Object[]{userId, id}, rs -> {
            if (rs.next()) {
                PooRecord p = new PooRecord();
                p.setId(rs.getInt("id"));
                p.setPooWhere(rs.getString("pooWhere"));
                p.setPooType(rs.getString("pooType"));
                p.setPooColor(rs.getString("pooColor"));
                p.setPainBefore(rs.getString("painBefore"));
                p.setPainDuring(rs.getString("painDuring"));
                p.setPainAfter(rs.getString("painAfter"));
                p.setUrgent(rs.getString("urgent"));
                p.setLaxative(rs.getString("laxative"));
                p.setBleeding(rs.getString("bleeding"));
                p.setNotes(rs.getString("notes"));
                // Convert to LocalDateTime
                Timestamp timestamp = rs.getTimestamp("timestamp");
                p.setTimestamp(timestamp != null ? timestamp.toLocalDateTime() : null);
                p.setSatisfactionLevel(rs.getString("satisfactionLevel"));
    
                return p;
            }
            return null; 
        });
    }

    //To update a poo entry
    public void updatePooEntry(String userId, PooRecord newPoo) {
        final String SQL_UPDATE = "UPDATE records SET pooWhere = ?, pooType = ?, pooColor = ?, painBefore = ?, painDuring = ?, painAfter = ?, urgent = ?, laxative = ?, bleeding = ?, notes = ?, timestamp = ?, satisfactionLevel = ? WHERE userId = ? and id = ?";

        Timestamp timestamp = newPoo.getTimestamp() != null ? Timestamp.valueOf(newPoo.getTimestamp()) : null;

        int isUpdated = template.update(SQL_UPDATE, newPoo.getPooWhere(), newPoo.getPooType(), newPoo.getPooColor(), newPoo.getPainBefore(), newPoo.getPainDuring(), newPoo.getPainAfter(), newPoo.getUrgent(), newPoo.getLaxative(), newPoo.getBleeding(), newPoo.getNotes(), timestamp, newPoo.getSatisfactionLevel(), userId, newPoo.getId());

        if (isUpdated == 1) {
            System.out.println("Poo entry updated successfully");
        } else {
            System.out.println("Poo entry failed to update");
        }
    }

    //To delete a poo entry
    public void deletePooEntry(String userId, int id) {
        final String SQL_DELETE = "DELETE FROM records WHERE userId = ? and id = ?";

        int isDeleted = template.update(SQL_DELETE, userId, id);

        if (isDeleted == 1) {
            System.out.println("Poo entry deleted successfully");
        } else {
            System.out.println("Poo entry failed to delete");
        }
    }

    @SuppressWarnings("deprecation")
    public int getTotalPooCount(String userId) {
        final String SQL_COUNT = "SELECT COUNT(*) FROM records WHERE userId = ?";

        return template.queryForObject(SQL_COUNT, new Object[]{userId}, Integer.class);
    }

    public String getTopPooType(String userId) {
        final String SQL_POOTYPE = "SELECT pooType, COUNT(pooType) as count FROM records WHERE userId = ? GROUP BY pooType ORDER BY count DESC LIMIT 1";

        SqlRowSet rs = template.queryForRowSet(SQL_POOTYPE, userId);

        if (rs.next()) {
            return rs.getString("pooType");
        }

        return "No poo entries";
    }

    public String getTopPooColor(String userId) {
        final String SQL_POOCOLOR = "SELECT pooColor, COUNT(pooColor) as count FROM records WHERE userId = ? GROUP BY pooColor ORDER BY count DESC LIMIT 1";

        SqlRowSet rs = template.queryForRowSet(SQL_POOCOLOR, userId);

        if (rs.next()) {
            return rs.getString("pooColor");
        }

        return "No poo entries";
    }

    public int[] getUrgentPoos(String userId) {
        final String SQL_URGENT = "SELECT COUNT(*) as total, urgent FROM records WHERE userId = ? GROUP BY urgent ORDER BY urgent DESC";

        //since order by urgent desc, 
        //first row will be no, second row will be yes

        SqlRowSet rs = template.queryForRowSet(SQL_URGENT, userId);

        int rowIndex = 0;

        while (rs.next()) {

            if (rowIndex == 0) {
                int totalNo = rs.getInt("total");
                rowIndex++;

                if (rowIndex == 1) {
                    int totalYes = rs.getInt("total");
                    return new int[]{totalNo, totalYes};
                }
            }
        }

        return new int[]{0, 0};


    }

    public int[] getSatisfyingPoos(String userId) {
        final String SQL_SATISFYING =   "SELECT s.satisfactionLevel, COALESCE(r.total, 0) as total " +
                                        "FROM (SELECT 'good' AS satisfactionLevel UNION ALL SELECT 'mid' UNION ALL SELECT 'bad') AS s " + 
                                        "LEFT JOIN (SELECT satisfactionLevel, COUNT(*) as total FROM records WHERE userId = ? GROUP BY satisfactionLevel) AS r " + 
                                        "ON s.satisfactionLevel = r.satisfactionLevel " +
                                        "ORDER BY CASE " +
                                        "WHEN s.satisfactionLevel = 'good' THEN 1 " +
                                        "WHEN s.satisfactionLevel = 'mid' THEN 2 " +
                                        "WHEN s.satisfactionLevel = 'bad' THEN 3 END";

                                        //force return all results, example if no good return 0 still
                                        //easier for front end. just get the array. 

        //since order by satisfactionLevel desc, 
        //first row will be no, second row will be yes

        SqlRowSet rs = template.queryForRowSet(SQL_SATISFYING, userId);

        int[] totals = new int[3];
        
        while (rs.next()) {
            String level = rs.getString("satisfactionLevel");
            int total = rs.getInt("total");
    
            switch (level) {
                case "good":
                    totals[0] = total;
                    break;
                case "mid":
                    totals[1] = total;
                    break;
                case "bad":
                    totals[2] = total;
                    break;
            }
        }
    
        return totals;
    }

    @SuppressWarnings("deprecation")
    public PooRecord getLastestPoo(String userId) {
        final String SQL_LASTEST = "SELECT * FROM records WHERE userId = ? ORDER BY timestamp DESC LIMIT 1";

        return template.query(SQL_LASTEST, new Object[]{userId}, rs -> {
            if (rs.next()) {
                PooRecord p = new PooRecord();
                p.setId(rs.getInt("id"));
                p.setPooWhere(rs.getString("pooWhere"));
                p.setPooType(rs.getString("pooType"));
                p.setPooColor(rs.getString("pooColor"));
                p.setPainBefore(rs.getString("painBefore"));
                p.setPainDuring(rs.getString("painDuring"));
                p.setPainAfter(rs.getString("painAfter"));
                p.setUrgent(rs.getString("urgent"));
                p.setLaxative(rs.getString("laxative"));
                p.setBleeding(rs.getString("bleeding"));
                p.setNotes(rs.getString("notes"));
                // Convert to LocalDateTime
                Timestamp timestamp = rs.getTimestamp("timestamp");
                p.setTimestamp(timestamp != null ? timestamp.toLocalDateTime() : null);
                p.setSatisfactionLevel(rs.getString("satisfactionLevel"));
    
                return p;
            }
            return null; 
        });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPooWhereCounts(String userId) {
        final String SQL_POO_WHERE =    "SELECT s.pooWhere, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'home' AS pooWhere UNION ALL SELECT 'outside') AS s " + 
                                        "LEFT JOIN (SELECT pooWhere, COUNT(*) as total FROM records WHERE userId = ? GROUP BY pooWhere) AS r " + 
                                        "ON s.pooWhere = r.pooWhere " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.pooWhere = 'home' THEN 1 " + 
                                        "WHEN s.pooWhere = 'outside' THEN 2 END";

        return template.query(SQL_POO_WHERE, new Object[]{userId}, rs -> {
            Map<String, Integer> whereCounts = new java.util.HashMap<>();

            while (rs.next()) {
                whereCounts.put(rs.getString("pooWhere"), rs.getInt("total")); //put key Vvalue pair shit //home-number
            }

            return whereCounts;
        });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPooTypeCounts(String userId) {
        final String SQL_POO_TYPE = "SELECT s.pooType, COALESCE(r.total, 0) as total " + 
                                    "FROM (SELECT 'Type 1' AS pooType " + 
                                    "UNION ALL SELECT 'Type 2' " + 
                                    "UNION ALL SELECT 'Type 3' " + 
                                    "UNION ALL SELECT 'Type 4' " + 
                                    "UNION ALL SELECT 'Type 5' " + 
                                    "UNION ALL SELECT 'Type 6' " + 
                                    "UNION ALL SELECT 'Type 7') AS s " + 
                                    "LEFT JOIN (SELECT pooType, COUNT(*) as total " + 
                                    "FROM records " + 
                                    "WHERE userId = ? " + 
                                    "GROUP BY pooType) AS r " + 
                                    "ON s.pooType = r.pooType " + 
                                    "ORDER BY CASE " + 
                                    "WHEN s.pooType = 'Type 1' THEN 1 " + 
                                    "WHEN s.pooType = 'Type 2' THEN 2 " + 
                                    "WHEN s.pooType = 'Type 3' THEN 3 " + 
                                    "WHEN s.pooType = 'Type 4' THEN 4 " + 
                                    "WHEN s.pooType = 'Type 5' THEN 5 " + 
                                    "WHEN s.pooType = 'Type 6' THEN 6 " + 
                                    "WHEN s.pooType = 'Type 7' THEN 7 END";
    
        return template.query(SQL_POO_TYPE, new Object[]{userId}, rs -> {
            Map<String, Integer> typeCounts = new java.util.LinkedHashMap<>();
    
            while (rs.next()) {
                typeCounts.put(rs.getString("pooType"), rs.getInt("total"));
            }
    
            return typeCounts;
        });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPooColorCounts(String userId) {
        final String SQL_POO_COLOR = "SELECT s.pooColor, COALESCE(r.total, 0) as total " + 
                                     "FROM (SELECT 'Brown' AS pooColor " + 
                                     "UNION ALL SELECT 'Bright Brown' " + 
                                     "UNION ALL SELECT 'Yellowish' " + 
                                     "UNION ALL SELECT 'White Clay Colored' " + 
                                     "UNION ALL SELECT 'Green' " + 
                                     "UNION ALL SELECT 'Bright Red' " + 
                                     "UNION ALL SELECT 'Reddish' " + 
                                     "UNION ALL SELECT 'Black or Dark Brown') AS s " + 
                                     "LEFT JOIN (SELECT pooColor, COUNT(*) as total " + 
                                     "FROM records " + 
                                     "WHERE userId = ? " + 
                                     "GROUP BY pooColor) AS r " + 
                                     "ON s.pooColor = r.pooColor " + 
                                     "ORDER BY CASE " + 
                                     "WHEN s.pooColor = 'Brown' THEN 1 " + 
                                     "WHEN s.pooColor = 'Bright Brown' THEN 2 " + 
                                     "WHEN s.pooColor = 'Yellowish' THEN 3 " + 
                                     "WHEN s.pooColor = 'White Clay Colored' THEN 4 " + 
                                     "WHEN s.pooColor = 'Green' THEN 5 " + 
                                     "WHEN s.pooColor = 'Bright Red' THEN 6 " + 
                                     "WHEN s.pooColor = 'Reddish' THEN 7 " + 
                                     "WHEN s.pooColor = 'Black or Dark Brown' THEN 8 END";
    
        return template.query(SQL_POO_COLOR, new Object[]{userId}, rs -> {
            Map<String, Integer> colorCounts = new java.util.LinkedHashMap<>();
    
            while (rs.next()) {
                colorCounts.put(rs.getString("pooColor"), rs.getInt("total"));
            }
    
            return colorCounts;
        });

    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPainLevelBeforeCounts(String userId) {
        final String SQL_PAIN_LEVEL =   "SELECT s.painBefore, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'Unbearable' AS painBefore " + 
                                        "UNION ALL SELECT 'Severe' " + 
                                        "UNION ALL SELECT 'Moderate' " + 
                                        "UNION ALL SELECT 'Mild' " + 
                                        "UNION ALL SELECT 'None') AS s " + 
                                        "LEFT JOIN (SELECT painBefore, COUNT(*) as total " + 
                                        "FROM records " + 
                                        "WHERE userId = ? " + 
                                        "GROUP BY painBefore) AS r " + 
                                        "ON s.painBefore = r.painBefore " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.painBefore = 'Unbearable' THEN 1 " + 
                                        "WHEN s.painBefore = 'Severe' THEN 2 " + 
                                        "WHEN s.painBefore = 'Moderate' THEN 3 " + 
                                        "WHEN s.painBefore = 'Mild' THEN 4 " + 
                                        "WHEN s.painBefore = 'None' THEN 5 END";

        return template.query(SQL_PAIN_LEVEL, new Object[]{userId}, rs -> {
            Map<String, Integer> painCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                painCounts.put(rs.getString("painBefore"), rs.getInt("total"));
            }

            return painCounts;
            });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPainLevelDuringCounts(String userId) {
        final String SQL_PAIN_LEVEL =   "SELECT s.painDuring, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'Unbearable' AS painDuring " + 
                                        "UNION ALL SELECT 'Severe' " + 
                                        "UNION ALL SELECT 'Moderate' " + 
                                        "UNION ALL SELECT 'Mild' " + 
                                        "UNION ALL SELECT 'None') AS s " + 
                                        "LEFT JOIN (SELECT painDuring, COUNT(*) as total " + 
                                        "FROM records " + 
                                        "WHERE userId = ? " + 
                                        "GROUP BY painDuring) AS r " + 
                                        "ON s.painDuring = r.painDuring " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.painDuring = 'Unbearable' THEN 1 " + 
                                        "WHEN s.painDuring = 'Severe' THEN 2 " + 
                                        "WHEN s.painDuring = 'Moderate' THEN 3 " + 
                                        "WHEN s.painDuring = 'Mild' THEN 4 " + 
                                        "WHEN s.painDuring = 'None' THEN 5 END";

        return template.query(SQL_PAIN_LEVEL, new Object[]{userId}, rs -> {
            Map<String, Integer> painCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                painCounts.put(rs.getString("painDuring"), rs.getInt("total"));
            }

            return painCounts;
            });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getPainLevelAfterCounts(String userId) {
        final String SQL_PAIN_LEVEL =   "SELECT s.painAfter, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'Unbearable' AS painAfter " + 
                                        "UNION ALL SELECT 'Severe' " + 
                                        "UNION ALL SELECT 'Moderate' " + 
                                        "UNION ALL SELECT 'Mild' " + 
                                        "UNION ALL SELECT 'None') AS s " + 
                                        "LEFT JOIN (SELECT painAfter, COUNT(*) as total " + 
                                        "FROM records " + 
                                        "WHERE userId = ? " + 
                                        "GROUP BY painAfter) AS r " + 
                                        "ON s.painAfter = r.painAfter " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.painAfter = 'Unbearable' THEN 1 " + 
                                        "WHEN s.painAfter = 'Severe' THEN 2 " + 
                                        "WHEN s.painAfter = 'Moderate' THEN 3 " + 
                                        "WHEN s.painAfter = 'Mild' THEN 4 " + 
                                        "WHEN s.painAfter = 'None' THEN 5 END";

        return template.query(SQL_PAIN_LEVEL, new Object[]{userId}, rs -> {
            Map<String, Integer> painCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                painCounts.put(rs.getString("painAfter"), rs.getInt("total"));
            }

            return painCounts;
            });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getUrgentPooCounts(String userId) {
        final String SQL_URGENT = "SELECT s.urgent, COALESCE(r.total, 0) as total " + 
                                  "FROM (SELECT 'no' AS urgent " + 
                                  "UNION ALL SELECT 'yes') AS s " + 
                                  "LEFT JOIN (SELECT urgent, COUNT(*) as total " + 
                                  "FROM records " + 
                                  "WHERE userId = ? " + 
                                  "GROUP BY urgent) AS r " + 
                                  "ON s.urgent = r.urgent " + 
                                  "ORDER BY CASE " + 
                                  "WHEN s.urgent = 'yes' THEN 1 " + 
                                  "WHEN s.urgent = 'no' THEN 2 END";

        return template.query(SQL_URGENT, new Object[]{userId}, rs -> {
            Map<String, Integer> urgentCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                urgentCounts.put(rs.getString("urgent"), rs.getInt("total"));
            }

            return urgentCounts;
            });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getLaxativePooCounts(String userId) {
            final String SQL_LAXATIVE = "SELECT s.laxative, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'no' AS laxative " + 
                                        "UNION ALL SELECT 'yes') AS s " + 
                                        "LEFT JOIN (SELECT laxative, COUNT(*) as total " + 
                                        "FROM records " + 
                                        "WHERE userId = ? " + 
                                        "GROUP BY laxative) AS r " + 
                                        "ON s.laxative = r.laxative " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.laxative = 'yes' THEN 1 " + 
                                        "WHEN s.laxative = 'no' THEN 2 END";

        return template.query(SQL_LAXATIVE, new Object[]{userId}, rs -> {
            Map<String, Integer> laxativeCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                laxativeCounts.put(rs.getString("laxative"), rs.getInt("total"));
            }

            return laxativeCounts;
            });
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getBleedingPooCounts(String userId) {
        final String SQL_BLEEDING = "SELECT s.bleeding, COALESCE(r.total, 0) as total " + 
                                    "FROM (SELECT 'no' AS bleeding " + 
                                    "UNION ALL SELECT 'yes') AS s " + 
                                    "LEFT JOIN (SELECT bleeding, COUNT(*) as total " + 
                                    "FROM records " + 
                                    "WHERE userId = ? " + 
                                    "GROUP BY bleeding) AS r " + 
                                    "ON s.bleeding = r.bleeding " + 
                                    "ORDER BY CASE " + 
                                    "WHEN s.bleeding = 'yes' THEN 1 " + 
                                    "WHEN s.bleeding = 'no' THEN 2 END";   

        return template.query(SQL_BLEEDING, new Object[]{userId}, rs -> {
            Map<String, Integer> bleedingCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                bleedingCounts.put(rs.getString("bleeding"), rs.getInt("total"));
            }

            return bleedingCounts;
            });
                        
    }

    @SuppressWarnings("deprecation")
    public Map<String, Integer> getSatisfactionLevelCounts(String userId) {
        final String SQL_SATISFACTION = "SELECT s.satisfactionLevel, COALESCE(r.total, 0) as total " + 
                                        "FROM (SELECT 'good' AS satisfactionLevel " + 
                                        "UNION ALL SELECT 'mid' " + 
                                        "UNION ALL SELECT 'bad') AS s " + 
                                        "LEFT JOIN (SELECT satisfactionLevel, COUNT(*) as total " + 
                                        "FROM records " + 
                                        "WHERE userId = ? " + 
                                        "GROUP BY satisfactionLevel) AS r " + 
                                        "ON s.satisfactionLevel = r.satisfactionLevel " + 
                                        "ORDER BY CASE " + 
                                        "WHEN s.satisfactionLevel = 'good' THEN 1 " + 
                                        "WHEN s.satisfactionLevel = 'mid' THEN 2 " + 
                                        "WHEN s.satisfactionLevel = 'bad' THEN 3 END";

        return template.query(SQL_SATISFACTION, new Object[]{userId}, rs -> {
            Map<String, Integer> satisfactionCounts = new java.util.LinkedHashMap<>();

            while (rs.next()) {
                satisfactionCounts.put(rs.getString("satisfactionLevel"), rs.getInt("total"));
            }

            return satisfactionCounts;
            });
    }



    

    


    

}
