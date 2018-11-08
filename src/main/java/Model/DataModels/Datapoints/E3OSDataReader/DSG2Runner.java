
package Model.DataModels.Datapoints.E3OSDataReader;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DSG2Runner {
    
    private final List<String> stationPointNames;
    private final String prefix;
    
    public DSG2Runner( String prefix, List<String> stationPointNames){
        this.stationPointNames = stationPointNames;
        this.prefix = prefix;
    }

    public List<DSG2QueryResultRecord> runDSG2Query() throws SQLServerException, SQLException, ClassNotFoundException {

        Connection conn = null;
        ResultSet rs = null;
        SQLServerCallableStatement cs = null;
        
       List<DSG2QueryResultRecord> list = new ArrayList<>(); 

        try {

            SQLServerDataTable sourceDataTable = new SQLServerDataTable();
            
            sourceDataTable.addColumnMetadata("seqNbr", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("DataPointXID", java.sql.Types.VARCHAR);
            sourceDataTable.addColumnMetadata("DataPointID", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("Time_AggregateType", java.sql.Types.VARCHAR);
            sourceDataTable.addColumnMetadata("Rollup_AggregateType", java.sql.Types.VARCHAR);
            
            int index=1;
            for( String s : stationPointNames ){
                sourceDataTable.addRow(index, prefix + s, null, "HourlyAvg", "Sum");
                index++;
            }

            /*
            sourceDataTable.addRow(1, "ATT.WB3090.WB3090LP.WB3090LP.OldkW", null, "HourlyAvg", "Sum");
            sourceDataTable.addRow(2, "ATT.WB3090.WB3090LP.WB3090LP.OldkWh", null, "HourlyAvg", "Sum");
            sourceDataTable.addRow(3, "ATT.WB3090.WB3090LP.WB3090LP.ProjkW", null, "HourlyAvg", "Sum");
            sourceDataTable.addRow(4, "ATT.WB3090.WB3090LP.WB3090LP.ProjkWh", null, "HourlyAvg", "Sum");
            sourceDataTable.addRow(5, "ATT.WB3090.WB3090LP.WB3090LP.TotalkW", null, "HourlyAvg", "Sum");
            sourceDataTable.addRow(6, "ATT.WB3090.WB3090LP.WB3090LP.kWh", null, "HourlyAvg", "Sum");
            */

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.setLoginTimeout(30);
            //String url = "jdbc:sqlserver://192.168.20.27:1433;databaseName=oemvmdata;user=Tominator;password=dT0A@N^V;";

            String url = "jdbc:sqlserver://192.168.20.27:1433;databaseName=oemvmdata;user=hwilkinson;password=xxxxxxx;";
            DriverManager.setLoginTimeout(30);
            conn = DriverManager.getConnection(url);

            String sprocCaller = "{call oemvmdata.fact.DataSeriesGet2 (?,?,?,?,?,?,?,?,?,?,?,?,?,? )}";

            /*
  @FromTime_Local
  ,@ToTime_Local
  ,@DataPointsOfInterest
  ,@TimeRange
  ,@TimeInterval
  ,@CalculatedFromTime OUTPUT
  ,@CalculatedToTime OUTPUT
  ,@RequestProcessing
  ,@RequestGUID
  ,@IncludeOutOfBounds
  ,@IncludeUncommissioned
  ,@ForceCacheRefresh
  ,@UserName
  ,@UserID
             */
            cs = (SQLServerCallableStatement) conn.prepareCall(sprocCaller);

            /*
        String queryString = "declare @blah fact.datapointsofinterest , "
                + " @FromTime datetime , "
                + " @ToTime datetime , "
                + " @CacheTime date declare @Results_Orig     fact.DataPointResults "
                + " declare @Results "
                + " fact.DataPointResults insert into @blah (seqnbr, datapointxid, time_aggregatetype, rollup_aggregatetype) values "
                + " (1, 'ATT.WB3090.WB3090LP.WB3090LP.OldkW', 'HourlyAvg', 'Sum'), "
                + " (2, 'ATT.WB3090.WB3090LP.WB3090LP.OldkWh', 'HourlyAvg', 'Sum'), "
                + "  (3, 'ATT.WB3090.WB3090LP.WB3090LP.ProjkW', 'HourlyAvg', 'Sum'), "
                + "  (4, 'ATT.WB3090.WB3090LP.WB3090LP.ProjkWh', 'HourlyAvg', 'Sum'), "
                + "   (5, 'ATT.WB3090.WB3090LP.WB3090LP.TotalkW', 'HourlyAvg', 'Sum'), "
                + "  (6, 'ATT.WB3090.WB3090LP.WB3090LP.kWh', 'HourlyAvg', 'Sum') "
                + "  insert @Results exec oemvmdata.fact.DataSeriesGet2 "
                + "  @DataPointsOfInterest=@blah, "
                + "   @TimeRange=null, "
                + "    @TimeInterval='Minute', "
                + "	 @FromTime_Local='2016-03-01', "
                + "	  @ToTime_Local='2016-04-01', "
                + "	   @IncludeOutOfBounds=0, @IncludeUncommissioned=0 "
                + "select * from @Results";
             */
            //DECLARE @FromTime_Local datetime
            java.sql.Date fromTime = getSqlDate("01/01/2017");
            cs.setDate(1, fromTime);

            //DECLARE @ToTime_Local datetime
            java.sql.Date toTime = getSqlDate("01/31/2017");
            cs.setDate(2, toTime);

            //DECLARE @DataPointsOfInterest [fact].[DataPointsOfInterest]
            cs.setStructured(3, "fact.DataPointsOfInterest", sourceDataTable);

            //DECLARE @TimeRange varchar(50)
            cs.setNull(4, java.sql.Types.NULL);

            //DECLARE @TimeInterval varchar(50)
            cs.setString(5, "Minute");

            //DECLARE @CalculatedFromTime datetime
            cs.registerOutParameter(6, java.sql.Types.DATE);

            //DECLARE @CalculatedToTime datetime
            cs.registerOutParameter(7, java.sql.Types.DATE);
            //cs.setString(6, "week");

            //DECLARE @RequestProcessing bit
            cs.setNull(8, java.sql.Types.NULL);

            //DECLARE @RequestGUID uniqueidentifier
            cs.setNull(9, java.sql.Types.NULL);

            //DECLARE @IncludeOutOfBounds bit
            cs.setBoolean(10, false);

            //DECLARE @IncludeUncommissioned bit
            cs.setBoolean(11, false);

            //DECLARE @ForceCacheRefresh bit
            cs.setNull(12, java.sql.Types.NULL);

            //DECLARE @UserName nvarchar(256)
            cs.setString(13, "tkitchen");

            //DECLARE @UserID uniqueidentifier 
            cs.setString(14, "1d355ea9-7b16-4822-9483-1dd34173b5b8");

            boolean resultSetReturned = cs.execute();
            if (resultSetReturned) {
                try (ResultSet rs_Temp = cs.getResultSet()) {
                    while (rs_Temp.next()) {
                        list.add( new DSG2QueryResultRecord( rs_Temp ));
                    }
                }
            }
            

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.WARNING, null, ex);
                }
            }
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.WARNING, null, ex);
                }
            }
        }

        return list;

    }

    private static java.sql.Date getSqlDate(String dateString) {

        //DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime ts = DateTime.parse(dateString, fmt);
        return new java.sql.Date(ts.getMillis());
    }
}


/*
SQLServerDataTable sourceDataTable = new SQLServerDataTable();   
sourceDataTable.addColumnMetadata("SDate", java.sql.Types.DECIMAL);
sourceDataTable.addColumnMetadata("EDate", java.sql.Types.DECIMAL);
sourceDataTable.addColumnMetadata("PlantCode", java.sql.Types.NVARCHAR);
sourceDataTable.addColumnMetadata("LoadType", java.sql.Types.NCHAR);
sourceDataTable.addColumnMetadata("Asset", java.sql.Types.BIGINT);

// sample data
sourceDataTable.addRow(123, 234, "Plant1", "Type1", 123234);   
sourceDataTable.addRow(456, 789, "Plant2", "Type2", 456789);   

try (CallableStatement cs = conn.prepareCall("{CALL dbo.RegisterInitAssets (?)}")) {
    ((SQLServerCallableStatement) cs).setStructured(1, "dbo.INITVALS_MSG", sourceDataTable);
    boolean resultSetReturned = cs.execute();
    if (resultSetReturned) {
        try (ResultSet rs = cs.getResultSet()) {
            rs.next();
            System.out.println(rs.getInt(1));
        }
    }
}
 */

