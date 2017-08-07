package org.cbioportal.persistence.mybatis;

import org.cbioportal.model.VariantCount;

import java.util.List;

public interface VariantCountMapper {
    
    List<VariantCount> fetchVariantCounts(String geneticProfileId, List<Integer> entrezGeneIds, List<String> keywords);
}
