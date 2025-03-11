package com.lask.poopal_server.poopal_server.repository;

import java.util.ArrayList;
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
        final String SQL_INSERT = "INSERT INTO records (userId, public, pooType, pooColor, painBefore, painDuring, painAfter, urgent, laxative, bleeding, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int isAdded = template.update(SQL_INSERT, userId, poo.isPublic(), poo.getPooType(), poo.getPooColor(), poo.getPainBefore(), poo.getPainDuring(), poo.getPainAfter(), poo.isUrgent(), poo.isLaxative(), poo.isBleeding(), poo.getNotes());

        if (isAdded == 1) {
            System.out.println("Poo entry added successfully");
        } else {
            System.out.println("Poo entry failed to add");
        }
    }

    //To get all poo entries
    public List<PooRecord> getAllPooEntries(String userId) {
        final String SQL_SELECT_ALL = "SELECT * FROM records where userId = ?";

        List<PooRecord> list = new ArrayList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL, userId);
        PooRecord p = new PooRecord();

        while(rs.next()){
            p.setId(rs.getInt("id"));
            p.setPublic(rs.getBoolean("public"));
            p.setPooType(rs.getString("pooType"));
            p.setPooColor(rs.getString("pooColor"));
            p.setPainBefore(rs.getInt("painBefore"));
            p.setPainDuring(rs.getInt("painDuring"));
            p.setPainAfter(rs.getInt("painAfter"));
            p.setUrgent(rs.getBoolean("urgent"));
            p.setLaxative(rs.getBoolean("laxative"));
            p.setBleeding(rs.getBoolean("bleeding"));
            p.setNotes(rs.getString("notes"));
            list.add(p);
        }

        System.out.printf("Retrieved records for user %s: %s%n", userId, p.toString());

        return list;
    }

    //To get a single poo entry
    public PooRecord getPooEntry(String userId, int id) {
        final String SQL_SELECT_A_POO = "SELECT * FROM records where userId = ? and id = ?";

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_A_POO, id);
        PooRecord p = new PooRecord();

        while(rs.next()){
            p.setId(rs.getInt("id"));
            p.setPublic(rs.getBoolean("public"));
            p.setPooType(rs.getString("pooType"));
            p.setPooColor(rs.getString("pooColor"));
            p.setPainBefore(rs.getInt("painBefore"));
            p.setPainDuring(rs.getInt("painDuring"));
            p.setPainAfter(rs.getInt("painAfter"));
            p.setUrgent(rs.getBoolean("urgent"));
            p.setLaxative(rs.getBoolean("laxative"));
            p.setBleeding(rs.getBoolean("bleeding"));
            p.setNotes(rs.getString("notes"));
        }

        System.out.printf("Retrieved record: %s%n", p.toString());

        return p;
    }

    //To update a poo entry
    public void updatePooEntry(String userId, PooRecord newPoo) {
        final String SQL_UPDATE = "UPDATE records SET public = ?, pooType = ?, pooColor = ?, painBefore = ?, painDuring = ?, painAfter = ?, urgent = ?, laxative = ?, bleeding = ?, notes = ?, timestamp = ? WHERE userId = ? and id = ?";

        int isUpdated = template.update(SQL_UPDATE, newPoo.isPublic(), newPoo.getPooType(), newPoo.getPooColor(), newPoo.getPainBefore(), newPoo.getPainDuring(), newPoo.getPainAfter(), newPoo.isUrgent(), newPoo.isLaxative(), newPoo.isBleeding(), newPoo.getNotes(), newPoo.getTimestamp(), userId, newPoo.getId());

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
