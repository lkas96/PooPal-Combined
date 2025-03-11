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
        final String SQL_INSERT = "INSERT INTO records (userId, pooWhere, pooType, pooColor, painBefore, painDuring, painAfter, urgent, laxative, bleeding, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int isAdded = template.update(SQL_INSERT, userId, poo.getPooWhere(), poo.getPooType(), poo.getPooColor(), poo.getPainBefore(), poo.getPainDuring(), poo.getPainAfter(), poo.getUrgent(), poo.getLaxative(), poo.getBleeding(), poo.getNotes());

        if (isAdded == 1) {
            System.out.println("Poo entry added successfully");
        } else {
            System.out.println("Poo entry failed to add");
        }
    }

    //To get all poo entries
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

            return p;
    });

    }

    //To get a single poo entry
    public PooRecord getPooEntry(String userId, int id) {
        final String SQL_SELECT_A_POO = "SELECT * FROM records where userId = ? and id = ?";

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_A_POO, id);
        PooRecord p = new PooRecord();

        while(rs.next()){
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
        }

        System.out.printf("Retrieved record: %s%n", p.toString());

        return p;
    }

    //To update a poo entry
    public void updatePooEntry(String userId, PooRecord newPoo) {
        final String SQL_UPDATE = "UPDATE records SET pooWhere = ?, pooType = ?, pooColor = ?, painBefore = ?, painDuring = ?, painAfter = ?, urgent = ?, laxative = ?, bleeding = ?, notes = ?, timestamp = ? WHERE userId = ? and id = ?";

        int isUpdated = template.update(SQL_UPDATE, newPoo.getPooWhere(), newPoo.getPooType(), newPoo.getPooColor(), newPoo.getPainBefore(), newPoo.getPainDuring(), newPoo.getPainAfter(), newPoo.getUrgent(), newPoo.getLaxative(), newPoo.getBleeding(), newPoo.getNotes(), newPoo.getTimestamp(), userId, newPoo.getId());

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

}
