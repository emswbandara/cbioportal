package org.cbioportal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cbioportal.model.Gene;
import org.cbioportal.model.GeneGeneticData;
import org.cbioportal.model.GenesetCorrelation;
import org.cbioportal.model.GenesetGeneticData;
import org.cbioportal.model.GeneticProfile;
import org.cbioportal.model.GeneticProfile.DataType;
import org.cbioportal.service.GenesetDataService;
import org.cbioportal.service.GenesetService;
import org.cbioportal.service.GeneticDataService;
import org.cbioportal.service.GeneticProfileService;
import org.cbioportal.service.SampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class GenesetCorrelationServiceImplTest extends BaseServiceImplTest {

    @InjectMocks
    private GenesetCorrelationServiceImpl genesetCorrelationService;

    @Mock
    private GenesetDataService genesetDataService;
    @Mock
    private GeneticDataService geneticDataService;
    @Mock
    private GenesetService genesetService;
    @Mock
    private SampleService sampleService;
    @Mock
    private GeneticProfileService geneticProfileService;

    /**
     * This is executed n times, for each of the n test methods below:
     * @throws Exception 
     * @throws DaoException
     */
    @Before 
    public void setUp() throws Exception {

        //stub for geneset gene list:
        List<Gene> expectedGeneList = new ArrayList<>();
        Gene gene = new Gene();
        gene.setEntrezGeneId(1);
        expectedGeneList.add(gene);
        gene = new Gene();
        gene.setEntrezGeneId(2);
        expectedGeneList.add(gene);
        Mockito.when(genesetService.getGenesByGenesetId(GENESET_ID1))
            .thenReturn(expectedGeneList);

        //stub for geneset data list:
        List<GenesetGeneticData> expectedGenesetDataList = new ArrayList<GenesetGeneticData>();
        expectedGenesetDataList.add(getSimpleFlatGenesetDataItem(SAMPLE_ID1, GENESET_ID1, "0.2"));
        expectedGenesetDataList.add(getSimpleFlatGenesetDataItem(SAMPLE_ID2, GENESET_ID1, "0.499"));
        expectedGenesetDataList.add(getSimpleFlatGenesetDataItem(SAMPLE_ID1, GENESET_ID2, "0.89"));
        expectedGenesetDataList.add(getSimpleFlatGenesetDataItem(SAMPLE_ID2, GENESET_ID2, "-0.509"));
        Mockito.when(genesetDataService.fetchGenesetData(GENETIC_PROFILE_ID, Arrays.asList(SAMPLE_ID1, SAMPLE_ID2),
                Arrays.asList(GENESET_ID1, GENESET_ID2)))
            .thenReturn(expectedGenesetDataList);
        
        //dummy stubs (normally these will return different profiles, but for the test this is enough:
        GeneticProfile geneticProfile = new GeneticProfile();
        geneticProfile.setStableId(GENETIC_PROFILE_ID);
        Mockito.when(geneticProfileService.getGeneticProfilesReferredBy(GENETIC_PROFILE_ID))
            .thenReturn(Arrays.asList(geneticProfile));
        GeneticProfile zscoreGeneticProfile = new GeneticProfile();
        zscoreGeneticProfile.setStableId(GENETIC_PROFILE_ID);
        zscoreGeneticProfile.setDatatype(DataType.Z_SCORE);
        Mockito.when(geneticProfileService.getGeneticProfilesReferringTo(GENETIC_PROFILE_ID))
            .thenReturn(Arrays.asList(zscoreGeneticProfile));
        
        //stub for gene data list:
        List<GeneGeneticData> expectedGeneDataList = new ArrayList<GeneGeneticData>();
        expectedGeneDataList.add(getSimpleFlatGeneDataItem(SAMPLE_ID1, 1, "0.2"));
        expectedGeneDataList.add(getSimpleFlatGeneDataItem(SAMPLE_ID2, 1, "0.499"));
        expectedGeneDataList.add(getSimpleFlatGeneDataItem(SAMPLE_ID1, 2, "0.89"));
        expectedGeneDataList.add(getSimpleFlatGeneDataItem(SAMPLE_ID2, 2, "-0.509"));
        
        Mockito.when(geneticDataService.fetchGeneticData(GENETIC_PROFILE_ID, Arrays.asList(SAMPLE_ID1, SAMPLE_ID2),
                Arrays.asList(1, 2), "SUMMARY"))
            .thenReturn(expectedGeneDataList);
    }


    private GenesetGeneticData getSimpleFlatGenesetDataItem(String sampleStableId, String genesetId, String value){
    
        GenesetGeneticData item = new GenesetGeneticData();
        item.setGeneticProfileId(GENETIC_PROFILE_ID);
        item.setGenesetId(genesetId);
        item.setSampleId(sampleStableId);
        item.setValue(value);
        return item;
    }
    
    private GeneGeneticData getSimpleFlatGeneDataItem(String sampleStableId, int entrezGeneId, String value){
        
        GeneGeneticData item = new GeneGeneticData();
        item.setGeneticProfileId(GENETIC_PROFILE_ID);
        item.setEntrezGeneId(entrezGeneId);
        item.setSampleId(sampleStableId);
        item.setValue(value);
        return item;
    }
    
    @Test
    public void fetchCorrelatedGenes() throws Exception {

        List<GenesetCorrelation> result = genesetCorrelationService.fetchCorrelatedGenes(GENESET_ID1, GENETIC_PROFILE_ID,
                Arrays.asList(GENESET_ID1, GENESET_ID2), 0.3);

        //what we expect: TODO
        //Assert.assertEquals(4, result.size());
        //TODO

    }
}