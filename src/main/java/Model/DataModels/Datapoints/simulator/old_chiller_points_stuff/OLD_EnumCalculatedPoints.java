/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DataModels.Datapoints.simulator.old_chiller_points_stuff;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hal
 */
public enum OLD_EnumCalculatedPoints {

    kwPerTon("kwPerTon", 0 ),
    totalkW("totalkW", 1),
    kWh("kWh", 2),
    Totalton("Totalton", 3),
    ton("ton", 4),
    TotalCapacity("TotalCapacity", 5),
    tonHours("tonHours", 6),
    totalLoad("totalLoad", 7),
    CHxrunhours("CHx run hours", 8),
    Chillerkwhusage("Chiller kwh usage", 9),
    OldkW("OldkW", 10),
    Calc_OldkWTon("Calc_OldkWTon", 11),
    OldCo2Cost("OldCo2Cost", 12),
    OldDollarCost("OldDollarCost", 13),
    DollarCost("DollarCost", 14),
    CO2Produced("CO2Produced", 15),
    kWDelta("kW Delta", 16),
    DollarsSaved("Dollars Saved", 17),
    EnergySaved("Energy Saved", 18),
    CO2Saved("CO2 Saved", 19),
    PercentOptimized("PercentOptimized", 20),
    PercentNotOptimized("PercentNotOptimized", 21),
    PercentNotOperating("PercentNotOperating", 22),
    PartiallyOptimized("PartiallyOptimized", 23),
    NotOptimized("NotOptimized", 24),
    FullyOptimized("FullyOptimized", 25);

    private String name;
    private int dropDownIndex;

    OLD_EnumCalculatedPoints(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getName() {
        return this.name;
    }

    public int getDropDownIndex() {
        return this.dropDownIndex;
    }

    static public List<String> getLevelNames() {
        List<String> names = new ArrayList<>();
        for (OLD_EnumCalculatedPoints res : OLD_EnumCalculatedPoints.values()) {
            names.add(res.getName());
        }
        return names;
    }

    static public OLD_EnumCalculatedPoints getLevelFromName(String name) {
        for (OLD_EnumCalculatedPoints res : OLD_EnumCalculatedPoints.values()) {
            if (res.getName().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}
