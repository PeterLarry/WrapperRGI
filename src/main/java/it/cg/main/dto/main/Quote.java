package it.cg.main.dto.main;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mapfre.engines.rating.business.objects.wrapper.Coverage;
import com.mapfre.engines.rating.business.objects.wrapper.Figure;
import com.mapfre.engines.rating.business.objects.wrapper.RatingQuote;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.ICoverage;
import com.mapfre.engines.rating.common.base.intefaces.bo.proxy.IFigure;

public class Quote extends RatingQuote
{
    @JsonDeserialize(contentAs = Coverage.class)
    private List<ICoverage> coverages;
    @JsonDeserialize(contentAs = Figure.class)
    private List<IFigure> figures;

    @Override
    public List<ICoverage> getCoverages()
    {
        if (coverages == null)
            coverages = new ArrayList<>();

        return coverages;
    }

    @Override
    public void setCoverages(List<ICoverage> coverages)
    {
        this.coverages = coverages;
    }

    @Override
    public List<IFigure> getFigures()
    {
        if (figures == null)
            figures = new ArrayList<>();

        return figures;
    }

    @Override
    public void setFigures(List<IFigure> figures)
    {
        this.figures = figures;
    }
    
    
}


