
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.StatsTable;

import java.util.ArrayList;
import java.util.List;


public enum EnumTeslaStatsTableRows {
    AVERAGE("mean", 0),
    STDDEV("std dev", 1),
    SUM("sum",2),
    MAX("max", 3),
    MIN("min", 4),
    COUNT("# not null", 5),
    TOTALROWS("num of rows", 6);

    private final String name;
    private final int row;

    EnumTeslaStatsTableRows( String name, int row )
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
    
    static public EnumTeslaStatsTableRows getEnumFromRowNumber( int row ){
        for( EnumTeslaStatsTableRows avgsRowEnum : EnumTeslaStatsTableRows.values()){
            if( avgsRowEnum.getRow() == row ){
                return avgsRowEnum;
            }
        }
        
        return null;
    }

    
    static public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for( EnumTeslaStatsTableRows avgsRowEnum : EnumTeslaStatsTableRows.values()){
          names.add(avgsRowEnum.getName() );
        }
        return names;
    }
    
}
