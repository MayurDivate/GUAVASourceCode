/*
 * Copyright (C) 2017 mayurdivate
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package umac.guava.diff;

import java.util.Comparator;

/**
 *
 * @author mayurdivate
 */
public class ATACpeakChrStartComparator implements Comparator<ATACseqPeak>{

    @Override
    public int compare(ATACseqPeak o1, ATACseqPeak o2) {
        String chr1 = o1.getChromosome();
        String chr2 = o2.getChromosome();
        
        int chrCompare = chr1.compareToIgnoreCase(chr2);
        if(chrCompare != 0){
            return chrCompare;
        }
        else{
            Integer start1 = o1.getStart();
            Integer start2 = o2.getStart();
            int startCompare = start1.compareTo(start2);
            
            if(startCompare == 0){
                Integer end1 = o1.getEnd();
                Integer end2 = o2.getEnd();
                return end1.compareTo(end2);
            }
            else{
                return startCompare;
            }
        }
        
    }
    

    
}
