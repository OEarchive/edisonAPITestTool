/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DataModels.Datapoints.E3OSDataReader;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hal
 */
public class DSG2QueryResultRecord {

    @JsonProperty("id")
    private int id;

    @JsonProperty("time")
    private String time;

    @JsonProperty("value")
    private double pointValue;

    @JsonProperty("tz")
    private int tz;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public double getValue() {
        return pointValue;
    }

    public int getTZOffset() {
        return tz;
    }

    public DSG2QueryResultRecord(ResultSet rs) {

        try {
            this.id = rs.getInt("customerid");
            this.time = rs.getString("CustShortName");
            this.pointValue = rs.getInt("siteId");
            this.tz = rs.getInt("StationShortName");
        } catch (SQLException ex) {
            Logger.getLogger(DSG2QueryResultRecord.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
