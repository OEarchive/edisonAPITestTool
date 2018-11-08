
package View.Sites.EditSite.A_History.ReportVerification.C_RVGrandStatsTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumGrandStatsTableRows {
    AVERAGE("mean", 0),
    STDDEV("std dev", 1),
    SUM("sum",2),
    MAX("max", 3),
    MIN("min", 4),
    COUNT("# not null", 5),
    TOTALROWS("num of rows", 6);

    private final String name;
    private final int row;

    EnumGrandStatsTableRows( String name, int row )
    {
        this.name = name;
        this.row = row;
        
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getRow(){
        return this.row;
    }
    
    static public EnumGrandStatsTableRows getEnumFromRowNumber( int row ){
        for( EnumGrandStatsTableRows avgsRowEnum : EnumGrandStatsTableRows.values()){
            if( avgsRowEnum.getRow() == row ){
                return avgsRowEnum;
            }
        }
        
        return null;
    }

    
    static public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for( EnumGrandStatsTableRows avgsRowEnum : EnumGrandStatsTableRows.values()){
          names.add(avgsRowEnum.getName() );
        }
        return names;
    }
    
}
