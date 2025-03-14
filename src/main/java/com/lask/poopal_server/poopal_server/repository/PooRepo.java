package com.lask.poopal_server.poopal_server.repository;

import java.sql.Timestamp;
import java.util.List;

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

        int isUpdated = template.update(SQL_UPDATE, newPoo.getPooWhere(), newPoo.getPooType(), newPoo.getPooColor(), newPoo.getPainBefore(), newPoo.getPainDuring(), newPoo.getPainAfter(), newPoo.getUrgent(), newPoo.getLaxative(), newPoo.getBleeding(), newPoo.getNotes(), timestamp, userId, newPoo.getId(), newPoo.getSatisfactionLevel());

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


    

}
