
package View.Sites.NewSite.Address;

import java.util.ArrayList;
import java.util.List;


public enum EnumAddressTableRows {
    Street("Street", 0),
    City("City",1),
    State("State", 2),
    Country("Country", 3),
    PostCode("PostCode", 4),
    Timezone("Timezone", 5),
    Latitude("Latitude", 6),
    Longitude("Longitude",7);

    private final String name;
    private final int row;

    EnumAddressTableRows( String name, int row )
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
    
    static public EnumAddressTableRows getEnumFromRowNumber( int row ){
        for( EnumAddressTableRows rowEnum : EnumAddressTableRows.values()){
            if( rowEnum.getRow() == row ){
                return rowEnum;
            }
        }
        return null;
    }

    
    static public List<String> getNames(){
        List<String> names = new ArrayList<>();
        for( EnumAddressTableRows rowEnum : EnumAddressTableRows.values()){
          names.add(rowEnum.getName() );
        }
        return names;
    }
    
}