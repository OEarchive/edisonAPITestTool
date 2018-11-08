package Model.RestClient.Clients;

import Model.RestClient.OEResponse;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PGDAOIClient {

    public OEResponse runQuery(String queryString) {
        Statement st = null;
        ResultSet rs = null;

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://192.168.20.27:1433;databaseName=oemvmdata;user=Tominator;password=dT0A@N^V;";
            url = "jdbc:sqlserver://192.168.20.28:1433;databaseName=oemvmdata;user=Tominator;password=dT0A@N^V;";

            DriverManager.setLoginTimeout(30);
            Connection conn = DriverManager.getConnection(url);

            st = conn.createStatement();
            rs = st.executeQuery(queryString);

        } catch (Exception ex) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.WARNING, null, ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(
                            Level.WARNING, null, ex);
                }
            }
        }
        
        OEResponse resp = new OEResponse();
        resp.responseCode = 200;
        resp.responseObject = rs;
        
        return resp;
        
        
    }

}
