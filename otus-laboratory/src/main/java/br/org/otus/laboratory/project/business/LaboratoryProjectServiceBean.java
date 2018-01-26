package br.org.otus.laboratory.project.business;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.aliquot.AliquoteDescriptor;
import br.org.otus.laboratory.configuration.collect.aliquot.CenterAliquot;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.businnes.ExamLotService;
import br.org.otus.laboratory.project.transportation.business.TransportationLotService;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Stateless
public class LaboratoryProjectServiceBean implements LaboratoryProjectService{

    @Inject
    TransportationLotService transportationLotService;

    @Inject
    ExamLotService examLotService;

    @Inject
    LaboratoryConfigurationService laboratoryConfigurationService;

    @Override
    public List<WorkAliquot> getAllAliquots() throws DataNotFoundException {
        return transportationLotService.getAliquots();
    }

    @Override
    public LinkedHashSet<AliquoteDescriptor> getAvailableExams(String center) throws DataNotFoundException {
        LinkedHashSet<AliquoteDescriptor> aliquotDescriptors = new LinkedHashSet<>();

        HashSet<String> support = new HashSet<>();

        List<CenterAliquot> aliquotDescriptorsByCenter = laboratoryConfigurationService.getAliquotDescriptorsByCenter(center);
        HashSet<String> aliquotsDescriptorsInTransportationLots = examLotService.getAliquotsDescriptorsInTransportationLots();

        for (CenterAliquot centerAliquot : aliquotDescriptorsByCenter) {
            String name = centerAliquot.getName();
            if (support.add(name)){
                aliquotDescriptors.add(laboratoryConfigurationService.getAliquotDescriptorsByName(name));
            }
        }

        for (String aliquotsDescriptorName : aliquotsDescriptorsInTransportationLots) {
            if (support.add(aliquotsDescriptorName)){
                aliquotDescriptors.add(laboratoryConfigurationService.getAliquotDescriptorsByName(aliquotsDescriptorName));
            }
        }
        return aliquotDescriptors;
    }


}
