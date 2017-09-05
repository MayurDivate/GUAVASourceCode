/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;

/**
 *
 * @author mayurdivate
 */
public class AnalysisResultWriter {
    
    private AlignmentResult alignmentResult;
    private FilteredAlignment alignmentFilteringResult;
    private File insertSizePlot ;
    private ChIPseeker chipSeeker;
    private int macs2PeakCount;

    /**
     * @return the alignmentResult
     */
    public AlignmentResult getAlignmentResult() {
        return alignmentResult;
    }

    /**
     * @param alignmentResult the alignmentResult to set
     */
    public void setAlignmentResult(AlignmentResult alignmentResult) {
        this.alignmentResult = alignmentResult;
    }

    /**
     * @return the alignmentFilteringResult
     */
    public FilteredAlignment getAlignmentFilteringResult() {
        return alignmentFilteringResult;
    }

    /**
     * @param alignmentFilteringResult the alignmentFilteringResult to set
     */
    public void setAlignmentFilteringResult(FilteredAlignment alignmentFilteringResult) {
        this.alignmentFilteringResult = alignmentFilteringResult;
    }

    /**
     * @return the insertSizePlot
     */
    public File getInsertSizePlot() {
        return insertSizePlot;
    }

    /**
     * @param insertSizePlot the insertSizePlot to set
     */
    public void setInsertSizePlot(File insertSizePlot) {
        this.insertSizePlot = insertSizePlot;
    }

    /**
     * @return the chipSeeker
     */
    public ChIPseeker getChipSeeker() {
        return chipSeeker;
    }

    /**
     * @param chipSeeker the chipSeeker to set
     */
    public void setChipSeeker(ChIPseeker chipSeeker) {
        this.chipSeeker = chipSeeker;
    }

    /**
     * @return the macs2PeakCount
     */
    public int getMacs2PeakCount() {
        return macs2PeakCount;
    }

    /**
     * @param macs2PeakCount the macs2PeakCount to set
     */
    public void setMacs2PeakCount(int macs2PeakCount) {
        this.macs2PeakCount = macs2PeakCount;
    }
    
    
    
    
    
    
}
